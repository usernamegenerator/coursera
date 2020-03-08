/******************************************************************************
 *  Compilation:  javac Graph.java
 *  Execution:    java Graph input.txt
 *  Dependencies: Bag.java Stack.java In.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                https://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  A graph, implemented using an array of sets.
 *  Parallel edges and self-loops allowed.
 *
 *  % java Graph tinyG.txt
 *  13 vertices, 13 edges
 *  0: 6 2 1 5
 *  1: 0
 *  2: 0
 *  3: 5 4
 *  4: 5 6 3
 *  5: 3 4 0
 *  6: 0 4
 *  7: 8
 *  8: 7
 *  9: 11 10 12
 *  10: 9
 *  11: 9 12
 *  12: 11 9
 *
 *  % java Graph mediumG.txt
 *  250 vertices, 1273 edges
 *  0: 225 222 211 209 204 202 191 176 163 160 149 114 97 80 68 59 58 49 44 24 15
 *  1: 220 203 200 194 189 164 150 130 107 72
 *  2: 141 110 108 86 79 51 42 18 14
 *  ...
 *
 * https://algs4.cs.princeton.edu/41graph/Graph.java.html
 ******************************************************************************/

package Graphs.UndirectedGraphs;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Graph {
    private final int V; // number of vertex;
    private int E;  // number of edge;
    // the index of this array is the vertex, the element is a bag of adj vertex of this vertex
    private Bag<Integer>[] adj;

    // create a graph with vertex only
    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    // take tinyG.txt
    /*
    13  // V
    13  // E
    0 5
    4 3
    0 1
    9 12
    6 4
    5 4
    0 2
     */
    public Graph(In in) {
        this(in.readInt());
        int E = in.readInt();
        StdOut.println(E);
        for (int i = 0; i < E; i++) {
            int v = in.readInt(); // one of the vertex
            int w = in.readInt(); // the other vertex
            addEdge(v, w);
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    // return all vertex's bag
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices " + E + " edges\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : this.adj(v))
                s.append(w + " ");
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        StdOut.println(G);
    }
}
