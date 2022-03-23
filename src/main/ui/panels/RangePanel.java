package ui.panels;

import model.Range;
import ui.BudgetGUI;

// // Represents CategoryPanel containing RANGE info
public class RangePanel extends CategoryPanel {

    BudgetGUI parent;
    Range range;

    // Creates JInternalFrame containing range info and buttons
    public RangePanel(BudgetGUI parent, Range range) {
        super(parent, range);
        this.parent = parent;
        this.range = range;
    }

    // MODIFIES: parent
    // EFFECTS: removes range from budget
    @Override
    public void remove() {
        parent.getBudget().removeCategory(range);
        parent.getBudget().removeRange(range);
        parent.getCategories().remove(this);
        parent.getRanges().remove(this);
        parent.displayUpdate();
    }
}
