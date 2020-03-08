/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Fundamentals.BagsQueuesandStacks;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Stack<Item> {
    private class Node {
        Node next;
        Item item;
    }

    private int N;
    private Node first;

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public void push(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    public Item pop() {
        Item item = first.item;
        first = first.next;
        N--;
        return item;
    }


    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (!s.equals("-"))
                stack.push(s);
            else if (!stack.isEmpty())
                StdOut.println(stack.pop());
        }
        StdOut.println("(" + stack.size() + " left on the stack");
    }
}
