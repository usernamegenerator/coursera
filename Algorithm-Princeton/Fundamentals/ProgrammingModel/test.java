/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Fundamentals.ProgrammingModel;

import edu.princeton.cs.algs4.StdDraw;

public class test {
    public static void main(String[] args) {
        //StdDraw.point(1, 1);
        //StdDraw.line(1, 1, 2, 2);
        int N = 100;
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N * N);
        StdDraw.setPenRadius(.01);
        for (int i = 1; i <= N; i++) {
            StdDraw.point(i, i);
            StdDraw.point(i, i * i);
            StdDraw.point(i, i * Math.log(i));

        }
    }
}
