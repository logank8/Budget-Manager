package ui;

import model.Budget;
import model.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public abstract class CategoryPanel extends JInternalFrame {

    private BudgetGUI parent;
    private Category category;
    private JLabel valText;
    private JButton editButton;
    private JButton removeButton;

    public CategoryPanel(BudgetGUI parent, Category category) {
        super(category.getTitle());
        setLayout(new GridLayout(1, 3));
        this.parent = parent;
        this.category = category;
        reshape(20, 130, 200, 60);
        setTitle((category.getTitle().equals("")) ? "[NULL]" : category.getTitle());
        valText = new JLabel("Value: " + category.getValString());
        editButton = new JButton("Edit");
        editButton.addActionListener(new EditAction());
        removeButton = new JButton("Remove");

    }

    public abstract void remove();

    public void displayUpdate() {
        setTitle((category.getTitle().equals("")) ? "[NULL]" : category.getTitle());
        valText.setText("Value: " + category.getValString());
    }

    private class EditAction extends AbstractAction {
        public EditAction() {
            super("Edit value");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            parent.edit(category);
        }
    }

    private class RemoveAction extends AbstractAction {
        public RemoveAction() {
            super("Remove category");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            remove();
        }
    }
}
