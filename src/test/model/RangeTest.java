package model;

import model.exceptions.UnevenRangeException;
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
        assertEquals(1, testRange.type());
        assertEquals(100, testRange.getVal());
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
        try {
            testRange.setHigh(500);
        } catch (UnevenRangeException e) {
            fail();
        }
        assertEquals(500, testRange.getHigh());
        testRange.setLow(400);
        try {
            testRange.setHigh(100);
            fail();
        } catch (UnevenRangeException e2) {
            assertEquals(400, testRange.getLow());
            assertEquals(500, testRange.getHigh());
        }
    }

    @Test
    public void getRangeTest() {
        assertEquals("0 - 100", testRange.getRange());
        try {
            testRange.setHigh(500);
        } catch (UnevenRangeException e) {
            fail();
        }
        assertEquals("0 - 500", testRange.getRange());
        testRange.setLow(200);
        assertEquals("200 - 500", testRange.getRange());
    }

    @Test
    public void valStringTest() {
        assertEquals("0 - 100", testRange.getValString());
        try {
            testRange.setVal(300, 400);
            assertEquals("300 - 400", testRange.getValString());
        } catch (UnevenRangeException e) {
            fail();
        }
    }
}