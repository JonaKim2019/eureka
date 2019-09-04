package bearmaps;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private Node[] pQueue;
    private HashMap<T, Double> setMapPriority;
    private HashMap<T, Integer> setMapIndexValue;
    private int size;

    public ArrayHeapMinPQ() {
        pQueue = new ArrayHeapMinPQ.Node[16];
        setMapPriority = new HashMap<>();
        setMapIndexValue = new HashMap<>();
        // Make the Heap neater
        pQueue[0] = null;
        // Number of elements in the heap
        size = 0;
    }

    private class Node {
        private T myItem;
        private double myPriority;

        private Node(T item, double priority) {
            myItem = item;
            myPriority = priority;
        }

        public double getPriority() {
            return myPriority;
        }

        public T getItem() {
            return myItem;
        }

        public void setPriority(double priority) {
            this.myPriority = priority;
        }

    }

    // Returns the index of the keyNode to the left of the keyNode at i.
    private static int leftIndex(int i) {
        return i * 2;
    }

    // Returns the index of the keyNode to the right of the keyNode at i.
    private static int rightIndex(int i) {
        return (i * 2) + 1;
    }

    // Returns the index of the keyNode that is the parent of the keyNode at i.
    private static int parentIndex(int i) {
        return i / 2;
    }

    // Returns true if the PQ contains the given item.
    public boolean contains(T item) {
        if (size() == 0) {
            return false;
        }
        if (setMapPriority.containsKey(item)) {
            return true;
        }
        return false;
    }

    // Adds an item of type T with the given priority.
    // If the item already exists, throw an IllegalArgumentException.
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        /* If the array is totally full, resize. */
        if (size == pQueue.length - 1) {
            resize(pQueue.length * 2);
        }
        setMapPriority.put(item, priority);
        Node node = new Node(item, priority);
        size += 1;
        setMapIndexValue.put(item, size);
        if (size() == 1) {
            pQueue[1] = node;
        } else {
            pQueue[size] = node;
            swim(size);
        }
    }

    //Returns the item with smallest priority.
    // If no items exist, throw a NoSuchElementException.
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return pQueue[1].myItem;
    }

    // Removes and returns the item with smallest priority.
    // If no items exist, throw a NoSuchElementException.
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        double refactor = ((double) size / (double) pQueue.length);
        if (refactor <= 0.25) {
            resize(pQueue.length / 2);
        }
        Node node = pQueue[1];
        if (size() == 1) {
            setMapPriority.remove(pQueue[1].getItem());
            setMapIndexValue.remove(pQueue[1].getItem());
            pQueue[1] = null;
            size -= 1;
        } else {
            setMapPriority.remove(pQueue[1].getItem());
            setMapIndexValue.remove(pQueue[1].getItem());
            pQueue[1] = pQueue[size];
            pQueue[size] = null;
            size -= 1;

            if (size != 0) {
                sink(1);
            }
        }
        return node.getItem();
    }

    // Swaps the values of the index position
    private void swap(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        setMapIndexValue.put(node1.getItem(), index2);
        setMapIndexValue.put(node2.getItem(), index1);
        pQueue[index1] = node2;
        pQueue[index2] = node1;

    }

    private Node getNode(int index) {
        if (!withInBounds(index)) {
            return null;
        }
        return pQueue[index];
    }

    // Checks to see if the index is a valid value
    private boolean withInBounds(int index) {
        if ((index > size) || (index < 1)) {
            return false;
        }
        return true;
    }

    private void sink(int index) {
        if (validateSink(index)) {
            return;
        }

        // if only left child exists and
        if ((pQueue[leftIndex(index)] != null) && (pQueue[rightIndex(index)] == null)) {
            if (pQueue[leftIndex(index)].getPriority() < pQueue[index].getPriority()) {
                int sinkIndex = getMinPriority(index, leftIndex(index));
                if (sinkIndex > size()) {
                    return;
                }
                swap(index, sinkIndex);
                sink(sinkIndex);
            }
        }
        // if both child exists
        if ((pQueue[leftIndex(index)] != null) && (pQueue[rightIndex(index)] != null)) {
            int left = leftIndex(index);
            int right = rightIndex(index);

            int sinkIndex = getMinPriority(left, right);
            if (sinkIndex > size()) {
                return;
            }
            swap(index, sinkIndex);
            sink(sinkIndex);
        }
        // if both children exists and all the priorities are same
        if (pQueue[leftIndex(index)].getPriority() == pQueue[index].getPriority()
                && pQueue[rightIndex(index)].getPriority() == pQueue[index].getPriority()) {
            return;

        }
    }

    private void swim(int index) {
        if (validateSwim(index)) {
            return;
        }
        int parentIndex = parentIndex(index);
        Node indexNode = pQueue[index];
        Node parent = pQueue[parentIndex];
        if (parent == null || index == 0) {
            return;
        } else if (indexNode.getPriority() < parent.getPriority()) {
            swap(index, parentIndex);
            swim(parentIndex);
        }
    }

    private int getMinPriority(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        if (node1 == null) {
            return index2;
        } else if (node1.getPriority() < node2.getPriority()) {
            return index1;
        } else if (node2 == null) {
            return index1;
        } else {
            return index2;
        }
    }
    private boolean validateSwim(int index) {
        if (index > size) {
            return true;
        }
        if (pQueue[index] == null) {
            return true;
        }
        if (index < 1) {
            return true;
        }
        return false;
    }
    private boolean validateSink(int index) {
        if (index > size) {
            return true;
        }
        if (pQueue[index] == null) {
            return true;
        }
        if (index < 1) {
            return true;
        }
        if ((leftIndex(index) > size())) {
            return true;
        }
        if ((leftIndex(index) > size())
                && (rightIndex(index) > size())) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        double newPriorty = priority;
        int oldIndex = setMapIndexValue.get(item);
        setMapPriority.put(item, priority);
        pQueue[oldIndex].setPriority(priority);
        sink(oldIndex);
        swim(oldIndex);
    }

    private void resize(int capacity) {
        Node[] tempNodeList = new ArrayHeapMinPQ.Node[capacity];
        for (int i = 1; i <= size(); i++) {
            tempNodeList[i] = pQueue[i];
        }
        pQueue = tempNodeList;
    }
    /*
    public static void main(String[] args) {

        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        Long startTime =  System.currentTimeMillis();
        for (int i = 1; i < 100000; i ++ ){
            pq.add("x" + i, i);
        }
        Long stopTime =  System.currentTimeMillis();
        System.out.println(stopTime - startTime);

        Long startTime1 =  System.currentTimeMillis();
        for (int i = 1; i < 100000; i ++ ){
            pq.removeSmallest();
        }
        Long stopTime1 =  System.currentTimeMillis();
        System.out.println(stopTime1 - startTime1);

    }
    */
}
