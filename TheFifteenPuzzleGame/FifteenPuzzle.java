class PuzzleSolver{
    public void startGame(int[][] inputBoard){
        Board board = new Board(inputBoard);
        AstarSolver astarSolver = new AstarSolver(board);

    }
}


public class FifteenPuzzle {
    public static void main(String[] args) {
        int[][] inputBoard = {
            {9, 10, 3, 4},
            {2, 5, 6, 12},
            {1, 11, 8, 0},
            {13, 7, 14, 15}
        };

        PuzzleSolver puzzleSolver = new PuzzleSolver();
        puzzleSolver.startGame(inputBoard);
    }
}
