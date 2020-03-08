/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        private final Point2D p;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private final int level;      // even level split left/right, odd level split up/bottom


        private Node(Point2D p, RectHV rect, int level) {
            this.p = p;
            this.rect = rect;
            lb = null;
            rt = null;
            this.level = level;
        }
    }


    // construct an empty set of points
    public KdTree() {
        // root = new Node(null, null);
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("null argument");

        // the very first add to the root
        if (root == null) {
            assert (size == 0);
            // axis-aligned rectangle is the unit square
            RectHV rect = new RectHV(0, 0, 1, 1);
            root = insert(p, rect, 0);
        }
        // recusion start from the root
        else {
            if (!contains(p))
                root = insert(root, p);
        }
    }

    private Node insert(Node curNode, Point2D p) {
        // left and right
        // use the x-coordinate(if the point to be inserted has a smaller x-coordinate
        // than the point at the root, go left; otherwise go right)
        if (curNode.level % 2 == 0) {
            // StdOut.println(curNode.p.toString());
            // curNode.rect.draw();
            // smaller, go left
            if (p.x() < curNode.p.x()) {
                // left is null, need to add
                if (curNode.lb == null) {
                    // current node is at even level, means this is left, not the bottom
                    // so this new point has a new right boundary (xmax) equals to current node's x
                    RectHV rect = new RectHV(curNode.rect.xmin(), curNode.rect.ymin(),
                                             curNode.p.x(), curNode.rect.ymax());
                    // StdOut.println(p.toString());
                    // rect.draw();
                    curNode.lb = insert(p, rect, curNode.level + 1);
                }
                // left is not null, go left
                else {
                    curNode.lb = insert(curNode.lb, p);
                }
            }
            // otherwise go right
            else {
                // right is empty, need to add
                if (curNode.rt == null) {
                    // current node is at even level, means this is right, not the top
                    // so this new point has a new left boundary (xmin) equals to current node's x
                    RectHV rect = new RectHV(curNode.p.x(), curNode.rect.ymin(),
                                             curNode.rect.xmax(), curNode.rect.ymax());
                    curNode.rt = insert(p, rect, curNode.level + 1);
                }
                else {
                    curNode.rt = insert(curNode.rt, p);
                }
            }
        }
        // top and bottom
        // use the y-coordinate (if the point to be inserted has a smaller y-coordinate
        // than the point in the node, go left; otherwise go right)
        else {
            // smaller, go left in the tree (bottom in the 2D plane)
            if (p.y() < curNode.p.y()) {
                // left is empty, need to add
                if (curNode.lb == null) {
                    // current node is at odd level, means this is bottom, not the left
                    // so this new point has a new top boudary (ymax) equals to current node's y
                    RectHV rect = new RectHV(curNode.rect.xmin(), curNode.rect.ymin(),
                                             curNode.rect.xmax(), curNode.p.y());
                    curNode.lb = insert(p, rect, curNode.level + 1);
                }
                // keep going left
                else {
                    curNode.lb = insert(curNode.lb, p);
                }
            }
            // otherwise, go right in the tree (top in the 2D plane)
            else {
                // current node is at odd level, means this is top, not the right
                // so this new point has a new bottom boudary (ymin) equals to current node's y
                if (curNode.rt == null) {
                    RectHV rect = new RectHV(curNode.rect.xmin(), curNode.p.y(),
                                             curNode.rect.xmax(), curNode.rect.ymax());
                    curNode.rt = insert(p, rect, curNode.level + 1);

                }
                // keep going right
                else {
                    curNode.rt = insert(curNode.rt, p);
                }
            }
        }
        return curNode;
    }

    private Node insert(Point2D p, RectHV rect, int level) {
        // Each node corresponds to an axis-aligned rectangle in the unit square.
        // which encloses all of the points in its subtree
        Node newNode = new Node(p, rect, level);
        size++;
        return newNode;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("null argument");
        if (root == null)
            return false;

        return search(root, p);
    }

    private boolean search(Node curNode, Point2D p) {

        if (curNode == null)
            return false;

        if (Double.compare(curNode.p.x(), p.x()) == 0
                && Double.compare(curNode.p.y(), p.y()) == 0)
            return true;

        // use the x-coordinate(if the point to be inserted has a smaller x-coordinate
        // than the point at the root, go left; otherwise go right)
        if (curNode.level % 2 == 0) {
            if (p.x() < curNode.p.x()) {
                return search(curNode.lb, p);
            }
            else {
                return search(curNode.rt, p);
            }
        }
        // use the y-coordinate (if the point to be inserted has a smaller y-coordinate
        // than the point in the node, go left; otherwise go right)
        else {
            if (p.y() < curNode.p.y()) {
                return search(curNode.lb, p);
            }
            else {
                return search(curNode.rt, p);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null)
            return;
        draw(x.lb);
        draw(x.rt);

        // draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();

        // draw line
        // StdDraw.setPenRadius(0.001);
        if (x.level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
    }


    // all points that are inside the rectangle (or on the boundary)

    /*
    Range search. To find all points contained in a given query rectangle,
    start at the root and recursively search for points
    in both subtrees using the following pruning rule:
    if the query rectangle does not intersect the rectangle corresponding to a node,
    there is no need to explore that node (or its subtrees).
    A subtree is searched only if it might contain a point contained in the query rectangle.
    */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.IllegalArgumentException("null argument");
        // Queue<Point2D> pointsQ = new Queue<Point2D>();
        ArrayList<Point2D> pointsArr = new ArrayList<Point2D>();
        // StdOut.println(root.p.toString());

        rangeSeach(root, rect, pointsArr);
        return pointsArr;
    }

    private void rangeSeach(Node x, RectHV queryRect, ArrayList<Point2D> q) {
        if (x == null)
            return;
        if (isPointInsideRect(x.p, queryRect)) {
            // StdOut.println(x.p.toString() + " in ");
            q.add(x.p);
        }
        if (x.lb != null && isIntersect(queryRect, x.lb.rect)) {
            // StdOut.println("go left");
            rangeSeach(x.lb, queryRect, q);
        }
        if (x.rt != null && isIntersect(queryRect, x.rt.rect)) {

            rangeSeach(x.rt, queryRect, q);
        }
    }

    private boolean isIntersect(RectHV queryRect, RectHV nodeRect) {
        if (nodeRect == null)
            return false;
        // 1. if one rect's bottom is above the other's, return false
        if (queryRect.ymin() > nodeRect.ymax() || nodeRect.ymin() > queryRect.ymax()) {
            // StdOut.println("1");
            return false;
        }
        // 2. if one rect's right edge is on the left of the other's, return false
        if (queryRect.xmax() < nodeRect.xmin() || nodeRect.xmax() < queryRect.xmin()) {
            // StdOut.println("2");
            return false;
        }
        // 3. all else return true
        return true;
    }

    private boolean isPointInsideRect(Point2D p, RectHV r) {
        boolean res = (p.x() <= r.xmax() && p.x() >= r.xmin() && p.y() <= r.ymax()
                && p.y() >= r
                .ymin());
        // StdOut.println(res + p.toString());
        return res;
    }

    // a nearest neighbor in the set to point p; null if the set is empty

    /*
    Nearest-neighbor search.
    To find a closest point to a given query point,
    start at the root and recursively search in both subtrees using the following pruning rule:
    if the closest point discovered so far is closer than the distance
    between the query point and the rectangle corresponding to a node,
    there is no need to explore that node (or its subtrees).
    That is, search a node only only if it might contain a point that is closer than
    the best one found so far. The effectiveness of the pruning rule
    depends on quickly finding a nearby point. To do this,
    organize the recursive method so that when there are two possible subtrees to go down,
    you always choose the subtree that is on the same side of the splitting line
    as the query point as the first subtree to explore—the closest point found
    while exploring the first subtree may enable pruning of the second subtree.
    */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("null argument");
        if (root == null)
            return null;

        Point2D closestPoint = root.p;
        // StdOut.println(root.p.toString());

        return nearest(root, p, closestPoint);

        // return closestPoint;
    }

    /*
organize the recursive method so that when there are two possible subtrees to go down,
you always choose the subtree that is on the same side of the splitting line
as the query point as the first subtree to explore—
the closest point found while exploring the first subtree may enable pruning of the second subtree.
 */
    private Point2D nearest(Node curNode, Point2D queryPoint, Point2D closestPoint) {
        if (curNode == null)
            return closestPoint;
        if (curNode.p.equals(queryPoint))
            return curNode.p;

        // StdOut.println(curNode.p.toString());

        if (curNode.p.distanceSquaredTo(queryPoint) < closestPoint.distanceSquaredTo(queryPoint))
            closestPoint = curNode.p;

        // if distanceToLine is negative, means it's left or bottom
        // if distanceToLine is positive, means it's right or top
        double distanceToLine;
        if (curNode.level % 2 == 0) {
            distanceToLine = queryPoint.x() - curNode.p.x();
        }
        else {
            distanceToLine = queryPoint.y() - curNode.p.y();
        }
        if (distanceToLine < 0) {
            closestPoint = nearest(curNode.lb, queryPoint, closestPoint);

            if (closestPoint.distanceSquaredTo(queryPoint) >= distanceToLine * distanceToLine) {
                closestPoint = nearest(curNode.rt, queryPoint, closestPoint);
            }
        }
        else {
            closestPoint = nearest(curNode.rt, queryPoint, closestPoint);

            if (closestPoint.distanceSquaredTo(queryPoint) >= distanceToLine * distanceToLine) {
                closestPoint = nearest(curNode.lb, queryPoint, closestPoint);
            }
        }

        return closestPoint;
    }

    /*
        private double distanceBetweenPointAndRect(Point2D queryPoint, RectHV nodeRect) {
            // If that squared distance is zero, it means the point touches or is inside the rectangle.
            Point2D center = new Point2D(nodeRect.xmin() + (nodeRect.xmax() - nodeRect.xmin()) / 2,
                                         nodeRect.ymin() + (nodeRect.ymax() - nodeRect.ymin()) / 2);
            double width = nodeRect.xmax() - nodeRect.xmin();
            double height = nodeRect.ymax() - nodeRect.ymin();
            double dx = Math.max(Math.abs(queryPoint.x() - center.x()) - width / 2, 0.0);
            double dy = Math.max(Math.abs(queryPoint.y() - center.y()) - height / 2, 0.0);
            // return squared distance for performance
            return dx * dx + dy * dy;
        }
    */
    public static void main(String[] args) {
        KdTree kd = new KdTree();
/*
        Point2D A = new Point2D(0.7, 0.2);
        Point2D B = new Point2D(0.5, 0.4);
        Point2D C = new Point2D(0.2, 0.3);
        Point2D D = new Point2D(0.4, 0.7);
        Point2D E = new Point2D(0.9, 0.6);
        kd.insert(A);
        kd.insert(B);
        kd.insert(C);
        kd.insert(D);
        kd.insert(E);
        Point2D query = new Point2D(0.28, 0.41);
        Point2D point = kd.nearest(query);
        StdOut.println("res = " + point.toString());
        // A B D C
*/

        Point2D A = new Point2D(0.75, 0.125);
        Point2D B = new Point2D(0.5, 0.75);
        Point2D C = new Point2D(0.875, 0.375);
        Point2D D = new Point2D(1.0, 0.5);
        Point2D E = new Point2D(0.25, 0.875);
        kd.insert(A);
        kd.insert(B);
        kd.insert(C);
        kd.insert(D);
        kd.insert(E);
        Point2D query = new Point2D(0.0, 0.25);
        Point2D point = kd.nearest(query);
        StdOut.println("res = " + point.toString());
        // A B E

/*
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.RED);
        p3.draw();
        p4.draw();

        kd.draw(kd.root);
        kd.draw(kd.root.lb);
        kd.root.rect.draw();
        kd.root.lb.rect.draw();

        RectHV rectBad = new RectHV(0.3, -0.1, 0.6, 0.6);
        RectHV rectGood = new RectHV(0.3, 0.0, 0.6, 0.6);
        RectHV rectTest = new RectHV(0, 0, 1, 1);
        StdOut.println(kd.isIntersect(rectBad, rectTest));
        //StdOut.println(kd.isIntersect(rectGood, rectTest));


        Queue<Point2D> q = (Queue<Point2D>) kd.range(rectBad);
        for (Point2D p : q) {
            StdOut.println(p.toString());
        }
*/

    }
}
