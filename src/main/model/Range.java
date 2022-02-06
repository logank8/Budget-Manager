package model;

import java.nio.file.ClosedWatchServiceException;

public class Range extends Category {

    private int low;
    private int high;

    public Range(String title, int low, int high) {
        super(title);
        this.low = low;
        this.high = high;
    }

    public int getDifference() {
        return high - low;
    }

    public void setLow(int newLow) {
        low = newLow;
    }

    public void setHigh(int newHigh) {
        high = newHigh;
    }

    public String getRange() {
        return (low + " - " + high);
    }

    public int getLow() {
        return low;
    }

    public int getHigh() {
        return high;
    }


}
