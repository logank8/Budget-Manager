package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BudgetTest {

    private Budget testBudget;
    private Amount A1;

    @BeforeEach
    public void runBefore() {
        testBudget = new Budget();
        A1 = new Amount("A1", 0);
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
        testBudget.setIncome(100);
        assertEquals(100, testBudget.getIncome());
    }

    @Test
    public void addRangeTest() {
        Range range1 = new Range("R1", 0, 1);
        testBudget.addRange(range1);
        assertEquals("R1", testBudget.ranges.get(0).getTitle());
        assertEquals("R1", testBudget.categories.get(0).getTitle());
    }

    @Test
    public void addAmountTest() {
        testBudget.addAmount(A1);
        assertEquals(1, testBudget.amounts.size());
        assertEquals(1, testBudget.categories.size());
        assertEquals("A1", testBudget.amounts.get(0).getTitle());
        assertEquals("A1", testBudget.categories.get(0).getTitle());
    }

    @Test
    public void getTotalCostsTest() {
        testBudget.addAmount(new Amount("A1", 100));
        assertEquals(100, testBudget.getTotalCosts());
        testBudget.addAmount(new Amount("A2", 90));
        assertEquals(190, testBudget.getTotalCosts());
    }


    @Test
    public void removeCategoryTest() {
        testBudget.addAmount(new Amount("C1", 0));
        testBudget.addAmount(new Amount("C2", 0));
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
        testBudget.addAmount(A1);
        assertEquals(1, testBudget.categories.size());
        assertEquals(1, testBudget.amounts.size());
        testBudget.removeAmount(A1);
        assertEquals(0, testBudget.amounts.size());
        assertEquals(A1, testBudget.categories.get(0));
    }


    @Test
    public void getSavingsTest(){
        testBudget.addAmount(A1);
        testBudget.setIncome(700);
        assertEquals(700, testBudget.getSavings());
        testBudget.amounts.get(0).setVal(90, 0);
        assertEquals(610, testBudget.getSavings());
        testBudget.ranges.add(new Range("R1", 0, 1));
        assertEquals(609, testBudget.getSavings());
    }
}
