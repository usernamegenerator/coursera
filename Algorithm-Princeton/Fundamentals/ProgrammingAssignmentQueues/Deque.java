package Fundamentals.ProgrammingAssignmentQueues.Queues;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (this.size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("adding null");
        }
        if (this.isEmpty()) {
            first = new Node();
            first.item = item;
            first.next = null;
            first.prev = null;
            last = first;
        }
        else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            first.prev = null;
            oldfirst.prev = first;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("adding null");
        }
        if (this.isEmpty()) {
            last = new Node();
            last.item = item;
            last.next = null;
            last.prev = null;
            first = last;
        }
        else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.prev = oldlast;
            oldlast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException("removing empty");
        }
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        }
        else {
            last = null;
        }
        size--;
        // StdOut.println(first);
        // StdOut.println(last);
        // StdOut.println(size);
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException("removing empty");
        }
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        }
        else {
            first = null;
        }
        size--;
        // StdOut.println(last);
        // StdOut.println(first);
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return (current != null);
        }

        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException("no more item");
            }
            Item item = current.item;
            current = current.next;
            // first.prev = null; // cann't modify the collection
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("remove not supported");
        }
    }

    public static void main(String[] args) {
        Deque<String> dq = new Deque<String>();
        StdOut.println(dq.isEmpty());
        dq.addFirst("1st");
        dq.addFirst("0st");


        dq.addLast("2nd");
        dq.addLast("3rd");
        dq.addFirst("-1st");
        for (String s : dq) {
            StdOut.println(s);
        }
        StdOut.println("==========");
        StdOut.println(dq.size());
        StdOut.println("remove first = " + dq.removeFirst());
        StdOut.println("remove last = " + dq.removeLast());
        StdOut.println("remove last = " + dq.removeLast());
        StdOut.println("remove first = " + dq.removeFirst());
        StdOut.println("remove first = " + dq.removeFirst());
        // StdOut.println("remove last = " + dq.removeLast());
        dq.addLast("1st");
        dq.addFirst("0st");
        StdOut.println(dq.size());
        for (String s : dq) {
            StdOut.println(s);
        }
    }
}
