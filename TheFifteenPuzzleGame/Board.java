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
    
}
