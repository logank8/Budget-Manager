package model;

import java.util.ArrayList;
import java.util.List;

public class Budget {

    private int income;
    List<Category> categories;

    // EFFECTS: creates a new Budget with no categories and income set at 0
    public Budget() {
        income = 0;
        categories = new ArrayList<>();
    }

    public void addIncome(int amount) {
        income += amount;
    }

    public void addCategory(String title) {
        Category newCategory = new Category(title, 0);
        categories.add(newCategory);
    }

    public int getTotalCosts() {
        int total = 0;
        for (Category c : categories) {
            total += c.getAmount();
        }
        return total;
    }


    // REQUIRES: category in categories list
    public void removeCategory(Category category) {
        categories.remove(category);
    }

    public int getSavings() {
        return income - getTotalCosts();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int getIncome() {
        return income;
    }
}
