/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        // make a new digraph to make it immutable
        Digraph gPrime = new Digraph(G.V());
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                gPrime.addEdge(v, w);
            }
        }
        this.G = gPrime;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    private int[] lengthAndAncestorFind(int v, int w) {
        int[] ret = new int[2];
        // [0] for ancestor, [1] for length
        ret[0] = -1;
        ret[1] = -1;
        /*
        length(v, w) and ancestor(v, w) should each use BreadthFirstDirectedPath only twice.

        Use v as the source for a BreadthFirstDirectedPath graph.
        Use w as the source for a BreadthFirstDirectedPath graph.

        By doing so, you will know the whole graph based on both v source and w source.
        In other words, you will have one marked[] array and one distTo[] array for v-source graph,
        and another marked[] array and distTo[] array for w-source graph.
        */
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);

        /*
        Now how do you get one ancestor? Just iterate through every vertex,
        if the marked[] is true for both v-source graph and w-source graph,
        it means v and w can both get to this vertex.
        * */
        int shortestLength = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int x = 0; x < G.V(); x++) {
            if (bfsV.hasPathTo(x) && bfsW.hasPathTo(x)) {
                /*
                 *Next, how do you get THE closest ancestor?
                 * Use distTo[]. for above ancestors,
                 * calculate the summation from both v and w to this vertex.
                 * Find the minimum summation, that ancestor is the closest ancestor.
                 * */
                int len = bfsV.distTo(x) + bfsW.distTo(x);
                if (len < shortestLength) {
                    shortestLength = len;
                    ancestor = x;
                }
            }
        }
        if (ancestor != -1) {
            ret[0] = ancestor;
            ret[1] = shortestLength;
        }
        return ret;
    }

    public int length(int v, int w) {
        if (v == -1 || w == -1)
            throw new java.lang.IllegalArgumentException();
        return lengthAndAncestorFind(v, w)[1];
    }

    public int ancestor(int v, int w) {
        if (v == -1 || w == -1)
            throw new java.lang.IllegalArgumentException();
        return lengthAndAncestorFind(v, w)[0];
    }

    private int[] lengthAndAncestorFind(Iterable<Integer> v, Iterable<Integer> w) {
        // in this problem, which exact v and which exact w has the shortest connection
        // is a don't care. It is hidden in bfs(v,w) where v and w is a vector
        // Just need to find the the shortest path and ancestor

        int[] ret = new int[2];
        ret[0] = -1;
        ret[1] = -1;
        /*
        length(v, w) and ancestor(v, w) should each use BreadthFirstDirectedPath only twice.

        Use v as the source for a BreadthFirstDirectedPath graph.
        Use w as the source for a BreadthFirstDirectedPath graph.

        By doing so, you will know the whole graph based on both v source and w source.
        In other words, you will have one marked[] array and one distTo[] array for v-source graph,
        and another marked[] array and distTo[] array for w-source graph.
        */
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);

        /*
        Now how do you get one ancestor? Just iterate through every vertex,
        if the marked[] is true for both v-source graph and w-source graph,
        it means v and w can both get to this vertex.
        * */
        int shortestLength = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int x = 0; x < G.V(); x++) {
            if (bfsV.hasPathTo(x) && bfsW.hasPathTo(x)) {
                /*
                 *Next, how do you get THE closest ancestor?
                 * Use distTo[]. for above ancestors,
                 * calculate the summation from both v and w to this vertex.
                 * Find the minimum summation, that ancestor is the closest ancestor.
                 * */
                int len = bfsV.distTo(x) + bfsW.distTo(x);
                if (len < shortestLength) {
                    shortestLength = len;
                    ancestor = x;
                }
            }
        }
        if (ancestor != -1) {
            ret[0] = ancestor;
            ret[1] = shortestLength;
        }
        return ret;
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new java.lang.IllegalArgumentException();
        for (int x : v) {
            if (x == -1) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        for (int x : w) {
            if (x == -1) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        return lengthAndAncestorFind(v, w)[1];

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new java.lang.IllegalArgumentException();

        for (int x : v) {
            if (x == -1) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        for (int x : w) {
            if (x == -1) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        return lengthAndAncestorFind(v, w)[0];
    }

    // do unit testing of this class

    /*
    *
     % more digraph1.txt             % java-algs4 SAP digraph1.txt
    13                              3 11
    11                              length = 4, ancestor = 1
     7  3
     8  3                           9 12
     3  1                           length = 3, ancestor = 5
     4  1
     5  1                           7 2
     9  5                           length = 4, ancestor = 0
    10  5
    11 10                           1 6
    12 10                           length = -1, ancestor = -1
     1  0
     2  0
    * */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            // StdOut.printf("ancestor = %d\n", ancestor);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

        /*
        // test with digraph25.txt
        // results:
        // length = 4, ancestor = 3
        Bag<Integer> b1 = new Bag<Integer>();
        b1.add(13);
        b1.add(23);
        b1.add(24);
        Bag<Integer> b2 = new Bag<Integer>();
        b2.add(6);
        b2.add(16);
        b2.add(17);
        StdOut.printf("length = %d, ancestor = %d\n", sap.length(b1, b2), sap.ancestor(b1, b2));
        */
    }
}
