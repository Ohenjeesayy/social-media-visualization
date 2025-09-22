package prj5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.Random;
import java.util.function.Function;

/**
 * Swing version of GUIInfluencerAnalytics (no cs2 dependency).
 * Same logic: month toggle, traditional vs reach, sort by name or rate, N/A handling.
 */
public class GUIInfluencerAnalytics {

    // ---- Layout settings ----
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final String WINDOW_TITLE = "Arshias - Princeg - Sakdipong - Zaybish";

    private static final int MARGIN = 30;
    private static final int TOP_TEXT_LINES = 3;     // month + title + sort-mode
    private static final int TOP_TEXT_LINE_H = 22;   // px per line
    private static final int BASE_Y_OFFSET = 60;     // bottom padding for labels
    private static final int MAX_BAR_HEIGHT_BUFFER = 200;

    // ---- Data & state ----
    private final SinglyLinkedList<Influencer> influencers;
    private final EngagementCalculator calculator;
    private boolean showingTraditional = true;
    private String selectedMonth = "FirstQuarter";
    private String currentSortMode = "Unsorted";

    // ---- Swing ----
    private final JFrame frame;
    private final JPanel north;
    private final JPanel west;
    private final JPanel south;
    private final GraphPanel graph;
    private final Random rand = new Random(42); // stable colors

    public GUIInfluencerAnalytics(SinglyLinkedList<Influencer> influencerList) {
        this.influencers = influencerList;
        this.calculator = new EngagementCalculator();

        frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());

        // North controls (sort + quit)
        north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton sortNameBtn = new JButton("Sort by Channel Name");
        JButton sortRateBtn = new JButton("Sort by Engagement Rate");
        JButton quitBtn = new JButton("Quit");
        north.add(sortNameBtn);
        north.add(sortRateBtn);
        north.add(quitBtn);

        // West controls (metric toggle)
        west = new JPanel();
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
        JButton traditionalBtn = new JButton("Traditional Engagement");
        JButton reachBtn = new JButton("Reach Engagement");
        traditionalBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        reachBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        west.add(traditionalBtn);
        west.add(Box.createVerticalStrut(8));
        west.add(reachBtn);

        // South controls (months)
        south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton januaryBtn = new JButton("January");
        JButton februaryBtn = new JButton("February");
        JButton marchBtn = new JButton("March");
        JButton firstQuarterBtn = new JButton("First Quarter");
        south.add(januaryBtn);
        south.add(februaryBtn);
        south.add(marchBtn);
        south.add(firstQuarterBtn);

        // Center graph (single panel that paints text + bars cleanly)
        graph = new GraphPanel();

        frame.add(north, BorderLayout.NORTH);
        frame.add(west, BorderLayout.WEST);
        frame.add(south, BorderLayout.SOUTH);
        frame.add(graph, BorderLayout.CENTER);

        // Actions
        traditionalBtn.addActionListener(this::showTraditional);
        reachBtn.addActionListener(this::showReach);
        sortNameBtn.addActionListener(this::sortByName);
        sortRateBtn.addActionListener(this::sortByRate);
        quitBtn.addActionListener(e -> System.exit(0));
        januaryBtn.addActionListener(e -> { selectedMonth = "January"; redraw(); });
        februaryBtn.addActionListener(e -> { selectedMonth = "February"; redraw(); });
        marchBtn.addActionListener(e -> { selectedMonth = "March"; redraw(); });
        firstQuarterBtn.addActionListener(e -> { selectedMonth = "FirstQuarter"; redraw(); });

        redraw();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ----- Actions -----
    private void showTraditional(ActionEvent e) { showingTraditional = true; redraw(); }
    private void showReach(ActionEvent e)       { showingTraditional = false; redraw(); }

    private void sortByName(ActionEvent e) {
        influencers.insertionSort((a, b) ->
            a.getChannelName().compareToIgnoreCase(b.getChannelName()));
        currentSortMode = "Sorted by Channel Name";
        redraw();
    }

    private void sortByRate(ActionEvent e) {
        final Function<Influencer, Double> metric = this::getScore;
        // sort DESC by metric; null (N/A) always last
        influencers.insertionSort((a, b) -> {
            Double va = metric.apply(a), vb = metric.apply(b);
            if (va == null && vb == null) return 0;
            if (va == null) return 1;
            if (vb == null) return -1;
            return Double.compare(vb, va);
        });
        currentSortMode = (showingTraditional ? "Sorted by Traditional Engagement (" : "Sorted by Reach Engagement (")
                + selectedMonth + ")";
        redraw();
    }

    private void redraw() {
        frame.setTitle(WINDOW_TITLE);
        graph.repaint();
    }

    // ----- Metric helpers -----
    private Double getScore(Influencer inf) {
        if ("FirstQuarter".equals(selectedMonth)) {
            return showingTraditional
                ? calculator.getQuarterTraditionalEngagement(inf)
                : calculator.getQuarterReachEngagement(inf);
        } else {
            return showingTraditional
                ? calculator.getMonthlyTraditionalEngagement(inf, selectedMonth)
                : calculator.getMonthlyReachEngagement(inf, selectedMonth);
        }
    }

    // ----- Graph panel that paints text + bars -----
    private class GraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g0) {
            super.paintComponent(g0);
            Graphics2D g = (Graphics2D) g0;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Header text
            String title = showingTraditional ? "Traditional Engagement Rate" : "Reach Engagement Rate";
            g.setColor(Color.BLACK);
            int topY = MARGIN;
            g.setFont(g.getFont().deriveFont(Font.BOLD, 14f));
            g.drawString(selectedMonth, MARGIN, topY);
            g.drawString(title, MARGIN, topY + TOP_TEXT_LINE_H);
            g.drawString(currentSortMode, MARGIN, topY + 2 * TOP_TEXT_LINE_H);

            int topArea = MARGIN + TOP_TEXT_LINES * TOP_TEXT_LINE_H + 10;

            // Bars region
            int availableW = w - 2 * MARGIN;
            int availableH = h - topArea - BASE_Y_OFFSET;
            if (availableH < 50) return; // too small to draw

            int n = Math.max(1, influencers.size());
            // dynamic bar width & spacing so it fills neatly
            int barW = Math.max(24, Math.min(60, availableW / (n * 2)));
            int spacing = (availableW - n * barW) / Math.max(1, n + 1);

            int baseY = h - BASE_Y_OFFSET;

            // find max score for scaling
            double maxVal = 0.0;
            for (int i = 0; i < influencers.size(); i++) {
                Double s = getScore(influencers.get(i));
                double v = (s == null) ? 0.0 : s;
                if (v > maxVal) maxVal = v;
            }
            if (maxVal <= 0) maxVal = 1;

            int usableBarH = Math.min(availableH, h - MAX_BAR_HEIGHT_BUFFER);

            // draw bars
            g.setFont(g.getFont().deriveFont(12f));
            for (int i = 0; i < influencers.size(); i++) {
                Influencer inf = influencers.get(i);
                Double sObj = getScore(inf);
                double sVal = (sObj == null) ? 0.0 : sObj;

                int x = MARGIN + spacing * (i + 1) + barW * i;
                int bh = (int) Math.round((sVal / maxVal) * usableBarH);
                bh = Math.max(2, bh);

                // bar
                g.setColor(Color.getHSBColor((i * 0.12f) % 1f, 0.55f, 0.85f));
                int y = baseY - bh;
                g.fillRect(x, y, barW, bh);
                g.setColor(Color.DARK_GRAY);
                g.drawRect(x, y, barW, bh);

                // channel label
                String name = inf.getChannelName() == null ? "" : inf.getChannelName();
                drawCentered(g, name, x, barW, baseY + 14, Color.BLACK);

                // score label
                String txt = (sObj == null) ? "N/A" : String.format(Locale.US, "%.1f", sObj);
                Color color = (sObj == null) ? new Color(180, 0, 0) : new Color(0, 70, 200);
                drawCentered(g, txt, x, barW, baseY + 30, color);
            }
        }

        private void drawCentered(Graphics2D g, String text, int barX, int barW, int baselineY, Color fg) {
            FontMetrics fm = g.getFontMetrics();
            int textW = fm.stringWidth(text);
            int x = barX + (barW - textW) / 2;
            // light background halo for readability
            g.setColor(Color.WHITE);
            g.fillRect(x - 2, baselineY - fm.getAscent(), textW + 4, fm.getHeight());
            g.setColor(fg);
            g.drawString(text, x, baselineY);
        }
    }

    // Optional demo main (remove in production)
    public static void main(String[] args) {
        SinglyLinkedList<Influencer> demo = new SinglyLinkedList<>();
        Influencer a = new Influencer("userA", "Alpha", "US", "tech");
        a.addData(new InfluencerData("January", 100, 5, 1000, 12, 9000));
        a.addData(new InfluencerData("February", 120, 6, 1100, 10, 9500));
        a.addData(new InfluencerData("March", 130, 7, 1200, 9, 9700));
        demo.add(a);

        Influencer b = new Influencer("userB", "Beta", "US", "fashion");
        b.addData(new InfluencerData("January", 80, 4, 900, 20, 8000));
        b.addData(new InfluencerData("February", 90, 5, 950, 18, 8200));
        demo.add(b);

        Influencer c = new Influencer("userC", "GammaLongChannel", "CA", "gaming");
        c.addData(new InfluencerData("March", 200, 8, 2500, 30, 15000));
        demo.add(c);

        new GUIInfluencerAnalytics(demo);
    }
}
