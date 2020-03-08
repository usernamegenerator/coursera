/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 *
 *  DirectedDFS "D:\github-repo\Algorithm-Princeton\Graphs\DirectedGraphs\tinyDG.txt" 1 2 6
 *  0 1 2 3 4 5 6 8 9 10 11 12
 *
 *  DirectedDFS "D:\github-repo\Algorithm-Princeton\Graphs\DirectedGraphs\tinyDG.txt" 1
 *  1
 *
 *  DirectedDFS "D:\github-repo\Algorithm-Princeton\Graphs\DirectedGraphs\tinyDG.txt" 2
 *  0 1 2 3 4 5
 **************************************************************************** */

package Graphs.DirectedGraphs;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DirectedDFS {
    private boolean[] marked;

    // find all reachable vertices from s
    public DirectedDFS(Digraph D, int s) {
        marked = new boolean[D.V()];
        dfs(D, s);
    }

    public DirectedDFS(Digraph D, Iterable<Integer> sources) {
        marked = new boolean[D.V()];
        for (int s : sources) {
            if (!marked[s]) {
                dfs(D, s);
            }
        }
    }

    private void dfs(Digraph D, int v) {
        marked[v] = true;
        for (int w : D.adj(v)) {
            if (!marked[w])
                dfs(D, w);
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public static void main(String[] args) {
        Digraph G = new Digraph(new In(args[0]));

        Bag<Integer> sources = new Bag<Integer>();
        for (int i = 1; i < args.length; i++)
            sources.add(Integer.parseInt(args[i]));

        DirectedDFS reachable = new DirectedDFS(G, sources);
        for (int v = 0; v < G.V(); v++) {
            if (reachable.marked(v)) StdOut.print(v + " ");
        }
        StdOut.println();
    }

}
