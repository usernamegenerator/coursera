/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Sorting.Quicksort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Goal: Given an array of N items , find a kth smallest item Ex: Min(k=0), max (k=N-1), median
 * (K=N/2)
 */
public class Select {
    public static Comparable slect(Comparable[] a, int k) {
        StdRandom.shuffle(a);
        int low = 0, high = a.length - 1;
        while (high > low) {
            int j = partition(a, low, high);
            if (j < k) low = j + 1;
            else if (j > k) high = j - 1;
            else return a[k];
        }
        return a[k];
    }

    private static int partition(Comparable[] a, int low, int high) {
        int i = low;
        int j = high + 1;
        Comparable v = a[low];
        while (true) {

            while (less(a[++i], v)) {
                if (i == high) break;
            }
            while (less(v, a[--j])) {
                if (j == low) break;
            }
            if (j <= i)
                break;

            exch(a, i, j);
        }
        exch(a, low, j);
        return j;
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

    }
}
