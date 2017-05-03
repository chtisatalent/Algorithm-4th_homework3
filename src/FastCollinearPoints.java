
import java.util.Arrays;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
   private int num = 0;
   private Point[] slopes;
   private Queue<LineSegment> point_pair;
   private LineSegment[] LineSegments;
   private Iterator<LineSegment> iter;
   private Point[] clone;
   
   public FastCollinearPoints(Point[] points) {
     point_pair = new Queue<LineSegment>();
     int len = points.length;
     clone = new Point[len];
     // 增加判断空点，并且为了immutable 新建clone数组
     for (int i = 0; i < len ; i++) {
       if(points[i] == null) throw new java.lang.NullPointerException("no point");
       clone[i] = points[i];
     }   
          
     Arrays.sort(clone);
     for (int i = 0; i < len - 1; i++) {
         // 判断相等要用compareTo
       if (clone[i].compareTo(clone[i + 1]) == 0) throw new java.lang.IllegalArgumentException("duplicate");
     }
     
     for (int i = 0; i < len; i++) {
      // StdOut.print("now point i:"+ i + " " +clone[i] );
       slopes = new Point [len - i - 1];
       for (int j = i + 1; j < len; j++){
         slopes[j - i - 1] = clone[j];
         // 利用斜率来判断重复点
       }
       
       Arrays.sort(slopes, (clone[i]).slopeOrder());
       
     //  for (int h = 0; h < slopes.length; h++) {
     //    StdOut.print(slopes[h] + " ");
      // }
     //  StdOut.println("\nhallo");
       
       // 一定要初始化 不然findbugs会爆 Read of unwritten field
       

       int p = 0;
       for (; p < slopes.length - 2; p ++) {
         if (clone[i].slopeTo(slopes[p]) == clone[i].slopeTo(slopes[p + 2])) {
          // StdOut.print("now p:" +p + " point is" + slopes[p]);
           p = p + 1;
           //StdOut.print("now 2rd point is" + slopes[p]);
           p = p + 1;
          //  StdOut.println("now 3rd point is" + slopes[p]);
         //  StdOut.println("::" + slopes[p]);
          for (; p < slopes.length - 1; p++) {
             if (slopes[p] != slopes[p + 1]) {
               break;
             }
          }   
           point_pair.enqueue(new LineSegment(clone[i],slopes[p]));
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

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    StdOut.println("Hello2\n"+ collinear.numberOfSegments());
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}