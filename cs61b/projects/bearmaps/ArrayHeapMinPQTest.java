package bearmaps;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void testAddOnceAndRemoveOnce() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("a", 1);
        String removed = pq.removeSmallest();
        assertEquals("a", removed);
    }

    @Test
    public void testAddAndRemoveAll() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("a", 1);
        pq.add("b", 2);
        pq.add("c", 3);
        pq.add("d", 4);
        pq.add("e", 5);

        String removed1 = pq.removeSmallest();
        assertEquals("a", removed1);

        String removed2 = pq.removeSmallest();
        assertEquals("b", removed2);

        String removed3 = pq.removeSmallest();
        assertEquals("c", removed3);

        String removed4 = pq.removeSmallest();
        assertEquals("d", removed4);

        String removed5 = pq.removeSmallest();
        assertEquals("e", removed5);

    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();

        pq.add("a", 1);
        pq.add("b", 2);
        pq.add("c", 3);
        pq.add("d", 4);
        pq.add("e", 5);


        pq.changePriority("e", 0);
        String removed = pq.removeSmallest();
        assertEquals("e", removed);

        pq.changePriority("d", -1);
        String removed2 = pq.removeSmallest();
        assertEquals("d", removed2);
    }

    @Test
    public void testAdd() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();

        pq.add("a", 1);
        pq.add("b", 2);
        pq.add("c", 3);
        pq.add("d", 4);
        pq.add("e", 5);

        /*
        assertEquals("a", pq.pQueue[1].getItem());
        assertEquals("b", pq.pQueue[2].getItem());
        assertEquals("c", pq.pQueue[3].getItem());
        assertEquals("d", pq.pQueue[4].getItem());
        assertEquals("e", pq.pQueue[5].getItem());
        */

    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        //Long startTime = System.currentTimeMillis();

        pq.add("a", 1);
        pq.add("b", 2);
        pq.add("c", 3);
        pq.add("d", 4);
        pq.add("e", 5);

        assertEquals(true, pq.contains("a"));
    }

    @Test
    public void testIndexing() {
        /*
        assertEquals(6, leftIndex(3));
        assertEquals(10, leftIndex(5));
        assertEquals(7, rightIndex(3));
        assertEquals(11, rightIndex(5));

        assertEquals(3, parentIndex(6));
        assertEquals(5, parentIndex(10));
        assertEquals(3, parentIndex(7));
        assertEquals(5, parentIndex(11));
        */
    }

    @Test
    public void testSwim() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        for (int i = 1; i <= 7; i += 1) {
            pq.add("l" + i, i);
        }
        /*
        pq.pQueue[6].myPriority = 0;
        System.out.println("PQ before swimming:");
        System.out.println(pq);

        // Swim the root up
        pq.swim(6);
        System.out.println("PQ after swimming:");
        System.out.println(pq);
        assertEquals("l6", pq.pQueue[1].myItem);
        assertEquals("l2", pq.pQueue[2].myItem);
        assertEquals("l1", pq.pQueue[3].myItem);
        assertEquals("l4", pq.pQueue[4].myItem);
        assertEquals("l5", pq.pQueue[5].myItem);
        assertEquals("l3", pq.pQueue[6].myItem);
        assertEquals("l7", pq.pQueue[7].myItem);
        */
    }

    @Test
    public void testSink() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        for (int i = 1; i <= 7; i += 1) {
            pq.add("l" + i, i);
        }
        /*
        pq.pQueue[1].myPriority = 10;
        System.out.println("PQ before sinking:");
        System.out.println(pq);

        // Sink the root
        pq.sink(1);
        System.out.println("PQ after sinking:");
        System.out.println(pq);
        assertEquals("l2", pq.pQueue[1].myItem);
        assertEquals("l4", pq.pQueue[2].myItem);
        assertEquals("l3", pq.pQueue[3].myItem);
        assertEquals("l1", pq.pQueue[4].myItem);
        assertEquals("l5", pq.pQueue[5].myItem);
        assertEquals("l6", pq.pQueue[6].myItem);
        assertEquals("l7", pq.pQueue[7].myItem);
        */
    }
}
