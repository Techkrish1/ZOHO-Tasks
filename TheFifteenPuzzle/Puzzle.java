/*
It also works for n puzzle (8puzzle, 6puzzle, etc) by adjustiong the input board.
Following are the actual game reference. And here, Empty tile denoted as 0.
----------------------
 |  1|  2|  3|  4|
 |  5|  6|  0|  8|
 |  9| 10|  7| 11|
 | 13| 14| 15| 12|    
----------------------
---> Type any one UP /DOWN /LEFT /RIGHT: down
----------------------
 |  1|  2|  3|  4|    
 |  5|  6|  7|  8|    
 |  9| 10|  0| 11|
 | 13| 14| 15| 12|
----------------------
---> Type any one UP /DOWN /LEFT /RIGHT: right
----------------------
 |  1|  2|  3|  4|
 |  5|  6|  7|  8|
 |  9| 10| 11|  0|
 | 13| 14| 15| 12|
----------------------
---> Type any one UP /DOWN /LEFT /RIGHT: down
----------------------
 |  1|  2|  3|  4|
 |  5|  6|  7|  8|
 |  9| 10| 11| 12|
 | 13| 14| 15|  0|
----------------------
Hey!!! You are win!
 */


import java.util.*;
class StartPuzzle{
    private int[][] inputBoard;
    private final int[] rowMoves = {-1, 1, 0, 0}; // up, down , left, right for row index
    private final int[] colMoves = {0, 0, -1, 1};// up, down , left, right for column index
    private final int rowSize;
    private final int colSize;
    private int emptyTileRow;
    private int emptyTileCol;
    
    public StartPuzzle(int[][] givenBoard){
        this.rowSize = givenBoard.length;
        this.colSize = givenBoard[0].length;
        this.inputBoard = givenBoard;
        findEmptyRowAndCol();
        printBoard();
    }

    private void findEmptyRowAndCol(){   // finding the empty tile's  row and column 
        for (int row = 0; row < rowSize; row++){
            for (int col = 0; col < colSize; col++){
                if (inputBoard[row][col] == 0){
                    emptyTileRow = row;
                    emptyTileCol = col;
                    break;
                }
            }
        }
    }

    public void printBoard(){
        System.out.println("----------------------");
        for (int row = 0; row < rowSize; row++){
            System.out.print(" |");
            for (int col = 0; col < colSize; col++){
                System.out.printf("%3d", inputBoard[row][col]);
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println("----------------------");
    }

    private boolean isGoal(){
        int number = 1;
        for (int row = 0; row < rowSize; row++){
            for (int col = 0; col < colSize; col++){
                if (inputBoard[row][col] != number) {
                    return false;
                }
                number += 1;
                if (number == (rowSize * colSize)){
                    number = 0;
                }
            }
        }
        return true;
    }

    public void playGame(){
        Scanner input = new Scanner(System.in);

        while (isGoal() == false) {
            System.out.print("---> Type any one UP /DOWN /LEFT /RIGHT: ");
            String move = input.next().toLowerCase();

            if (isinputCorrect(move) == false){
                printBoard();
                System.out.println("Typed Wrongly... Please type any one to move the empty space(0) (UP /DOWN /LEFT /RIGHT)");
                continue;
            }
            
            int newRow = emptyTileRow + rowMoves[getStepIndex(move)];
            int newCol = emptyTileCol + colMoves[getStepIndex(move)];

            if (isValidMove(newRow, newCol) == false){
                printBoard();
                System.out.println("it is not a valid move... ");
                continue;
            }

            inputBoard[emptyTileRow][emptyTileCol] = inputBoard[newRow][newCol];
            inputBoard[newRow][newCol] = 0;
            
            emptyTileRow = newRow;
            emptyTileCol = newCol;
            printBoard();
        }
        input.close();
        System.out.println("Hey!!! You are win!");
        
    }

    private boolean isValidMove(int newRow, int newCol){  // checking given row and column is possible or not
        return (newRow >= 0 && newRow < rowSize && newCol >= 0 && newCol < colSize);
    }

    private int getStepIndex(String move){  // it returns index of the row and column to move up or down or left or right
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

    private boolean isinputCorrect(String move){   // checking the input name is correct or not

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
                // {1, 2, 3},
                // {4, 0, 6},
                // {7, 5, 8}
                // {1,0,3},
                // {4,2,5}
                
            };
        StartPuzzle startPuzzle = new StartPuzzle(inputBoard);
        
        startPuzzle.playGame();
        
    }
}
