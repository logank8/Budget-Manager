package model;


import org.json.JSONObject;
import persistence.Writable;

// Represents a category with a range rather than a definite amount
public class Range extends Category implements Writable {
    private int low;
    private int high;

    // REQUIRES: low < high
    // EFFECTS: creates range with given title and lower and upper bounds
    public Range(String title, int low, int high) {
        super(title);
        this.low = low;
        this.high = high;
    }

    // EFFECTS: returns difference between high and low
    public int getDifference() {
        return high - low;
    }

    // MODIFIES: this
    // EFFECTS: sets low to given value
    public void setLow(int newLow) {
        low = newLow;
    }

    // MODIFIES: this
    // EFFECTS: sets high to given values
    public void setHigh(int newHigh) {
        high = newHigh;
    }

    // EFFECTS: returns string of lower and upper bounds
    public String getRange() {
        return (low + " - " + high);
    }

    // EFFECTS: returns lower bound
    public int getLow() {
        return low;
    }

    // EFFECTS: returns upper bound
    public int getHigh() {
        return high;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("low", low);
        json.put("high", high);
        return json;
    }
}
