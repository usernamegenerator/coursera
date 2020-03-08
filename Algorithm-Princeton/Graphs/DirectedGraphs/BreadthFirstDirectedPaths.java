/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Graphs.DirectedGraphs;


import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class BreadthFirstDirectedPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public BreadthFirstDirectedPaths(Digraph D, int s) {
        marked = new boolean[D.V()];
        edgeTo = new int[D.V()];
        this.s = s;
        bfs(D, s);
    }

    private void bfs(Digraph D, int s) {
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : D.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    queue.enqueue(w);
                }
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
