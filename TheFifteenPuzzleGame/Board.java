import java.util.*;


public class Board {
    private static final int size = 4;
    private int[][] tiles;
    public int emptyTileRow;
    public int emptyTileCol;
    public Board(int[][] inputBoard){
        this.tiles = inputBoard;
        findEmptyTile();
    }

    private void findEmptyTile(){
        for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++){
                if (tiles[row][col] == 0){
                    emptyTileRow = row;
                    emptyTileCol = col;
                    break;
                }
            }
        }
    }

    public boolean isGoal() {
        int[][] goal = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } };
        return Arrays.deepEquals(this.tiles, goal);
    }

    public List<Board> getNeighbors() {
        List<Board> neighbors = new ArrayList<>();
        int[] rowMoves = { -1, 1, 0, 0 }; // Move up, down, left, right
        int[] colMoves = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int newRow = emptyTileRow + rowMoves[i];
            int newCol = emptyTileCol + colMoves[i];

            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                int[][] newTiles = copyTiles();
                newTiles[emptyTileRow][emptyTileCol] = newTiles[newRow][newCol];
                newTiles[newRow][newCol] = 0;
                neighbors.add(new Board(newTiles));
            }
        }
        return neighbors;
    }

    private int[][] copyTiles() {
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            copy[i] = Arrays.copyOf(this.tiles[i], size);
        }
        return copy;
    }

    public int getManhattanDistance() {
        int distance = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = tiles[i][j];
                if (value != 0) {
                    int targetRow = (value - 1) / size;
                    int targetCol = (value - 1) % size;
                    distance += Math.abs(targetRow - i) + Math.abs(targetCol - j);
                }
            }
        }
        return distance;
    }

    public int getLinearConflict() {
        int conflict = 0;
        for (int i = 0; i < size; i++) {
            conflict += countLinearConflicts(tiles[i]);
        }
        for (int j = 0; j < size; j++) {
            int[] column = new int[size];
            for (int i = 0; i < size; i++) {
                column[i] = tiles[i][j];
            }
            conflict += countLinearConflicts(column);
        }
        return conflict;
    }

    private int countLinearConflicts(int[] line) {
        int conflict = 0;
        for (int i = 0; i < size; i++) {
            if (line[i] == 0) continue;
            for (int j = i + 1; j < size; j++) {
                if (line[j] != 0 && line[i] > line[j] && ((line[i] - 1) / size == (line[j] - 1) / size)) {
                    conflict += 2;
                }
            }
        }
        return conflict;
    }

    public int[][] getTiles() {
        return this.tiles;
    }
}
