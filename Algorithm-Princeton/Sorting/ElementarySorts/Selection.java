/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Sorting.ElementarySorts;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Selection {
    public static void sort(Comparable[] a) {
        int N = a.length;

        // start from the start to the end
        for (int i = 0; i < N; i++) {
            // set current to the min, this is to include the current in the following compares
            // we are going to switch the min to this location
            int min = i;
            // start from next to the current
            for (int j = i + 1; j < N; j++) {
                // iterate through to find the min, from current to the end
                if (less(a[j], a[min])) {
                    min = j;
                }
            }
            // exchange the min to the current location
            exch(a, i, min);
        }
    }

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1]))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] a = In.readStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
