/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Strings.RegularExpressions;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.Stack;

public class NFA {

    private char[] re; // match-transition
    private Digraph G; // epsilon-transition
    private int M; // number of states

    public NFA(String regexp) {
        // given the re, to build nfa
        Stack<Integer> ops = new Stack<Integer>();
        re = regexp.toCharArray();
        M = re.length;
        G = new Digraph(M + 1);

        for (int i = 0; i < M; i++) {
            int lp = i; // left parenthese
            if (re[i] == '(' || re[i] == '|') {
                ops.push(i);
            }
            else if (re[i] == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or + 1);
                    G.addEdge(or, i);
                }
                else lp = or;
            }
            if (i < M - 1 && re[i + 1] == '*') {
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }
            if (re[i] == '(' || re[i] == '*' || re[i] == ')') {
                G.addEdge(i, i + 1);
            }
        }


    }

    public boolean recognizes(String txt) {
        Bag<Integer> pc = new Bag<Integer>();
        // 1
        // all reachable from start 0 by e-transition
        DirectedDFS dfs = new DirectedDFS(G, 0);
        for (int v = 0; v < G.V(); v++) {
            if (dfs.marked(v)) pc.add(v);
        }

        for (int i = 0; i < txt.length(); i++) {
            // 2
            // state reachable after scanning past txt.charAt(i)
            Bag<Integer> match = new Bag<Integer>();
            // in all the reachable e-transition, if there is a match
            for (int v : pc) {
                if (v == M) continue;
                if ((re[v] == txt.charAt(i)) || re[v] == '.') {
                    // match-transition can't jump around
                    // it is always v+1
                    match.add(v + 1); // add the match-transition
                }
            }
            // 3
            // follow e-transition
            dfs = new DirectedDFS(G, match);
            pc = new Bag<Integer>(); // set pc to a new empty bag
            for (int v = 0; v < G.V(); v++) {
                if (dfs.marked(v)) pc.add(v);
            }
        }

        for (int v : pc) {
            if (v == M) return true; // done
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
