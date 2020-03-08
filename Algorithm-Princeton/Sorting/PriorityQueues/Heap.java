/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Sorting.PriorityQueues;

import edu.princeton.cs.algs4.StdOut;

public class Heap {
    public static void sort(Comparable[] a) {
        int n = a.length;

        for (int k = n / 2; k >= 1; k--) {
            sink(a, k, n);
        }
        while (n > 1) {
            exch(a, 1, n--); // index n has the largest element
            sink(a, 1, n);
        }
    }

    private static void sink(Comparable[] a, int k, int N) {
        while (k * 2 <= N) {
            int j = k * 2;
            // find which child is bigger, exch k to the bigger one
            if (j < N && less(a, j, j + 1)) {
                j++;
            }
            // if k is greater than j, then no need to exch
            if (!less(a, k, j)) break;
            exch(a, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] a, int i, int j) {
        return (a[i - 1].compareTo(a[j - 1]) < 0);
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = temp;
    }


    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a, i, i - 1))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] a = {
                "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"
        };
        show(a);
        sort(a);
        assert isSorted(a);
        show(a);
        // expected output:
        // A E E L M O P R S T X
    }
}
