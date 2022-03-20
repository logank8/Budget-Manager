package model;

import persistence.Writable;

// Represents a category of spending within the budget with a title
public abstract class Category implements Writable {

    private String title;

    public abstract String getValString();

    // EFFECTS: creates category with given title
    public Category(String title) {
        this.title = title;
    }

    // MODIFIES: this
    // EFFECTS: changes title to given string
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    // EFFECTS: returns title
    public String getTitle() {
        return title;
    }
}
