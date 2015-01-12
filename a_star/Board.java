import java.util.Stack;
import java.util.Arrays;

public class Board {
    private int D;
    private int[][] blocks;
    private int manhattan;

    public Board(int[][] b) {
        D = b.length;
        this.blocks = new int[D][D];
        for (int i = 0; i < D; i++) {
            for (int j = 0; j < D; j++) {
                this.blocks[i][j] = b[i][j];
                if(this.blocks[i][j] != 0 && this.blocks[i][j] != i * D + j % D + 1) {
                    manhattan += Math.abs(i - ((this.blocks[i][j] - 1)/D)) +
                            Math.abs(j - ((this.blocks[i][j] - 1)%D));
                }
            }
        }
    }

    public int dimension() {
        return D;
    }

    public int hamming() {
        int h = 0;
        for (int i = 0; i < D; i++) {
            for (int j = 0; j < D; j++) {
                if(this.blocks[i][j] != 0 && this.blocks[i][j] != i * D + j % D + 1) {
                    h++;
                }
            }
        }
        return h;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return manhattan() == 0;
    }

    private int[][] exchange(int i1, int j1, int i2, int j2) {
        int[][] board = new int[D][D];
        for (int i = 0; i < D; i++) {
            board[i] = Arrays.copyOf(this.blocks[i], D);
        }
        int a;
        a = board[i1][j1];
        board[i1][j1] = board[i2][j2];
        board[i2][j2] = a;
        return board;
    }

    public Board twin() {
        int [][] board;
        if (this.blocks[0][0] == 0 || this.blocks[0][1] == 0) {
            board = exchange(1, 0, 1, 1);
        }
        else {
            board = exchange(0, 0, 0, 1);
        }
        return new Board(board);
    }

    public boolean equals(Object y) {
        if (this == y) { return true; }
        if ( !(y instanceof Board) ) { return false; }
        Board that = (Board) y;
        return Arrays.deepEquals(this.blocks, that.blocks);
    }

    private int[] getEmpty() {
        int[] empty = new int[2];
        for (int i = 0; i < D; i++) {
            for (int j = 0; j < D; j++) {
                if (blocks[i][j] == 0) {
                    empty[0] = i;
                    empty[1] = j;
                }
            }
        }
        return empty;
    }

    public Iterable<Board> neighbors() {
        int[] empty = getEmpty();
        int i = empty[0];
        int j = empty[1];
        int[][] pr = {{i, j-1}, {i, j+1}, {i+1, j}, {i-1, j}};
        Stack<Board> stack = new Stack<Board>();
        for (int[] n: pr) {
            if (n[0] < 0 || n[0] >= D || n[1] < 0 || n[1] >= D) {
                n[0] = -1;
            }
        }
        for (int x = 0; x < 4; x++) {
            if (pr[x][0] != -1) {
                stack.push(new Board(exchange(pr[x][0], pr[x][1], i, j)));
            }
        }
        return stack;
    }

    public String toString() {
        String result = "";
        result += D + "\n";
        for (int[] row: this.blocks) {
            result += ' ';
            for (int i = 0; i < D; i++) {
                result += row[i];
                if (i != D-1) {
                    result += "  ";
                }
            }
            result += '\n';
        }
        return result;
    }
}
