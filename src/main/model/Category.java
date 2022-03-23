package model;

import model.exceptions.UnevenRangeException;
import persistence.Writable;

// Represents a category of spending within the budget with a title
public abstract class Category implements Writable {

    private String title;

    // EFFECTS: creates category with given title
    public Category(String title) {
        this.title = title;
    }

    public abstract String getValString();

    // MODIFIES: this
    // EFFECTS: changes title to given string
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public abstract void setVal(int num1, int num2) throws UnevenRangeException;

    // EFFECTS: returns title
    public String getTitle() {
        return title;
    }

    public abstract int type();
}
