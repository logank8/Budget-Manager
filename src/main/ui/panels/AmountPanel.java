package ui.panels;

import model.Amount;
import ui.BudgetGUI;

// Represents Category containing AMOUNT info
public class AmountPanel extends CategoryPanel {

    private final BudgetGUI parent;
    private final Amount amount;

    // creates AmountPanel with amount info and buttons
    public AmountPanel(BudgetGUI parent, Amount amount) {
        super(parent, amount);
        this.parent = parent;
        this.amount = amount;
    }

    // MODIFIES: parent
    // EFFECTS: removes category from budget
    public void remove() {
        parent.getBudget().removeCategory(amount);
        parent.getBudget().removeAmount(amount);
        parent.getCategories().remove(this);
        parent.getAmounts().remove(this);
    }
}
