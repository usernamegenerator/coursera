/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Graphs.UndirectedGraphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Cycle {
    private boolean[] marked;
    private boolean hasCycle;

    public Cycle(Graph G) {
        marked = new boolean[G.V()];
        hasCycle = false;
        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s, s); // graph, start, end
            }
        }
    }

    private void dfs(Graph G, int v, int u) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                StdOut.println(v + " " + w);
                dfs(G, w, v); // pass in to next serach, v is w's previous vertex
            }
            // u is v's previous
            // stand at v, look at next vertex w, if w is marked
            // it is either the previous vertex in the path
            // or it is marked from somewhere else
            // if it is not the previous vertex, then means it is in a cycle
            // being marked from other vertex
            else if (w != u) {
                StdOut.println("true " + v + " " + w + " " + u);
                hasCycle = true;
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }


    public static void main(String[] args) {
        Graph G = new Graph(new In(args[0]));
        Cycle C = new Cycle(G);
        StdOut.println(C.hasCycle());
    }
}
