/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Strings.StringSorts;

public class MSD {
    private static int R = 256;
    private static final int M = 15;
    private static String[] aux;

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1;
    }

    public static void sort(String[] a) {
        int N = a.length;
        aux = new String[N];
        sort(a, 0, N - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        // there is no such call in Insertion.sort
        /*
        if (hi <= lo + M) {
            Insertion.sort(a, lo, hi, d);
            return;
        }
        */

        // count array equals to the total number of chars, plus 2
        // 1 for the -1, for string end
        // another 1 for adding the frequency to index
        // for example, if R = 26. Alphabetic 'a' is at 0 in Alphabet.LOWERCASE
        int[] count = new int[R + 2];

        // count the frequency
        // if the string is already ended, charAt(a[i], d) returns -1. count[1]++

        // count[0] is not used in this step
        // count[1] counts the number of strings that have the length of d
        // count[r] to count[R+1] is the number of strings that has this letter in dth position
        for (int i = lo; i <= hi; i++) {
            count[charAt(a[i], d) + 2]++;
        }

        // convert the frequency to the index
        for (int r = 0; r < R + 1; r++) {
            count[r + 1] += count[r];
        }
        // at this moment, count[r+1] is the start position to put the dth letter

        // put the string to the correct index
        // iterate through the strings
        // get the charAt(a[i], d), the position of the char in count[]
        // put the +1 offset
        // count[] returns the correct sorted location in aux
        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }

        // write back
        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }

        // sort every ket in the string iterately
        // lo + count[r] is the start location of all the strings started with a letter
        // lo + count[r+1] is the start location of all the string started with next letter
        for (int r = 0; r < R; r++) {
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }

    }

    public static void main(String[] args) {

    }
}
