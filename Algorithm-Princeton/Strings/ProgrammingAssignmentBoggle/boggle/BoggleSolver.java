/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

public class BoggleSolver {

    // private final TrieST<Integer> trie;
    private final TST<Integer> trie;
    private BoggleBoard gameBoard;
    private Graph boardGraph;
    private ST<String, Integer> results;
    // private int scoreResult;


    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        // trie = new TrieST<Integer>();
        trie = new TST<Integer>();
        for (int i = 0; i < dictionary.length; i++) {
            trie.put(dictionary[i], i);
        }
        // StdOut.println(trie.get("HELLO"));
        // scoreResult = 0;
    }

    private void initGraph() {
        int c = gameBoard.cols();
        int r = gameBoard.rows();
        boardGraph = new Graph(r * c);
        StdOut.println("col = " + c + " row = " + r);

        // connect the elements row by row, horizontal
        // and diagonal
        for (int col = 0; col < c - 1; col++) {
            for (int row = 0; row < r; row++) {
                boardGraph.addEdge(convert2DIndexTo1DIndex(row, col),
                                   convert2DIndexTo1DIndex(row, col + 1));
                if (row > 0) {
                    boardGraph.addEdge(convert2DIndexTo1DIndex(row, col),
                                       convert2DIndexTo1DIndex(row - 1, col + 1));
                }
                if (row < r - 1) {
                    boardGraph.addEdge(convert2DIndexTo1DIndex(row, col),
                                       convert2DIndexTo1DIndex(row + 1, col + 1));
                }
            }
        }
        // connect the elects col by col, veritcal
        for (int row = 0; row < r - 1; row++) {
            for (int col = 0; col < c; col++) {
                boardGraph.addEdge(convert2DIndexTo1DIndex(row, col),
                                   convert2DIndexTo1DIndex(row + 1, col));
            }
        }


        // unit test the board graph
        /*
        for (int v = 0; v < boardGraph.V(); v++) {
            int row = v / r;
            int col = v % c;
            StdOut.println(board.getLetter(row, col));
        }
        // StdOut.println(boardGraph.toString());
        */
    }

    private int convert2DIndexTo1DIndex(int row, int col) {
        return row * gameBoard.cols() + col;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        this.gameBoard = board;
        initGraph();
        // results = new Queue<String>();
        results = new ST<String, Integer>();

        // do a dfs for every node
        // check in the dfs, is there are matching words
        for (int v = 0; v < boardGraph.V(); v++) {
            boolean[] marked = new boolean[boardGraph.V()];
            StringBuilder sb = new StringBuilder();
            sb.append(getLetterFrom1DIndex(v));
            dfs(v, v, sb, marked);
        }

        // StdOut.println("results:");
        /*
        for (String s : results) {
            // StdOut.println(s);
            scoreResult += scoreOf(s);
        }
        */
        // StdOut.println(scoreResult);
        return results;
    }

    private void dfs(int vertex, final int source, StringBuilder sb, boolean[] marked) {
        // visited
        marked[vertex] = true;

        // StdOut.println("start: " + getLetterFrom1DIndex(vertex));

        for (int v : boardGraph.adj(vertex)) {
            // StdOut.println("from " + vertex + " check " + getLetterFrom1DIndex(v) + " at " + v);
            if (!marked[v]) {
                // StdOut.println("not marked, continue");

                sb.append(getLetterFrom1DIndex(v));
                // StdOut.println("StringBuilder:" + sb.toString());
                // Queue<String> tempPrefixQ = (Queue<String>) trie.keysWithPrefix(sb.toString());
                Queue<String> tempPrefixQ = new Queue<String>();
                for (String s : trie.keysWithPrefix(sb.toString())) {
                    tempPrefixQ.enqueue(s);
                }

                if (!tempPrefixQ.isEmpty()) {
                    /*
                    StdOut.println("found prefix: ");
                    for (String s : tempPrefixQ) {
                        StdOut.println(s);
                    }
                    */

                    // Queue<String> matchQ = (Queue<String>) trie.keysThatMatch(sb.toString());
                    Queue<String> matchQ = new Queue<String>();
                    for (String s : trie.keysThatMatch(sb.toString())) {
                        matchQ.enqueue(s);
                    }
                    while (!matchQ.isEmpty()) {
                        String resString = matchQ.dequeue();


                        if (resString.length() >= 3 && !results.contains(resString)) {
                            // StdOut.println("found match, add to results: " + resString);
                            results.put(resString, source);
                        }
                            /*
                            else {
                                // StdOut.println("found match, but already in result: " + resString);
                                // StdOut.println("Or");
                                // StdOut.println("found match, but too short, not added " + resString);
                            }
                            */
                    }

                    // StdOut.println("continue with prefix : " + sb.toString());
                    dfs(v, source, sb, marked);
                    // after dfs on this path is done
                    // need to roll back to previous
                    // rollBackString(sb);

                }
                /*
                else {

                    // rollBackString(sb);
                    // StdOut.println("NOT found prefix, roll back to " + sb.toString());
                }
                */
                rollBackString(sb);
            }
            /*
            else {
                // StdOut.println("already marked");
            }
            */
        }
        // make sure to remove from visited
        // so that this vertex can be visited from other path
        marked[vertex] = false;
    }

    private void rollBackString(StringBuilder sb) {
        if (sb.charAt(sb.length() - 2) == 'Q' && sb.charAt(sb.length() - 1) == 'U') {
            // StdOut.println("DELETE QU");
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        else {
            sb.deleteCharAt(sb.length() - 1);
        }
    }


    private String getLetterFrom1DIndex(int x) {
        int row = x / gameBoard.cols();
        int col = x % gameBoard.cols();

        if (gameBoard.getLetter(row, col) == 'Q') {
            return "QU";
        }
        else
            return Character.toString(gameBoard.getLetter(row, col));

    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {

        if (!trie.contains(word)) {
            return 0;
        }

        int score;
        if (word.length() == 3 || word.length() == 4) {
            score = 1;
        }
        else if (word.length() == 5)
            score = 2;
        else if (word.length() == 6)
            score = 3;
        else if (word.length() == 7)
            score = 5;
        else if (word.length() >= 8) {
            score = 11;
        }
        else {
            // StdOut.println("invalid string length in results");
            score = 0;
        }
        return score;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        // solver.getAllValidWords(board);

        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);

    }
}
