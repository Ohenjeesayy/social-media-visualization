package prj5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for InfluencerData (JUnit 5).
 * Verifies construction and getters.
 */
public class InfluencerDataTest {

    private InfluencerData data;

    @BeforeEach
    void setUp() {
        data = new InfluencerData("March", 150, 12, 1200, 45, 9800);
    }

    @Test
    void testGetters() {
        assertEquals("March", data.getMonth());
        assertEquals(150, data.getLikes());
        assertEquals(12, data.getPosts());
        assertEquals(1200, data.getFollowers());
        assertEquals(45, data.getComments());
        assertEquals(9800, data.getViews());
    }

    @Test
    void testToString() {
        String output = data.toString();
        assertTrue(output.contains("March"));
        assertTrue(output.contains("likes=150"));
        assertTrue(output.contains("views=9800"));
    }
}
