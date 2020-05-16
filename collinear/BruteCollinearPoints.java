import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints
{
   private java.util.List<LineSegment> list = new ArrayList<>();

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null)
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
        //sd
        for(int i = 0 ; i < pointsCopy.length-1;i++)
        {
            if(pointsCopy[i].compareTo(pointsCopy[i+1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        for (int first = 0; first < pointsCopy.length-3; first++)
        {
            if (first != 0 && pointsCopy[first-1].compareTo(pointsCopy[first]) == 0)
            {
                throw new IllegalArgumentException();
            }
            for (int second = first+1; second < pointsCopy.length - 2; second++)
            {
                if (pointsCopy[second-1].compareTo(pointsCopy[second]) == 0)
                {
                    throw new IllegalArgumentException();
                }
                for (int third = second+1; third < pointsCopy.length - 1; third++)
                {
                    if (pointsCopy[third-1].compareTo(pointsCopy[third]) == 0)
                    {
                        throw new IllegalArgumentException();
                    }
                    for (int fourth = third + 1; fourth < pointsCopy.length; fourth++)
                    {

                        if (pointsCopy[fourth-1].compareTo(pointsCopy[fourth]) == 0)
                        {
                            throw new IllegalArgumentException();
                        }
                        double initialSlope = pointsCopy[first].slopeTo(pointsCopy[second]);
                        if (pointsCopy[first].slopeTo(pointsCopy[third]) == initialSlope &&
                                pointsCopy[first].slopeTo(pointsCopy[fourth]) == initialSlope)
                        {
                            list.add(new LineSegment(pointsCopy[first],pointsCopy[fourth]));
                        }

                    }

                }
            }
        }

    }
    public int numberOfSegments()        // the number of line segments
    {

        return list.size();
    }
    public LineSegment[] segments()                // the line segments
    {
        LineSegment [] segments = new LineSegment[numberOfSegments()];
        return list.toArray(segments);
    }
    public static void main(String args[])
    {
        int num = Integer.parseInt(StdIn.readLine());

        Point[] points = new Point[num];
        int index=0;
        while(!StdIn.isEmpty())
        {
            String str = StdIn.readLine();

            str = str.trim();

            String ints[] = str.split("\\s+");

            points[index] = new Point(Integer.parseInt(ints[0]),Integer.parseInt(ints[1]));
            index++;
        }

        BruteCollinearPoints bp = new BruteCollinearPoints(points);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            //StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}