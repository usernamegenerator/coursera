/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Fundamentals.AnalysisofAlgorithms;

import Fundamentals.ProgrammingModel.BinarySearch;

import java.util.Arrays;

public class ThreeSumFast {
    public static int count(int[] a) {
        int cnt = 0;
        int N = a.length;
        Arrays.sort(a);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (BinarySearch.rank(-(a[i] + a[j]), a) > j) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {

    }
}
