import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

   private int num = 0;
   private Queue<LineSegment> point_pair ;
   private LineSegment[] LineSegments;
   private Iterator<LineSegment> iter;
   private Point[] clone;
   
   
   public BruteCollinearPoints(Point[] points) {
     point_pair = new Queue<LineSegment>();
     int len = points.length;
     
     clone = new Point[len];
     // 增加判断空点，并且为了immutable 新建clone数组
     for (int i = 0; i < len ; i++) {
       if(points[i] == null) throw new java.lang.NullPointerException("no point");
       clone[i] = points[i];
     } 
     
     Merge.sort(clone);
     for (int i = 0; i < len - 1; i++) {
         // 判断相等要用compareTo
       if (clone[i].compareTo(clone[i + 1]) == 0) throw new java.lang.IllegalArgumentException("duplicate");
     }
     
     if(len >= 4) {      
       for (int i = 0; i < len; i++) {
          for (int j = i + 1; j < len; j++) {
            for (int q = j + 1; q < len; q++) {
              for (int p = q + 1; p < len; p++) {
                  if ((clone[i].slopeTo(clone[j]) == clone[j].slopeTo(clone[q])) && (clone[j].slopeTo(clone[q]) == clone[q].slopeTo(clone[p]))) {
                    point_pair.enqueue(new LineSegment(clone[i],clone[p]));
                    num ++;            
                  }
              } 
            }
          }
       }
     }
   }
   
   public int numberOfSegments() {
     return num;
   }
   
   public LineSegment[] segments() {
     LineSegments = new LineSegment[num];
     int iter_num = 0;
     iter = point_pair.iterator();
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
    
    double aa = Double.POSITIVE_INFINITY;
    double bb = Double.POSITIVE_INFINITY;
    if (aa == bb) StdOut.println("bingo");

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    StdOut.println("Hello1\n" + collinear.numberOfSegments());
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}