package ui;

import model.BattleShipGrid;
import model.BattleShipGame;
import model.Player;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class BattleShipApp {
    public static final String RESET = "\u001B[0m"; //Colors to change console colors
    public static final String CYAN = "\u001B[36m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    private static final String JSON_STORE = "project_h8d3b/data/battleship.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner userInput;
    private String player1Name;
    private String player2Name;
    private String name;
    private HashMap letterToNumberHash;
//    private Player player1;
//    private Player player2;
    private BattleShipGame battleShipGame;

    // EFFECTS: runs the BattleShipGame application along with user inputs
    public BattleShipApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        this.userInput = new Scanner(System.in);
        this.userInput.useDelimiter("\n");
        runBoard();
    }

    // MODIFIES: this
    // EFFECTS: starts the game cycle of initializing game boards and different phases of the game
    private void runBoard() {
        String selection;
        loadGame();
        modeSelection();
        selection = userInput.next();
        selection = selection.toLowerCase();
        gameStartSelection(selection);
        letterToNumber();
        if (battleShipGame.getTurn() == 0) {
            initializeGameBoards();
            shipPlacementPhase(battleShipGame.getPlayer1().getOceanGrid(), battleShipGame.getPlayer1().getPlayerName());
            shipPlacementPhase(battleShipGame.getPlayer2().getOceanGrid(), battleShipGame.getPlayer2().getPlayerName());
            attackingPhase(battleShipGame, battleShipGame.getPlayer1(), battleShipGame.getPlayer2());
        } else if (battleShipGame.getTurn() == 1) {
            attackingPhase(battleShipGame, battleShipGame.getPlayer1(), battleShipGame.getPlayer2());
        } else {
            attackingPhase(battleShipGame, battleShipGame.getPlayer2(), battleShipGame.getPlayer1());
        }
    }

    //EFFECTS: initializes battleship game boards
    private void initializeGameBoards() {
        player1Name = playerNameInput("Player 1");
        player2Name = playerNameInput("Player 2");
        battleShipGame = new BattleShipGame(player1Name, player2Name);
    }



    //REQUIRES: board and valid string name
    //MODIFIES: this
    //EFFECTS: places ships at different coordinates on the grid
    private void shipPlacementPhase(BattleShipGrid board, String name) {
        int shipCount = 5;
        System.out.println(name + "'s" + " turn to place ships");
        while (shipCount > 0) {
            System.out.println("You have " + shipCount + " ships left");
            System.out.println("Please enter your ship coordinates: ");
            String coordinates = this.userInput.next();
            char x = coordinates.charAt(0);
            char y = coordinates.charAt(1);
            board.placeShip((int) letterToNumberHash.get(x), Character.getNumericValue(y));
            printBoard(board);
            shipCount--;
        }
    }

    //REQUIRES: Both player's personal and target grids along with their names
    //MODIFIES: this
    //EFFECTS: alternates turns between player 1 and 2 to place missiles until a player wins
    private void attackingPhase(BattleShipGame battleShipGame, Player player1, Player player2) {
        while (player1.getScore() != 5 && player2.getScore() != 5) {
            battleShipGame.player1Turn();
            playerTurn(player1, player2);
            if (player1.getScore() == 5 || player2.getScore() == 5) {
                break;
            }
            battleShipGame.player2Turn();
            playerTurn(player2, player1);
            if (player1.getScore() == 5) {
                System.out.println(player1.getPlayerName() + " wins!");
            } else {
                System.out.println(player2.getPlayerName() + " wins!");
            }
        }
    }

    //EFFECTS: user input for player name
    private String playerNameInput(String player) {
        System.out.println(player + " please enter your name:");
        return this.userInput.next();
    }

    //EFFECTS: prints the grid in the console in a matrix format with labels
    private void printBoard(BattleShipGrid board) {
        for (int j = 1; j <= board.getBoardSize(); j++) {
            System.out.print("\t" + j);
        }
        System.out.println();
        for (int i = 0; i < board.getBoardSize(); i++) {
            System.out.print(((char) ('A' + i)) + " | ");
            for (int j = 0; j < board.getBoardSize(); j++) {
                System.out.print((board.getElement(i, j + 1).equals("~") ? CYAN :
                        board.getElement(i, j + 1).equals("S") ? GREEN :
                                board.getElement(i, j + 1).equals("X") ? RED :
                                        YELLOW) + board.getElement(i, j + 1) + RESET + " | ");
            }
            System.out.println();
        }
    }

    //REQUIRES: x and y <= grid size
    //MODIFIES: this
    //EFFECTS: notifies current player about a hit or miss and places X or O depending on the latter
    private void hitOrMiss(Player player1, Player player2, int x, int y) {
        if (player2.getOceanGrid().getElement(x, y).equals("S")) {
            System.out.println("Hit!");
            player2.getOceanGrid().placeHit(x, y);
            player1.getTargetGrid().placeHit(x, y);
            player1.increaseScore();
        } else {
            System.out.println("Miss!");
            player1.getTargetGrid().placeMiss(x, y);
        }
    }

    //MODIFIES: this
    //EFFECTS: Starts a player's turn and allows them to target a coordinate and notifies them of a hit or miss
    private void playerTurn(Player player1, Player player2) {
        System.out.println(player1.getPlayerName() + "'s" + " turn");
        System.out.println("Your current score is " + player1.getScore());
        System.out.println("Target board");
        printBoard(player1.getTargetGrid());
        System.out.println("Your board");
        printBoard(player1.getOceanGrid());
        System.out.println("Target a coordinate or press 's' to save and exit game:");
        String input = this.userInput.next();
        if (input.equals("s")) {
            saveGame();
        }
        char x = input.charAt(0);
        char y = input.charAt(1);
        hitOrMiss(player1, player2,(int) letterToNumberHash.get(x), Character.getNumericValue(y));
    }

    //MODIFIES: this
    //EFFECTS: creates a hashmap that stores a letter as a key and number as the value
    private void letterToNumber() {
        letterToNumberHash = new HashMap();
        char x = 'A';
        for (int i = 0; i < 10; i++) {
            letterToNumberHash.put(((char) (x + i)), i);
        }
    }

    private void modeSelection() {
        System.out.println("\tWelcome to Battleship!");
        System.out.println("\tSelect from:");
        System.out.println("\t n -> To start new game");
        System.out.println("\t l -> load saved game from file");
    }

    private void gameStartSelection(String selection) {
        if (selection.equals("n")) {
            battleShipGame.changeTurn(0);
        } else if (selection.equals("l")) {
            System.out.println("Loaded game from " + JSON_STORE);
        } else {
            System.out.println("Selection not valid");
        }
    }

    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(battleShipGame);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void loadGame() {
        try {
            battleShipGame = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
