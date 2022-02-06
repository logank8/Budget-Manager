package model;

public class Amount extends Category {

    private int amount;

    public Amount(String title, int amount) {
        super(title);
        this.amount = amount;

    }

    public void setAmount(int newAmount) {
        amount = newAmount;
    }

    public int getAmount() {
        return amount;
    }
}
