package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RangeTest {

    private Range testRange;

    @BeforeEach
    public void runBefore() {
        testRange = new Range("Title", 0, 100);
    }

    @Test
    public void constructorTest() {
        assertEquals("Title", testRange.getTitle());
        assertEquals(0, testRange.getLow());
        assertEquals(100, testRange.getHigh());
    }

    @Test
    public void getDifferenceTest() {
        assertEquals(100, testRange.getDifference());
    }

    @Test
    public void setLowTest() {
        assertEquals(0, testRange.getLow());
        testRange.setLow(50);
        assertEquals(50, testRange.getLow());
    }

    @Test
    public void setHighTest() {
        assertEquals(100, testRange.getHigh());
        testRange.setHigh(500);
        assertEquals(500, testRange.getHigh());
    }

    @Test
    public void getRangeTest() {
        assertEquals("0 - 100", testRange.getRange());
        testRange.setHigh(500);
        assertEquals("0 - 500", testRange.getRange());
        testRange.setLow(200);
        assertEquals("200 - 500", testRange.getRange());
    }
}