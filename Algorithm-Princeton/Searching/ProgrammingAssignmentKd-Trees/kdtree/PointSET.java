/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeSet;

public class PointSET {
    // private SET<Point2D> points;
    private final TreeSet<Point2D> points;
    private int size;

    // construct an empty set of points
    public PointSET() {
        // points = new SET<Point2D>();
        points = new TreeSet<Point2D>();
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("null argument");
        if (!contains(p)) {
            points.add(p);
            size++;
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("null argument");
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.IllegalArgumentException("null argument");

        Stack<Point2D> stack = new Stack<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                stack.push(p);
            }
        }
        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("null argument");

        Point2D resPoint = null;
        for (Point2D point : points) {

            if (resPoint == null)
                resPoint = point;

            if (point.distanceSquaredTo(p) < resPoint.distanceSquaredTo(p))
                resPoint = point;
        }
        return resPoint;
    }

    public static void main(String[] args) {
        PointSET ps = new PointSET();

        ps.insert(new Point2D(0.5, 0.0));
        ps.insert(new Point2D(0.0, 0.1));
        ps.insert(new Point2D(1.0, 0.5));
        ps.insert(new Point2D(0.0, 0.5));
        ps.insert(new Point2D(0.5, 0.0));
        StdOut.println(ps.size);
        StdOut.println(ps.range(new RectHV(0.25, 0.0, 0.75, 0.25)));




        /*
        StdOut.println(ps.size());
        StdOut.println(ps.isEmpty());
        ps.insert(new Point2D(0.0, 0.0));
        ps.insert(new Point2D(1.0, 1.0));
        StdOut.println(ps.range(new RectHV(0.0, 0.0, 1.0, 1.0)));
        StdOut.println(ps.nearest(new Point2D(0.0, 1.0)));
        */


        /*
        for (int i = 0; i < 10; i++) {
            double j = i / 10.0;
            Point2D p = new Point2D(j, j);
            ps.insert(p);
        }
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        ps.draw();
        */
    }
}

