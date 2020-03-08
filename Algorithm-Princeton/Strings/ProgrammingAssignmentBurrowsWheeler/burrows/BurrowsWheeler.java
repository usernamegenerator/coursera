/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int first;
        for (int i = 0; i < csa.length(); i++) {
            if (csa.index(i) == 0) {
                first = i;
                BinaryStdOut.write(first, 32);
            }
        }
        for (int i = 0; i < csa.length(); i++) {
            int idx = csa.index(i) - 1;
            if (idx < 0)
                idx = csa.length() - 1;
            char c = s.charAt(idx);
            BinaryStdOut.write(c);
        }

        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        /*
        // read first and t[]
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int N = s.length();
        // allocate the ending array
        char[] t = new char[N];
        for (int i = 0; i < N; i++)
            t[i] = s.charAt(i);
        // allocate an array to store the next array
        int[] next = new int[N];
        // allocate an array to store 1st char of the sorted suffixes
        char[] f = new char[N];
        // an array to store the total count for each character
        int[] count = new int[256 + 1];
        // do key-index counting, but store values in the next[] array
        for (int i = 0; i < N; i++)
            count[t[i] + 1]++;
        for (int r = 0; r < 256; r++)
            count[r + 1] += count[r];
        for (int i = 0; i < N; i++) {
            next[count[t[i]]] = i;
            f[count[t[i]]++] = t[i];
        }
        // write out
        int current = first;
        for (int i = 0; i < N; i++) {
            BinaryStdOut.write(f[current]);
            current = next[current];
        }
        // close output
        BinaryStdOut.close();
        */
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        }
        else if (args[0].equals("+")) {
            inverseTransform();
        }
    }

}
