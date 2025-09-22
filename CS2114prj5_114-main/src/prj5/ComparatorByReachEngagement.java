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


import java.util.Comparator;

/**
 * Comparator that compares influencers by reach engagement rate
 * for a given period (month or quarter). Sorts in descending order.
 * "N/A" (null) values are treated as lowest.
 * 
 * Supports months like "January" and quarters like "FirstQuarter".
 * 
 * @author Zaybish
 * @author Arshia Saeidifar
 * @version 2025-04-30
 */
public class ComparatorByReachEngagement implements Comparator<Influencer> {

    
 // "January", "February", ..., or "FirstQuarter"
    private final String period; 
    private final EngagementCalculator calc = new EngagementCalculator();

    
    /**
     * Creates a comparator for reach engagement based on a given period.
     *
     * @param period the month (e.g., "January") or "FirstQuarter"
     */
    public ComparatorByReachEngagement(String period) {
        this.period = period;
    }

    
    /**
     * Compares two influencers by reach engagement 
     * rate in the specified period.
     * Sorting is in descending order (higher engagement appears first).
     * If engagement is missing (null), it is treated as the lowest value.
     *
     * @param a the first influencer
     * @param b the second influencer
     * @return negative if a has higher engagement,
     *         positive if b has higher engagement,
     *         zero if equal or both missing
     */
    @Override
    public int compare(Influencer a, Influencer b) {
        Double aReach;
        Double bReach;

        if (period.endsWith("Quarter")) {
            aReach = calc.getQuarterReachEngagement(a);
            bReach = calc.getQuarterReachEngagement(b);
        } 
        else {
            aReach = calc.getMonthlyReachEngagement(a, period);
            bReach = calc.getMonthlyReachEngagement(b, period);
        }

        if (aReach == null && bReach == null) {
            return 0;
        }
        if (aReach == null) {
            return 1;
        }
        if (bReach == null) {
            return -1;
        }

        return Double.compare(bReach, aReach); // descending
    }
}
