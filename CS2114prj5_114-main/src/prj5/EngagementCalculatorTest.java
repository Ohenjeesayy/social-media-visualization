package prj5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * EngagementCalculatorTest (JUnit 5)
 * Validates EngagementCalculator methods for quarterly and monthly engagement.
 */
public class EngagementCalculatorTest {

    private EngagementCalculator calc;
    private Influencer normalInf;
    private Influencer zeroFollowersInf;
    private Influencer zeroViewsInf;
    private Influencer mixedInf;
    private Influencer noDataInf;

    @BeforeEach
    void setUp() {
        calc = new EngagementCalculator();

        normalInf = new Influencer("u1", "Normal", "US", "Topic");
        normalInf.getDataList().add(new InfluencerData("January", 10, 10, 10, 10, 1));
        normalInf.getDataList().add(new InfluencerData("February", 20, 20, 20, 20, 2));
        normalInf.getDataList().add(new InfluencerData("March", 30, 30, 30, 30, 3));

        zeroFollowersInf = new Influencer("u2", "ZeroFol", "US", "Topic");
        zeroFollowersInf.getDataList().add(new InfluencerData("January", 5, 0, 0, 1, 50));
        zeroFollowersInf.getDataList().add(new InfluencerData("February", 5, 0, 0, 1, 50));
        zeroFollowersInf.getDataList().add(new InfluencerData("March", 0, 0, 0, 0, 50));

        zeroViewsInf = new Influencer("u3", "ZeroViews", "US", "Topic");
        zeroViewsInf.getDataList().add(new InfluencerData("January", 8, 0, 10, 2, 0));
        zeroViewsInf.getDataList().add(new InfluencerData("February", 8, 0, 10, 2, 0));
        zeroViewsInf.getDataList().add(new InfluencerData("March", 8, 0, 10, 2, 0));

        mixedInf = new Influencer("u4", "Mixed", "US", "Topic");
        mixedInf.getDataList().add(new InfluencerData("January", 1, 0, 0, 1, 10));
        mixedInf.getDataList().add(new InfluencerData("March", 3, 0, 15, 3, 30));
        mixedInf.getDataList().add(new InfluencerData("April", 50, 0, 999, 50, 500));

        noDataInf = new Influencer("u5", "Empty", "US", "Topic");
    }

    @Test
    void testQuarterAndMonthlyTraditionalEngagement() {
        double expectedQuarter = (10 + 20 + 30 + 10 + 20 + 30) / 30.0 * 100.0;
        assertEquals(expectedQuarter, calc.getQuarterTraditionalEngagement(normalInf), 1e-4);

        assertEquals(200.0, calc.getMonthlyTraditionalEngagement(normalInf, "January"), 1e-4);
        assertEquals(200.0, calc.getMonthlyTraditionalEngagement(normalInf, "February"), 1e-4);
        assertEquals(200.0, calc.getMonthlyTraditionalEngagement(normalInf, "March"), 1e-4);

        assertNull(calc.getQuarterTraditionalEngagement(zeroFollowersInf));
        assertNull(calc.getMonthlyTraditionalEngagement(zeroFollowersInf, "January"));
        assertNull(calc.getMonthlyTraditionalEngagement(zeroFollowersInf, "March"));

        assertNull(calc.getMonthlyTraditionalEngagement(mixedInf, "February"));

        Influencer noMarch = new Influencer("u6", "MissingMarch", "US", "Topic");
        noMarch.getDataList().add(new InfluencerData("January", 10, 0, 0, 5, 100));
        noMarch.getDataList().add(new InfluencerData("February", 15, 5, 5, 10, 150));
        assertNotNull(calc.getQuarterTraditionalEngagement(noMarch));

        Influencer onlyMarch = new Influencer("u7", "MarchOnly", "US", "Topic");
        onlyMarch.getDataList().add(new InfluencerData("March", 10, 5, 40, 5, 100));
        double expectedMarchOnly = (10 + 5) / 40.0 * 100.0;
        assertEquals(expectedMarchOnly, calc.getQuarterTraditionalEngagement(onlyMarch), 1e-4);

        Influencer zeroActivity = new Influencer("u8", "ZeroActivity", "US", "Topic");
        zeroActivity.getDataList().add(new InfluencerData("January", 0, 0, 50, 0, 100));
        zeroActivity.getDataList().add(new InfluencerData("February", 0, 0, 60, 0, 100));
        zeroActivity.getDataList().add(new InfluencerData("October", 0, 0, 60, 0, 100));
        assertEquals(0.0, calc.getQuarterTraditionalEngagement(zeroActivity), 1e-4);
    }

    @Test
    void testQuarterReachEngagement() {
        double expected = (10 + 20 + 30 + 10 + 20 + 30) / (1 + 2 + 3) * 100.0;
        assertEquals(expected, calc.getQuarterReachEngagement(normalInf), 1e-4);

        assertNull(calc.getQuarterReachEngagement(zeroViewsInf));

        Influencer inf = new Influencer("u10", "MarchOnlyReach", "US", "Topic");
        inf.getDataList().add(new InfluencerData("January", 10, 5, 100, 5, 0));
        inf.getDataList().add(new InfluencerData("February", 10, 5, 100, 5, 0));
        inf.getDataList().add(new InfluencerData("March", 10, 5, 100, 5, 100));
        inf.getDataList().add(new InfluencerData("October", 10, 5, 100, 5, 100));
        double expected2 = 45.0 / 100.0 * 100.0;
        assertEquals(expected2, calc.getQuarterReachEngagement(inf), 1e-4);
    }

    @Test
    void testMonthlyReachEngagement() {
        assertEquals(2000.0, calc.getMonthlyReachEngagement(normalInf, "January"), 1e-4);
        assertEquals(2000.0, calc.getMonthlyReachEngagement(normalInf, "February"), 1e-4);
        assertEquals(2000.0, calc.getMonthlyReachEngagement(normalInf, "March"), 1e-4);

        assertNull(calc.getMonthlyReachEngagement(zeroViewsInf, "January"));
        assertNull(calc.getMonthlyReachEngagement(zeroViewsInf, "March"));
        assertNull(calc.getMonthlyReachEngagement(mixedInf, "February"));
    }

    @Test
    void testReturnNullWithEmptyData() {
        assertNull(calc.getQuarterTraditionalEngagement(noDataInf));
        assertNull(calc.getQuarterReachEngagement(noDataInf));
        assertNull(calc.getMonthlyTraditionalEngagement(noDataInf, "March"));
        assertNull(calc.getMonthlyReachEngagement(noDataInf, "March"));
    }
}
