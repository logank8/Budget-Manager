package ui;

import model.Range;

public class RangePanel extends CategoryPanel {

    BudgetGUI parent;
    Range range;

    public RangePanel(BudgetGUI parent, Range range) {
        super(parent, range);
        this.parent = parent;
        this.range = range;
    }

    @Override
    public void remove() {
        parent.getBudget().removeCategory(range);
        parent.getBudget().removeRange(range);
        parent.getCategories().remove(this);
        parent.getRanges().remove(this);
        parent.displayUpdate();
    }
}
