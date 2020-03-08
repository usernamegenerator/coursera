/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Searching.BinarySearchTrees;

import edu.princeton.cs.algs4.Queue;

public class BST<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left;
        private Node right;
        private int N;

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.N;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return min(x.right);
    }


    // largest value that is smaller than or equal to key
    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        // smaller than current node, go left
        if (cmp < 0) return floor(x.left, key);
        // else, could be in right
        Node t = floor(x.right, key);
        // in right
        if (t != null) return t;
            // not in right
        else return x;

    }

    public Key select(int k) {
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        // there are more elements in left child tree, so go left
        if (t > k) return select(x.left, k);
            // otherwise, select the (k-t-1)th item from the right
        else if (t < k) return select(x.right, k - t - 1);
        else return x;
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        // if key is in left, then return the key's rank in x's left
        if (cmp < 0) return rank(x.left, key);
            // else if key is in right, then return the key's rank in x's right,
            // plus itself, plus the total count in the left
        else if (cmp > 0) return 1 + size(x.left) + rank(x.right, key);
            // if the node is the key, return this node's total count in the left
        else return size(x.left);
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        // why don't need this line?
        // if (x == null) return null;
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
            // to delete current node
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(x.right);
            x.right = deleteMin(x.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    private Iterable<Key> keys(Key low, Key high) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, low, high);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key low, Key high) {
        if (x == null) return;
        int comlo = low.compareTo(x.key);
        int comhi = high.compareTo(x.key);
        // low boundary is less than current, there may be more in the left tree
        if (comlo < 0) keys(x.left, queue, low, high);
        // current node is within the boundary
        if (comlo <= 0 && comhi >= 0) queue.enqueue(x.key);
        // high boundary is more than current, there may be more in the right tree
        if (comhi > 0) keys(x.right, queue, low, high);

    }

    public static void main(String[] args) {

    }
}
