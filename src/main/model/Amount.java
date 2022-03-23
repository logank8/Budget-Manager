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

    @Override
    public String getValString() {
        return "" + amount;
    }

    @Override
    public int type() {
        return 0;
    }

    // MODIFIES: this
    // EFFECTS: changes amount to given number
    @Override
    public void setVal(int num1, int num2) {
        amount = num1;
    }

    // EFFECTS: returns amount
    public int getAmount() {
        return amount;
    }

    // EFFECTS: writes amount to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", getTitle());
        json.put("amount", amount);
        return json;
    }
}
