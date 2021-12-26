package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents the whole battleship game that features 2 players
public class BattleShipGame implements Writable {
    private Player player1;
    private Player player2;
    private int turn;

    public BattleShipGame(String name1, String name2) {
        player1 = new Player(name1);
        player2 = new Player(name2);
        turn = 0;
    }

    //EFFECTS: returns which player's turn it is
    public int getTurn() {
        return turn;
    }

    //EFFECTS: returns player1
    public Player getPlayer1() {
        return player1;
    }

    //EFFECTS: returns player2
    public Player getPlayer2() {
        return player2;
    }

    //EFFECTS: sets turn to player1's
    public void player1Turn() {
        turn = 1;
    }

    //EFFECTS: sets turn to player2's
    public void player2Turn() {
        turn = 2;
    }

    //MODIFIES: this
    //EFFECTS: changes turn
    public void changeTurn(int turn) {
        this.turn = turn;
        EventLog.getInstance().logEvent(new Event("Changed turn"));
    }

    //EFFECTS: puts player turn along with player1 and player2 objects in a json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        EventLog.getInstance().logEvent(new Event("Saved progress to JSON file"));
        json.put("player turn", turn);
        json.put("player 1", player1.toJson());
        json.put("player 2", player2.toJson());
        return json;
    }

}
