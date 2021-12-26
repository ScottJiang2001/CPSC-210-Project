package ui;

import model.BattleShipGame;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//This class represents the entire GUI of the battleship game
public class BattleShipGUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "project_h8d3b/data/battleship.json";

    private StartButtonPanel panelButtons;
    private NameInputPanel nameInput;
    private JPanel panelImage;
    private BattleShipPlacement player1Placement;
    private BattleShipPlacement player2Placement;
    private BattleShipAttacking attackingPhase;
    private BattleShipGame battleShipGame;
    private JsonReader jsonReader;
    private String player1Name = null;
    private String player2Name = null;
    private CardLayout layout;
    private JPanel gameStart;
    private int p1Ships = 5;
    TextField p1Ship = new TextField(20);

    //MODIFIES: this
    //EFFECTS: constructs the GUI of the battleship game
    public BattleShipGUI() {
        setSize(900,700);
        jsonReader = new JsonReader(JSON_STORE);
        loadGame();
        newGame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("BattleShip");
        setVisible(true);
    }

    //EFFECTS: returns battleShipGame
    public BattleShipGame getBattleShipGame() {
        return battleShipGame;
    }

    //EFFECTS: Creates a new game by instantiating the home button screen and name input screen
    public void newGame() {
        layout = new CardLayout();
        gameStart = new JPanel(layout);
        panelButtons = new StartButtonPanel(battleShipGame, panelImage, nameInput,this);
        player2Placement = new BattleShipPlacement(this, battleShipGame.getPlayer2().getOceanGrid(), null);
        nameInput = new NameInputPanel(this);
        gameStart.add(panelButtons, "panelButtons");
        gameStart.add(nameInput, "nameInput");
        startGame();
        add(gameStart);
    }

    //MODIFIES: this
    //EFFECTS: sets a player1 and player2's names
    public void setPlayersName(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        battleShipGame.getPlayer1().changeName(player1Name);
        battleShipGame.getPlayer2().changeName(player1Name);
    }

    //EFFECTS: reads from a JSON file and loads it into battleShipGame
    private void loadGame() {
        try {
            battleShipGame = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: switches to name input screen
    public void switchNameInput() {
        layout.show(gameStart, "nameInput");
        setSize(500,400);
    }

    //MODIFIES: this
    //EFFECTS: switches to home screen with new game and load game buttons
    public void startGame() {
        layout.show(gameStart, "panelButtons");
    }

    //MODIFIES: this
    //EFFECTS: switches to placement phase for second player
    public void switchPlacement() {
        player2Placement = new BattleShipPlacement(this, battleShipGame.getPlayer2().getOceanGrid(), player2Name);
        gameStart.add(player2Placement, "p2Placement");
        layout.show(gameStart,"p2Placement");
    }

    //MODIFIES: this
    //EFFECTS: switches to placement phase for first player
    public void switchStartPlacement() {
        battleShipGame = new BattleShipGame(player1Name, player2Name);
        player1Placement = new BattleShipPlacement(this, battleShipGame.getPlayer1().getOceanGrid(), player1Name);
        gameStart.add(player1Placement, "p1Placement");
        layout.show(gameStart,"p1Placement");
        setSize(800,700);
    }

    //MODIFIES: this
    //EFFECTS: switches to attacking phase screen
    public void switchAttacking(int turn) {
        if (turn == 1) {
            attackingPhase = new BattleShipAttacking(battleShipGame, battleShipGame.getPlayer1(),
                    battleShipGame.getPlayer2());
        } else if (turn == 2) {
            attackingPhase = new BattleShipAttacking(battleShipGame, battleShipGame.getPlayer2(),
                    battleShipGame.getPlayer1());
        } else if (turn == 0) {
            attackingPhase = new BattleShipAttacking(battleShipGame, battleShipGame.getPlayer1(),
                    battleShipGame.getPlayer2());
        }
        gameStart.add(attackingPhase, "attackingPhase");
        layout.show(gameStart, "attackingPhase");
        setSize(1200,700);
    }

    //MODIFIES: this
    //EFFECTS: listens for ship placements and sets text to indicate how many ships a player has left
    @Override
    public void actionPerformed(ActionEvent evt) {
        JButton button = (JButton) evt.getSource();
        button.setBackground(Color.GRAY);
        String actionCommand = button.getActionCommand();
        int coord1 = Character.getNumericValue(actionCommand.charAt(0));
        int coord2 = Character.getNumericValue(actionCommand.charAt(1));
        battleShipGame.getPlayer1().getOceanGrid().placeShip(coord1,coord2);
        p1Ships--;
        p1Ship.setText("You have " + p1Ships + " ships left");
    }
}

