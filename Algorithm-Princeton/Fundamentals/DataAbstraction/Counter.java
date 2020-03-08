/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Fundamentals.DataAbstraction;

import edu.princeton.cs.algs4.StdOut;

public class Counter {
    private final String name;
    private int count;

    public Counter(String id) {
        name = id;
    }

    public void increment() {
        count++;
    }

    public String getName() {
        return name;
    }

    public int tally() {
        return count;
    }
/*
    public String toString() {
        return count + " " + name;
    }
*/

    public static void main(String[] args) {
        Counter heads = new Counter("heads");
        Counter tails = new Counter("tails");

        heads.increment();
        tails.increment();
        heads.increment();

        StdOut.println(heads + " " + tails);
        StdOut.println(heads.tally() + tails.tally());

    }

}
