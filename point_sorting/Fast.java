import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.005);  // make the points a bit larger

        In in = new In(args[0]);
        int count = in.readInt();
        Point[] points = new Point[count];
        Point[] sorted_points = new Point[count];
        for (int i = 0; i < count; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            points[i] = p;
            sorted_points[i] = p;
            p.draw();
        }
        Arrays.sort(points);
        for (Point k: points) {
            Arrays.sort(sorted_points, k.SLOPE_ORDER);
            for (int j = 1; j <= count-3; j++) {
                int incr = 1;
                while (j+incr < count && k.slopeTo(sorted_points[j]) == k.slopeTo(sorted_points[j+incr])) {
                    incr++;
                }
                if (incr >= 3) {
                    Point[] a = new Point[incr+1];
                    a[0] = k;
                    for (int z = 0; z < incr; z++) {
                        a[z+1] = sorted_points[j+z];
                    }
                    j += incr-1;
                    Arrays.sort(a);
                    if (k == a[0]) {
                        k.drawTo(a[a.length - 1]);
                        String out = "";
                        for (Point p : a) {
                            out += p.toString();
                            if (p == a[a.length - 1]) {
                                out += '\n';
                            } else {
                                out += " -> ";
                            }
                        }
                        System.out.print(out);
                    }
                }
            }
        }
        StdDraw.show(0);
    }
}
