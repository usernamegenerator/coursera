/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Fundamentals.BagsQueuesandStacks;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public class ListIterator implements Iterator<Item> {
        private Node current = first;

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        public boolean hasNext() {
            return current != null;
        }
    }

    private int N;
    private Node first;

    private class Node {
        Node next;
        Item item;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void add(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    public String toString() {
        return "hello nimei";
    }

    public static void main(String[] args) {
        Bag<String> bag = new Bag<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            bag.add(s);
        }
        StdOut.println("(" + bag.size() + " left on the bag");
        StdOut.println(bag);
        for (String b : bag) {
            StdOut.println(b);
        }
    }
}
