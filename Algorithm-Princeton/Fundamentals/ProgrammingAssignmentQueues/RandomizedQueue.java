package Fundamentals.ProgrammingAssignmentQueues.Queues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

// can't use linked list, because it can't get constant time random dequeue operation
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n; // the last item, aka number of items
    private Item[] queue; // =(Item)new Object[]

    public RandomizedQueue()                // construct an empty randomized queue
    {
        // n always point to an empty hole, which is used to fill in next item
        // queue.length returns the array of holes of the elements, the hole may not be filled
        n = 0;
        queue = (Item[]) new Object[1];
    }

    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return (n == 0);
    }

    public int size()                        // return the number of items on the randomized queue
    {
        return n;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    public void enqueue(Item item)           // add the item to last
    {
        if (item == null)
            throw new java.lang.IllegalArgumentException("enqueuing null");

        if (n == queue.length) {
            resize(queue.length * 2);
        }
        queue[n++] = item;
    }

    public Item dequeue()                    // remove and return a random item
    {
        if (n == 0) {
            throw new java.util.NoSuchElementException("dequeuing empty");
        }
        if (n <= queue.length / 4) {
            resize(queue.length / 2);
        }
        int rdm = StdRandom.uniform(n);
        Item item = queue[rdm];
        queue[rdm] = queue[--n];
        queue[n] = null;
        return item;
    }

    public Item sample()                     // return a random item (but do not remove it)
    {
        if (n == 0) {
            throw new java.util.NoSuchElementException("sampling empty");
        }
        return queue[StdRandom.uniform(n)];
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {

        private final Item[] copyWithNull;
        private final Item[] copyNoNull;
        private int current;

        public QueueIterator() {
            copyWithNull = queue.clone();
            copyNoNull = (Item[]) new Object[n];
            for (int i = 0, j = 0; i < copyWithNull.length; i++) {
                if (copyWithNull[i] == null)
                    continue;
                copyNoNull[j] = copyWithNull[i];
                j++;
            }
            current = 0;
            StdRandom.shuffle(copyNoNull);
        }

        public boolean hasNext() {
            if (current < n) {
                return true;
            }
            return false;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("no next");
            }
            Item item = copyNoNull[current++];
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("not supported");
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        StdOut.println(rq.isEmpty());

        for (int i = 0; i < 800; i++)
            rq.enqueue(Integer.toString(i));

        for (String s : rq) {
            StdOut.print(s + " ");
        }

        StdOut.println();

        for (int i = 0; i < 800; i++)
            rq.dequeue();

        StdOut.println(rq.isEmpty());
        for (String s : rq) {
            StdOut.print(s + " ");
        }

    }
}
