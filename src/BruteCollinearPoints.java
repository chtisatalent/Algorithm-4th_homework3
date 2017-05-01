import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
   private int num = 0;
   private Queue<LineSegment> point_pair ;
   private LineSegment[] LineSegments;
   public BruteCollinearPoints(Point[] points) {
     if (points == null) throw new java.lang.NullPointerException("no point");
     point_pair = new Queue<LineSegment>() ;
     int len = points.length;
     Merge.sort(points);
     for (int i = 0; i < len; i++) {
        for (int j = i + 1; j < len; j++) {
        // 利用外层循环检测重复点
        if(points[i] == points[j]) throw new java.lang.IllegalArgumentException("duplicate");
          for (int q = j + 1; q < len; q++) {
            for (int p = q + 1; p < len; p++) {
                if ((points[i].slopeTo(points[j]) == points[j].slopeTo(points[q])) && (points[j].slopeTo(points[q]) == points[q].slopeTo(points[p]))) {
              /*    StdOut.println("0" + points[i] + " " + points[j] + " "+ points[q]+ " "+points[p]);
                  StdOut.println("1" + points[i].slopeTo(points[j]));
                  StdOut.println("2" + points[j].slopeTo(points[q]));
                  StdOut.println("3" + points[q].slopeTo(points[p]));*/
                  point_pair.enqueue(new LineSegment(points[i],points[p]));
                  num ++;            
                }
            } 
          }
        }
     }
     LineSegments = new LineSegment[num];
     for (int i = 0; i < num; i++) {
       LineSegments[i] = point_pair.dequeue();
     }
   }
   public int numberOfSegments() {
     return num;
   }
   
   public LineSegment[] segments() {
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
    StdOut.println("Helllo" + collinear.numberOfSegments());
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}