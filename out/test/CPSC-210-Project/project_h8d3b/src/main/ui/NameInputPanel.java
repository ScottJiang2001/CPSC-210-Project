package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//This class represents the name input stage of the game
public class NameInputPanel extends JPanel implements ActionListener {
    JTextField player1Name;
    JTextField player2Name;
    JButton startPlaying;
    JPanel firstName;
    JPanel secondName;
    BattleShipGUI game;
    String p1Name;
    String p2Name;

    //MODIFIES: this
    //EFFECTS: constructs the name input stage by instantiating text fields and start game button
    public NameInputPanel(BattleShipGUI game) {
        this.game = game;
        setBorder(BorderFactory.createEmptyBorder(50,100,50,100));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        player1Name = new JTextField(10);
        player2Name = new JTextField(10);
        startPlaying = new JButton("Start Playing!");
        startPlaying.addActionListener(this);
        firstName = new JPanel();
        firstName.setBorder(BorderFactory.createEmptyBorder());
        firstName.add(new JLabel("Enter player 1's name:"));
        firstName.add(player1Name);
        secondName = new JPanel();
        secondName.add(new JLabel("Enter player 2's name:"));
        secondName.add(player2Name);
        add(firstName);
        add(secondName);
        add(startPlaying);
    }

    //MODIFIES: this
    //EFFECTS: listens for when the start game button is pressed to set the player's names
    // and start the next phase of the game
    @Override
    public void actionPerformed(ActionEvent evt) {
        game.setPlayersName(player1Name.getText(),player2Name.getText());
        game.switchStartPlacement();
    }
}

