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

import java.util.Arrays;
import java.util.Comparator;

import student.TestCase;

/**
 * 
 * Tests the equals and toArray methods of a singly linked list.
 * 
 * @author Arshia Saeidifar
 * @version 2025
 *
 */
public class SLLEqualsToArrayTest extends TestCase {

    private SinglyLinkedList<String> emptyListA;
    private SinglyLinkedList<String> emptyListB;
    private SinglyLinkedList<String> smallListA;
    private SinglyLinkedList<String> smallListB;
    private SinglyLinkedList<String> bigListA;
    private SinglyLinkedList<String> bigListB;
    private String nullObject;

    //
    /**
     * Initializes 2 empty lists, 2 lists with a small number of items, and 2
     * lists
     * with a large number of items
     */
    public void setUp() {
        emptyListA = new SinglyLinkedList<String>();
        emptyListB = new SinglyLinkedList<String>();

        smallListA = new SinglyLinkedList<String>();
        smallListB = new SinglyLinkedList<String>();

        smallListA.add("football");
        smallListA.add("swimming");
        smallListA.add("gymnastics");

        smallListB.add("football");
        smallListB.add("swimming");
        smallListB.add("gymnastics");

        bigListA = new SinglyLinkedList<String>();

        for (int i = 0; i < 100; i++) {
            bigListA.add("sport" + i);
        }

        bigListB = new SinglyLinkedList<String>();
        for (int i = 0; i < 100; i++) {
            bigListB.add("sport" + i);
        }

        // to be explicit
        nullObject = null;
    }


    /**
     * Tests the equals method on an empty list
     */
    @SuppressWarnings("unlikely-arg-type")
    public void testEqualsEmptyList() {
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


    /**
     * Verifies that insertionSort() immediately returns (does nothing) when the
     * list is empty or when it holds just a single element.
     */
    public void testInsertionSortEdgeCases() {
        Comparator<String> alpha = Comparator.naturalOrder(); 

        // Test sorting an empty list
        SinglyLinkedList<String> emptyList = new SinglyLinkedList<>();
        emptyList.insertionSort(alpha);
        assertTrue(emptyList.isEmpty());
        assertEquals(0, emptyList.size());

        // Test sorting a list with one element
        SinglyLinkedList<String> singleElementList = new SinglyLinkedList<>();
        singleElementList.add("only");
        singleElementList.insertionSort(alpha);
        assertEquals(1, singleElementList.size());
        assertEquals("only", singleElementList.get(0));
    }


    /**
     * Tests the equals method on a list with a small number of items in it
     */
    @SuppressWarnings("unlikely-arg-type")
    public void testEqualsSmallList() {
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

        Exception myExp7 = null;
        try {
            smallListA.add(null);

        }
        catch (Exception e) {
            myExp7 = e;
        }
        assertNotNull(myExp7);

        Exception myExp = null;
        try {
            smallListA.add(4, null);

        }
        catch (Exception e) {
            myExp = e;
        }
        assertNotNull(myExp);

        Exception myExp2 = null;
        try {
            smallListA.add(-1, "Ali");

        }
        catch (Exception e) {
            myExp2 = e;
        }
        assertNotNull(myExp2);

        Exception myExp3 = null;
        try {
            smallListA.add(7, "Ali");

        }
        catch (Exception e) {
            myExp3 = e;
        }
        assertNotNull(myExp3);

        smallListA.add(0, "Emad");

        assertTrue(smallListA.contains("Emad"));
        assertEquals(smallListA.get(0), "Emad");

        Exception myExp4 = null;
        try {
            smallListA.get(10);

        }
        catch (Exception e) {
            myExp4 = e;
        }
        assertNotNull(myExp4);

    }


    /**
     * Tests the equals method on a list with a large number of items in it
     */
    @SuppressWarnings("unlikely-arg-type")
    public void testEqualsBigList() {
        assertEquals(bigListA, bigListA);
        assertEquals(bigListA, bigListB);
        assertFalse(bigListA.equals(nullObject));
        assertFalse(bigListA.equals("football"));
        assertFalse(bigListA.equals(smallListA));
        assertFalse(bigListA.equals(emptyListA));
        bigListB.add("jump roping");
        assertFalse(bigListA.equals(bigListB));

        // Same content, same size, but reversed
        bigListB.clear();
        assertFalse(bigListA.equals(bigListB));
        for (int i = 100; i > 0; i--) {
            bigListB.add("sport" + i);
        }
        assertFalse(bigListA.equals(bigListB));

        // one a subset of the other but with dups
        bigListB.clear();
        assertFalse(bigListA.equals(bigListB));
        for (int i = 0; i < 50; i++) {
            bigListB.add("sport" + i);
        }
        for (int i = 0; i < 50; i++) {
            bigListB.add("sport" + i);
        }
        assertFalse(bigListA.equals(bigListB));

        // make them equal again
        bigListB.clear();
        assertFalse(bigListA.equals(bigListB));
        for (int i = 0; i < 100; i++) {
            bigListB.add("sport" + i);
        }
        assertEquals(bigListA, bigListB);

    }


    /**
     * Tests the toArray method on an empty list
     */
    public void testToArrayEmpty() {

        Object[] emptyArray = {};
        assertTrue(Arrays.equals(emptyListA.toArray(), emptyArray));
        assertTrue(Arrays.equals(emptyListA.toArray(), emptyListB.toArray()));
        assertFalse(Arrays.equals(emptyListA.toArray(), smallListB.toArray()));
        Object[] oneItemArray = { "one thing" };
        emptyListA.add("one thing");
        assertTrue(Arrays.equals(emptyListA.toArray(), oneItemArray));

    }


    /**
     * Tests the toArray method on a list with items in it
     */
    public void testToArrayContents() {

        Object[] origArray = { "football", "swimming", "gymnastics" };
        String origArrayTest = "{football, swimming, gymnastics}";
        assertTrue(Arrays.equals(smallListA.toArray(), origArray));
        assertTrue(Arrays.equals(emptyListA.toArray(), emptyListB.toArray()));
        assertFalse(Arrays.equals(smallListA.toArray(), bigListB.toArray()));
        assertTrue(smallListA.contains("football"));
        assertEquals(smallListA.toString(), origArrayTest);
    }


    // ----------------------------------------------------------
    /**
     * Testing the remove method.
     */
    public void testRemove() {
        assertTrue(smallListA.remove(2));
        assertTrue(smallListA.remove(0));
        assertTrue(smallListB.remove(1));

        assertFalse(smallListA.contains("football"));
        assertFalse(smallListA.remove("Ali"));
        assertFalse(emptyListA.remove("Ali"));
        assertTrue(bigListA.remove("sport99"));
        assertTrue(bigListA.remove("sport0"));

        Exception myExp = null;
        try {
            smallListA.remove(-1);

        }
        catch (Exception e) {
            myExp = e;
        }
        assertNotNull(myExp);

        Exception myExp2 = null;
        try {
            smallListA.remove(10);

        }
        catch (Exception e) {
            myExp2 = e;
        }
        assertNotNull(myExp2);

    }


    // ----------------------------------------------------------
    /**
     * Testing the last index mehtod.
     */
    public void testLastIndex() {
        assertEquals(1, smallListA.lastIndexOf("swimming"));

    }

}
