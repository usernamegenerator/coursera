/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Fundamentals.ProgrammingAssignmentPercolation.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final boolean OPEN = true;
    private static final boolean BLOCKED = false;
    private final int virtualTop;
    private final int virtualBotoom;
    private boolean[] grid;
    private final int n;
    private final WeightedQuickUnionUF uf;
    private int numOfOpens = 0;


    /* By convention, the row and column indices are integers between 1 and n,
    where (1, 1) is the upper-left site: Throw a java.lang.IllegalArgumentException
    if any argument to open(), isOpen(), or isFull() is outside its prescribed range.
    The constructor should throw a java.lang.IllegalArgumentException if n ≤ 0.*/
    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("n must be greater than 0");
        }

        // + 2 for virtualTop and virtualBottom
        uf = new WeightedQuickUnionUF(n * n + 2);
        this.n = n;
        // open the grid
        this.grid = new boolean[n * n];

        for (int idx = 0; idx < n * n; idx++) {
            this.grid[idx] = BLOCKED;
        }

        virtualTop = n * n;
        virtualBotoom = n * n + 1;
    }

    /*
     *  1  2  3  4  5
     *1 0  1  2  3  4
     *2 5  6  7  8  9
     *3 10 11 12 13 14
     *4 15 16 17 18 19
     *5 20 21 22 23 24
     * */

    private int translateFrom2Dto1D(int row, int col) {
        int idx = (row - 1) * this.n + (col - 1);
        return idx;
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new java.lang.IllegalArgumentException("open");
        }
        int left = col - 1;
        int right = col + 1;
        int up = row - 1;
        int down = row + 1;

        int idx = translateFrom2Dto1D(row, col);
        // StdOut.println("open + " + row + " " + col + ", idx = " + idx);

        if (grid[idx] == BLOCKED) {
            // open the block
            grid[idx] = OPEN;
            numOfOpens++;
            // check left(colIdx-1), right(colIdx+1), up(rowIdx-1) and down(rowIdx+1)
            // to connect to any neibour open site

            // top row open site, connect to virtual top
            if (row == 1) {
                uf.union(idx, virtualTop);
            }
            // bottom row open site, connec to virtual bottom
            if (row == this.n) {
                uf.union(idx, virtualBotoom);
            }
            // check up
            if (row > 1) {
                if (isOpen(up, col)) {
                    uf.union(idx, translateFrom2Dto1D(up, col));
                }
            }
            // check down
            if (row < this.n) {
                if (isOpen(down, col)) {
                    uf.union(idx, translateFrom2Dto1D(down, col));
                }
            }
            // check left
            if (col > 1) {
                if (isOpen(row, left)) {
                    uf.union(idx, translateFrom2Dto1D(row, left));
                }
            }
            // check right
            if (col < this.n) {
                if (isOpen(row, right)) {
                    uf.union(idx, translateFrom2Dto1D(row, right));
                }
            }
        }
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new java.lang.IllegalArgumentException("isOpen");
        }
        int idx = translateFrom2Dto1D(row, col);
        if (grid[idx] == OPEN) {
            // StdOut.println("is open + " + row + " " + col + ", idx = " + idx);
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new java.lang.IllegalArgumentException("isFull");
        }

        return (uf.connected(translateFrom2Dto1D(row, col), virtualTop));
        /*
        // check first row only
        for (int idx = 0; idx < this.n; idx++) {
            if (!isOpen(1, idx + 1)) {
                continue;
            }
            if (uf.connected(idx, translateFrom2Dto1D(row, col))) {
                return true;
            }
        }
        */
    }

    public int numberOfOpenSites()       // number of open sites
    {
        /*
        int num = 0;
        for (int idx = 0; idx < this.n * this.n; idx++) {
            if (grid[idx] == OPEN) {
                num++;
            }
        }
        */
        return numOfOpens;
    }

    public boolean percolates()              // does the system percolate?
    {
        return uf.connected(virtualTop, virtualBotoom);
        /*
        for (int i = 1; i <= this.n; i++) {
            // if one of the last row is full, then percolates
            if (isFull(this.n, i)) {
                return true;
            }
        }
        return false;
        */
    }

    public static void main(String[] args) {
        /**
         * empty body
         * */
    }
}
