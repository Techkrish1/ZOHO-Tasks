import java.util.*;

class PlayGame{
    private static int size = 10; 
    private static int totalMines = 8; 
    private static char emptyCells = '.';
    private static char mine = '*';
    private static char flag = 'F';

    char[][] board;
    boolean[][] minesPositions;
    boolean[][] revealedPositions;
    boolean[][] flaggedPositions;
    boolean gameOver;
    boolean winStatus;

    public PlayGame() {
        // board = new char[size][size];
        minesPositions = new boolean[size][size];
        revealedPositions = new boolean[size][size];
        flaggedPositions = new boolean[size][size];
        gameOver = false;
        winStatus = false;
        initializeBoard();
    }

    public void initializeBoard(){
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // board[row][col] = emptyCells;
                minesPositions[row][col] = false;
                revealedPositions[row][col]= false;
                flaggedPositions[row][col] = false;
            }
        }

        Random rand = new Random();
        int placedMines = 0;
        while (placedMines < totalMines) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            if (!minesPositions[x][y]) {
                minesPositions[x][y] = true;
                placedMines++;
            }
        }
    }

    public void startGame(String input){
        Scanner scanner = new Scanner(input);
        String[] parts = input.split(" ");
        if (parts.length < 3) {
            System.out.println("Invalid input. Use format: 'action row column'");
            return;
        }

        String action = parts[0];
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);

        if (action.equals("reveal")) {
            if (x < 0 || x >= size || y < 0 || y >= size) {
                System.out.println("Invalid input. Try again.");
                return;
            }
            reveal(x, y);
        } else if (action.equals("flag")) {
            flag(x, y);
        } else {
            System.out.println("Unknown action. Use 'reveal' or 'flag'.");
        }

    }

    public void printBoard() {
        System.out.print("  ");
        for (int col = 0; col < size; col++) {
            System.out.print(col + " ");
        }
        System.out.println();
        for (int row = 0; row < size; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < size; col++) {
                if (flaggedPositions[row][col]) {
                    System.out.print(flag + " ");
                } else if (revealedPositions[row][col]) {
                    if (minesPositions[row][col]) {
                        System.out.print(mine + " ");
                    } else {
                        System.out.print(countMines(row, col) + " ");
                    }
                } else {
                    System.out.print(emptyCells + " ");
                }
            }
            System.out.println();
            checkWins();
        }
    }

    public void checkWins(){
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (minesPositions[row][col]) {
                    if (!flaggedPositions[row][col]) {
                        return; 
                    }
                } else {
                    if (!revealedPositions[row][col]) {
                        return; 
                    }
                }
            }
        }
        winStatus = true;

    }

    public int countMines(int x, int y){
        int count = 0;
        for (int row = x - 1; row <= x + 1; row++) {
            for (int col = y - 1; col <= y + 1; col++) {
                if (row >= 0 && row < size && col >= 0 && col < size && minesPositions[row][col]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void reveal(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size || revealedPositions[x][y] || flaggedPositions[x][y]) {
            return;
        }
        revealedPositions[x][y] = true;
        if (minesPositions[x][y]) {
            gameOver = true;
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

    public void flag(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size || revealedPositions[x][y]) {
            System.out.println("Invalid action. Cell is already revealed.");
            return;
        }
        flaggedPositions[x][y] = !flaggedPositions[x][y];
    }


}

class GameSetting{
    public void validateInput(){

    }
    public void playerInput(){
        Scanner scanner = new Scanner(System.in);
        PlayGame playGame = new PlayGame();
        while (!playGame.gameOver && !playGame.winStatus) {
            playGame.printBoard();
            if (!playGame.winStatus){
                System.out.print("Enter action (reveal/flag) and coordinates (e.g., reveal 3 4): ");
                String input = scanner.nextLine();
                playGame.startGame(input);
            }
            
        }
        if (playGame.gameOver) {
            playGame.printBoard();
            System.out.println("Game Over! You hit a mine.");
        }else{
            System.out.println("Heh!!! You Win!");
        }
    }
}

public class Minesweeper {

    public static void main(String[] args){
        GameSetting gameSetting = new GameSetting();
        gameSetting.playerInput();
    }

}
