import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
   private int num = 0;
   private Double[] slopes;
   private Queue<LineSegment> point_pair;
   private LineSegment[] LineSegments;
   
   public FastCollinearPoints(Point[] points) {
     Merge.sort(points);

     int len = points.length;
     for (int i = 0; i < len; i++) {
       slopes = new Double [len - i - 1];
       for (int j = i + 1; j < len; j++){
         slopes[j - i - 1] = (points[i].slopeTo(points[j]));
         // 利用斜率来判断重复点
         if (slopes[j - i - 1] == Double.NEGATIVE_INFINITY) throw new java.lang.IllegalArgumentException("duplicate");
       }
       Merge.sort(slopes);
       for (int p = 0; p < slopes.length - 2; p++) {
         if (slopes [p] == slopes[p + 2]) {
           int endpoint = p + 2;
           for (int e = p + 2; e < slopes.length; e++) {
             if (slopes[e] != slopes[e + 1]) {
               endpoint = e;
               break;
             }
           }
           point_pair.enqueue(new LineSegment(points[i],points[endpoint+ 1 + i]));
           num ++;
         }
       }
     }
   }
   
   public int numberOfSegments()  {
     return num;
   }
   
   public LineSegment[] segments() {
     LineSegments = new LineSegment[num];
     int iter_num = 0;
     Iterator<LineSegment> iter = point_pair.iterator();
     while (iter.hasNext()) {
       LineSegments[iter_num] = iter.next();
       iter_num++;
     }
     return LineSegments;
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

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    StdOut.println("Hello2\n"+ collinear.numberOfSegments());
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}