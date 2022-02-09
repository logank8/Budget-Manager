package model;

// Represents a category of spending within the budget with a title
public class Category {

    private String title;

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
