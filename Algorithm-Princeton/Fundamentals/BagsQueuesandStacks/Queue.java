/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Fundamentals.BagsQueuesandStacks;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Queue<Item> {
    private class Node {
        Item item;
        Node next;
    }

    private Node first;
    private Node last;
    private int N;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    //push to the last
    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldlast.next = last;
        }
        N++;
    }

    public Item dequeue() {
        if (!this.isEmpty()) {
            Item item = first.item;
            first = first.next;
            N--;
            if (this.isEmpty()) {
                last = null;
            }
            return item;
        }
        else {
            return null;
        }
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (!s.equals("-")) {
                queue.enqueue(s);
            }
            else if (!queue.isEmpty()) {
                StdOut.println(queue.dequeue() + " ");
            }
        }
        StdOut.println("(" + queue.size() + " left on queue");
    }
}
