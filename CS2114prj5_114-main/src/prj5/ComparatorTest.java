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
 * Unit tests for all Comparators used in the project.
 * 
 * Tests include sorting by channel name, traditional engagement,
 * and reach engagement. Null-handling is also verified to ensure
 * stable and predictable sorting behavior. Comparators are tested
 * for both monthly and quarterly periods.
 * 
 * @author zay
 * @version 2025-04-30
 */
public class ComparatorTest extends TestCase {

    private ComparatorByChannelName channelComp;
    private ComparatorByTraditionalEngagement tradComp;
    private ComparatorByReachEngagement reachComp;
    private SinglyLinkedList<Influencer> influencerList;

    /**
     * Sets up test data for influencer comparison.
     */
    @Override
    public void setUp() {
        channelComp = new ComparatorByChannelName();
        tradComp = new ComparatorByTraditionalEngagement("FirstQuarter");
        reachComp = new ComparatorByReachEngagement("FirstQuarter");

        Influencer infA = new Influencer("aaa", "Zebra", "US", "Music");
        infA.getDataList().add(
            new InfluencerData("January", 40, 2, 100, 20, 500));
        infA.getDataList().add(
            new InfluencerData("February", 60, 3, 100, 30, 500));
        infA.getDataList().add(
            new InfluencerData("March", 50, 1, 100, 25, 0));

        Influencer infB = new Influencer("bbb", "Alpha", "US", "Art");
        infB.getDataList().add(
            new InfluencerData("January", 90, 3, 100, 40, 500));
        infB.getDataList().add(
            new InfluencerData("February", 10, 2, 100, 5, 500));
        infB.getDataList().add(
            new InfluencerData("March", 0, 0, 100, 0, 0));

        Influencer infC = new Influencer("ccc", "Delta", "US", "Tech");
        infC.getDataList().add(
            new InfluencerData("January", 10, 2, 100, 5, 0));
        infC.getDataList().add(
            new InfluencerData("February", 20, 2, 100, 10, 0));
        infC.getDataList().add(
            new InfluencerData("March", 30, 2, 100, 15, 0));

        influencerList = new SinglyLinkedList<>();
        influencerList.add(infA);
        influencerList.add(infB);
        influencerList.add(infC);
    }

    /**
     * Tests sorting influencers by channel name.
     */
    public void testSortByChannelName() {
        influencerList.insertionSort(channelComp);
        assertEquals("Alpha", influencerList.get(0).getChannelName());
        assertEquals("Delta", influencerList.get(1).getChannelName());
        assertEquals("Zebra", influencerList.get(2).getChannelName());
    }

    /**
     * Tests sorting influencers by traditional engagement rate for
     * the first quarter.
     */
    public void testSortByTraditionalEngagement() {
        influencerList.insertionSort(tradComp);
        assertEquals("Zebra", influencerList.get(0).getChannelName());
        assertEquals("Alpha", influencerList.get(1).getChannelName());
        assertEquals("Delta", influencerList.get(2).getChannelName());
    }

    /**
     * Tests sorting influencers by reach engagement rate for
     * the first quarter.
     */
    public void testSortByReachEngagement() {
        influencerList.insertionSort(reachComp);
        assertEquals("Zebra", influencerList.get(0).getChannelName());
        assertEquals("Alpha", influencerList.get(1).getChannelName());
        assertEquals("Delta", influencerList.get(2).getChannelName());
    }

    /**
     * Tests reach engagement comparator with influencers having
     * null (unavailable) data.
     */
    public void testReachEngagementNullCases() {
        ComparatorByReachEngagement comp =
            new ComparatorByReachEngagement("FirstQuarter");
        
        ComparatorByReachEngagement comp2 =
            new ComparatorByReachEngagement("january");
        

        Influencer d = new Influencer("ddd", "NullOne", "US", "Gaming");
        d.getDataList().add(
            new InfluencerData("January", 0, 1, 100, 0, 0));
        d.getDataList().add(
            new InfluencerData("February", 0, 1, 100, 0, 0));
        d.getDataList().add(
            new InfluencerData("March", 0, 1, 100, 0, 0));

        Influencer e = new Influencer("eee", "NullTwo", "US", "Gaming");
        e.getDataList().add(
            new InfluencerData("January", 0, 1, 100, 0, 0));
        e.getDataList().add(
            new InfluencerData("February", 0, 1, 100, 0, 0));
        e.getDataList().add(
            new InfluencerData("March", 0, 1, 100, 0, 0));

        Influencer f = new Influencer("fff", "Valid", "US", "Gaming");
        f.getDataList().add(
            new InfluencerData("January", 10, 1, 100, 5, 100));
        f.getDataList().add(
            new InfluencerData("February", 15, 1, 100, 10, 100));
        f.getDataList().add(
            new InfluencerData("March", 5, 1, 100, 5, 100));

        assertEquals(0, comp.compare(d, e)); // Both null
        assertEquals(1, comp.compare(d, f)); // d is null, f is valid
        assertEquals(-1, comp.compare(f, e)); // f is valid, e is null
        
        assertEquals(0, comp2.compare(d, e)); // Both null
        assertEquals(1, comp2.compare(d, f)); // d is null, f is valid
        assertEquals(-1, comp2.compare(f, e)); // f is valid, e is null
    }
    
    
    // ----------------------------------------------------------
    /**
     * This test is included null version.
     */
    public void testTraditionalEngagementComparator() {
        ComparatorByTraditionalEngagement comp = 
            new ComparatorByTraditionalEngagement("FirstQuarter");
        ComparatorByTraditionalEngagement comp2 = 
            new ComparatorByTraditionalEngagement("january");
        // A: null vs null
        Influencer nullA = new Influencer("nullA", "Null A", "US", "X");
        nullA.getDataList().add(
            new InfluencerData("January", 0, 0, 100, 0, 0));
        nullA.getDataList().add(
            new InfluencerData("February", 0, 0, 100, 0, 0));
        nullA.getDataList().add(
            new InfluencerData("March", 0, 0, 100, 0, 0));

        Influencer nullB = new Influencer("nullB", "Null B", "US", "X");
        nullB.getDataList().add(
            new InfluencerData("January", 0, 0, 100, 0, 0));
        nullB.getDataList().add(
            new InfluencerData("February", 0, 0, 100, 0, 0));
        nullB.getDataList().add(
            new InfluencerData("March", 0, 0, 100, 0, 0));

        // B: null vs non-null
        Influencer validA = new Influencer("validA", "Valid A", "US", "X");
        validA.getDataList().add(
            new InfluencerData("January", 10, 5, 100, 5, 100));
        validA.getDataList().add(
            new InfluencerData("February", 5, 5, 100, 5, 100));
        validA.getDataList().add(
            new InfluencerData("March", 10, 5, 100, 5, 100)); 

        // C: validA vs null
        // D: validA (35) vs validB (20)
        Influencer validB = new Influencer("validB", "Valid B", "US", "X");
        validB.getDataList().add(
            new InfluencerData("January", 5, 2, 100, 5, 100));
        validB.getDataList().add(
            new InfluencerData("February", 5, 3, 100, 5, 100));
        validB.getDataList().add(
            new InfluencerData("March", 2, 3, 100, 5, 100)); 

        // E: validB (20) vs validA (35)
        // F: validA == copyA
        Influencer copyA = new Influencer("copyA", "Same as A", "US", "X");
        copyA.getDataList().add(
            new InfluencerData("January", 10, 5, 0, 5, 100));
        copyA.getDataList().add(
            new InfluencerData("February", 5, 5, 0, 5, 100));
        copyA.getDataList().add(
            new InfluencerData("March", 10, 5, 0, 5, 100)); 
        copyA.getDataList().add(
            new InfluencerData("October", 10, 5, 0, 5, 100)); 

        // A: both null
        assertEquals(0, comp.compare(nullA, nullB));
        assertEquals(0, comp2.compare(nullA, nullB));

        // B: first null, second not null
        assertEquals(1, comp.compare(nullA, validA));

        // C: first not null, second null
        assertEquals(-1, comp.compare(validA, nullB));

        // D: both valid, first > second
        assertEquals(-1, comp.compare(validA, validB));

        // E: both valid, first < second
        assertEquals(1, comp.compare(copyA, validA));
        assertEquals(0, comp.compare(copyA, copyA));

        // F: both valid, equal
        assertEquals(-1, comp.compare(validA, copyA));
    }

}
