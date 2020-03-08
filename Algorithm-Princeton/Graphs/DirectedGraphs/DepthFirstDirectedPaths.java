/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Graphs.DirectedGraphs;

import edu.princeton.cs.algs4.Stack;

public class DepthFirstDirectedPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public DepthFirstDirectedPaths(Digraph D, int s) {
        marked = new boolean[D.V()];
        edgeTo = new int[D.V()];
        this.s = s;
        dfs(D, s);
    }

    private void dfs(Digraph D, int v) {
        marked[v] = true;
        for (int w : D.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(D, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> stack = new Stack<Integer>();
        for (int w = v; w != s; w = edgeTo[w]) {
            stack.push(w);
        }
        stack.push(s);
        return stack;
    }

    public static void main(String[] args) {

    }
}
