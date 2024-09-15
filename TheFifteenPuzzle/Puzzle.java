import java.util.*;
class StartPuzzle{
    private static int[][] inputBoard;
    private static int[] rowMoves = {-1, 1, 0, 0}; // up, down , left, right for row index
    private static int[] colMoves = {0, 0, -1, 1};// up, down , left, right for column index
    private static int size;
    private int emptyTileRow;
    private int emptyTileCol;
    
    public StartPuzzle(int[][] givenBoard){
        this.size = givenBoard.length;
        this.inputBoard = givenBoard;
        findEmptyRowAndCol();
    }

    private void findEmptyRowAndCol(){
        for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++){
                if (inputBoard[row][col] == 0){
                    emptyTileRow = row;
                    emptyTileCol = col;
                    break;
                }
            }
        }
        printBoard(inputBoard);
    }

    public void printBoard(int[][] inputBoard){
        for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++){
                System.out.printf("%3d", inputBoard[row][col]);
            }
            System.out.println();
        }
    }

    private boolean isGoal(){
        int number = 1;
        for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++){
                if (inputBoard[row][col] != number) {
                    return false;
                }
                number += 1;
                if (number == (size * size)){
                    number = 0;
                }
            }
        }
        return true;
    }

    public void playGame(){

        while (!isGoal()) {
            Scanner input = new Scanner(System.in);
            System.out.print("To move emptySpace(0) you can type any one UP /DOWN /LEFT /RIGHT:");
            String move = input.next().toLowerCase();

            if (!isinputCorrect(move)){
                printBoard(inputBoard);
                System.out.println("Typed Wrongly... Please type any one to move the empty space(0) (UP /DOWN /LEFT /RIGHT)");
                continue;
            }
            
            int newRow = emptyTileRow + rowMoves[correctMove(move)];
            int newCol = emptyTileCol + colMoves[correctMove(move)];

            if (!isValidMove(newRow, newCol)){
                printBoard(inputBoard);
                System.out.println("it is not valid move... ");
                continue;
            }

            inputBoard[emptyTileRow][emptyTileCol] = inputBoard[newRow][newCol];
            System.out.println(inputBoard[emptyTileRow][emptyTileCol]);
            inputBoard[newRow][newCol] = 0;
            
            emptyTileRow = newRow;
            emptyTileCol = newCol;
            printBoard(inputBoard);
        }

        System.out.println("Hey!!! You are win!");
        
    }

    private boolean isValidMove(int newRow, int newCol){
        return (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size);
    }

    private int correctMove(String move){
        if (move.equals("up")){
            return 0;
        }else if (move.equals("down")){
            return 1;
        }else if (move.equals("left")){
            return 2;
        }else{
            return 3;
        }
    }

    private boolean isinputCorrect(String move){

        if (move.equals("up") || move.equals("down") || move.equals("left") || move.equals("right")){
            return true;
        }else{
            return false;
        }
        
    }

}

public class Puzzle {
    public static void main(String[] args) {
        int[][] inputBoard = {
                {1, 2, 3, 4},
                {5, 6, 0, 8},
                {9, 10, 7, 11},
                {13, 14, 15, 12}
                // {1, 2, 3, 4},
                // {5, 6, 7, 8},
                // {9, 10, 11, 12},
                // {13, 14, 15, 0}
                
                
            };
        StartPuzzle startPuzzle = new StartPuzzle(inputBoard);
        
        startPuzzle.playGame();
        
    }
}
