package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BattleShipGameTest {
    private BattleShipGame testBsg;

    @BeforeEach
    void runBefore() {
        testBsg = new BattleShipGame("name1", "name2");
    }

    @Test
    void testCheckTurns() {
        assertEquals(0, testBsg.getTurn());
        testBsg.changeTurn(2);
        assertEquals(2, testBsg.getTurn());
        testBsg.player1Turn();
        assertEquals(1, testBsg.getTurn());
        testBsg.player2Turn();
        assertEquals(2, testBsg.getTurn());
    }

    @Test
    void testGetPlayers() {
        assertEquals("name1", testBsg.getPlayer1().getPlayerName());
        assertEquals("name2", testBsg.getPlayer2().getPlayerName());
    }

    @Test
    void testToJson() {
        JSONObject testJson = testBsg.toJson();
        assertEquals(0, testJson.get("player turn"));
    }
}
