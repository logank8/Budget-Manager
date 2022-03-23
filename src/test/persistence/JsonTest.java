package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkAmount(String title, int amount, Amount a) {
        assertEquals(title, a.getTitle());
        assertEquals(amount, a.getVal());
    }

    protected void checkRange(String title, int low, int high, Range range) {
        assertEquals(title, range.getTitle());
        assertEquals(low, range.getLow());
        assertEquals(high, range.getHigh());
    }
}
