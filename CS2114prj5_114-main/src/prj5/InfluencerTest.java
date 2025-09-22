package prj5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Influencer class (JUnit 5).
 * Ensures getters and addData() behave correctly.
 */
public class InfluencerTest {

    private Influencer influencer;
    private InfluencerData data1;
    private InfluencerData data2;

    @BeforeEach
    void setUp() {
        influencer = new Influencer("user123", "CoolChannel", "CA", "tech");
        data1 = new InfluencerData("January", 100, 10, 1000, 50, 5000);
        data2 = new InfluencerData("February", 200, 20, 2000, 60, 6000);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("user123", influencer.getUsername());
        assertEquals("CoolChannel", influencer.getChannelName());
        assertEquals("CA", influencer.getCountry());
        assertEquals("tech", influencer.getMainTopic());
        assertNotNull(influencer.getDataList());
        assertEquals(0, influencer.getDataList().size());
    }

    @Test
    void testAddData() {
        influencer.addData(data1);
        influencer.addData(data2);

        assertEquals(2, influencer.getDataList().size());
        assertEquals(data1, influencer.getDataList().get(0));
        assertEquals(data2, influencer.getDataList().get(1));
    }

    @Test
    void testToString() {
        influencer.addData(data1);
        String output = influencer.toString();
        assertTrue(output.contains("user123"));
        assertTrue(output.contains("CoolChannel"));
        assertTrue(output.contains("data count=1"));
    }
}
