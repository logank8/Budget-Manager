package model;

import java.util.ArrayList;
import java.util.List;

public class Budget {

    private int income;
    List<Category> categories;
    List<Range> ranges;
    List<Amount> amounts;

    // EFFECTS: creates a new Budget with no categories and income set at 0
    public Budget() {
        income = 0;
        categories = new ArrayList<>();
        ranges = new ArrayList<>();
        amounts = new ArrayList<>();
    }

    public void addIncome(int amount) {
        income += amount;
    }

    public void addRange(String title) {
        Range newRange = new Range(title, 0, 1);
        ranges.add(newRange);
        categories.add(newRange);
    }

    public void addAmount(String title) {
        Amount newAmount = new Amount(title, 0);
        amounts.add(newAmount);
        categories.add(newAmount);
    }


    public int getTotalCosts() {
        int total = 0;
        for (Range r : ranges) {
            total += r.getHigh();
        }
        for (Amount a : amounts) {
            total += a.getAmount();
        }
        return total;
    }


    // REQUIRES: category in categories list
    public void removeCategory(Category category) {
        categories.remove(category);
    }

    public void removeRange(Range range) {
        ranges.remove(range);
    }

    public void removeAmount(Amount amount) {
        amounts.remove(amount);
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

    public List<Range> getRanges() {
        return ranges;
    }

    public List<Amount> getAmounts() {
        return amounts;
    }
}
