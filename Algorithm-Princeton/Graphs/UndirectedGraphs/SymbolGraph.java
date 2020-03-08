/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description: java SymbolGraph movies.txt "/"
 *
 *  Bacon, Kevin
  Woodsman, The (2004)
  Wild Things (1998)
  Where the Truth Lies (2005)
  Tremors (1990)
  Trapped (2002)
  Stir of Echoes (1999)
  Sleepers (1996)
  She's Having a Baby (1988)
  River Wild, The (1994)
  Planes, Trains & Automobiles (1987)
  Picture Perfect (1997)
  Novocaine (2001)
  Mystic River (2003)
  My Dog Skip (2000)
  Murder in the First (1995)
  JFK (1991)
  In the Cut (2003)
  Hollow Man (2000)
  He Said, She Said (1991)
  Friday the 13th (1980)
  Footloose (1984)
  Flatliners (1990)
  Few Good Men, A (1992)
  Diner (1982)
  Beauty Shop (2005)
  Apollo 13 (1995)
  Animal House (1978)


 **************************************************************************** */

package Graphs.UndirectedGraphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SymbolGraph {
    private ST<String, Integer> st; // symbol -> index
    private String[] keys;          // index -> symbol
    private Graph G;

    // (file name, delim)
    public SymbolGraph(String stream, String sp) {
        st = new ST<String, Integer>();
        In in = new In(stream);
        // first time iterate through the inputs
        // create the ST indexing
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(sp); // read strings
            for (int i = 0; i < a.length; i++) {
                // each string is a vertex
                // create the index for every string
                // key is the string, value is the index of the string in 0,1,2,3..
                if (!st.contains(a[i]))
                    st.put(a[i], st.size());
            }
        }
        // ST done

        // start index -> symbol
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name; // [index] = symbol
        }

        // second time iterate through
        G = new Graph(st.size());
        in = new In(stream);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(sp);
            // connect the first vertex in a line to all other vertices in the same line
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                G.addEdge(v, st.get(a[i]));
            }
        }
    }

    public boolean contains(String s) {
        return st.contains(s);
    }

    public int index(String s) {
        return st.get(s);
    }

    public String name(int v) {
        return keys[v];
    }

    public Graph G() {
        return G;
    }


    public static void main(String[] args) {
        String filename = args[0];
        String delim = args[1];
        SymbolGraph sg = new SymbolGraph(filename, delim);

        Graph G = sg.G();

        while (StdIn.hasNextLine()) {
            String source = StdIn.readLine();
            for (int w : G.adj(sg.index(source))) {
                StdOut.println("  " + sg.name(w));
            }
        }
    }
}
