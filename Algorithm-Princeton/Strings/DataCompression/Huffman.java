/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Strings.DataCompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Huffman {
    // ASCII
    private static int R = 256;

    // node implementation
    private static class Node implements Comparable<Node> {

        private char ch;
        private int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    // 1. build a Huffman trie
    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<Node>();
        // create new nodes
        for (char c = 0; c < R; c++) {
            if (freq[c] > 0) {
                pq.insert(new Node(c, freq[c], null, null));
            }
        }
        while (pq.size() > 1) {
            // merge two node with min freq
            Node x = pq.delMin();
            Node y = pq.delMin();
            Node parent = new Node('\0', x.freq + y.freq, x, y); // '\0' means internal node
            pq.insert(parent);
        }
        return pq.delMin();
    }

    // 2. output the binary steam of a trie
    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true); // leaf node has the char, write 1 as header
            BinaryStdOut.write(x.ch);
            return;
        }
        BinaryStdOut.write(false); // internal node start with 0
        writeTrie(x.left);
        writeTrie(x.right);
    }

    // 3. build the table (key(char)->value(bit stream)) from the trie
    private static String[] buildCode(Node root) {
        String[] st = new String[R];
        buildCode(st, root, "");
        return st;
    }

    private static void buildCode(String[] st, Node x, String s) {
        if (x.isLeaf()) {
            st[x.ch] = s;
            return;
        }
        buildCode(st, x.left, s + '0');
        buildCode(st, x.left, s + '1');
    }

    // 4. from bit steam build the trie
    private static Node readTrie() {
        // if read 1, means it's a leaf node
        if (BinaryStdIn.readBoolean()) {
            return new Node(BinaryStdIn.readChar(), 0, null, null);
        }
        // if read 0, means it's a internal node
        return new Node('\0', 0, readTrie(), readTrie());
    }

    // 5. use the trie to write the chars
    public static void expand() {
        Node root = readTrie();
        int N = BinaryStdIn.readInt();
        for (int i = 0; i < N; i++) {
            // expand the ith code's letter
            Node x = root;
            while (!x.isLeaf()) {
                if (BinaryStdIn.readBoolean()) {
                    x = x.right;
                }
                else x = x.left;
            }
            BinaryStdOut.write(x.ch);
        }
        BinaryStdOut.close();
    }

    public static void compress() {
        // read the input
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        // count the freq
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++) {
            freq[input[i]]++;
        }

        // build the huffman tree
        Node root = buildTrie(freq);

        // build the table (key(char)->value(bit stream))
        String[] st = new String[R];
        buildCode(st, root, "");

        // output the trie for decoding
        writeTrie(root);

        // output the total length of the chars
        BinaryStdOut.write(input.length);

        // compress the chars to bit steam
        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '1') {
                    BinaryStdOut.write(true);
                }
                else {
                    BinaryStdOut.write(false);
                }
            }
        }
        BinaryStdOut.close();

    }

    public static void main(String[] args) {

    }
}
