/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Fundamentals.ProgrammingAssignmentPercolation.percolation;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] fractionOfOpenSites;
    private final int n;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException("<0");

        this.n = n;
        this.fractionOfOpenSites = new double[trials];

        for (int t = 0; t < trials; t++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                // StdOut.println("open + " + randomRow + "," + randomCol + perc.percolates());
                perc.open(randomRow, randomCol);
            }
            fractionOfOpenSites[t] = (double) perc.numberOfOpenSites() / (double) (n * n);
        }
    }    // perform trials independent experiments on an n-by-n grid

    public double mean() {
        return StdStats.mean(fractionOfOpenSites);
    }                          // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(fractionOfOpenSites);
    }                        // sample standard deviation of percolation threshold

    // 95%: Z = 1.960
    // x bar +- Z * s / sqrt(n)
    // where X bar is the sample mean
    // Z is the Z value from the table
    // s is the standard deviation
    // n is the number of observations
    public double confidenceLo() {
        return (this.mean() - CONFIDENCE_95 * this.stddev() / Math.sqrt(n));
    }                  // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return (this.mean() + CONFIDENCE_95 * this.stddev() / Math.sqrt(n));
    }                 // high endpoint of 95% confidence interval

    /*
     % java-algs4 PercolationStats 200 100
     mean                    = 0.5929934999999997
     stddev                  = 0.00876990421552567
     95% confidence interval = [0.5912745987737567, 0.5947124012262428]

     % java-algs4 PercolationStats 200 100
     mean                    = 0.592877
     stddev                  = 0.009990523717073799
     95% confidence interval = [0.5909188573514536, 0.5948351426485464]


     % java-algs4 PercolationStats 2 10000
     mean                    = 0.666925
     stddev                  = 0.11776536521033558
     95% confidence interval = [0.6646167988418774, 0.6692332011581226]

     % java-algs4 PercolationStats 2 100000
     mean                    = 0.6669475
     stddev                  = 0.11775205263262094
     95% confidence interval = [0.666217665216461, 0.6676773347835391]
     */
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats percStats = new PercolationStats(n, trials);
        StdOut.println("mean = " + percStats.mean());
        StdOut.println("stddev = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats
                .confidenceHi() + "]");
    }
}
