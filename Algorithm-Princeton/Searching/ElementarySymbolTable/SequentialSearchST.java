/******************************************************************************
 *  Compilation:  javac SequentialSearchST.java
 *  Execution:    java SequentialSearchST
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/31elementary/tinyST.txt
 *
 *  Symbol table implementation with sequential search in an
 *  unordered linked list of key-value pairs.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java SequentialSearchST < tiny.txt
 *  L 11
 *  P 10
 *  M 9
 *  X 7
 *  H 5
 *  C 4
 *  R 3
 *  A 8
 *  E 12
 *  S 0
 *
 ******************************************************************************/

package Searching.ElementarySymbolTable;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SequentialSearchST<Key, Value> {
    private Node first;
    private int size;

    private class Node {
        Key key;
        Value val;
        Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    // value paired with key (null if key is absent)
    public Value get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (x.key.equals(key)) {
                return x.val;
            }
        }
        return null;
    }

    // put key-value pair into the table (remove key from table if value is null)
    public void put(Key key, Value val) {
        for (Node x = first; x != null; x = x.next) {
            if (x.key.equals(key)) {
                x.val = val;
                size++;
                return;
            }
        }
        Node oldFirst = first;
        first = new Node(key, val, oldFirst);
    }

    // is there a value paird with key?
    public boolean contains(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (x.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    // is the table empty
    public boolean isEmpty() {
        return (this.size == 0);
    }

    // number of key-value pairs in the table
    public int size() {
        return size;
    }

    // all the keys in the table
    public Iterable<Key> keys() {
        Queue<Key> keyQ = new Queue<Key>();
        for (Node x = first; x != null; x = x.next) {
            keyQ.enqueue(x.key);
        }
        return keyQ;
    }

    // remove key (and its value) from table
    public void delete(Key key) {
        if (key == null)
            throw new IllegalArgumentException("argument to delete() is null");
        first = delete(first, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        // if current node is the one to delete,
        // return current's next
        if (key.equals(x.key)) {
            size--;
            return x.next;
        }
        // else keep finding
        // link x's next to x.next's next if x.next is the key
        x.next = delete(x.next, key);

        // return first, first never changed
        return x;
    }

    public static void main(String[] args) {
        SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}
