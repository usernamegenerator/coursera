/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

package Sorting.ProgrammingAssignmentCollinearPoints.collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] returnSegments;


    // finds all line segments containing 4 points
    // points is a set of n distinct points in the plane
    public BruteCollinearPoints(Point[] points) {
        // sanity check
        if (points == null)
            throw new java.lang.IllegalArgumentException("null");

        int numOfPoints = points.length;

        for (Point p : points) {
            if (p == null)
                throw new java.lang.IllegalArgumentException("null");
        }
        for (int i = 0; i < numOfPoints - 1; i++) {
            for (int j = i + 1; j < numOfPoints; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException("null");
            }
        }
        // sanity check end

        Arrays.sort(points);
        ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
        /*
         * Brute force. Write a program BruteCollinearPoints.java that
         * examines 4 points at a time and checks whether they all lie on the same line segment,
         * returning all such line segments. To check whether the 4 points p, q, r, and s are collinear,
         * check whether the three slopes between p and q, between p and r,
         * and between p and s are all equal.
         * */

        for (int i = 0; i < numOfPoints - 3; i++) {
            Point p = points[i];
            for (int j = i + 1; j < numOfPoints - 2; j++) {
                Point q = points[j];
                for (int k = j + 1; k < numOfPoints - 1; k++) {
                    Point r = points[k];
                    for (int m = k + 1; m < numOfPoints; m++) {
                        Point s = points[m];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            segments.add(new LineSegment(p, s));
                        }
                    }
                }

            }
        }

        numberOfSegments = segments.size();
        returnSegments = segments.toArray(new LineSegment[numberOfSegments]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] deepCopy = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            deepCopy[i] = returnSegments[i];
        }
        return deepCopy;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // Point origin = new Point(0, 0);
        // Point test = new Point(1, 1);

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
            // p.drawTo(origin);
        }

        // StdOut.println(test.slopeTo(new Point(0, 5)));

        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        // StdOut.println("=======================");
        // StdOut.println(collinear.numberOfSegments);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            // StdOut.println(collinear.numberOfSegments());
            segment.draw();
        }
        StdDraw.show();

    }

}

