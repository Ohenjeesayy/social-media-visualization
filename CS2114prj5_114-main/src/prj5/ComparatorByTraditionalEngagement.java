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
 * Comparator that orders influencers by their traditional engagement rate
 * for a given month (e.g., "January") or quarter (e.g., "FirstQuarter"),
 * in descending order. Influencers with null data are sorted to the end.
 * 
 * @author Zaybish
 * @version 2025-04-30
 */
public class ComparatorByTraditionalEngagement
    implements Comparator<Influencer> {

    private final String period; // Month or Quarter
    private final EngagementCalculator calc = new EngagementCalculator();

    /**
     * Creates a comparator for the given period.
     * 
     * @param period month or quarter
     */
    public ComparatorByTraditionalEngagement(String period) {
        this.period = period;
    }

    @Override
    public int compare(Influencer first, Influencer second) {
        Double firstRate;
        Double secondRate;
        
        

        if (period.endsWith("Quarter")) {
            firstRate = calc.getQuarterTraditionalEngagement(first);
            secondRate = calc.getQuarterTraditionalEngagement(second);
        } 
        else {
            firstRate = calc.getMonthlyTraditionalEngagement(first, period);
            secondRate = calc.getMonthlyTraditionalEngagement(second, period);
        }
        
        if (firstRate == null && secondRate == null) {
            return 0;
        }
        if (firstRate == null) {
            return 1;
        }
        if (secondRate == null) {
            return -1;
        }


        return Double.compare(secondRate, firstRate); // Descending
    }
}
