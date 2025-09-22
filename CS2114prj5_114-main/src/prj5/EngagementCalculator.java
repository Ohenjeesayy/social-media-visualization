package prj5;

// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal,
// nor will I accept the actions of those who do.

/**
 * The EngagementCalculator class provides methods to calculate
 * various engagement metrics for influencers, including:
 * 
 *  Traditional Engagement Rate: 
 *       ((likes + comments) from Jan–Mar) / followers in March * 100
 *  Reach Engagement Rate: 
 *       ((likes + comments) from Jan–Mar) / total views Jan–Mar * 100
 *  Monthly Traditional Engagement: 
 *       ((likes + comments) for selected month) / followers that month * 100
 *  Monthly Reach Engagement: 
 *       ((likes + comments) for selected month) / views that month * 100
 * 
 * 
 * These metrics are useful in comparing how actively audiences engage with an
 * influencer’s content, relative to their reach and audience size.
 * 
 * @author zaybisht
 * @version 2025-04-23
 */
public class EngagementCalculator {

    /**
     * Calculates the traditional engagement rate 
     * for the first quarter (Jan–Mar).
     * Uses the latest available follower count from the data in these months.
     * 
     * @param inf the influencer whose data is used
     * @return traditional engagement rate as a percentage, or {@code null} if
     *         follower data is missing
     */
    public Double getQuarterTraditionalEngagement(Influencer inf) {
        int totalLikes = 0;
        int totalComments = 0;
        Integer lastAvailableFollowers = null;

        for (int i = 0; i < inf.getDataList().size(); i++) {
            InfluencerData data = inf.getDataList().get(i);
            String month = data.getMonth();

            if (month.equalsIgnoreCase("January") ||
                month.equalsIgnoreCase("February") ||
                month.equalsIgnoreCase("March")) {

                totalLikes += data.getLikes();
                totalComments += data.getComments();

                if (data.getFollowers() > 0) {
                    lastAvailableFollowers = data.getFollowers();
                }
            }
        }

        if (lastAvailableFollowers == null) {
            return null;
        }

        return ((double)
            (totalLikes + totalComments) / lastAvailableFollowers) * 100;
    }

    /**
     * Calculates the reach engagement rate for the first quarter (Jan–Mar).
     * 
     * @param inf the influencer whose data is used
     * @return reach engagement rate as a percentage, or {@code null} if total
     *         views across the quarter are zero
     */
    public Double getQuarterReachEngagement(Influencer inf) {
        int totalLikes = 0;
        int totalComments = 0;
        int totalViews = 0;

        for (int i = 0; i < inf.getDataList().size(); i++) {
            InfluencerData data = inf.getDataList().get(i);
            String month = data.getMonth();

            if (month.equalsIgnoreCase("January") ||
                month.equalsIgnoreCase("February") ||
                month.equalsIgnoreCase("March")) {
                totalLikes += data.getLikes();
                totalComments += data.getComments();
                totalViews += data.getViews();
            }
        }

        if (totalViews == 0) {
            return null;
        }

        return ((double)
            (totalLikes + totalComments) / totalViews) * 100;
    }

    /**
     * Returns the traditional engagement rate for a specific month.
     * Formula: ((likes + comments) / followers) * 100
     * 
     * @param inf   the influencer
     * @param month the month to compute engagement for
     * @return engagement rate as a percentage, or {@code null} if follower
     *         data is zero or month not found
     */
    public Double 
        getMonthlyTraditionalEngagement(Influencer inf, String month) {
        for (int i = 0; i < inf.getDataList().size(); i++) {
            InfluencerData data = inf.getDataList().get(i);
            if (data.getMonth().equalsIgnoreCase(month)) {
                int followers = data.getFollowers();
                if (followers == 0) {
                    return null;
                }
                return ((double)
                    (data.getLikes() + data.getComments()) / followers) * 100;
            }
        }
        return null;
    }

    /**
     * Returns the reach engagement rate for a specific month.
     * Formula: ((likes + comments) / views) * 100
     * 
     * @param inf   the influencer
     * @param month the month to compute engagement for
     * @return engagement rate as a percentage, or null if views are zero
     *         or month not found
     */
    public Double getMonthlyReachEngagement(Influencer inf, String month) {
        for (int i = 0; i < inf.getDataList().size(); i++) {
            InfluencerData data = inf.getDataList().get(i);
            if (data.getMonth().equalsIgnoreCase(month)) {
                int views = data.getViews();
                if (views == 0) {
                    return null;
                }
                return ((double)
                    (data.getLikes() + data.getComments()) / views) * 100;
            }
        }
        return null;
    }
}
