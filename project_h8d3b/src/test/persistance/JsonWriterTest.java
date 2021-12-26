package persistance;

import model.BattleShipGame;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            BattleShipGame bs = new BattleShipGame("Joe","Bob");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterCorrectWriteAndRead() {
        try {
            BattleShipGame bs = new BattleShipGame("Scott", "Bob");
            bs.changeTurn(1);
            bs.getPlayer1().increaseScore();
            bs.getPlayer1().increaseScore();
            bs.getPlayer2().increaseScore();
            bs.getPlayer2().increaseScore();
            bs.getPlayer2().increaseScore();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBattleship.json");
            writer.open();
            writer.write(bs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBattleShip.json");
            bs = reader.read();
            checkFields(bs);
        } catch(IOException e) {
            fail();
        }
    }
}
