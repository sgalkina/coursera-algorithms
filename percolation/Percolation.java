public class Percolation {
    private WeightedQuickUnionUF UF;
    private boolean[][] openMatrix;
    private int rowLen;
    private int total;
    private int fStart;
    private int fFinish;
    private WeightedQuickUnionUF BW;

    private class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x-1;
            this.y = y-1;
        }

        public int getIndex(int row) {
            if (0 <= this.x && this.x < row && 0 <= this.y && this.y < row) {
                return this.y % row + this.x * row;
            }
            return -1;
        }

        private Point[] getNeighbors() {
            Point[] array = {
                    new Point(this.x+1, this.y),
                    new Point(this.x+2, this.y+1),
                    new Point(this.x+1, this.y+2),
                    new Point(this.x, this.y+1)
            };
            return array;
        }
    }
    
    public Percolation(int N) {
        if (N <= 0) throw new java.lang.IllegalArgumentException();
        this.rowLen = N;
        this.total = N*N;
        this.openMatrix = new boolean[N][N];
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                this.openMatrix[x][y] = false;
            }
        }
        this.UF = new WeightedQuickUnionUF(total+2); // plus two fictive items
        this.BW = new WeightedQuickUnionUF(total+1); // for backwash
        this.fStart = total;
        this.fFinish = total+1;
    }

    public void open(int i, int j) {
        check(i, j);
        Point p = new Point(i, j);
        this.openMatrix[p.x][p.y] = true;
        int index = p.getIndex(this.rowLen);
        Point[] neighbors = p.getNeighbors();
        if (index >= 0 && index < this.rowLen) {
            this.UF.union(index, this.fStart);
            this.BW.union(index, this.fStart);
        }
        if (index >= this.total - this.rowLen && index < this.total) {
            this.UF.union(index, this.fFinish);
        }
        for (Point k: neighbors) {
            int n = k.getIndex(this.rowLen);
            if (n != -1 && isOpen(k.x+1, k.y+1)) {
                this.UF.union(index, n);
                this.BW.union(index, n);
            }
        }
    }

    private void check(int i, int j) {
        if (i <= 0 || i > this.rowLen) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > this.rowLen) throw new IndexOutOfBoundsException("row index j out of bounds");
    }
    
    public boolean isOpen(int i, int j) {
        check(i, j);
        Point p = new Point(i, j);
        return this.openMatrix[p.x][p.y];
    }

    public boolean isFull(int i, int j) {
        check(i, j);
        Point p = new Point(i, j);
        return this.BW.connected(fStart, p.getIndex(this.rowLen));
    }

    public boolean percolates() {
        return this.UF.connected(fStart, fFinish);
    }
}
