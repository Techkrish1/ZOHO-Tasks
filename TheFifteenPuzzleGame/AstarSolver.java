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

        while (!queue.isEmpty()) {
            currentNode = queue.poll();

            if (currentNode.board.isGoal()) {
                break;
            }

            // Generate neighbors and add them to the queue
            List<Board> neighbors = currentNode.board.getNeighbors();
            for (int i = 0; i < neighbors.size(); i++) {
                Board neighbor = neighbors.get(i);
                String boardState = neighbor.toString();
                if (!visited.contains(boardState)) {
                    SearchNode newNode = new SearchNode(neighbor, currentNode);
                    queue.add(newNode);
                    visited.add(boardState);
                }
            }

        }

        // Full Solution path retrieves
        solution = new Stack<>();
        while (currentNode != null) {
            solution.push(currentNode.board);
            currentNode = currentNode.previous;
        }
    }

    public Stack<Board> getSolution() {
        return solution;
    }
}
