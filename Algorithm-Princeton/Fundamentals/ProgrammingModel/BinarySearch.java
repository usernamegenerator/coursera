//run
//java BinarySearch tinyW.txt < tinyT.txt
//tinyW.txt contains the array
//tinyT.txt contains the keys to search in the array

package Fundamentals.ProgrammingModel;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BinarySearch {
    public static int rank(int key, int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] whitelist = In.readInts(args[0]);
        /*
        File file = new File("tinyW.txt");
        Scanner scanner = new Scanner(file);
        int[] whitelist = new int[100];
        int i = 0;
        while (scanner.hasNextInt()) {
            whitelist[i++] = scanner.nextInt();
        }
        */
        Arrays.sort(whitelist);

        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (rank(key, whitelist) < 0)
                StdOut.println(key);
        }
        /*
        Scanner scanner2 = new Scanner(System.in);
        while (scanner2.hasNext()) {
            int key = scanner2.nextInt();
            if (rank(key, whitelist) < 0)
                System.out.println(key);
        }
        */
    }
}
