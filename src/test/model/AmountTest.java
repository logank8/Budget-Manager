package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmountTest {

    private Amount testAmount;

    @BeforeEach
    public void runBefore() {
        testAmount = new Amount("Title", 0);
    }

    @Test
    public void constructorTest() {
        assertEquals("Title", testAmount.getTitle());
        assertEquals(0, testAmount.getVal());
        assertEquals(0, testAmount.type());
    }

    @Test
    public void setAmountTest() {
        assertEquals(0, testAmount.getVal());
        testAmount.setVal(100, 0);
        assertEquals(100, testAmount.getVal());
    }

    @Test
    public void valStringTest() {
        assertEquals("0", testAmount.getValString());
        testAmount.setVal(30, 0);
        assertEquals("30", testAmount.getValString());
    }
}
