package ui;

import model.Amount;

public class AmountPanel extends CategoryPanel {

    private BudgetGUI parent;
    private Amount amount;

    public AmountPanel(BudgetGUI parent, Amount amount) {
        super(parent, amount);
        this.parent = parent;
        this.amount = amount;
    }

    public void remove() {

    }
}
