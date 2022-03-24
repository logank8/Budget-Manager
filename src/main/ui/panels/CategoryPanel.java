package ui.panels;

import model.Category;
import ui.BudgetGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Abstract class representing JInternalFrame that holds category info & buttons
public abstract class CategoryPanel extends JInternalFrame {

    private final BudgetGUI parent;
    private final Category category;
    private final JLabel valText;
    private final int coordinate;

    // Creates new panel containing category info and buttons
    public CategoryPanel(BudgetGUI parent, Category category) {
        super(category.getTitle());
        setLayout(new FlowLayout());
        this.parent = parent;
        this.category = category;
        setBackground(new Color(172, 176, 189));
        int width = 350 + (5 * category.getValString().length());
        coordinate = 50 + (80 * (parent.getBudget().getCategories().size()));
        reshape(200, coordinate, width, 80);

        setTitle((category.getTitle().equals("")) ? "[NULL]" : category.getTitle());
        valText = new JLabel("Value: " + category.getValString());
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new EditAction());
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new RemoveAction());
        add(valText);
        add(editButton);
        add(removeButton);
        addIcon();
        setVisible(true);
    }

    private void addIcon() {
        JLabel label = new JLabel("         ");
        Icon icon = new ImageIcon("./data/fullheart.png");
        label.setIcon(icon);
        add(label);
    }

    public abstract void remove();

    // MODIFIES: this
    // EFFECTS: updates values info
    public void displayUpdate() {
        setTitle((category.getTitle().equals("")) ? "[NULL]" : category.getTitle());
        valText.setText("Value: " + category.getValString());
        int width = 350 + (5 * category.getValString().length());
        reshape(200, coordinate, width, 80);
    }

    // Abstract action to edit category
    private class EditAction extends AbstractAction {

        // instantiates EditAction
        public EditAction() {
            super("Edit value");
        }

        // EFFECTS: calls to gui to edit category
        @Override
        public void actionPerformed(ActionEvent e) {
            parent.edit(category);
        }
    }

    // Abstract action to remove category
    private class RemoveAction extends AbstractAction {

        // instantiates RemoveAction
        public RemoveAction() {
            super("Remove category");
        }

        // EFFECTS: calls to each subtype's implementation of remove to remove panel
        @Override
        public void actionPerformed(ActionEvent e) {
            remove();
        }
    }
}
