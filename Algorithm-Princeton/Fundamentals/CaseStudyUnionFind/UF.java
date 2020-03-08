/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Fundamentals.CaseStudyUnionFind;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class UF {
    private int[] id;
    private int count; // how many components
    private int[] sz;  // weighted quick union

    public UF(int n) {
        count = n;
        id = new int[n];
        sz = new int[n]; // weighted quick union
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1; // weighted quick union
        }
    }

    public int count() {
        return count;
    }

    public boolean connect(int p, int q) {
        // quick_find_find can be changed to other find
        return quick_find_find(id[p]) == quick_find_find(id[q]);
    }

    // ********quick find*********//
    // search the whole array everytime and change the every node's connection
    public int quick_find_find(int p) {
        return id[p];
    }

    // connnect every element that connects to p to q
    public void quick_find_union(int p, int q) {
        int pID = id[p];
        int qID = id[q];
        if (pID == qID)
            return;
        for (int i = 0; i < count; i++) {
            if (id[i] == pID) {
                id[i] = qID;
            }
        }
        count--;
    }

    //*****************************//
    // ********quick union*********//
    // find the root and connect to the root
    public int quick_union_find(int p) {
        // id[p] points to p's root
        while (p != id[p]) p = id[p];
        return p;
    }

    // connect p's root to q's root
    public void quick_union_union(int p, int q) {
        int pRoot = quick_union_find(p);
        int qRoot = quick_union_find(q);
        if (pRoot == qRoot)
            return;
        id[pRoot] = qRoot;
        count--;
    }
    //*****************************//

    // ********weighted quick union*********//
    public int weighted_quick_union_find(int p) {
        // id[p] points to p's root
        while (p != id[p]) p = id[p];
        return p;
    }

    public void weighted_quick_union_union(int p, int q) {
        int pRoot = weighted_quick_union_find(p);
        int qRoot = weighted_quick_union_find(q);
        if (pRoot == qRoot)
            return;
        // connect p to q
        // and add p's size to q's size
        if (sz[pRoot] < sz[qRoot]) {
            id[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        }
        else {
            id[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }
        count--;
    }
    // ************************************//


    // ********weighted quick union with path compression*********//
    public int weighted_quick_union_with_path_compression_find(int p) {
        // id[p] points to p's parent
        while (p != id[p]) {
            // id[id[p]] points to id[p]'s parent, and p's grandparent
            id[p] = id[id[p]]; // make p points to its grandparent
            p = id[p];
        }
        return p;
    }

    public void weighted_quick_union_with_path_compression_union(int p, int q) {
        int pRoot = weighted_quick_union_with_path_compression_find(p);
        int qRoot = weighted_quick_union_with_path_compression_find(q);
        if (pRoot == qRoot)
            return;
        // connect p to q
        // and add p's size to q's size
        if (sz[pRoot] < sz[qRoot]) {
            id[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        }
        else {
            id[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }
        count--;
    }
    // **********************************************************//

    public static void main(String[] args) {
        int n = StdIn.readInt();
        UF uf = new UF(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connect(p, q)) continue;
            uf.quick_find_union(p, q);
            // uf.quick_union_union(p, q);
            // uf.weighted_quick_union_union(p, q);
            // uf.weighted_quick_union_with_path_compression_find(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}

