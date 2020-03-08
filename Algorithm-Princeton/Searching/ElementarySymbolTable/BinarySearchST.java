/******************************************************************************
 *  Compilation:  javac BinarySearchST.java
 *  Execution:    java BinarySearchST
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/31elementary/tinyST.txt
 *
 *  Symbol table implementation with binary search in an ordered array.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java BinarySearchST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 ******************************************************************************/

package Searching.ElementarySymbolTable;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] vals;
    private int n;

    public BinarySearchST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }

    public BinarySearchST() {
        this(2);
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return (n == 0);
    }

    // iteration binary search the rank of the key
    public int rank(Key key) {
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = keys[mid].compareTo(key);
            if (cmp < 0)
                high = mid - 1;
            else if (cmp > 0)
                low = mid + 1;
            else return mid;
        }
        // didn't find, return low
        // in this applicaton, low is the index to put the new key
        return low;
    }

    // recursive binary search rank
    public int rank(Key key, int low, int high) {
        // base condition, didn't find, return low
        if (high < low)
            return low;
        int mid = low + (high - low) / 2;
        int cmp = keys[mid].compareTo(key);
        if (cmp < 0)
            return rank(key, low, mid - 1);
        else if (cmp > 0)
            return rank(key, mid + 1, high);
        else return mid;
    }

    public Value get(Key key) {
        if (isEmpty()) return null;
        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0)
            return vals[i];
        else
            return null;
    }

    // always put in order
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {
            delete(key);
            return;
        }

        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }

        if (n == keys.length)
            resize(2 * keys.length);

        // no current key, need to insert
        for (int j = n; j > i; j--) {
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = key;
        vals[i] = val;
        n++;

        assert check();
    }

    public Key min() {
        return keys[0];
    }

    public Key max() {
        return keys[n - 1];
    }

    public Key select(int k) {
        return keys[k];
    }

    // Returns the smallest key in this symbol table greater than or equal to key
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        int i = rank(key);
        if (i == n) return null;
        return keys[i];
    }

    // Returns the largest key in this symbol table less than or equal to key.
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        int i = rank(key);
        if (i < n && key.compareTo(keys[i]) == 0) return keys[i];
        if (i == 0) return null;
        return keys[i - 1];
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (isEmpty()) return;
        int i = rank(key);
        // not the table
        if (i == n || keys[i].compareTo(key) != 0)
            return;

        for (int j = i; j < n - 1; i++) {
            keys[j] = keys[j + 1];
            vals[j] = vals[j + 1];
        }
        n--;
        keys[n] = null;
        keys[n] = null;

        if (n <= keys.length / 4 && n >= 0)
            resize(keys.length / 2);
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Key[] tempk = (Key[]) new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        keys = tempk;
        vals = tempv;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public Iterable<Key> keys(Key low, Key high) {
        int i = rank(low);
        int j = rank(high);
        Queue<Key> keyQ = new Queue<Key>();
        for (int k = i; k < j; k++) {
            keyQ.enqueue(keys[k]);
        }
        if (contains(high)) {
            keyQ.enqueue(keys[j]);
        }
        return keyQ;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    private boolean check() {
        return isSorted() && rankCheck();
    }

    // are the items in the array in ascending order?
    private boolean isSorted() {
        for (int i = 1; i < size(); i++)
            if (keys[i].compareTo(keys[i - 1]) < 0) return false;
        return true;
    }

    // check that rank(select(i)) = i
    private boolean rankCheck() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (int i = 0; i < size(); i++)
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) return false;
        return true;
    }

    public static void main(String[] args) {
        BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        assert st.check();
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}
