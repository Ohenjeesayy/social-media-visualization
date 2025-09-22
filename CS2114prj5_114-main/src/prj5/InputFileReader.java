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


import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Reads influencer data from a CSV file and constructs the list of influencers.
 * Handles empty cells by replacing missing string values with "None"
 * and missing numeric values with 0.
 * 
 * @author Arshia Saeidifar
 * @version 2025.04.22
 */
public class InputFileReader {
    private SinglyLinkedList<Influencer> influencerList;

    /**
     * Constructor: initializes the list and reads from file.
     * 
     * @param filePath
     *            path to the CSV file
     */
    public InputFileReader(String filePath) {
        influencerList = new SinglyLinkedList<>();
        readCSV(filePath);
    }


    /**
     * Returns the list of influencers read from the file.
     * 
     * @return list of influencers
     */
    public SinglyLinkedList<Influencer> getInfluencerList() {
        return influencerList;
    }


    /**
     * Reads the file line-by-line and constructs Influencer and InfluencerData
     * objects.
     * 
     * @param filePath
     *            the path to the CSV file
     */
    private void readCSV(String filePath) {
        try {
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(new File(filePath));
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip header
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",", -1); // Use comma for CSV

                if (!isValid(fields)) {
                    continue;
                }

                String month = safeString(fields[0]);
                String username = safeString(fields[1]);
                String channelName = safeString(fields[2]);
                String country = safeString(fields[3]);
                String mainTopic = safeString(fields[4]);

                int likes = parseOrDefault(fields[5]);
                int posts = parseOrDefault(fields[6]);
                int followers = parseOrDefault(fields[7]);
                int comments = parseOrDefault(fields[8]);
                int views = parseOrDefault(fields[9]);

                Influencer influencer = findOrCreateInfluencer(username,
                    channelName, country, mainTopic);
                InfluencerData data = new InfluencerData(month, likes, posts,
                    followers, comments, views);
                influencer.getDataList().add(data);
            }

            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("CSV file not found: " + filePath);
        }
    }


    /**
     * Parses an integer field. If empty or invalid, returns 0.
     */
    private int parseOrDefault(String field) {
        try {
            return Integer.parseInt(field.trim());
        }
        catch (Exception e) {
            return 0;
        }
    }


    /**
     * Converts a string field to "None" if empty and trims it.
     */
    private String safeString(String field) {
        return (field == null || field.trim().isEmpty())
            ? "None"
            : field.trim();
    }


    /**
     * Checks if a CSV row is valid: must have 10 fields and a valid month.
     */
    private boolean isValid(String[] fields) {
        if (fields.length != 10) {
            return false;
        }

        String month = safeString(fields[0]);
        return isValidMonth(month);
    }


    /**
     * Checks if the given month is one of the 12 valid calendar months.
     */
    private boolean isValidMonth(String month) {
        return month.equals("January") || month.equals("February") || month
            .equals("March") || month.equals("April") || month.equals("May")
            || month.equals("June") || month.equals("July") || month.equals(
                "August") || month.equals("September") || month.equals(
                    "October") || month.equals("November") || month.equals(
                        "December");
    }


    /**
     * Finds an existing Influencer by username and channel,
     * or creates one if not found.
     */
    private Influencer findOrCreateInfluencer(
        String username,
        String channelName,
        String country,
        String mainTopic) {
        for (int i = 0; i < influencerList.size(); i++) {
            Influencer inf = influencerList.get(i);
            if (inf.getUsername().equals(username) && inf.getChannelName()
                .equals(channelName)) {
                return inf;
            }
        }

        Influencer newInfluencer = new Influencer(username, channelName,
            country, mainTopic);
        influencerList.add(newInfluencer);
        return newInfluencer;
    }


    /**
     * Debugging method to print all influencer data.
     */
    public void printAllInfluencerData() {
        for (int i = 0; i < influencerList.size(); i++) {
            Influencer inf = influencerList.get(i);
            System.out.println("Influencer: " + inf.getUsername()
                + " | Channel: " + inf.getChannelName());
            for (int j = 0; j < inf.getDataList().size(); j++) {
                InfluencerData data = inf.getDataList().get(j);
                System.out.println("  Month: " + data.getMonth() + ", Likes: "
                    + data.getLikes() + ", Posts: " + data.getPosts()
                    + ", Followers: " + data.getFollowers() + ", Comments: "
                    + data.getComments() + ", Views: " + data.getViews());
            }
            System.out.println();
        }
    }


    /**
     * Debugging method to print all data with engagement rates.
     */
    public void printAllInfluencerDataWithRates() {
        EngagementCalculator calculator = new EngagementCalculator();
        DecimalFormat df = new DecimalFormat("#.#");

        System.out.println("=== Influencer Data + Engagement Rates ===");

        for (int i = 0; i < influencerList.size(); i++) {
            Influencer inf = influencerList.get(i);
            System.out.println("Influencer: " + inf.getUsername()
                + " | Channel: " + inf.getChannelName() + " | Country: " + inf
                    .getCountry() + " | Main Topic: " + inf.getMainTopic());

            for (int j = 0; j < inf.getDataList().size(); j++) {
                InfluencerData data = inf.getDataList().get(j);
                System.out.println("  Month: " + data.getMonth() + ", Likes: "
                    + data.getLikes() + ", Posts: " + data.getPosts()
                    + ", Followers: " + data.getFollowers() + ", Comments: "
                    + data.getComments() + ", Views: " + data.getViews());
            }

            double traditional = calculator.getQuarterTraditionalEngagement(
                inf);
            Double reach = calculator.getQuarterReachEngagement(inf);

            System.out.print("  Traditional Engagement (Q1): ");
            System.out.println(df.format(traditional));

            System.out.print("  Reach Engagement (Q1): ");
            System.out.println((reach == null) ? "N/A" : df.format(reach));
            System.out.println();
        }
    }
}
