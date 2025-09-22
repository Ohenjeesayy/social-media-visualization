package prj5;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * SLLEqualsToArrayTest — JUnit 5 version (no student.TestCase).
 * Keep your SinglyLinkedList API exactly as-is.
 */
public class SLLEqualsToArrayTest {

    private SinglyLinkedList<String> emptyListA;
    private SinglyLinkedList<String> emptyListB;
    private SinglyLinkedList<String> smallListA;
    private SinglyLinkedList<String> smallListB;
    private SinglyLinkedList<String> bigListA;
    private SinglyLinkedList<String> bigListB;
    private String nullObject;

    @BeforeEach
    void setUp() {
        emptyListA = new SinglyLinkedList<>();
        emptyListB = new SinglyLinkedList<>();

        smallListA = new SinglyLinkedList<>();
        smallListB = new SinglyLinkedList<>();

        smallListA.add("football");
        smallListA.add("swimming");
        smallListA.add("gymnastics");

        smallListB.add("football");
        smallListB.add("swimming");
        smallListB.add("gymnastics");

        bigListA = new SinglyLinkedList<>();
        for (int i = 0; i < 100; i++) {
            bigListA.add("sport" + i);
        }

        bigListB = new SinglyLinkedList<>();
        for (int i = 0; i < 100; i++) {
            bigListB.add("sport" + i);
        }

        nullObject = null;
    }

    // equals — empty list cases
    @Test
    @SuppressWarnings("unlikely-arg-type")
    void testEqualsEmptyList() {
        assertEquals(emptyListA, emptyListA);
        assertEquals(emptyListA, emptyListB);
        assertFalse(emptyListA.equals(nullObject));
        assertFalse(emptyListA.equals("soccer"));
        assertFalse(emptyListA.equals(smallListA));
        assertFalse(smallListA.equals(emptyListA));

        emptyListB.add("jump roping");
        assertFalse(emptyListA.equals(emptyListB));

        smallListA.clear();
        assertEquals(emptyListA, smallListA);

        emptyListA.clear();
        emptyListB.clear();
        assertEquals(emptyListA, emptyListB);
    }

    // insertionSort edge cases
    @Test
    void testInsertionSortEdgeCases() {
        Comparator<String> alpha = Comparator.naturalOrder();

        // empty list
        SinglyLinkedList<String> emptyList = new SinglyLinkedList<>();
        emptyList.insertionSort(alpha);
        assertTrue(emptyList.isEmpty());
        assertEquals(0, emptyList.size());

        // single element
        SinglyLinkedList<String> single = new SinglyLinkedList<>();
        single.add("only");
        single.insertionSort(alpha);
        assertEquals(1, single.size());
        assertEquals("only", single.get(0));
    }

    // equals — small list + index/exception behavior
    @Test
    @SuppressWarnings("unlikely-arg-type")
    void testEqualsSmallList() {
        assertEquals(smallListA, smallListA);
        assertEquals(smallListA, smallListB);
        assertFalse(smallListA.equals(nullObject));
        assertFalse(smallListA.equals("football"));
        assertFalse(smallListA.equals(bigListA));
        assertFalse(smallListA.equals(emptyListA));

        smallListB.add("jump roping");
        assertFalse(smallListA.equals(smallListB));

        smallListA.add("rope jumping");
        assertFalse(smallListA.equals(smallListB));

        smallListA.remove("rope jumping");
        smallListA.add("jump roping");
        assertEquals(smallListA, smallListB);

        smallListA.add(2, "Ali");
        assertTrue(smallListA.contains("Ali"));

        // null adds & bad indices should throw (match your original expectations)
        assertThrows(Exception.class, () -> smallListA.add(null));
        assertThrows(Exception.class, () -> smallListA.add(4, null));
        assertThrows(Exception.class, () -> smallListA.add(-1, "Ali"));
        assertThrows(Exception.class, () -> smallListA.add(7, "Ali"));

        smallListA.add(0, "Emad");
        assertTrue(smallListA.contains("Emad"));
        assertEquals("Emad", smallListA.get(0));

        assertThrows(Exception.class, () -> smallListA.get(10));
    }

    // equals — big list scenarios
    @Test
    @SuppressWarnings("unlikely-arg-type")
    void testEqualsBigList() {
        assertEquals(bigListA, bigListA);
        assertEquals(bigListA, bigListB);
        assertFalse(bigListA.equals(nullObject));
        assertFalse(bigListA.equals("football"));
        assertFalse(bigListA.equals(smallListA));
        assertFalse(bigListA.equals(emptyListA));

        bigListB.add("jump roping");
        assertFalse(bigListA.equals(bigListB));

        // reversed
        bigListB.clear();
        assertFalse(bigListA.equals(bigListB));
        for (int i = 100; i > 0; i--) {
            bigListB.add("sport" + i);
        }
        assertFalse(bigListA.equals(bigListB));

        // dup halves
        bigListB.clear();
        assertFalse(bigListA.equals(bigListB));
        for (int i = 0; i < 50; i++) {
            bigListB.add("sport" + i);
        }
        for (int i = 0; i < 50; i++) {
            bigListB.add("sport" + i);
        }
        assertFalse(bigListA.equals(bigListB));

        // equal again
        bigListB.clear();
        assertFalse(bigListA.equals(bigListB));
        for (int i = 0; i < 100; i++) {
            bigListB.add("sport" + i);
        }
        assertEquals(bigListA, bigListB);
    }

    // toArray — empty and one-item checks
    @Test
    void testToArrayEmpty() {
        Object[] emptyArray = {};
        assertTrue(Arrays.equals(emptyListA.toArray(), emptyArray));
        assertTrue(Arrays.equals(emptyListA.toArray(), emptyListB.toArray()));
        assertFalse(Arrays.equals(emptyListA.toArray(), smallListB.toArray()));

        Object[] oneItemArray = { "one thing" };
        emptyListA.add("one thing");
        assertTrue(Arrays.equals(emptyListA.toArray(), oneItemArray));
    }

    // toArray contents + toString
    @Test
    void testToArrayContents() {
        Object[] origArray = { "football", "swimming", "gymnastics" };
        String origArrayTest = "{football, swimming, gymnastics}";
        assertTrue(Arrays.equals(smallListA.toArray(), origArray));
        assertTrue(Arrays.equals(emptyListA.toArray(), emptyListB.toArray()));
        assertFalse(Arrays.equals(smallListA.toArray(), bigListB.toArray()));
        assertTrue(smallListA.contains("football"));
        assertEquals(origArrayTest, smallListA.toString());
    }

    // remove behavior + exceptions
    @Test
    void testRemove() {
        assertTrue(smallListA.remove(2));
        assertTrue(smallListA.remove(0));
        assertTrue(smallListB.remove(1));

        assertFalse(smallListA.contains("football"));
        assertFalse(smallListA.remove("Ali"));
        assertFalse(emptyListA.remove("Ali"));
        assertTrue(bigListA.remove("sport99"));
        assertTrue(bigListA.remove("sport0"));

        assertThrows(Exception.class, () -> smallListA.remove(-1));
        assertThrows(Exception.class, () -> smallListA.remove(10));
    }

    // lastIndex
    @Test
    void testLastIndex() {
        assertEquals(1, smallListA.lastIndexOf("swimming"));
    }
}
