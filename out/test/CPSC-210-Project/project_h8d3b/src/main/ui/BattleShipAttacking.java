package ui;

import model.BattleShipGame;
import model.BattleShipGrid;
import model.EventLog;
import model.Event;
import model.Player;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Objects;

//Represents the attacking phase of battleship
public class BattleShipAttacking extends JPanel implements ActionListener {
    private static final String JSON_STORE = "project_h8d3b/data/battleship.json";
    private Player p1;
    private Player p2;
    private BattleShipGame game;
    private JPanel playerGrid;
    private JPanel targetBoard;
    private JPanel yourBoard;
    private JsonWriter jsonWriter;
    private JPanel p1Turn;
    private JPanel p2Turn;
    private JPanel turnAlternate;
    JLabel targetText = new JLabel("Target Board");
    JLabel yourText = new JLabel("Your Board");
    CardLayout layout = new CardLayout();
    private int hit = 0;

    //EFFECTS: constructor that starts the attacking phase
    public BattleShipAttacking(BattleShipGame game, Player p1, Player p2) {
        jsonWriter = new JsonWriter(JSON_STORE);
        turnAlternate = new JPanel(layout);
        setBorder(BorderFactory.createEmptyBorder(100,0,50,0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.p1 = p1;
        this.p2 = p2;
        this.game = game;
        p1Turn = new JPanel();
        p1Turn.setLayout(new BoxLayout(p1Turn, BoxLayout.Y_AXIS));
        playerTurn(p1.getOceanGrid(), p1.getTargetGrid(), p1.getPlayerName(), p1.getScore(), p1Turn);
        turnAlternate.add(p1Turn);
        add(turnAlternate);
    }

    //MODIFIES: target grid, score
    //EFFECTS: starts player turn and displays target boards, along with scores
    public void playerTurn(BattleShipGrid ocean, BattleShipGrid target, String name, int score, JPanel turn) {
        JPanel nameTurn = new JPanel();
        nameTurn.setLayout(new BoxLayout(nameTurn, BoxLayout.Y_AXIS));
        JPanel playerBoards = new JPanel();
        playerBoards.setLayout(new BoxLayout(playerBoards, BoxLayout.X_AXIS));
        playerBoards.setBorder(BorderFactory.createEmptyBorder(0,0,50,0));
        JLabel turnText = new JLabel(name + "'s " + "turn to attack");
        JLabel scoreText = new JLabel("<html><font color=red>Score</font>:  " + score);
        turnText.setFont(new Font("Calibri", Font.BOLD, 30));
        scoreText.setFont(new Font("Calibri", Font.BOLD, 25));
        nameTurn.add(turnText);
        nameTurn.add(scoreText);
        nameTurn.setBorder(BorderFactory.createEmptyBorder(0,0,50,0));
        boardPlacementButtons(target);
        showBoard(ocean);
        turn.add(nameTurn, BorderLayout.PAGE_START);
        playerBoards.add(targetBoard);
        playerBoards.add(yourBoard);
        JButton saveAndQuit = new JButton("Save and Quit");
        JButton quitNoSave = new JButton("Quit Without Saving");
        setButtons(saveAndQuit, quitNoSave);
        turn.add(playerBoards);
        turn.add(saveAndQuit);
        turn.add(quitNoSave);
    }

    public void setButtons(JButton save, JButton quit) {
        save.setActionCommand("saveAndQuit");
        save.addActionListener(this);
        quit.setActionCommand("quitNoSave");
        quit.addActionListener(this);
    }


    //MODIFIES: place, playerGrid
    //EFFECTS: sets buttons color and text depending on the color parameter
    private void setButtons(String text, JPanel playerGrid, String color, int i, int j) {
        JButton place = new JButton();
        place.setPreferredSize(new Dimension(70,70));
        if (Objects.equals(color, "red")) {
            place.setBackground(Color.RED);
            place.setText(text);
        } else if (Objects.equals(color, "yellow")) {
            place.setBackground(Color.YELLOW);
            place.setText(text);
        } else if (Objects.equals(color, "cyan")) {
            place.setBackground(Color.CYAN);
            place.setActionCommand(String.format("%s%s",i,j));
            place.addActionListener(this);
        }
        playerGrid.add(place);
    }

    //EFFECTS: creates a column of letters next to the battle ship grid
    private void showLetters(int i, JPanel playerGrid) {
        JPanel letterLabel = new JPanel();
        JLabel letter = new JLabel((String.valueOf(((char) ('A' + i)))));
        letter.setFont(new Font("Calibri", Font.BOLD, 30));
        letterLabel.add(letter);
        playerGrid.add(letterLabel);
    }

    //MODIFIES: target
    //EFFECTS: creates a grid of different color buttons to place missiles
    private void boardPlacementButtons(BattleShipGrid battleShipGrid) {
        int row = battleShipGrid.getBoardSize();
        targetBoard = new JPanel();
        targetBoard.setLayout(new BoxLayout(targetBoard, BoxLayout.Y_AXIS));
        JPanel targetBoardText = new JPanel();
        targetBoardText.add(targetText);
        targetText.setFont(new Font("Calibri", Font.BOLD, 25));
        JPanel playerGrid = new JPanel();
        playerGrid.setLayout(new GridLayout(row, row));
        for (int i = 0; i < row; i++) {
            showLetters(i, playerGrid);
            for (int j = 0; j < row; j++) {
                if (Objects.equals(battleShipGrid.getElement(i, j + 1), "~")) {
                    setButtons("none", playerGrid, "cyan", i, j);
                } else if (Objects.equals(battleShipGrid.getElement(i, j + 1), "X")) {
                    setButtons("Hit!", playerGrid, "red", 0, 0);
                } else if (Objects.equals(battleShipGrid.getElement(i, j + 1), "O")) {
                    setButtons("Miss!", playerGrid, "yellow", 0, 0);
                }
            }
        }
        targetBoard.add(targetBoardText);
        targetBoard.add(playerGrid);
    }

    //MODIFIES: p1, p2
    //EFFECTS: determines whether a missile placement is a hit and places either a miss or
    // hit on the players target board (Places a hit on opposing player's ocean grid if it's a hit)
    private void hitOrMiss(Player player1, Player player2, int x, int y) {
        hit = 0;
        if (player2.getOceanGrid().getElement(x, y + 1).equals("S")) {
            player2.getOceanGrid().placeHit(x, y + 1);
            player1.getTargetGrid().placeHit(x, y + 1);
            player1.increaseScore();
            hit = 1;
        } else {
            player1.getTargetGrid().placeMiss(x, y + 1);
            hit = 0;
        }
    }

    //EFFECTS: sets each part of player grid a background color and text
    private void setShowGrid(int row, JLabel[][] grid, BattleShipGrid battleShipGrid) {
        for (int i = 0; i < row; i++) {
            showLetters(i, playerGrid);
            for (int j = 0; j < row; j++) {
                grid[i][j] = new JLabel("", SwingConstants.CENTER);
                grid[i][j].setBorder(new LineBorder(Color.BLACK));
                if (Objects.equals(battleShipGrid.getElement(i, j + 1), "~")) {
                    grid[i][j].setBackground(Color.cyan);
                    grid[i][j].setPreferredSize(new Dimension(70, 70));
                } else if (Objects.equals(battleShipGrid.getElement(i, j + 1), "S")) {
                    grid[i][j].setBackground(Color.GRAY);
                    grid[i][j].setText("Ship");
                    grid[i][j].setPreferredSize(new Dimension(70, 70));
                } else if (Objects.equals(battleShipGrid.getElement(i, j + 1), "X")) {
                    grid[i][j].setBackground(Color.red);
                    grid[i][j].setText("Hit");
                    grid[i][j].setPreferredSize(new Dimension(70, 70));
                }
                playerGrid.add(grid[i][j]);
                grid[i][j].setOpaque(true);
            }
        }
    }

    //EFFECTS: displays the player's ocean grid (no buttons as they don't click on this)
    public void showBoard(BattleShipGrid battleShipGrid) {
        int row = battleShipGrid.getBoardSize();
        playerGrid = new JPanel();
        yourBoard = new JPanel();
        yourBoard.setLayout(new BoxLayout(yourBoard, BoxLayout.Y_AXIS));
        JPanel yourBoardText = new JPanel();
        yourBoardText.add(yourText);
        yourText.setFont(new Font("Calibri", Font.BOLD, 25));
        JLabel[][] grid = new JLabel[row][row];
        playerGrid.setLayout(new GridLayout(row, row));
        playerGrid.setBorder(BorderFactory.createEmptyBorder(0,0,0,25));
        setShowGrid(row,grid,battleShipGrid);
        yourBoard.add(yourBoardText);
        yourBoard.add(playerGrid);
    }


    //EFFECTS: creates a popup window to notify a hit or miss
    public void notifyHitOrMiss() {
        JFrame popUp = new JFrame();
        if (hit == 1) {
            JOptionPane.showMessageDialog(popUp, "Hit!");
        } else {
            JOptionPane.showMessageDialog(popUp, "Miss!");
        }
    }

    //EFFECTS: saves current state of the game to a JSON file
    private void saveGame() {
        try {
            printLog(EventLog.getInstance());
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //EFFECTS: Creates a popup window to notify a win
    private void notifyWin(int score1,int score2, String name1, String name2) {
        JFrame winPopUp = new JFrame();
        if (score1 == 5 || score2 == 5) {
            JLabel winDialog;
            if (score1 == 5) {
                winDialog = new JLabel(name1 + " Wins!");
            } else {
                winDialog = new JLabel(name2 + " Wins!");
            }
            winDialog.setFont(new Font("Calibri", Font.BOLD, 100));
            winDialog.setForeground(Color.BLUE);
            JOptionPane.showMessageDialog(winPopUp, winDialog);
            System.exit(0);
        }
    }

    //MODIFIES: game, p1, p2
    //EFFECTS: displays the entire turn of the first player
    private void firstTurn(int coord1, int coord2) {
        hitOrMiss(p1, p2, coord1, coord2);
        notifyHitOrMiss();
        game.changeTurn(2);
        p2Turn = new JPanel();
        p2Turn.setLayout(new BoxLayout(p2Turn, BoxLayout.Y_AXIS));
        turnAlternate.add(p2Turn, "p2");
        playerTurn(p2.getOceanGrid(), p2.getTargetGrid(), p2.getPlayerName(), p2.getScore(), p2Turn);
        notifyWin(p1.getScore(), p2.getScore(), p1.getPlayerName(), p2.getPlayerName());
        layout.show(turnAlternate, "p2");
    }

    //MODIFIES: game, p1, p2
    //EFFECTS: displays the entire turn of the second player
    private void secondTurn(int coord1, int coord2) {
        hitOrMiss(p2, p1, coord1, coord2);
        notifyHitOrMiss();
        game.changeTurn(1);
        p1Turn = new JPanel();
        p1Turn.setLayout(new BoxLayout(p1Turn, BoxLayout.Y_AXIS));
        turnAlternate.add(p1Turn, "p1");
        playerTurn(p1.getOceanGrid(), p1.getTargetGrid(), p1.getPlayerName(), p1.getScore(), p1Turn);
        notifyWin(p1.getScore(), p2.getScore(), p1.getPlayerName(), p2.getPlayerName());
        layout.show(turnAlternate, "p1");
    }

    public void printLog(EventLog el) {
        for (Event next: el) {
            System.out.println(next.toString() + "\n");
        }
    }

    //EFFECTS: performs a save game of saveAndQuit button is clicked or listens for a
    // when a button on the target grid is clicked
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (Objects.equals(evt.getActionCommand(), "saveAndQuit")) {
            saveGame();
            System.exit(0);
        }
        if (Objects.equals(evt.getActionCommand(), "quitNoSave")) {
            printLog(EventLog.getInstance());
            System.exit(0);
        }
        JButton button = (JButton) evt.getSource();
        String actionCommand = button.getActionCommand();
        int coord1 = Character.getNumericValue(actionCommand.charAt(0));
        int coord2 = Character.getNumericValue(actionCommand.charAt(1));
        if (game.getTurn() == 1) {
            firstTurn(coord1, coord2);
        } else if (game.getTurn() == 2) {
            secondTurn(coord1,coord2);
        }
    }
}
