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


import student.TestCase;

/**
 * Test class for InfluencerData.
 * Verifies correct construction and getter behavior.
 * 
 * @author Arshia Saeidifar
 * @version 2025-04-22
 */
public class InfluencerDataTest extends TestCase {

    private InfluencerData data;

    /**
     * Sets up a sample data object.
     */
    public void setUp() {
        data = new InfluencerData("March", 150, 12, 1200, 45, 9800);
    }


    /**
     * Tests all the getter methods.
     */
    public void testGetters() {
        assertEquals("March", data.getMonth());
        assertEquals(150, data.getLikes());
        assertEquals(12, data.getPosts());
        assertEquals(1200, data.getFollowers());
        assertEquals(45, data.getComments());
        assertEquals(9800, data.getViews());
    }


    /**
     * Tests the toString method.
     */
    public void testToString() {
        String output = data.toString();
        assertTrue(output.contains("March"));
        assertTrue(output.contains("likes=150"));
        assertTrue(output.contains("views=9800"));
    }
}
