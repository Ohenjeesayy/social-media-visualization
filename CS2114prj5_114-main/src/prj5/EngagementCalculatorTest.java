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
 * EngagementCalculatorTest
 * 
 * Validates EngagementCalculator methods for quarterly and monthly
 * engagement calculations. Covers normal, edge, and null-input cases.
 * Ensures correct handling of zero followers/views and missing data.
 *
 * @author zay
 * @version 2025.04.24
 */
public class EngagementCalculatorTest extends TestCase {

    private EngagementCalculator calc;
    private Influencer normalInf;
    private Influencer zeroFollowersInf;
    private Influencer zeroViewsInf;
    private Influencer mixedInf;
    private Influencer noDataInf;

    @Override
    public void setUp() {
        calc = new EngagementCalculator();

        normalInf = new Influencer("u1", "Normal", "US", "Topic");
        normalInf.getDataList().add(
            new InfluencerData("January", 10, 10, 10, 10, 1));
        normalInf.getDataList().add(
            new InfluencerData("February", 20, 20, 20, 20, 2));
        normalInf.getDataList().add(
            new InfluencerData("March", 30, 30, 30, 30, 3));

        zeroFollowersInf = new Influencer("u2", "ZeroFol", "US", "Topic");
        zeroFollowersInf.getDataList().add(
            new InfluencerData("January", 5, 0, 0, 1, 50));
        zeroFollowersInf.getDataList().add(
            new InfluencerData("February", 5, 0, 0, 1, 50));
        zeroFollowersInf.getDataList().add(
            new InfluencerData("March", 0, 0, 0, 0, 50));

        zeroViewsInf = new Influencer("u3", "ZeroViews", "US", "Topic");
        zeroViewsInf.getDataList().add(
            new InfluencerData("January", 8, 0, 10, 2, 0));
        zeroViewsInf.getDataList().add(
            new InfluencerData("February", 8, 0, 10, 2, 0));
        zeroViewsInf.getDataList().add(
            new InfluencerData("March", 8, 0, 10, 2, 0));

        mixedInf = new Influencer("u4", "Mixed", "US", "Topic");
        mixedInf.getDataList().add(
            new InfluencerData("January", 1, 0, 0, 1, 10));
        mixedInf.getDataList().add(
            new InfluencerData("March", 3, 0, 15, 3, 30));
        mixedInf.getDataList().add(
            new InfluencerData("April", 50, 0, 999, 50, 500));

        noDataInf = new Influencer("u5", "Empty", "US", "Topic");
    }

    /**
     * Tests quarterly and monthly traditional engagement including:
     * - Normal influencer
     * - Missing months
     * - Zero followers
     * - Empty data
     */
    public void testQuarterAndMonthlyTraditionalEngagement() {
        double expectedQuarter = (10 + 20 + 30 + 10 + 20 + 30) / 30.0 * 100.0;
        assertEquals(expectedQuarter,
            calc.getQuarterTraditionalEngagement(normalInf), 0.0001);

        assertEquals(
            200.0,
            calc.getMonthlyTraditionalEngagement(normalInf, "January"),
            0.0001);
        
        assertEquals(
            200.0,
            calc.getMonthlyTraditionalEngagement(normalInf, "February"),
            0.0001);
        
        assertEquals(
            200.0,
            calc.getMonthlyTraditionalEngagement(normalInf, "March"),
            0.0001);

        assertNull(
            calc.getQuarterTraditionalEngagement(zeroFollowersInf));
        
        assertNull(
            calc.getMonthlyTraditionalEngagement(zeroFollowersInf, "January"));
        
        assertNull(
            calc.getMonthlyTraditionalEngagement(zeroFollowersInf, "March"));

        assertNull(
            calc.getMonthlyTraditionalEngagement(mixedInf, "February"));

        
        
        Influencer noMarch = 
            new Influencer("u6", "MissingMarch", "US", "Topic");
        
        
        noMarch.getDataList().add(
            new InfluencerData("January", 10, 0, 0, 5, 100));
        noMarch.getDataList().add(
            new InfluencerData("February", 15, 5, 5, 10, 150));
        assertNotNull(calc.getQuarterTraditionalEngagement(noMarch));

        Influencer onlyMarch = new Influencer("u7", "MarchOnly", "US", "Topic");
        onlyMarch.getDataList().add(
            new InfluencerData("March", 10, 5, 40, 5, 100));
        double expectedMarchOnly = (10 + 5) / 40.0 * 100;
        assertEquals(expectedMarchOnly,
            calc.getQuarterTraditionalEngagement(onlyMarch), 0.0001);

        Influencer zeroActivity =
            new Influencer("u8", "ZeroActivity", "US", "Topic");
        zeroActivity.getDataList().add(
            new InfluencerData("January", 0, 0, 50, 0, 100));
        zeroActivity.getDataList().add(
            new InfluencerData("February", 0, 0, 60, 0, 100));
        zeroActivity.getDataList().add(
            new InfluencerData("October", 0, 0, 60, 0, 100));
        
        assertEquals(0.0,
            calc.getQuarterTraditionalEngagement(zeroActivity), 0.0001);
    }

    /**
     * Tests quarterly reach engagement including normal case,
     * zero views, and missing/non-matching months.
     */
    public void testQuarterReachEngagement() {
        double expected = (10 + 20 + 30 + 10 + 20 + 30) / (1 + 2 + 3) * 100.0;
        
        assertEquals(
            expected,
            calc.getQuarterReachEngagement(normalInf),
            0.0001);
        
        
        assertNull(calc.getQuarterReachEngagement(zeroViewsInf));

        Influencer inf = new Influencer("u10", "MarchOnlyReach", "US", "Topic");
        inf.getDataList().add(
            new InfluencerData("January", 10, 5, 100, 5, 0));
        inf.getDataList().add(
            new InfluencerData("February", 10, 5, 100, 5, 0));
        inf.getDataList().add(
            new InfluencerData("March", 10, 5, 100, 5, 100));
        inf.getDataList().add(
            new InfluencerData("October", 10, 5, 100, 5, 100));
        double expected2 = 45.0 / 100.0 * 100;
        assertEquals(expected2, calc.getQuarterReachEngagement(inf), 0.0001);
    }

    /**
     * Tests monthly reach engagement for normal, zero view,
     * and missing month scenarios.
     */
    public void testMonthlyReachEngagement() {
        assertEquals(2000.0,
            calc.getMonthlyReachEngagement(normalInf, "January"), 0.0001);
        assertEquals(2000.0,
            calc.getMonthlyReachEngagement(normalInf, "February"), 0.0001);
        assertEquals(2000.0,
            calc.getMonthlyReachEngagement(normalInf, "March"), 0.0001);

        assertNull(calc.getMonthlyReachEngagement(zeroViewsInf, "January"));
        assertNull(calc.getMonthlyReachEngagement(zeroViewsInf, "March"));

        assertNull(calc.getMonthlyReachEngagement(mixedInf, "February"));
    }

    /**
     * Verifies null is returned for all engagement types
     * when influencer has no data.
     */
    public void testReturnNullWithEmptyData() {
        assertNull(calc.getQuarterTraditionalEngagement(noDataInf));
        assertNull(calc.getQuarterReachEngagement(noDataInf));
        assertNull(calc.getMonthlyTraditionalEngagement(noDataInf, "March"));
        assertNull(calc.getMonthlyReachEngagement(noDataInf, "March"));
    }
}
