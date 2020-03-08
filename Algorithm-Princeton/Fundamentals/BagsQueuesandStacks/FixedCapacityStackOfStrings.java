/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Fundamentals.BagsQueuesandStacks;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FixedCapacityStackOfStrings {
    private String[] a;
    private int N;

    public FixedCapacityStackOfStrings(int cap) {
        a = new String[cap];
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(String s) {
        a[N++] = s;
    }

    public String pop() {
        return a[--N];
    }

    public static void main(String[] args) {
        FixedCapacityStackOfStrings s;
        s = new FixedCapacityStackOfStrings(100);

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                s.push(item);
            else if (!s.isEmpty()) {
                StdOut.println(s.pop() + " ");
            }
        }
        System.out.println("(" + s.size() + " left on stack)");
    }
}
