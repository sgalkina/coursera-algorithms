public class PercolationStats {
    private double[] fractions;
    private int T;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) throw new java.lang.IllegalArgumentException();
        this.T = T;
        this.fractions = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            double total = (double) N*N;
            for (int j = 0; j < N*N; j++) {
                int x = StdRandom.uniform(1, N+1);
                int y = StdRandom.uniform(1, N+1);
                while (p.isOpen(x, y)) {
                    x = StdRandom.uniform(1, N+1);
                    y = StdRandom.uniform(1, N+1);
                }
                p.open(x, y);
                if (p.percolates()) {
                    double J = (double) j+1;
                    this.fractions[i] = J/total;
                    break;
                }
            }
        }
    }

    private double sum(double[] array) {
        double sum = 0.0;
        for (double a: array) {
            sum += a;
        }
        return sum;
    }

    public double mean() {
        double t = (double) this.T;
        return sum(this.fractions)/t;
    }

    public double stddev() {
        double[] dev = new double[T];
        for (int i = 0; i < T; i++) {
            dev[i] = (this.fractions[i] - mean())*(this.fractions[i] - mean());
        }
        return Math.sqrt(sum(dev)/(T-1));
    }

    private double conf() {
        return 1.96*stddev()/Math.sqrt(this.T);
    }

    public double confidenceLo() {
        return mean() - conf();
    }

    public double confidenceHi() {
        return mean() + conf();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(n, t);
        System.out.print("mean                    = " + p.mean() + "\n" +
                        "stddev                  = " + p.stddev() + "\n" +
                        "95% confidence interval = " + p.confidenceLo() + ", " + p.confidenceHi() + "\n");
    }
}