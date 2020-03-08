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

public class FastCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] returnSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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

        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

        /*
        * A faster, sorting-based solution. Remarkably,
        * it is possible to solve the problem much faster than the brute-force solution described above.
        * Given a point p, the following method determines whether p participates
        * in a set of 4 or more collinear points.

        Think of p as the origin.
        For each other point q, determine the slope it makes with p.
        Sort the points according to the slopes they makes with p.
        Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p.
        If so, these points, together with p, are collinear.
        */

        for (int i = 0; i < numOfPoints; i++) {
            Point p = pointsCopy[i];
            Point[] eachOtherPoints = Arrays.copyOfRange(pointsCopy, i + 1, numOfPoints);
            Arrays.sort(eachOtherPoints, p.slopeOrder());
            for (int j = 0; j < eachOtherPoints.length - 2; j++) {
                if (eachOtherPoints[j].slopeTo(p) == eachOtherPoints[j + 1].slopeTo(p)
                        && eachOtherPoints[j].slopeTo(p) == eachOtherPoints[j + 2].slopeTo(p)) {
                    segments.add(new LineSegment(p, eachOtherPoints[j + 2]));
                    numberOfSegments++;
                }
            }
        }

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
