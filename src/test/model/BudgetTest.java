package model;

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
    }

    @Test
    public void addIncomeTest() {
        testBudget.addIncome(100);
        assertEquals(100, testBudget.getIncome());
    }

    @Test
    public void removeCategoryTest() {
        testBudget.addCategory("C1");
        testBudget.addCategory("C2");
        testBudget.removeCategory(testBudget.getCategories().get(0));
        assertEquals(1, testBudget.getCategories().size());
        assertEquals("C1", testBudget.getCategories().get(0).getTitle());
    }

    @Test
    public void getSavingsTest() {
        testBudget.addCategory("C1");
        testBudget.addIncome(700);
        assertEquals(700, testBudget.getSavings());
        testBudget.getCategories().get(0).setAmount(90);
        assertEquals(610, testBudget.getSavings());
    }

    @Test
    public void addCategoryTest() {
        testBudget.addCategory("TestCategory1");
        assertEquals(1, testBudget.getCategories().size());
        assertEquals(0, testBudget.getCategories().get(0).getAmount());
    }

    @Test
    public void getTotalCostsTest() {
        testBudget.getCategories().add(new Category("TestCategory1", 100));
        testBudget.addCategory("TestCategory2");
        assertEquals(100, testBudget.getTotalCosts());
        testBudget.getCategories().get(1).setAmount(90);
        assertEquals(190, testBudget.getTotalCosts());
    }

}