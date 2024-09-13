import java.util.*;
class PuzzleSolver{
     void startGame(int[][] inputBoard){
        Board board = new Board(inputBoard);
        AstarSolver astarSolver = new AstarSolver(board);
        Stack<Board> solution = astarSolver.getSolution();

        
        System.out.println("Solution found:");
        printSolution(solution);
        
    }

    private static void printSolution(Stack<Board> solution) {
        while (!solution.isEmpty()) {
            Board board = solution.pop();
            printBoard(board);
            System.out.println();
        }
    }

    private static void printBoard(Board board) {
        int[][] tiles = board.getTiles();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%2d ", tiles[i][j]);
            }
            System.out.println();
        }
    }
}


public class FifteenPuzzle {
    public static void main(String[] args) {
        int[][] inputBoard = {
            {13, 2, 10, 3},
            {1, 12, 8, 4},
            {5, 0, 9, 6},
            {15, 14, 11, 7}
            
        };

        PuzzleSolver puzzleSolver = new PuzzleSolver();
        puzzleSolver.startGame(inputBoard);
    }
}
