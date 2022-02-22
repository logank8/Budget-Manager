package model;

import org.json.JSONObject;

// Represents a category with a definite amount
public class Amount extends Category {

    private int amount;

    // EFFECTS: creates a category with given definite amount and title
    public Amount(String title, int amount) {
        super(title);
        this.amount = amount;

    }

    // MODIFIES: this
    // EFFECTS: changes amount to given number
    public void setAmount(int newAmount) {
        amount = newAmount;
    }

    // EFFECTS: returns amount
    public int getAmount() {
        return amount;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", getTitle());
        json.put("amount", amount);
        return json;
    }
}
