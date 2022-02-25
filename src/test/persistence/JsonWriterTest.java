package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Budget b = new Budget();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBudget() {
        try {
            Budget b = new Budget();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBudget.json");
            writer.open();
            writer.write(b);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBudget.json");
            b = reader.read();
            assertEquals(0, b.getIncome());
            assertEquals(0, b.getRanges().size());
            assertEquals(0, b.getAmounts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBudget() {
        try {
            Budget b = new Budget();
            b.addIncome(800);
            b.addRange(new Range("testrange", 0, 100));
            b.addAmount("testamount", 100);
            b.addAmount("anothertestamount", 200);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBudget.json");
            writer.open();
            writer.write(b);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBudget.json");
            b = reader.read();
            assertEquals(800, b.getIncome());
            List<Range> ranges = b.getRanges();
            List<Amount> amounts = b.getAmounts();
            assertEquals(1, ranges.size());
            assertEquals(2, amounts.size());
            checkRange("testrange", 0, 100, ranges.get(0));
            checkAmount("testamount", 100, amounts.get(0));
            checkAmount("anothertestamount", 200, amounts.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
