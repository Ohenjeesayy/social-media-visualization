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
 * Comparator that compares two influencer alphabetically
 * by their channel name case-insensitive.
 * 
 * @author Sakdipong Rodphong
 * @version 04.22.2025
 */
public class ComparatorByChannelName implements Comparator<Influencer> {

    /**
     * Compares two influencers by their channel names (case-insensitive).
     *
     * @param a the first influencer
     * @param b the second influencer
     * @return negative if a < b, zero if equal, positive if a > b
     */
    public int compare(Influencer a, Influencer b) {
        return a.getChannelName().compareToIgnoreCase(b.getChannelName());
    }

}
