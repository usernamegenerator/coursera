/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Sorting.ProgrammingAssignment8Puzzle;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    // You may assume that the constructor receives an n-by-n array containing
    // the n2 integers between 0 and n2 − 1, where 0 represents the blank square.
    private final int n;
    private final int[][] initial;
    private final int[][] goal;

    private ArrayList<Board> neighbors;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.n = blocks[0].length;
        this.initial = new int[n][n];
        this.goal = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.initial[i][j] = blocks[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goal[i][j] = i * n + j + 1;
            }
        }
        goal[n - 1][n - 1] = 0;

        neighbors = new ArrayList<Board>();
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // Hamming priority function. The number of blocks in the wrong position,
    // plus the number of moves made so far to get to the search node.

    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // int goal_i_j = i * n + j + 1;
                if (initial[i][j] != goal[i][j] && initial[i][j] != 0) {
                    hamming++;
                }
            }
        }

        return hamming;
    }

    // Manhattan priority function.
    // The sum of the Manhattan distances (sum of the vertical and horizontal distance)
    // from the blocks to their goal positions,
    // plus the number of moves made so far to get to the search node.

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (initial[i][j] != goal[i][j] && initial[i][j] != 0) {
                    // a[i][j] = i * n + j + 1;
                    int correctI = (initial[i][j] - 1) / n; // row
                    int correctJ = (initial[i][j] - 1) % n; // col
                    int distance = Math.abs(correctI - i) + Math.abs(correctJ - j);
                    manhattan += distance;
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (initial[i][j] != goal[i][j])
                    return false;
            }
        }
        return true;
    }

    private Board getGoalBoard() {
        return new Board(this.goal);
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] temp = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = this.initial[i][j];
            }
        }
        // switch blank block doesn't make a difference
        // so can't switch blank block
        if (temp[0][0] == 0 || temp[0][1] == 0) {
            int t = temp[1][0];
            temp[1][0] = temp[1][1];
            temp[1][1] = t;
        }
        else {
            int t = temp[0][0];
            temp[0][0] = temp[0][1];
            temp[0][1] = t;
        }
        return new Board(temp);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board cmp = (Board) y;
        if (cmp.dimension() != this.dimension()) return false;

        // alternatively, check blocks one by one
        if (this.toString().equals(cmp.toString()))
            return true;
        else
            return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int iZero = 0, jZero = 0;
        int[][] up = new int[n][n];
        int[][] down = new int[n][n];
        int[][] left = new int[n][n];
        int[][] right = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.initial[i][j] == 0) {
                    iZero = i;
                    jZero = j;
                }
                up[i][j] = this.initial[i][j];
                down[i][j] = this.initial[i][j];
                left[i][j] = this.initial[i][j];
                right[i][j] = this.initial[i][j];
            }
        }
        // at this point, [i][j] is the blank, swap blank with neighbors

        // neighbor by swapping blank up
        if (iZero > 0) {
            up[iZero][jZero] = this.initial[iZero - 1][jZero];
            up[iZero - 1][jZero] = 0;
            neighbors.add(new Board(up));
            // neighborUp.setPredecessor(this);
        }
        // neighbor by swapping blank down
        if (iZero < n - 1) {
            down[iZero][jZero] = this.initial[iZero + 1][jZero];
            down[iZero + 1][jZero] = 0;
            neighbors.add(new Board(down));
        }

        if (jZero > 0) {
            left[iZero][jZero] = this.initial[iZero][jZero - 1];
            left[iZero][jZero - 1] = 0;
            neighbors.add(new Board(left));
        }

        if (jZero < n - 1) {
            right[iZero][jZero] = this.initial[iZero][jZero + 1];
            right[iZero][jZero + 1] = 0;
            neighbors.add(new Board(right));
        }

        return neighbors;
    }

    /*
    private void setPredecessor(Board pre) {
        this.predecessor = pre;
    }
    */

    // The input and output format for a board is the board dimension n
    // followed by the n-by-n initial board, using 0 to represent the blank square.
    /*
     * 3
     * 0  1  3
     * 4  2  5
     * 7  8  6
     * */

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(n + "\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == n - 1)
                    buf.append(initial[i][j]);
                else
                    buf.append(initial[i][j] + "\t");
            }
            if (i != n - 1)
                buf.append("\n");
        }
        return buf.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] node = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };

        int[][] node2 = {
                { 8, 1, 3 },
                { 4, 2, 0 },
                { 7, 6, 5 }
        };


        Board test = new Board(node);
        Board testSame = new Board(node);
        Board test2 = new Board(node2);

        StdOut.println(test.equals(testSame));
        StdOut.println(test.equals(test2));

        ArrayList<Board> neighbor = (ArrayList<Board>) test2.neighbors();
        for (Board n : neighbor) {
            StdOut.println();
            StdOut.println(n);
        }
        StdOut.println("=================");

        StdOut.println(test);
        StdOut.println(test.getGoalBoard());
        StdOut.println(test.dimension());
        StdOut.println(test.hamming());
        StdOut.println(test.manhattan());
        StdOut.println(test.isGoal());
        Board g = test.getGoalBoard();
        StdOut.println(g.isGoal());
        StdOut.println(test.twin());

        StdOut.println("=================");
        int[][] node3 = {
                { 0, 1, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };
        Board test3 = new Board(node3);
        StdOut.println(test3.manhattan());

    }
}
