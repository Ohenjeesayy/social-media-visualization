package prj5;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * The ProjectRunner class serves as the entry point for the Influencer Analytics application.
 * It reads influencer data from a CSV file, processes engagement metrics,
 * and provides output either to the console or through a graphical user interface (GUI).
 * 
 * Usage:
 * - Run with a filename as an argument to load a specific dataset.
 * - Without arguments, it defaults to "SampleInput1_2023.csv".
 * 
 * @version Apr 28, 2025
 */
public class ProjectRunner {

    // ----------------------------------------------------------
    /**
     * Launches the application
     * 
     * -If showConsole you can see the result in the console.
     * =If showGUI a window will be created and show the result. 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
       //====   Choosing between GUI and Cnsole
        boolean showConsole = true; // Set to true to enable console output
        boolean showGUI = true;     // Set to true to enable GUI output 
    
        
        
        
        String filename;
        if (args.length > 0) {
            filename = args[0];
        } else {
            filename = "SampleInput3_2023.csv";
        }


        InputFileReader reader = new InputFileReader(filename);
        SinglyLinkedList<Influencer> influencers = reader.getInfluencerList();

        if (showConsole) {
            EngagementCalculator calculator = new EngagementCalculator();
            DecimalFormat df = new DecimalFormat("#.#");

            // Traditional Engagement - Sorted by Channel Name
            influencers.insertionSort(new ComparatorByChannelName());
            for (int i = 0; i < influencers.size(); i++) {
                Influencer inf = influencers.get(i);
                Double trad = calculator.getQuarterTraditionalEngagement(inf);

                System.out.println(inf.getChannelName());
                System.out.println("traditional: " + (trad == null ? "N/A" : df.format(trad)));
                System.out.println("==========");
            }

            System.out.println("**********");
            System.out.println("**********");

            // Reach Engagement - Sorted by Engagement Rate (First Quarter)
            influencers.insertionSort(new ComparatorByReachEngagement("FirstQuarter"));
            for (int i = 0; i < influencers.size(); i++) {
                Influencer inf = influencers.get(i);
                Double reach = calculator.getQuarterReachEngagement(inf);

                System.out.println(inf.getChannelName());
                System.out.println("reach: " + (reach == null ? "N/A" : df.format(reach)));
                System.out.println("==========");
            }
        }

        if (showGUI) {
            @SuppressWarnings("unused")
            GUIInfluencerAnalytics window = new GUIInfluencerAnalytics(influencers);
        }
    }
}
