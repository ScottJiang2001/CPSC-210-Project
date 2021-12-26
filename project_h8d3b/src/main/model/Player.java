package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents one of two players in the battlefield game (Each player has their own target and ocean grids)
public class Player implements Writable {
    private String name;
    private BattleShipGrid targetGrid;
    private BattleShipGrid oceanGrid;
    private int score = 0;

    //EFFECTS: constructs a player class with oceangrid, targetgrid, and name
    public Player(String name) {
        oceanGrid = new BattleShipGrid();
        targetGrid = new BattleShipGrid();
        this.name = name;
    }

    //MODIFIES: this
    //EFFECTS: increments players score by 1
    public void increaseScore() {
        score += 1;
    }

    //REQUIRES: score > 0
    //MODIFIES: this
    //EFFECTS: Changes player score
    public void changeScore(int score) {
        this.score = score;
    }

    //EFFECTS: returns player score
    public int getScore() {
        return score;
    }

    //EFFECTS: returns player name
    public String getPlayerName() {
        return name;
    }

    //EFFECTS: returns player target grid
    public BattleShipGrid getTargetGrid() {
        return targetGrid;
    }

    //EFFECTS: returns player ocean grid
    public BattleShipGrid getOceanGrid() {
        return oceanGrid;
    }

    //EFFECTS: changes player name
    public void changeName(String name) {
        this.name = name;
    }

    //EFFECTS: puts player's name, score, target grid, and ocean grid into a json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("score", score);
        json.put("target grid", targetGrid.getGrid());
        json.put("ocean grid", oceanGrid.getGrid());
        return json;
    }
}
