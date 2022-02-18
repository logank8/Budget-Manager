package model;

import exceptions.UnbalancedRangeException;

import java.util.ArrayList;
import java.util.List;

// Represents a Budget with a given amount of categories (divided into corresponding lists of ranges and amounts),
// and an income
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

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: increases income by given amount
    public void addIncome(int amount) {
        income += amount;
    }

    // MODIFIES: this
    // EFFECTS: creates new range with given title with bounds 0 -1 ; adds range to ranges and categories lists
    public void addRange(String title, int lower, int upper) throws UnbalancedRangeException {
        if (lower >= upper) {
            throw new UnbalancedRangeException();
        } else {
            Range newRange = new Range(title, lower, upper);
            ranges.add(newRange);
            categories.add(newRange);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates new amount with given title and amount 0, adds to amounts and categories lists
    public void addAmount(String title, int value) {
        Amount newAmount = new Amount(title, value);
        amounts.add(newAmount);
        categories.add(newAmount);
    }

    // EFFECTS: returns cost of every category in categories list
    //          takes upper value of each range
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
    // MODIFIES: this
    // EFFECTS: removes selected category from categories list
    public void removeCategory(Category category) {
        categories.remove(category);
    }

    // REQUIRES: range in ranges list
    // MODIFIES: this
    // EFFECTS: removes selected range from ranges list
    public void removeRange(Range range) {
        ranges.remove(range);
    }

    // REQUIRES: amount in amounts list
    // MODIFIES: this
    // EFFECTS: removes selected amount from amounts list
    public void removeAmount(Amount amount) {
        amounts.remove(amount);
    }

    // EFFECTS: returns the difference between income and total cost
    public int getSavings() {
        return income - getTotalCosts();
    }

    // EFFECTS: returns categories list
    public List<Category> getCategories() {
        return categories;
    }

    // EFFECTS: returns income
    public int getIncome() {
        return income;
    }

    // EFFECTS: returns ranges list
    public List<Range> getRanges() {
        return ranges;
    }

    // EFFECTS: returns amounts list
    public List<Amount> getAmounts() {
        return amounts;
    }
}
