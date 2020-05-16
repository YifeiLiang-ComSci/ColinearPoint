import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> lines = new ArrayList<>();

    public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
    {
        if(points == null)
        {
            throw new IllegalArgumentException();
        }
        Point[] pointsCopy = points.clone();
        try {
            Arrays.sort(pointsCopy);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        if(pointsCopy[0] == null)
        {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < pointsCopy.length; i++)
        {
            if (i != 0 && pointsCopy[i-1].compareTo(pointsCopy[i]) == 0)
            {
                throw new IllegalArgumentException();
            }
            Point[] temp = pointsCopy.clone();

            Point origin = temp[i];


            for (int index = i; index < pointsCopy.length - 1; index++)
            {
                temp[index] = temp[index + 1];
            }
            temp[temp.length - 1] = origin;

            Comparator<Point> com = pointsCopy[i].slopeOrder();


            if ((i != 0) && pointsCopy[i - 1].compareTo(pointsCopy[i]) == 0) {
                continue;
            }

            Arrays.sort(temp, 0, pointsCopy.length - 1, com);

            int start = 0;
            for (int j = 1; j < temp.length ; j++) {
                //if (origin.slopeTo(temp[j]) == origin.slopeTo(temp[j - 1])) continue;
                // more than 4 points are the same

                if (origin.slopeTo(temp[j]) != origin.slopeTo(temp[j - 1])
                        && (j - 1) - start < 2) {
                    start = j;
                    continue;
                }
                if (origin.slopeTo(temp[j]) != origin.slopeTo(temp[j - 1]) && (j - 1) - start >= 2) {



                    Point point1 = temp[start];
                    if (origin.compareTo(point1) < 0)
                    {
                        // origin is the head
                        lines.add(new LineSegment(origin,temp[j-1]));
                        start = j;
                    } else {
                            // origin is not the head
                        start = j;
                    }

                }



            }
        }


    }
    public int numberOfSegments()// the number of line segments
    {
        return lines.size();
    }
    public LineSegment[] segments() // the line segments
    {
        LineSegment [] segments = new LineSegment[numberOfSegments()];
        return lines.toArray(segments);
    }
    public static void main(String args[])
    {

        In in = new In(args[0]);

        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}