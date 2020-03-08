package Sorting.PriorityQueues;

// https://stackoverflow.com/questions/8537500/java-the-meaning-of-t-extends-comparablet
// This means that the type parameter must support comparison with other instances of its own type, via the Comparable interface.

public class UnorderedArrayMaxPQ<Key extends Comparable<Key>> {

    private Key[] pq;
    private int n;

    // create a max priority queue
    public UnorderedArrayMaxPQ() {

    }

    // create a max priority queue, with capacity max
    public UnorderedArrayMaxPQ(int max) {
        pq = (Key[]) new Comparable[max];
        n = 0;
    }

    // create a max priority queue with elements in a[]
    public UnorderedArrayMaxPQ(Key[] a) {
        pq = (Key[]) new Comparable[a.length];
        for (int i = 0; i < a.length; i++) {
            pq[i] = a[i];
        }
    }

    // insert an element to priority queue
    public void Insert(Key v) {
        pq[n++] = v;
    }

    public boolean less(int i, int j) {
        // compareTo method shall be implemented in Key data struct,
        // which shall implement the Comparable interface
        return (pq[i].compareTo(pq[j]) < 0);
    }

    public void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    // return the max elemement
    public Key max() {
        int max = 0; // max as index
        for (int i = 1; i < n; i++) {
            if (less(max, i)) {
                max = i;
            }
        }
        return pq[max];
    }

    // delete and return the max element
    public Key delMax() {
        int max = 0;
        for (int i = 1; i < n; i++) {
            if (less(max, i)) {
                max = i;
            }
        }
        exch(max, n - 1); // exchange the max to the end of the array
        return pq[--n];
    }

    // return true if priority queue is empty
    boolean isEmpty() {
        return (n == 0);
    }

    // return the number of elements in the queue
    public int size() {
        return n;
    }


    public static void main(String[] args) {

    }
}
