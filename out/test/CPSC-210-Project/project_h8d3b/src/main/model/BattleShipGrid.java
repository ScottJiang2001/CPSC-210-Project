package model;

import java.util.Arrays;

// represents a battleship board for a single player
public class BattleShipGrid {

    private String[][] board; // general data structure for a grid
    private String ship = "S"; // represents a ship on the grid
    private String water = "~"; // represents water on the grid
    private String hit = "X"; // represents a hit on a ship
    private String miss = "O"; // represents a miss on the grid
    private int score = 0; // score to keep track of how many ships a player has sunk
    private int size = 5; // the size of the grid


    // EFFECTS: constructor that fills game board with water
    public BattleShipGrid() {
        board = new String[size][size];
        fillGameBoard(board);
    }

    public int getScore() {
        return score;
    }

    public int getBoardSize() {
        return size;
    }

    // REQUIRES: 2D char array
    // EFFECTS: Cycles through the 2D array and fills each index with water
    public void fillGameBoard(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            Arrays.fill(array[i], water);
        }
    }

    //MODIFIES: this
    //EFFECTS: increments the score of player board
    public void increaseScore() {
        score += 1;
        EventLog.getInstance().logEvent(new Event("Score increased"));
    }

    //EFFECTS: returns the board
    public String[][] getGrid() {
        return board;
    }

    //EFFECTS: changes whatever is at the specified coordinates of the grid
    public void changeGrid(int x, int y, String input) {
        board[x][y] = input;
    }

    //REQUIRES: x and y <= size of grid
    //MODIFIES: this
    //EFFECTS: places an 'S' at the specified position on the grid
    public void placeShip(int x, int y) {
        board[x][y - 1] = ship;
        String string  = String.format("Ship Placed at %d,%d",x,y);
        EventLog.getInstance().logEvent(new Event(string));
    }

    //REQUIRES: x and y <= size of grid
    //MODIFIES: this
    //EFFECTS: places an 'X' at the specified position on the grid
    public void placeHit(int x, int y) {
        board[x][y - 1] = hit;
        String string  = String.format("Hit placed at %d,%d",x,y);
        EventLog.getInstance().logEvent(new Event(string));
    }

    //REQUIRES: x and y <= size of grid
    //MODIFIES: this
    //EFFECTS: places an 'O' at the specified position on the grid
    public void placeMiss(int x, int y) {
        board[x][y - 1] = miss;
        String string  = String.format("Miss placed at %d,%d",x,y);
        EventLog.getInstance().logEvent(new Event(string));
    }

    //REQUIRES: x and y <= size of grid
    //EFFECTS: returns whatever element is present at the specified coordinate
    public String getElement(int x, int y) {
        return board[x][y - 1];
    }
}
