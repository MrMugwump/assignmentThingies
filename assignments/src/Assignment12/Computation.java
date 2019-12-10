package Assignment12;

import javax.swing.*;

class Computation {
    private boolean[][] board = new boolean[10][10];
    private int[][] neighbors = new int[11][11];
    //Minesweeper syncer = new Minesweeper();
    private RandomMines bombPlace = new RandomMines();
    private boolean placed = false;

    void setBoard(boolean[][] board) {
        this.board = board;
        neighbors = new int[board.length+1][board[0].length+1];
        //System.out.println("sadf"+neighbors.length);
    }
    private void checkNeighbors(){
        clearNeighbors();

        //checks each the blocks surrounding each square.
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board[0].length; j++) {
                if (board[i][j]){
                    neighbors[i+1][j+1] ++;
                    /*
                    OOO
                    OOO
                    OOX
                     */
                    neighbors[i+1][j] ++;
                    /*
                    OOO
                    OOO
                    OXO
                     */
                    neighbors[i+1][j-1] ++;
                    /*
                    OOO
                    OOO
                    XOO
                     */
                    neighbors[i][j-1] ++;
                    /*
                    OOO
                    XOO
                    OOO
                     */
                    neighbors[i][j+1] ++;
                    /*
                    OOO
                    OOX
                    OOO
                     */
                    neighbors[i-1][j-1] ++;
                    /*
                    XOO
                    OOO
                    OOO
                     */
                    neighbors[i-1][j] ++;
                    /*
                    OXO
                    OOO
                    OOO
                     */
                    neighbors[i-1][j+1] ++;
                    /*
                    OOX
                    OOO
                    OOO
                     */
                }
            }
        }
        //this checks the squares on the boarder, lazy solution to get rid of an error
        for (int i = 1; i < board.length; i++) {
            if (board[0][i]){
                neighbors[1][i+1] ++;
                    /*
                    OOO
                    OOO
                    OOX
                     */
                neighbors[1][i] ++;
                    /*
                    OOO
                    OOO
                    OXO
                     */
                neighbors[1][i-1] ++;
                    /*
                    OOO
                    OOO
                    XOO
                     */
                neighbors[0][i-1] ++;
                    /*
                    OOO
                    XOO
                    OOO
                     */
                neighbors[0][i+1] ++;
                    /*
                    OOO
                    OOX
                    OOO
                     */
            }
            if (board[i][0]){
                neighbors[i-1][0] ++;
                    /*
                    OXO
                    OOO
                    OOO
                     */
                neighbors[i-1][1] ++;
                    /*
                    OOX
                    OOO
                    OOO
                     */
                neighbors[i][1] ++;
                    /*
                    OOO
                    OOX
                    OOO
                     */
                neighbors[i+1][1] ++;
                    /*
                    OOO
                    OOO
                    OOX
                     */
                neighbors[i+1][0] ++;
                    /*
                    OOO
                    OOO
                    OXO
                     */

            }
        }
        if (board[0][0]){
            neighbors[0][1] ++;
                    /*
                    OOO
                    OOX
                    OOO
                     */
            neighbors[1][1] ++;
                    /*
                    OOO
                    OOO
                    OOX
                     */
            neighbors[1][0] ++;
                    /*
                    OOO
                    OOO
                    OXO
                     */
        }
        //
    }

    private void clearNeighbors(){
        //resets neighbors, so the board doesnt count neighbors twice.
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors.length; j++) {
                neighbors[i][j] = 0;
            }
        }
    }
    int[][] getNeighbors(int numOfBombs){
        boolean[][] settingBoard = new boolean[board.length][board[0].length];
        bombPlace.setMineField(settingBoard);
        bombPlace.setMineNumber(numOfBombs);
        if (!placed) {
            System.out.println("Placing");
            bombPlace.placeRandom();
            placed = true;
            board = bombPlace.getMineField();
            System.out.println("board been set "+ board.length);
            checkNeighbors();
        }

        //printNeighbors();
        return neighbors;
    }
    boolean[][] getBoard() {
        return board;
    }



}
