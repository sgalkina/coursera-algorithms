import java.util.Stack;

public class Solver {
    private Node result;

    private class Node implements Comparable {
        public Board board;
        public Node previous;
        public int path;

        public Node(Board board, Node previous, int path) {
            this.board = board;
            this.previous = previous;
            this.path = path;
        }

        public int compareTo(Object obj) {
            Node another = (Node) obj;
            int distance = this.path + this.board.manhattan();
            int another_distance = another.path + another.board.manhattan();
            if (distance < another_distance) {
                return -1;
            }
            if (distance > another_distance) {
                return 1;
            }
            else return 0;
        }
    }

    private boolean isInHistory(Board board, Node node) {
        while (node.previous != null) {
            if (board.equals(node.previous.board)) {
                return true;
            }
            node = node.previous;
        }
        return false;
    }

    public Solver(Board initial) {
        MinPQ<Node> PQ = new MinPQ<Node>(1);
        MinPQ<Node> twinPQ = new MinPQ<Node>(1);
        PQ.insert(new Node(initial, null, 0));
        twinPQ.insert(new Node(initial.twin(), null, 0));
        this.result = null;
        while (!PQ.isEmpty()) {
            Node min = PQ.min();
            Node twinMin = twinPQ.min();
            if (min.board.isGoal()) {
                this.result = min;
                break;
            }
            if (twinMin.board.isGoal()) {
                break;
            }
            for (Board i: min.board.neighbors()) {
                if (!isInHistory(i, min)) {
                    PQ.insert(new Node(i, min, min.path + 1));
                }
            }
            for (Board i: twinMin.board.neighbors()) {
                if (!isInHistory(i, twinMin)) {
                    twinPQ.insert(new Node(i, twinMin, twinMin.path + 1));
                }
            }
            PQ.delMin();
            twinPQ.delMin();
        }
    }

    public boolean isSolvable() {
        return this.result != null;
    }

    public int moves() {
        if (isSolvable()) {
            int i = -1;
            for (Board j: solution()) {
                i++;
            }
            return i;
        }
        return -1;
    }

    public Iterable<Board> solution() {
        Stack<Board> stack = new Stack<Board>();
        Stack<Board> main = new Stack<Board>();
        Node node = result;
        while (node != null) {
            stack.push(node.board);
            node = node.previous;
        }
        while (!stack.empty()) {
            main.push(stack.pop());
        }
        return main;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
