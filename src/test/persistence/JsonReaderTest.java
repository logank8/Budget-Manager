package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Budget b = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBudget() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBudget.json");
        try {
            Budget b = reader.read();
            assertEquals(0, b.getIncome());
            assertEquals(0, b.getCategories().size());
            assertEquals(0, b.getRanges().size());
            assertEquals(0, b.getAmounts().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBudget() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBudget.json");
        try {
            Budget b = reader.read();
            assertEquals(600, b.getIncome());
            List<Range> ranges = b.getRanges();
            List<Amount> amounts = b.getAmounts();
            assertEquals(1, ranges.size());
            assertEquals(2, amounts.size());
            checkRange("testrange", 100, 200, ranges.get(0));
            checkAmount("testamount", 90, amounts.get(0));
            checkAmount("anothertestamount", 100, amounts.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
