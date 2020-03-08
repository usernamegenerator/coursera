/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

// this shit program only runs in linux enviroment with java-algs4 installed,
// see https://lift.cs.princeton.edu/java/linux/
// install in Bash, linux subsystem
// set terminal to Bash

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        // Initialize the sequence by making the ith character in the sequence equal to the ith extended ASCII character.
        char[] ascii = new char[256];
        for (int i = 0; i < 256; i++) {
            ascii[i] = (char) i;
        }

        while (!BinaryStdIn.isEmpty()) {
            // read each 8-bit character c from standard input, one at a time
            char c = BinaryStdIn.readChar();
            int x;
            // output the 8-bit index in the sequence where c appears
            for (x = 0; x < 256; x++) {
                if (ascii[x] == c) {
                    BinaryStdOut.write(x, 8);
                    break;
                }
            }
            // move c to the front
            for (int i = x; i > 0; i--) {
                ascii[i] = ascii[i - 1];
            }
            ascii[0] = c;
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        // Initialize an ordered sequence of 256 characters, where extended ASCII character i appears ith in the sequence
        char[] ascii = new char[256];
        for (int i = 0; i < 256; i++) {
            ascii[i] = (char) i;
        }

        while (!BinaryStdIn.isEmpty()) {
            // read each 8-bit character i (but treat it as an integer between 0 and 255) from standard input one at a time
            char c = BinaryStdIn.readChar();
            int x = (int) c;

            // write the ith character in the sequence;
            BinaryStdOut.write(ascii[x]);

            // move that character to the front
            char thatChar = ascii[x];
            for (int i = x; i > 0; i--) {
                ascii[i] = ascii[i - 1];
            }
            ascii[0] = thatChar;

        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        }
        else if (args[0].equals("+")) {
            decode();
        }
    }

}
