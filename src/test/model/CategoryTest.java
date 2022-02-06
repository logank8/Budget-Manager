package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {

    private Category testCategory;

    @BeforeEach
    public void runBefore() {
        testCategory = new Category("Title", 0);
    }

    @Test
    public void constructorTest() {
        assertEquals("Title", testCategory.getTitle());
        assertEquals(0, testCategory.getAmount());
    }

    @Test
    public void setTitleTest() {
        assertEquals("Title", testCategory.getTitle());
        testCategory.setTitle("New Title");
        assertEquals("New Title", testCategory.getTitle());
    }

    @Test
    public void setAmountTest() {
        assertEquals(0, testCategory.getAmount());
        testCategory.setAmount(100);
        assertEquals(100, testCategory.getAmount());
    }
}
