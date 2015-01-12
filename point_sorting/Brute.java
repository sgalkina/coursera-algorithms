public class Brute {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.005);  // make the points a bit larger

        In in = new In(args[0]);
        int count = in.readInt();
        Point[] points = new Point[count];
        for (int i = 0; i < count; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            points[i] = p;
            p.draw();
        }
        for (Point k: points) {
            for (Point l: points) {
                for (Point m: points) {
                    for (Point n: points) {
                        if (k.slopeTo(l) == k.slopeTo(m) && k.slopeTo(m) == k.slopeTo(n)) {
                            if (k.compareTo(l) < 0 && l.compareTo(m) < 0 && m.compareTo(n) < 0) {
                                k.drawTo(n);
                                System.out.print(
                                        k.toString() + " -> " +
                                        l.toString() + " -> " +
                                        m.toString() + " -> " +
                                        n.toString() + "\n"
                                );
                            }
                        }
                    }
                }
            }
        }
        StdDraw.show(0);
    }
}
