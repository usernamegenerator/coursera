/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Graphs.UndirectedGraphs;

public class TwoColor {
    private boolean[] marked;
    private boolean[] color;
    private boolean isTWoColorable = true;

    public TwoColor(Graph G) {
        marked = new boolean[G.V()];
        color = new boolean[G.V()];
        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s);
            }
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                color[w] = !color[v];
                dfs(G, w);
            }
            else if (color[w] == color[v]) isTWoColorable = false;
        }
    }

    public boolean isBipartite() {
        return isTWoColorable;
    }

    public static void main(String[] args) {

    }
}
