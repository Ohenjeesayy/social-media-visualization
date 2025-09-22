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
 * Test class for the Influencer class.
 * Ensures all getters and methods like addData() behave correctly.
 * 
 * @author Arshia
 * @version 2025-04-22
 */
public class InfluencerTest extends TestCase {

    private Influencer influencer;
    private InfluencerData data1;
    private InfluencerData data2;

    /**
     * Set up test fixtures.
     */
    public void setUp() {
        influencer = new Influencer("user123", "CoolChannel", "CA", "tech");

        data1 = new InfluencerData("January", 100, 10, 1000, 50, 5000);
        data2 = new InfluencerData("February", 200, 20, 2000, 60, 6000);
    }


    /**
     * Test constructor and basic getters.
     */
    public void testConstructorAndGetters() {
        assertEquals("user123", influencer.getUsername());
        assertEquals("CoolChannel", influencer.getChannelName());
        assertEquals("CA", influencer.getCountry());
        assertEquals("tech", influencer.getMainTopic());
        assertNotNull(influencer.getDataList());
        assertEquals(0, influencer.getDataList().size());
    }


    /**
     * Test adding InfluencerData.
     */
    public void testAddData() {
        influencer.addData(data1);
        influencer.addData(data2);

        assertEquals(2, influencer.getDataList().size());
        assertEquals(data1, influencer.getDataList().get(0));
        assertEquals(data2, influencer.getDataList().get(1));
    }


    /**
     * Test toString formatting.
     */
    public void testToString() {
        influencer.addData(data1);
        String output = influencer.toString();
        assertTrue(output.contains("user123"));
        assertTrue(output.contains("CoolChannel"));
        assertTrue(output.contains("data count=1"));
    }
}
