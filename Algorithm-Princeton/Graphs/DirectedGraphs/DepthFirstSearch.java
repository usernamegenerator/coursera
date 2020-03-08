/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Graphs.DirectedGraphs;

public class DepthFirstSearch {
    private boolean[] marked;
    private int count = 0;

    public DepthFirstSearch(Digraph D, int v) {
        marked = new boolean[D.V()];
        dfs(D, v);
    }

    private void dfs(Digraph D, int v) {
        marked[v] = true;
        count++;
        for (int w : D.adj(v)) {
            if (!marked[w]) {
                dfs(D, w);
            }
        }
    }

    public boolean marked(int w) {
        return marked[w];
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {

    }
}
