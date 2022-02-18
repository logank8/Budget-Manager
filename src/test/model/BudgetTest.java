package model;

import exceptions.UnbalancedRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BudgetTest {

    private Budget testBudget;

    @BeforeEach
    public void runBefore() {
        testBudget = new Budget();
    }

    @Test
    public void constructorTest() {
        assertEquals(0, testBudget.getIncome());
        assertEquals(0, testBudget.getCategories().size());
        assertEquals(0, testBudget.getRanges().size());
        assertEquals(0, testBudget.getAmounts().size());
    }

    @Test
    public void addIncomeTest() {
        testBudget.addIncome(100);
        assertEquals(100, testBudget.getIncome());
    }

    @Test
    public void addRangeTest() {
        try {
            testBudget.addRange("R1", 0, 1);
        } catch (UnbalancedRangeException e) {
            fail();
        }

        try {
            testBudget.addRange("R2", 1, 0);
            fail();
        } catch (UnbalancedRangeException e) {
            assertEquals(1, testBudget.categories.size());
            assertEquals(1, testBudget.ranges.size());
        }
        assertEquals("R1", testBudget.ranges.get(0).getTitle());
        assertEquals("R1", testBudget.categories.get(0).getTitle());
    }

    @Test
    public void addAmountTest() {
        testBudget.addAmount("A1", 0);
        assertEquals(1, testBudget.amounts.size());
        assertEquals(1, testBudget.categories.size());
        assertEquals("A1", testBudget.amounts.get(0).getTitle());
        assertEquals("A1", testBudget.categories.get(0).getTitle());
    }

    @Test
    public void getTotalCostsTest() {
        testBudget.addAmount("A1", 100);
        assertEquals(100, testBudget.getTotalCosts());
        testBudget.addAmount("A2", 90);
        assertEquals(190, testBudget.getTotalCosts());
    }


    @Test
    public void removeCategoryTest() {
        testBudget.addAmount("C1", 0);
        testBudget.addAmount("C2", 0);
        assertEquals(2, testBudget.categories.size());
        testBudget.removeCategory(testBudget.getCategories().get(0));
        assertEquals(1, testBudget.getCategories().size());
        assertEquals("C2", testBudget.getCategories().get(0).getTitle());
    }

    @Test
    public void removeRangeTest() {
        testBudget.ranges.add(new Range("R1", 0, 1));
        Range testRange = testBudget.ranges.get(0);
        testBudget.categories.add(testRange);
        assertEquals(1, testBudget.categories.size());
        assertEquals(1, testBudget.ranges.size());
        testBudget.removeRange(testRange);
        assertEquals(0, testBudget.ranges.size());
        assertEquals(testRange, testBudget.categories.get(0));
    }

    @Test
    public void removeAmountTest() {
        testBudget.addAmount("A1", 0);
        Amount testAmount = testBudget.amounts.get(0);
        assertEquals(1, testBudget.categories.size());
        assertEquals(1, testBudget.amounts.size());
        testBudget.removeAmount(testAmount);
        assertEquals(0, testBudget.amounts.size());
        assertEquals(testAmount, testBudget.categories.get(0));
    }


    @Test
    public void getSavingsTest(){
        testBudget.addAmount("A1", 0);
        testBudget.addIncome(700);
        assertEquals(700, testBudget.getSavings());
        testBudget.amounts.get(0).setAmount(90);
        assertEquals(610, testBudget.getSavings());
        testBudget.ranges.add(new Range("R1", 0, 1));
        assertEquals(609, testBudget.getSavings());
    }
}
