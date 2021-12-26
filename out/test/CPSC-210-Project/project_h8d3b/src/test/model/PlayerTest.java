package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {
    private Player testPlayer;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player("name1");
    }

    @Test
    void testScoreOperations() {
        assertEquals(0,testPlayer.getScore());
        testPlayer.increaseScore();
        assertEquals(1,testPlayer.getScore());
        testPlayer.changeScore(5);
        assertEquals(5, testPlayer.getScore());
    }

    @Test
    void testGetName() {
        assertEquals("name1", testPlayer.getPlayerName());
    }

    @Test
    void testGetGrids() {
        BattleShipGrid testOceanGrid = testPlayer.getOceanGrid();
        BattleShipGrid testTargetGrid = testPlayer.getTargetGrid();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals("~", testOceanGrid.getElement(i,j+1));
                assertEquals("~", testTargetGrid.getElement(i,j+1));
            }
        }
    }

    @Test
    void testToJson() {
        JSONObject testJson = testPlayer.toJson();
        assertEquals("name1", testJson.get("name"));
        assertEquals(0, testJson.get("score"));
    }

    @Test
    void testChangeName() {
        testPlayer.changeName("new name");
        assertEquals("new name", testPlayer.getPlayerName());
    }
}
