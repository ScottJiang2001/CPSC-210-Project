package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.BattleShipGame;
import model.Player;
import org.json.*;

// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BattleShipGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBattleShip(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private BattleShipGame parseBattleShip(JSONObject jsonObject) {
        int turn = jsonObject.getInt("player turn");
        JSONObject jsonPlayer1 = jsonObject.getJSONObject("player 1");
        JSONObject jsonPlayer2 = jsonObject.getJSONObject("player 2");
        String name1 = jsonPlayer1.getString("name");
        String name2 = jsonPlayer2.getString("name");
        JSONArray p1OceanGrid = jsonPlayer1.getJSONArray("ocean grid");
        JSONArray p2OceanGrid = jsonPlayer2.getJSONArray("ocean grid");
        JSONArray p1TargetGrid = jsonPlayer1.getJSONArray("target grid");
        JSONArray p2TargetGrid = jsonPlayer2.getJSONArray("target grid");
        BattleShipGame battleShipGame = new BattleShipGame(name1, name2);
        Player player1 = battleShipGame.getPlayer1();
        Player player2 = battleShipGame.getPlayer2();
        battleShipGame.changeTurn(turn);
        player1.changeScore(jsonPlayer1.getInt("score"));
        player2.changeScore(jsonPlayer2.getInt("score"));
        changePieces(player1, p1OceanGrid, p1TargetGrid);
        changePieces(player2, p2OceanGrid, p2TargetGrid);
        return battleShipGame;
    }

    //MODIFIES: battleShipGame
    //EFFECTS: changes the pieces of the both the ocean grid and target grid, according to the JSON file
    private void changePieces(Player player, JSONArray ocean, JSONArray target) {
        for (int i = 0; i < 5; i++) {
            JSONArray internalOceanArray = ocean.getJSONArray(i);
            JSONArray internalTargetArray = target.getJSONArray(i);
            for (int  j = 0; j < 5; j++) {
                player.getOceanGrid().changeGrid(i,j,internalOceanArray.getString(j));
                player.getTargetGrid().changeGrid(i,j, internalTargetArray.getString(j));
            }
        }
    }
}
