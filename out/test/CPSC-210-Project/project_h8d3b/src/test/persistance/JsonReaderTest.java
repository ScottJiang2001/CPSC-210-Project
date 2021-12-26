package persistance;

import model.BattleShipGame;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/SomeRandomFileLOL.json");
        try {
            BattleShipGame bs = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderCorrectFields() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBattleShip.json");
        try {
            BattleShipGame bs = reader.read();
            checkFields(bs);
        } catch (IOException e) {
            fail();
        }
    }
}
