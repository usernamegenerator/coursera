/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CircularSuffixArray {


    private int len;
    private int[] index;


    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new java.lang.IllegalArgumentException();
        String[] originalSuffixes;
        String[] sortedSuffixes;
        this.len = s.length();
        index = new int[this.len];
        originalSuffixes = new String[this.len];
        sortedSuffixes = new String[this.len];
        originalSuffixes[0] = s;
        sortedSuffixes[0] = s;
        for (int i = 1; i < this.len; i++) {
            originalSuffixes[i] = rotate(originalSuffixes[i - 1]);
            sortedSuffixes[i] = originalSuffixes[i];
        }
        Arrays.sort(sortedSuffixes);

        for (int i = 0; i < this.len; i++) {
            String temp = sortedSuffixes[i];
            for (int x = 0; x < this.len; x++) {
                if (originalSuffixes[x].equals(temp)) {
                    index[i] = x;
                }
            }
        }
    }

    private String rotate(String s) {
        // StdOut.println("input  = " + s);
        char[] charArray = s.toCharArray();
        char c = charArray[0];
        int i;
        for (i = 1; i < s.length(); i++) {
            charArray[i - 1] = charArray[i];
        }

        charArray[--i] = c;

        String sReturn = new String(charArray);

        // StdOut.println("output  = " + sReturn + "\n");

        return sReturn;
    }

    // length of s
    // must take consitant time in worst case
    public int length() {
        return this.len;
    }

    // returns index of ith sorted suffix
    // must take consitant time in worst case
    public int index(int i) {

        /*
        for (int j = 0; j < s.length(); j++) {
            StdOut.println(originalSuffixes[j]);
        }
        StdOut.println();
        for (int j = 0; j < s.length(); j++) {
            StdOut.println(sortedSuffixes[j]);
        }
        */

        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < csa.length(); i++) {
            StdOut.println(csa.index(i));
        }
    }

}
