/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Sorting.ProgrammingAssignment8Puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;


public class Solver {
    private boolean solvable;
    private SearchNode finalSeachNode;

    // improvement, SearchNode can implement Comparable,
    // so that don't need to use Comparator
    private class SearchNode {
        private Board board;
        private int movesMade;
        private SearchNode predecessor;
        // private int hamming;
        private int manhattan;
        private int priorityM;
        // private int priorityH;

        // We define a search node of the game to be a board,
        // the number of moves made to reach the board, and the predecessor search node.
        public SearchNode(Board b, int m, SearchNode p) {
            board = b;
            movesMade = m;
            predecessor = p;
            manhattan = b.manhattan();
            // hamming = b.hamming();
            priorityM = manhattan + movesMade;
            // priorityH = hamming + movesMade;
        }

        public String toString() {
            StringBuilder b = new StringBuilder();
            b.append("priority = ");
            b.append(priorityM + "\n");
            b.append("moves = ");
            b.append(movesMade + "\n");
            b.append("manhattan = ");
            b.append(manhattan + "\n");
            b.append(board.toString());
            return b.toString();
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.IllegalArgumentException("null Board");

        /*
        Comparator<SearchNode> searchNodeHammingComparator = new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.priorityH > o2.priorityH) return +1;
                if (o1.priorityH < o2.priorityH) return -1;
                if (o1.hamming > o2.hamming) return +1;
                if (o1.hamming < o2.hamming) return -1;

                return 0;
            }
        };
        */
        // The sum of the Manhattan distances (sum of the vertical and horizontal distance)
        // from the blocks to their goal positions,
        // plus the number of moves made so far to get to the search node.
        Comparator<SearchNode> searchNodeManhattanComparator = new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.priorityM > o2.priorityM) return +1;
                if (o1.priorityM < o2.priorityM) return -1;
                // break tie by moves doesn't work!
                if (o1.manhattan > o2.manhattan) return +1;
                if (o1.manhattan < o2.manhattan) return -1;

                return 0;
            }
        };


        MinPQ<SearchNode> pqM = new MinPQ<SearchNode>(searchNodeManhattanComparator);
        MinPQ<SearchNode> pqMTwin = new MinPQ<SearchNode>(searchNodeManhattanComparator);

        // First, insert the initial search node (the initial board, 0 moves, and a null predecessor search node)
        // into a priority queue.
        SearchNode initialSeachNode = new SearchNode(initial, 0, null);
        SearchNode initialSeachNodeTwin = new SearchNode(initial.twin(), 0, null);

        pqM.insert(initialSeachNode);
        pqMTwin.insert(initialSeachNodeTwin);

        // Then, delete from the priority queue the search node with the minimum priority,
        // and insert onto the priority queue all neighboring search nodes
        // (those that can be reached in one move from the dequeued search node).
        // Repeat this procedure until the search node dequeued corresponds to a goal board.
        while (true) {
            // StdOut.println("==========");
            SearchNode currentSeachNode = pqM.delMin();
            SearchNode currentSeachNodeTwin = pqMTwin.delMin();
            // StdOut.println("currentSeachNode : " + currentSeachNode.priorityM);
            // StdOut.println(currentSeachNode.board);

/*
            for (SearchNode n : pqM) {
                StdOut.println(n);
            }
*/
            if (currentSeachNode.board.isGoal()) {
                finalSeachNode = new SearchNode(currentSeachNode.board, currentSeachNode.movesMade,
                                                currentSeachNode.predecessor);
                solvable = true;
                break;
            }

            if (currentSeachNodeTwin.board.isGoal()) {
                finalSeachNode = new SearchNode(currentSeachNodeTwin.board,
                                                currentSeachNodeTwin.movesMade,
                                                currentSeachNodeTwin.predecessor);
                solvable = false;
                break;
            }


            Board thisPre;
            if (currentSeachNode.predecessor != null)
                thisPre = currentSeachNode.predecessor.board;
            else
                thisPre = null;
            Board thisPreTwin;
            if (currentSeachNode.predecessor != null)
                thisPreTwin = currentSeachNode.predecessor.board;
            else
                thisPreTwin = null;
            // StdOut.println("neighbors");
            // move++;
            for (Board neighbor : currentSeachNode.board.neighbors()) {
                if (neighbor.equals(thisPre)) {
                    continue;
                }
                // StdOut.println(neighbor);
                // StdOut.println(" manhattan = " + neighbor.manhattan() + ", moves " + move);
                SearchNode newNode = new SearchNode(neighbor, currentSeachNode.movesMade + 1,
                                                    currentSeachNode);
                // StdOut.println("insert : " + newNode);
                pqM.insert(newNode);
            }

            for (Board neighborTwin : currentSeachNodeTwin.board.neighbors()) {
                if (neighborTwin.equals(thisPreTwin)) {
                    continue;
                }
                // StdOut.println(neighbor);
                SearchNode newNodeTwin = new SearchNode(neighborTwin,
                                                        currentSeachNodeTwin.movesMade + 1,
                                                        currentSeachNodeTwin);
                pqMTwin.insert(newNodeTwin);
            }

        }

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return finalSeachNode.movesMade;
        }
        else return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable

    public Iterable<Board> solution() {

        if (isSolvable()) {
            Stack<Board> s = new Stack<Board>();
            SearchNode currentNode = finalSeachNode;
            while (currentNode != null) {
                s.push(currentNode.board);
                currentNode = currentNode.predecessor;
            }
            /*
            for (Board b : s) {
                StdOut.println(b);
            }
            */
            return s;
        }
        else return null;
    }


    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();

/*
        int blocks[][] = {
                { 0, 1, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };
*/
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());

            for (Board board : solver.solution())
                StdOut.println(board);

        }
    }
}
