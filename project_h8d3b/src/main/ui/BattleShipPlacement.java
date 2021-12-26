package ui;

import model.BattleShipGrid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

//This class represents the entire ship placement phase
public class BattleShipPlacement extends JPanel implements ActionListener {
    private int shipCount = 5;
    private BattleShipGrid board;
    private BattleShipGUI gui;
    TextField p1Ship = new TextField(20);
    TextField nameTurn = new TextField(20);

//EFFECTS: constructor that instantiates components to indicate who's turn, number of ships left, and the ocean grid
    public BattleShipPlacement(BattleShipGUI gui, BattleShipGrid board, String name) {
        setVisible(true);
        this.board = board;
        this.gui = gui;
        setBorder(BorderFactory.createEmptyBorder(100,0,50,0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel ships = new JPanel();
        nameTurn.setText(name + "'s " + "turn to place ships");
        p1Ship.setText("You have " + shipCount + " ships left");
        p1Ship.setFont(new Font("Calibri", Font.BOLD, 30));
        ships.add(p1Ship);
        ships.add(nameTurn);
        ships.setBorder(BorderFactory.createEmptyBorder(100,0,0,100));
        setBorder(BorderFactory.createEmptyBorder(50,100,0,100));
        nameTurn.setFont(new Font("Calibri", Font.BOLD, 30));
        add(nameTurn, BorderLayout.PAGE_START);
        add(ships, BorderLayout.EAST);
        boardPlacementButtons(this.board);
    }

    //EFFECTS: displays a column of letters next to the ocean grid
    private void showLetters(int i, JPanel playerGrid) {
        JPanel letterLabel = new JPanel();
        JLabel letter = new JLabel((String.valueOf(((char) ('A' + i)))));
        letter.setFont(new Font("Calibri", Font.BOLD, 30));
        letterLabel.add(letter);
        playerGrid.add(letterLabel);
    }

    //MODIFIES: this
    //EFFECTS: creates a grid of buttons for the player to place their ships
    private void boardPlacementButtons(BattleShipGrid battleShipGrid) {
        int row = battleShipGrid.getBoardSize();
        JPanel playerGrid = new JPanel();
        playerGrid.setLayout(new GridLayout(row, row));
        playerGrid.setBorder(BorderFactory.createEmptyBorder(50,100,50,100));
        for (int i = 0; i < row; i++) {
            showLetters(i, playerGrid);
            for (int j = 0; j < row; j++) {
                if (Objects.equals(battleShipGrid.getElement(i, j + 1), "~")) {
                    JButton place = new JButton();
                    place.setActionCommand(String.format("%s%s",i,j));
                    place.addActionListener(this);
                    place.setBackground(Color.cyan);
                    playerGrid.add(place);
                } else if (Objects.equals(battleShipGrid.getElement(i, j + 1), "S")) {
                    JButton place = new JButton();
                    place.setBackground(Color.GRAY);
                    place.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    playerGrid.add(place);
                }
            }
        }
        add(playerGrid, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: listens for when a button on the ocean grid is clicked and places a ship
    //Also listens for when the number of ships is 0 to switch to the next player's turn
    @Override
    public void actionPerformed(ActionEvent evt) {
        JButton button = (JButton) evt.getSource();
        button.setBackground(Color.GRAY);
        button.setText("Ship");
        String actionCommand = button.getActionCommand();
        int coord1 = Character.getNumericValue(actionCommand.charAt(0));
        int coord2 = Character.getNumericValue(actionCommand.charAt(1));
        board.placeShip(coord1,coord2 + 1);
        shipCount--;
        p1Ship.setText("You have " + shipCount + " ships left");
        if (shipCount == 0 && gui.getBattleShipGame().getTurn() == 0) {
            gui.switchPlacement();
            gui.getBattleShipGame().changeTurn(2);
        } else if (shipCount == 0 && gui.getBattleShipGame().getTurn() == 2) {
            gui.getBattleShipGame().changeTurn(1);
            gui.switchAttacking(1);
        }
    }
}
