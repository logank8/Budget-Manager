package ui;

import model.Amount;
import model.Budget;
import model.Category;
import model.exceptions.UnevenRangeException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private final Budget budget;
    private final JDesktopPane desktop;

    private JLabel income;
    private JLabel savings;

    private final List<CategoryPanel> categories;
    private final List<AmountPanel> amounts;
    private final List<RangePanel> ranges;

    private JInternalFrame infoPanel;
    private JInternalFrame categoryFrame;

    public BudgetGUI() {
        budget = new Budget();
        desktop = new JDesktopPane();

        setContentPane(desktop);
        setTitle("CPSC 210: Budget Manager");
        setSize(WIDTH, HEIGHT);

        categories = new ArrayList<>();
        amounts = new ArrayList<>();
        ranges = new ArrayList<>();

        configureInfoPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        desktop.setBackground(new Color(11, 57, 72));
        setVisible(true);
        configureCategoryFrame();
    }

    public Budget getBudget() {
        return budget;
    }

    public List<CategoryPanel> getCategories() {
        return categories;
    }

    public List<RangePanel> getRanges() {
        return ranges;
    }

    public List<AmountPanel> getAmounts() {
        return amounts;
    }


    // MODIFIES: this
    // EFFECTS: Adds info panel to desktop
    private void configureInfoPanel() {
        infoPanel = new JInternalFrame("INFO", false, false,
                false, false);
        infoPanel.setLayout(new FlowLayout());
        desktop.add(infoPanel);
        infoPanel.setVisible(true);
        infoPanel.reshape(20, 20, 750, 80);
        addIncomeSavings();
    }

    // MODIFIES: this
    // EFFECTS: adds income and savings to info panel
    private void addIncomeSavings() {
        income = new JLabel("Income: " + budget.getIncome());
        income.setVisible(true);
        infoPanel.add(income);

        JButton editButton = new JButton();
        editButton.setText("Edit Income");
        editButton.setVisible(true);
        infoPanel.add(editButton);

        savings = new JLabel("Savings: " + budget.getSavings());
        infoPanel.add(savings);
    }

    private void configureCategoryFrame() {
        categoryFrame = new JInternalFrame("test", false, false, false);
        categoryFrame.setLayout(new FlowLayout());
        desktop.add(categoryFrame);
        categoryFrame.reshape(300, 100, 480, 300);
        // add menu bar
        makeTable();
        categoryFrame.setVisible(true);
    }

    public void displayUpdate() {
        income.setText("Income: " + budget.getIncome());
        savings.setText("Savings: " + budget.getSavings());
    }

    // Helper to centre main application window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    private void makeTable() {
        budget.addAmount(new Amount("test", 100));
        JTable categoryTable = new JTable(new CategoryTableModel(budget.getCategories()));
        categoryFrame.add(categoryTable);
        categoryTable.setVisible(true);
        JScrollPane panel = new JScrollPane(categoryTable);
        categoryFrame.add(panel);
    }

    public void edit(Category category) {
        String nameInput = JOptionPane.showInputDialog(null,
                "Please input a new name for the category");
        category.setTitle(nameInput);

        String message = (category.type() == 0) ? "lower value" : "value";
        String ansInput =  JOptionPane.showInputDialog(null,
                "Please input a new" + message + "for the category");
        try {
            int num1 = Integer.parseInt(ansInput);
            if (category.type() == 0) {
                category.setVal(num1, 0);
            } else {
                String ansInput2 = JOptionPane.showInputDialog(null,
                        "Please input a new upper value for the category");
                int num2 = Integer.parseInt(ansInput2);
                category.setVal(num1, num2);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "ERROR: input must be in the form of an integer");
        } catch (UnevenRangeException e2) {
            JOptionPane.showMessageDialog(null,
                    "ERROR: upper value must be higher than lower");
        }
    }
}
