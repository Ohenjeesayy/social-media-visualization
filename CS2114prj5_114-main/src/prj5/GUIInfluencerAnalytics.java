package prj5;
//Virginia Tech Honor Code Pledge:
//
//As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal,
//nor will I accept the actions of those who do.
//Arshia Saeidifar arshias@vt.edu
//Prince princeg@vt.edu
//Sakdipong Rodphong Sakdipong@vt.edu
//Zaybish  Tariq Zaybish@vt.edu



import cs2.Button;
import cs2.Shape;
import cs2.TextShape;
import cs2.Window;
import cs2.WindowSide;
import java.awt.Color;
import java.util.Random;

/**
 * The window class displays influencer analytics
 * in a graphical window. It supports sorting by name or engagement rate
 * for selected months or quarters and displays traditional or reach
 * engagement scores. Missing data is represented as "N/A".

 * 
 * @author Prince
 * @author Arshia Saeidifar
 * @version Apr 28, 2025
 */
public class GUIInfluencerAnalytics {

    // Constants for window dimensions and layout
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final String WINDOW_TITLE = "Arshias - Princeg - Sakdipong - Zaybish";

    private static final int MARGIN = 30;
    private static final int TITLE_Y_POSITION = 10;
    private static final int BAR_SCALING_FACTOR = 3;
    private static final int MAX_BAR_HEIGHT_BUFFER = 200;
    private static final int CUSTOM_BAR_WIDTH = 40;
    private static final int BASE_Y_OFFSET = 60;

    // GUI components and data structures
    private Window window;
    private SinglyLinkedList<Influencer> influencers;
    private EngagementCalculator calculator;
    private boolean showingTraditional;
    private String selectedMonth;
    private Random rand;
    private String currentSortMode;

    /**
     * Constructs a GUIInfluencerAnalytics object with the specified list of influencers.
     *
     * @param influencerList the list of influencers to display
     */
    public GUIInfluencerAnalytics(SinglyLinkedList<Influencer> influencerList) {
        this.influencers = influencerList;
        this.calculator = new EngagementCalculator();
        this.showingTraditional = true;
        this.selectedMonth = "FirstQuarter";
        this.rand = new Random();
        this.currentSortMode = "Unsorted";

        window = new Window(WINDOW_TITLE);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        initializeButtons();
        redraw();
    }

    /**
     * Initializes the buttons in the GUI and sets their respective actions.
     */
    private void initializeButtons() {
        Button traditionalBtn = new Button("Traditional Engagement");
        Button reachBtn = new Button("Reach Engagement");
        Button sortNameBtn = new Button("Sort by Channel Name");
        Button sortRateBtn = new Button("Sort by Engagement Rate");
        Button quitBtn = new Button("Quit");
        Button januaryBtn = new Button("January");
        Button februaryBtn = new Button("February");
        Button marchBtn = new Button("March");
        Button firstQuarterBtn = new Button("First Quarter");

        window.addButton(traditionalBtn, WindowSide.WEST);
        window.addButton(reachBtn, WindowSide.WEST);
        window.addButton(sortNameBtn, WindowSide.NORTH);
        window.addButton(sortRateBtn, WindowSide.NORTH);
        window.addButton(quitBtn, WindowSide.NORTH);
        window.addButton(januaryBtn, WindowSide.SOUTH);
        window.addButton(februaryBtn, WindowSide.SOUTH);
        window.addButton(marchBtn, WindowSide.SOUTH);
        window.addButton(firstQuarterBtn, WindowSide.SOUTH);

        traditionalBtn.onClick(this, "showTraditional");
        reachBtn.onClick(this, "showReach");
        sortNameBtn.onClick(this, "sortByName");
        sortRateBtn.onClick(this, "sortByRate");
        quitBtn.onClick(this, "quit");
        januaryBtn.onClick(this, "showJanuary");
        februaryBtn.onClick(this, "showFebruary");
        marchBtn.onClick(this, "showMarch");
        firstQuarterBtn.onClick(this, "showFirstQuarter");
    }

    /**
     * Displays traditional engagement metrics.
     *
     * @param b the button that was clicked
     */
    public void showTraditional(Button b) {
        showingTraditional = true;
        redraw();
    }

    /**
     * Displays reach engagement metrics.
     *
     * @param b the button that was clicked
     */
    public void showReach(Button b) {
        showingTraditional = false;
        redraw();
    }

    /**
     * Sorts the influencer list by channel name.
     *
     * @param b the button that was clicked
     */
    public void sortByName(Button b) {
        influencers.insertionSort(new ComparatorByChannelName());
        currentSortMode = "Sorted by Channel Name";
        redraw();
    }

    /**
     * Sorts the influencer list by engagement rate.
     *
     * @param b the button that was clicked
     */
    public void sortByRate(Button b) {
        if (showingTraditional) {
            influencers.insertionSort(new ComparatorByTraditionalEngagement(selectedMonth));
            currentSortMode = "Sorted by Traditional Engagement (" + selectedMonth + ")";
        } else {
            influencers.insertionSort(new ComparatorByReachEngagement(selectedMonth));
            currentSortMode = "Sorted by Reach Engagement (" + selectedMonth + ")";
        }
        redraw();
    }

    /**
     * Exits the application.
     *
     * @param b the button that was clicked
     */
    public void quit(Button b) {
        System.exit(0);
    }

    /**
     * Displays data for January.
     *
     * @param b the button that was clicked
     */
    public void showJanuary(Button b) {
        selectedMonth = "January";
        redraw();
    }

    /**
     * Displays data for February.
     *
     * @param b the button that was clicked
     */
    public void showFebruary(Button b) {
        selectedMonth = "February";
        redraw();
    }

    /**
     * Displays data for March.
     *
     * @param b the button that was clicked
     */
    public void showMarch(Button b) {
        selectedMonth = "March";
        redraw();
    }

    /**
     * Displays data for the first quarter.
     *
     * @param b the button that was clicked
     */
    public void showFirstQuarter(Button b) {
        selectedMonth = "FirstQuarter";
        redraw();
    }

    /**
     * Redraws the GUI components based on the current state.
     */
    private void redraw() {
        window.removeAllShapes();

        String titleText = showingTraditional ? "Traditional Engagement Rate" : "Reach Engagement Rate";
        TextShape title = new TextShape(MARGIN, TITLE_Y_POSITION + 30, titleText);
        title.setBackgroundColor(Color.WHITE);
        title.setForegroundColor(Color.BLACK);
        window.addShape(title);

        TextShape sortLabel = new TextShape(MARGIN, TITLE_Y_POSITION + 60, currentSortMode);
        sortLabel.setBackgroundColor(Color.WHITE);
        sortLabel.setForegroundColor(Color.BLACK);
        window.addShape(sortLabel);

        TextShape monthLabel = new TextShape(MARGIN, TITLE_Y_POSITION, selectedMonth);
        monthLabel.setBackgroundColor(Color.WHITE);
        monthLabel.setForegroundColor(Color.BLACK);
        window.addShape(monthLabel);

        int panelWidth = window.getGraphPanelWidth();
        int panelHeight = window.getGraphPanelHeight();
        int availableWidth = panelWidth - 2 * MARGIN;
        int numberOfBars = Math.max(1, influencers.size());
        int spacing = availableWidth / numberOfBars;
        int startX = MARGIN;
        int baseY = panelHeight - BASE_Y_OFFSET;

        for (int i = 0; i < influencers.size(); i++) {
            Influencer inf = influencers.get(i);
            double value = calculateEngagementValue(inf);

            int barHeight = Math.max(2, (int)(value * BAR_SCALING_FACTOR));
            barHeight = Math.min(barHeight, panelHeight - MAX_BAR_HEIGHT_BUFFER);

            int xPosition = startX + spacing * i + (spacing - CUSTOM_BAR_WIDTH) / 2;
            int yPosition = baseY - barHeight;

            Shape bar = new Shape(xPosition, yPosition, CUSTOM_BAR_WIDTH, barHeight);
            bar.setBackgroundColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            window.addShape(bar);

            TextShape label = new TextShape(xPosition, baseY + 5, inf.getChannelName());
            label.setBackgroundColor(Color.WHITE);
            label.setForegroundColor(Color.BLACK);
            window.addShape(label);

            String formattedValue;
            if (selectedMonth.equals("FirstQuarter")) {
                if (showingTraditional) {
                    Double score = calculator.getQuarterTraditionalEngagement(inf);
                    if (score == null) {
                        formattedValue = "N/A";
                    } else {
                        formattedValue = String.format("%.1f", score);
                    }
                } else {
                    Double score = calculator.getQuarterReachEngagement(inf);
                    if (score == null) {
                        formattedValue = "N/A";
                    } else {
                        formattedValue = String.format("%.1f", score);
                    }
                }
            } 
            else {
                Double score;
                if (showingTraditional) {
                    score = calculator.getMonthlyTraditionalEngagement(inf, selectedMonth);
                } else {
                    score = calculator.getMonthlyReachEngagement(inf, selectedMonth);
                }

                if (score == null) {
                    formattedValue = "N/A";
                } else {
                    formattedValue = String.format("%.1f", score);
                }
            }


            TextShape score = new TextShape(xPosition, baseY + 20, formattedValue);
            score.setBackgroundColor(Color.WHITE);
            score.setForegroundColor(formattedValue.equals("N/A") ? Color.RED : Color.BLUE);
            window.addShape(score);
        }
    }

    /**
     * Calculates the engagement value for a given influencer based on the current settings.
     *
     * @param inf the influencer
     * @return the engagement value, or 0.0 if data is unavailable
     */
    private double calculateEngagementValue(Influencer inf) {
        Double val = null;

        if (selectedMonth.equals("FirstQuarter")) {
            if (showingTraditional) {
                val = calculator.getQuarterTraditionalEngagement(inf);
            } else {
                val = calculator.getQuarterReachEngagement(inf);
            }
        } else {
            if (showingTraditional) {
                val = calculator.getMonthlyTraditionalEngagement(inf, selectedMonth);
            } else {
                val = calculator.getMonthlyReachEngagement(inf, selectedMonth);
            }
        }


        if (val == null) {
            return 0.0;
        }
        return val;

    }
}
