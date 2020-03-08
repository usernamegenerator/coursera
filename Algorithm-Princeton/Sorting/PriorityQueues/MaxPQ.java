/******************************************************************************
 *  Compilation:  javac MaxPQ.java
 *  Execution:    java MaxPQ < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/24pq/tinyPQ.txt
 *
 *  Generic max priority queue implementation with a binary heap.
 *  Can be used with a comparator instead of the natural order,
 *  but the generic Key type must still be Comparable.
 *
 *  % java MaxPQ < tinyPQ.txt
 *  Q X P (6 left on pq)
 *
 *  % java MaxPQ
 *  P Q E - X A M - P L E -
 *  Q X P (6 left on pq)
 *
 *  We use a one-based array to simplify parent and child calculations.
 *
 *  Can be optimized by replacing full exchanges with half exchanges
 *  (ala insertion sort).
 *
 ******************************************************************************/

package Sorting.PriorityQueues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int n;

    public MaxPQ() {
        this(1);
    }

    public MaxPQ(int maxN) {
        // from 1 to N, because 0 can't mutiply
        pq = (Key[]) new Comparable[maxN + 1];
        n = 0; // do not use 0, next will be ++n
    }

    // practice 2.4.19
    public MaxPQ(Key[] keys) {
        n = keys.length;
        pq = (Key[]) new Comparable[keys.length + 1];
        for (int i = 0; i < n; i++) {
            pq[i + 1] = keys[i];
        }
        // start from k = n / 2, this skips all the leaves, aka, the bottom level
        for (int k = n / 2; k >= 1; k--) {
            sink(k);
        }

        assert isMaxHeap();
    }

    // practice 2.4.22
    public void resize(int capacity) {
        assert capacity > n;
        Key[] temp = (Key[]) new Comparable[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public boolean isMaxHeap() {
        return isMaxHeap(1);
    }

    public boolean isMaxHeap(int root) {
        if (root > n) return true;
        int left = root * 2;
        int right = root * 2 + 1;
        if (less(root, left) && left <= n) return false;
        if (less(root, right) && right <= n) return false;
        return isMaxHeap(left) && isMaxHeap(right);
    }

    public boolean isEmpty() {
        return (n == 0);
    }

    public int size() {
        return n;
    }

    public void insert(Key v) {
        if (n == pq.length - 1) resize(2 * pq.length);

        // next item = v;
        pq[++n] = v;
        // swim n up to the proper location
        swim(n);

        assert isMaxHeap();
    }

    public Key delMax() {
        // get the max from the root
        Key max = pq[1];
        // exch the root to the last
        exch(1, n);
        // decrement the size
        n--;
        // remove the last
        pq[n + 1] = null;
        // sink the root(previous last) to the proper location
        sink(1);

        if ((n > 0 && n == (pq.length - 1) / 4))
            resize(pq.length / 2);

        // return the value
        return max;
    }

    public void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    public void sink(int k) {
        // as long as k has children
        while (k * 2 < n) {
            int j = k * 2;
            // between left and right child, find the bigger one to promote
            if (j < n && less(j, j + 1)) {
                j++;
            }
            // k is greater than j, good
            if (!less(k, j)) break;
            // else, k is less than j, sink k to j's position
            // j is the bigger one between the two child
            exch(k, j);
            k = j;
        }
    }

    public boolean less(int i, int j) {
        return (pq[i].compareTo(pq[j]) < 0);
    }

    public void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    public static void main(String[] args) {
        MaxPQ<String> pq = new MaxPQ<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) pq.insert(item);
            else if (!pq.isEmpty()) StdOut.print(pq.delMax() + " ");
        }
        StdOut.println("(" + pq.size() + " left on pq)");
    }
}
