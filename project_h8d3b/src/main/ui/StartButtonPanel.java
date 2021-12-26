package ui;

import model.BattleShipGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

//This class represents the home screen that allows user to start new game or load a game
public class StartButtonPanel extends JPanel implements ActionListener {
    private JButton newGameButton;
    private JButton loadGameButton;
    private BattleShipGame game;
    private JPanel image;
    private BattleShipGUI gui;
    private NameInputPanel name;
    private JPanel panelImage;

    private enum Actions {
        NEW,
        LOAD
    }

    //MODIFIES: this
    //EFFECTS: Constructs the home screen with a battleship logo and the start new game and load game buttons
    public StartButtonPanel(BattleShipGame g, JPanel image, NameInputPanel name, BattleShipGUI gui) {
        this.game = g;
        this.image = image;
        this.name = name;
        this.gui = gui;
        setBorder(BorderFactory.createEmptyBorder(50,100,50,100));
        setBackground(new Color(52,134,235));
        panelImage = new JPanel();
        panelImage.setBorder(BorderFactory.createEmptyBorder(50,100,50,100));
        panelImage.add(new JLabel(new ImageIcon("project_h8d3b/data/battleshiplogo.png")));
        panelImage.setBackground(new Color(52,134,235));
        newGameButton = new JButton("Start a new game");
        newGameButton.setActionCommand(Actions.NEW.name());
        setButtons(newGameButton,50,168,98);
        loadGameButton = new JButton("Load game");
        loadGameButton.setActionCommand((Actions.LOAD.name()));
        setButtons(loadGameButton,50,157,168);
        add(panelImage);
        add(newGameButton);
        add(loadGameButton);
    }

    //MODIFIES: this
    //EFFECTS: sets dimensions and background color of buttons
    private void setButtons(JButton button, int r, int g, int b) {
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(200,100));
        button.setBackground(new Color(r,g,b));
        button.setFont(new Font("Arial",Font.BOLD, 20));
    }

    //MODIFIES: this
    //EFFECTS: listens for when a new game button or load game is clicked to switch to their respective screens
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (Objects.equals(evt.getActionCommand(), Actions.NEW.name())) {
            System.out.println("Started new game");
            game.changeTurn(0);
            gui.switchNameInput();
        } else if (Objects.equals(evt.getActionCommand(), Actions.LOAD.name())) {
            System.out.println("Loaded game");
            gui.switchAttacking(game.getTurn());
        }
    }
}
