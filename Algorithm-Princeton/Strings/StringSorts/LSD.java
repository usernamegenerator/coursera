/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Strings.StringSorts;

public class LSD {

    public static void sort(String[] a, int W) {
        int N = a.length;
        int R = 256;
        String[] aux = new String[N];

        for (int d = W - 1; d >= 0; d--) {
            int[] count = new int[R + 1];

            // count the frequence
            for (int i = 0; i < N; i++) {
                count[a[i].charAt(d) + 1]++;
            }

            // convert frequence to index
            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }

            // sort
            for (int i = 0; i < N; i++) {
                // put a[i] to the correct location
                aux[count[a[i].charAt(d)]++] = a[i];
            }

            // write back
            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
            }
        }
    }

    public static void main(String[] args) {

    }
}
