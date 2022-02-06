package model;

public class Category {

    private String title;
    private int amount;

    public Category(String title, int amount) {
        this.title = title;
        this.amount = amount;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setAmount(int newAmount) {
        amount = newAmount;
    }

    public String getTitle() {
        return title;
    }

    public int getAmount() {
        return amount;
    }
}
