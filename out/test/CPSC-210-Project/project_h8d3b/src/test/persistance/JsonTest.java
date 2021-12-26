package persistance;

import model.BattleShipGame;
import static org.junit.jupiter.api.Assertions.assertEquals;

// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkFields(BattleShipGame bs) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals("~", bs.getPlayer1().getTargetGrid().getElement(i,j+1));
                assertEquals("~", bs.getPlayer1().getOceanGrid().getElement(i,j+1));
                assertEquals("~", bs.getPlayer2().getTargetGrid().getElement(i,j+1));
                assertEquals("~", bs.getPlayer2().getOceanGrid().getElement(i,j+1));
            }
        }
        assertEquals("Bob", bs.getPlayer2().getPlayerName());
        assertEquals("Scott", bs.getPlayer1().getPlayerName());
        assertEquals(1,bs.getTurn());
        assertEquals(2,bs.getPlayer1().getScore());
        assertEquals(3,bs.getPlayer2().getScore());
    }
}
