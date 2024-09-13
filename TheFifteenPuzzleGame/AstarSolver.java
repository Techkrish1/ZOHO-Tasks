import java.util.*;

class SearchNode implements Comparable<SearchNode> {
    Board board;
     int moves;
     SearchNode previous;

    public SearchNode(Board board, SearchNode previous) {
        this.board = board;
        this.previous = previous;
        this.moves = (previous == null) ? 0 : previous.moves + 1;
    }

    public int getPriority() {
        return moves + board.getManhattanDistance() + board.getLinearConflict();
    }

    @Override
    public int compareTo(SearchNode other) {
        return Integer.compare(this.getPriority(), other.getPriority());
    }
}

public class AstarSolver {
    private Stack<Board> solution;
    private SearchNode initialNode;

    public AstarSolver(Board initial){
        PriorityQueue<SearchNode> queue = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        initialNode = new SearchNode(initial, null);
        queue.add(initialNode);
        visited.add(initialNode.board.toString());

        SearchNode currentNode = null;
    }
}
