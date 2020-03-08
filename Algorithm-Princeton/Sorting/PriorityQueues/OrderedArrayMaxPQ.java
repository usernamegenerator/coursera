/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Sorting.PriorityQueues;

public class OrderedArrayMaxPQ<Key extends Comparable<Key>> {

    private Key[] pq;
    private int n;

    // create a max priority queue
    public OrderedArrayMaxPQ() {

    }

    // create a max priority queue, with capacity max
    public OrderedArrayMaxPQ(int max) {
        pq = (Key[]) new Comparable[max];
        n = 0;
    }

    // create a max priority queue with elements in a[]
    public OrderedArrayMaxPQ(Key[] a) {
        pq = (Key[]) new Comparable[a.length];
        for (int i = 0; i < a.length; i++) {
            pq[i] = a[i];
        }
    }

    // insert an element to priority queue
    public void Insert(Key v) {
        int i;
        for (i = n - 1; i >= 0; i--) {
            if (pq[i].compareTo(v) > 0) {
                pq[i + 1] = pq[i];
            }
            else {
                break;
            }
        }
        pq[i + 1] = v;
        n++;
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
        return pq[n - 1];
    }

    // delete and return the max element
    public Key delMax() {
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
