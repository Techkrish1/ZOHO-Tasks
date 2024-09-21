/*
 Here, I used backtracking to reveal how many mines around the square(3*3 matrix)
 Following is the overview of the working code

Total Ramaining Flags : 9

     0  1  2  3  4  5  6  7  8  9
  0  .  .  .  .  .  .  .  .  .  .
  1  .  .  .  .  .  .  .  .  .  .
  2  .  .  .  .  .  .  .  .  .  .
  3  .  .  .  .  .  .  .  .  .  .
  4  .  .  .  .  .  .  .  .  .  .
  5  .  .  .  .  .  .  .  .  .  .
  6  .  .  .  .  .  .  .  .  .  .
  7  .  .  .  .  .  .  .  .  .  .
  8  .  .  .  .  .  .  .  .  .  .
  9  .  .  .  .  .  .  .  .  .  .

Enter action (reveal/flag) and coordinates (e.g., reveal 3 4): reveal 1 7

Total Ramaining Flags : 9

     0  1  2  3  4  5  6  7  8  9
  0  .  .  .  .  .  .  .  .  .  .
  1  .  .  .  .  .  .  .  1  .  .
  2  .  .  .  .  .  .  .  .  .  .
  3  .  .  .  .  .  .  .  .  .  .
  4  .  .  .  .  .  .  .  .  .  .
  5  .  .  .  .  .  .  .  .  .  .
  6  .  .  .  .  .  .  .  .  .  .
  7  .  .  .  .  .  .  .  .  .  .
  8  .  .  .  .  .  .  .  .  .  .
  9  .  .  .  .  .  .  .  .  .  .

Enter action (reveal/flag) and coordinates (e.g., reveal 3 4): reveal 5 2 

Total Ramaining Flags : 9

     0  1  2  3  4  5  6  7  8  9
  0  0  0  0  1  .  .  .  .  1  0
  1  0  0  0  1  .  1  1  1  1  0
  2  0  0  0  1  1  1  0  0  0  0
  3  0  0  0  0  0  0  0  0  1  1
  4  0  0  0  0  0  0  0  1  3  .
  5  0  0  0  1  1  1  0  2  .  .
  6  0  0  0  1  .  1  0  3  .  .
  7  0  0  0  1  1  1  0  2  .  .
  8  0  0  0  0  0  0  0  1  2  2
  9  0  0  0  0  0  0  0  0  0  0

Enter action (reveal/flag) and coordinates (e.g., reveal 3 4): flag 6 4   

Total Ramaining Flags : 8

     0  1  2  3  4  5  6  7  8  9
  0  0  0  0  1  .  .  .  .  1  0
  1  0  0  0  1  .  1  1  1  1  0
  2  0  0  0  1  1  1  0  0  0  0
  3  0  0  0  0  0  0  0  0  1  1
  4  0  0  0  0  0  0  0  1  3  .
  5  0  0  0  1  1  1  0  2  .  .
  6  0  0  0  1  F  1  0  3  .  .
  7  0  0  0  1  1  1  0  2  .  .
  8  0  0  0  0  0  0  0  1  2  2
  9  0  0  0  0  0  0  0  0  0  0

Enter action (reveal/flag) and coordinates (e.g., reveal 3 4): reveal 1 4

Total Ramaining Flags : 8

     0  1  2  3  4  5  6  7  8  9
  0  0  0  0  1  .  .  .  M  1  0
  1  0  0  0  1  M  1  1  1  1  0
  2  0  0  0  1  1  1  0  0  0  0
  3  0  0  0  0  0  0  0  0  1  1
  4  0  0  0  0  0  0  0  1  3  M
  5  0  0  0  1  1  1  0  2  M  M
  6  0  0  0  1  F  1  0  3  M  .
  7  0  0  0  1  1  1  0  2  M  M
  8  0  0  0  0  0  0  0  1  2  2
  9  0  0  0  0  0  0  0  0  0  0

Game Over! You hit a mine.
 */



import java.util.*;

class MinesweeperGame{
    private int rowSize = 10; 
    private int colSize = 10;
    private int totalMines = 9; 
    private char emptyCells = '.';
    private char mine = 'M';
    private char flag = 'F';
    private int remainingFlags = totalMines;

    private boolean[][] minesPositions;
    private boolean[][] revealedPositions;
    private boolean[][] flaggedPositions;
    private boolean gameOver;

    public MinesweeperGame() {
        minesPositions = new boolean[rowSize][colSize];
        revealedPositions = new boolean[rowSize][colSize];
        flaggedPositions = new boolean[rowSize][colSize];
        gameOver = false;
        initializeBoard();
        printBoard();
    }

    private void initializeBoard(){
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                minesPositions[row][col] = false;
                revealedPositions[row][col]= false;
                flaggedPositions[row][col] = false;
            }
        }

        Random rand = new Random();
        int placedMines = 0;
        while (placedMines < totalMines) {
            int x = rand.nextInt(rowSize);
            int y = rand.nextInt(colSize);
            if (!minesPositions[x][y]) {
                minesPositions[x][y] = true;
                placedMines++;
            }
        }
    }

    private void startGame(String input){
        String[] parts = input.split(" ");
        if (parts.length < 3 || parts.length > 3) {
            System.out.println("Invalid input and don't put extra space. Use format: 'action row column'");
            return;
        }

        String action = parts[0].toLowerCase();
        try{
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            if (action.equals("reveal")) {
                if (x < 0 || x >= rowSize || y < 0 || y >= colSize) {
                    System.out.println("Invalid input. Try again.");
                    return;
                }
                reveal(x, y);
            } else if (action.equals("flag")) {
                flag(x, y);
            } else {
                System.out.println("Unknown action. Use 'reveal' or 'flag'.");
            }
        }catch (NumberFormatException e){
            System.out.println("Invalid input Rows and columns should be in integer...");
        }
        

    }

    private void printBoard() {
        System.out.println();
        System.out.println("Total Ramaining Flags : " + remainingFlags);
        System.out.println();
        System.out.printf("%3s", " ");
        for (int col = 0; col < colSize; col++) {
            System.out.printf("%3d",col);
        }
        System.out.println();
        for (int row = 0; row < rowSize; row++) {
            System.out.printf("%3d",row);
            for (int col = 0; col < colSize; col++) {
                if (flaggedPositions[row][col]) {
                    System.out.printf("%3c",flag);
                } else if (revealedPositions[row][col]) {
                    if (minesPositions[row][col]) {
                        System.out.printf("%3c",mine);
                    } else {
                        System.out.printf("%3d",countMines(row, col));
                    }
                } else {
                    System.out.printf("%3c",emptyCells);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean checkWins(){
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (minesPositions[row][col]) {
                    if (!flaggedPositions[row][col]) {
                        return false; 
                    }
                }
            }
        }
        return true;

    }

    private int countMines(int x, int y){
        int count = 0;
        for (int row = x - 1; row <= x + 1; row++) {
            for (int col = y - 1; col <= y + 1; col++) {
                if (row >= 0 && row < rowSize && col >= 0 && col < colSize && minesPositions[row][col]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void reveal(int x, int y) {
        if (x < 0 || x >= rowSize || y < 0 || y >= colSize || revealedPositions[x][y] || flaggedPositions[x][y]) {
            return;
        }
        revealedPositions[x][y] = true;
        if (minesPositions[x][y]) {
            gameOver = true;
            revealAllMines();
            return;
        }
        if (countMines(x, y) == 0) {
            for (int row = x - 1; row <= x + 1; row++) {
                for (int col = y - 1; col <= y + 1; col++) {
                    reveal(row, col);
                }
            }
        }
    }

    private void flag(int x, int y) {
        if (x < 0 || x >= rowSize || y < 0 || y >= colSize || revealedPositions[x][y]) {
            System.out.println("Invalid action. Cell is already revealed.");
            return;
        }
        flaggedPositions[x][y] = (flaggedPositions[x][y] == false);
        if (flaggedPositions[x][y] == true){
            remainingFlags -= 1;
        }else{
            remainingFlags += 1;
        }
    }

    private void revealAllMines(){
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if ((minesPositions[row][col] == true) && (flaggedPositions[row][col] == false))
                revealedPositions[row][col]= true;
            }
        }
    }

    public void playGame(){
        Scanner scanner = new Scanner(System.in);
        while (gameOver == false && checkWins() == false) {

            System.out.print("Enter action (reveal/flag) and coordinates (e.g., reveal 3 4): ");
            String input = scanner.nextLine();
            startGame(input);
            printBoard();

        }

        if (gameOver) {
            System.out.println("Game Over! You hit a mine.");
        }else{
            System.out.println("Heh!!! You Win. Placed all the flags perfectly!");
        }
        scanner.close();
    }


}


public class Minesweeper {

    public static void main(String[] args){
        MinesweeperGame minesweeperGame = new MinesweeperGame();
        minesweeperGame.playGame();
    }

}