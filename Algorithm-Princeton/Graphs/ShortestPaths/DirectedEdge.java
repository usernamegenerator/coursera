/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Graphs.ShortestPaths;

public class DirectedEdge {

    private final int v;    // start of the edge
    private final int w;    // end of the edge
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public String toString() {
        return String.format("%d->%d % .2f", v, w, weight);
    }

    public static void main(String[] args) {

    }
}
