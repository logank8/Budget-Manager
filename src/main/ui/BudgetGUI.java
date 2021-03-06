package ui;

import model.*;
import model.Event;
import model.exceptions.UnevenRangeException;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.panels.AmountPanel;
import ui.panels.CategoryPanel;
import ui.panels.RangePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Desktop display for budget
public class BudgetGUI extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Budget budget;
    private final JDesktopPane desktop;

    private JLabel income;
    private JLabel savings;

    private final List<CategoryPanel> categories;
    private final List<AmountPanel> amounts;
    private final List<RangePanel> ranges;

    private JInternalFrame infoPanel;

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    public static final String JSON_STORE = "./data/budget.json";

    // instantiates budget desktop display
    public BudgetGUI() {
        budget = new Budget();
        desktop = new JDesktopPane();
        setContentPane(desktop);
        setTitle("CPSC 210: Budget Manager");
        setSize(WIDTH, HEIGHT);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        categories = new ArrayList<>();
        amounts = new ArrayList<>();
        ranges = new ArrayList<>();
        configureInfoPanel();
        displayUpdate();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        centreOnScreen();
        desktop.setBackground(new Color(11, 57, 72));
        setVisible(true);
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
        infoPanel.setBackground(new Color(172, 176, 189));
        infoPanel.setLayout(new FlowLayout());
        desktop.add(infoPanel);
        infoPanel.setVisible(true);
        infoPanel.reshape(20, 20, 750, 100);
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
        editButton.addActionListener(new EditIncomeAction(this));
        editButton.setVisible(true);
        infoPanel.add(editButton);

        savings = new JLabel("Savings: " + budget.getSavings());
        infoPanel.add(savings);
        addMenus();
    }

    // MODIFIES: this
    // EFFECTS: adds menu options for save, load, and adding new category
    private void addMenus() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileHandler = new JMenu("File...");
        JMenuItem save = new JMenuItem("Save budget");
        save.addActionListener(new SaveAction());
        JMenuItem load = new JMenuItem("Load budget");
        load.addActionListener(new LoadAction());
        fileHandler.add(save);
        fileHandler.add(load);
        menuBar.add(fileHandler);

        JMenu addCategory = new JMenu("Add...");
        JMenuItem addAmount = new JMenuItem("New amount");
        addAmount.addActionListener(new AddAmountAction(this));
        JMenuItem addRange = new JMenuItem("New range");
        addRange.addActionListener(new AddRangeAction(this));
        addCategory.add(addAmount);
        addCategory.add(addRange);
        menuBar.add(addCategory);

        infoPanel.setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: updates all values displayed
    public void displayUpdate() {
        income.setText("Income: " + budget.getIncome());
        savings.setText("Savings: " + budget.getSavings());
        for (RangePanel rangePanel : ranges) {
            rangePanel.displayUpdate();
        }
        for (AmountPanel amountPanel : amounts) {
            amountPanel.displayUpdate();
        }
    }

    // Helper to centre main application window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // MODIFIES: this
    // EFFECTS: processes user input to edit name and value of category
    public void edit(Category category) {
        String nameInput = JOptionPane.showInputDialog(null,
                "Please input a new name for the category");
        category.setTitle(nameInput);

        String ansInput =  JOptionPane.showInputDialog(null,
                "Please input a new" + ((category.type() != 0) ? " lower value " : " value ")
                        + "for the category");
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
            displayUpdate();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "ERROR: input must be in the form of an integer");
        } catch (UnevenRangeException e2) {
            JOptionPane.showMessageDialog(null, "ERROR: upper value must be higher than lower");
        }
    }

    // MODIFIES: json file
    // EFFECTS: saves budget to file
    private void saveBudget() {
        try {
            jsonWriter.open();
            jsonWriter.write(budget);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved current budget to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write to file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads budget from json update
    private void loadBudget() {
        try {
            budget = jsonReader.read();
            loadCategories();
            displayUpdate();
            JOptionPane.showMessageDialog(null, "Loaded current budget from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file " + JSON_STORE);
        }
    }

    // Helper for loadBudget() - loads categories to display
    private void loadCategories() {
        for (CategoryPanel c : categories) {
            c.setVisible(false);
        }
        categories.clear();
        ranges.clear();
        amounts.clear();
        for (Range range : budget.getRanges()) {
            RangePanel rangePanel = new RangePanel(this, range);
            categories.add(rangePanel);
            ranges.add(rangePanel);
            desktop.add(rangePanel);
        }
        for (Amount amount : budget.getAmounts()) {
            AmountPanel amountPanel = new AmountPanel(this, amount);
            categories.add(amountPanel);
            amounts.add(amountPanel);
            desktop.add(amountPanel);
        }
    }

    // Abstract action for editing income
    private class EditIncomeAction extends AbstractAction {

        BudgetGUI parent;

        // instantiates EditIncome action
        public EditIncomeAction(BudgetGUI parent) {
            super("Edit income");
            this.parent = parent;
        }

        // MODIFIES: parent
        // EFFECTS: sets income to user input
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog("Enter new value for income");
            try {
                int newIncome = Integer.parseInt(input);
                budget.setIncome(newIncome);
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "ERROR: value must be an integer");
            }
            parent.displayUpdate();
        }
    }

    // Abstract action for adding an amount to budget
    private class AddAmountAction extends AbstractAction {

        BudgetGUI parent;

        // instantiates AddAmountAction
        public AddAmountAction(BudgetGUI parent) {
            super("Add amount");
            this.parent = parent;
        }

        // MODIFIES: parent
        // EFFECTS: adds new amount to budget
        @Override
        public void actionPerformed(ActionEvent e) {
            Amount newAmount = new Amount("[NO NAME]", 0);
            budget.addAmount(newAmount);
            AmountPanel amountPanel = new AmountPanel(parent, newAmount);
            categories.add(amountPanel);
            amounts.add(amountPanel);
            desktop.add(amountPanel);
            parent.displayUpdate();
        }
    }

    // Abstract action for adding range to budget
    private class AddRangeAction extends AbstractAction {

        BudgetGUI parent;

        // instantiates AddRangeAction
        public AddRangeAction(BudgetGUI parent) {
            super("Add range");
            this.parent = parent;
        }

        // MODIFIES: parent
        // EFFECTS: adds new range to budget
        @Override
        public void actionPerformed(ActionEvent e) {
            Range newRange = new Range("[NO NAME]", 0, 1);
            budget.addRange(newRange);
            RangePanel rangePanel = new RangePanel(parent, newRange);
            ranges.add(rangePanel);
            categories.add(rangePanel);
            desktop.add(rangePanel);
            parent.displayUpdate();
        }
    }

    // Abstract action for saving budget
    private class SaveAction extends AbstractAction {

        // instantiates SaveAction
        public SaveAction() {
            super("Save");
        }

        // EFFECTS: saves budget
        @Override
        public void actionPerformed(ActionEvent e) {
            saveBudget();
        }
    }

    // Abstract action for loading budget
    private class LoadAction extends AbstractAction {

        // instantiates LoadAction
        public LoadAction() {
            super("Load");
        }

        // EFFECTS: loads budget
        @Override
        public void actionPerformed(ActionEvent e) {
            loadBudget();
        }
    }

    // EFFECTS: prints out event log at closing
    @Override
    public void dispose() {
        if (budget.getLog() != null) {
            for (Event next : budget.getLog()) {
                System.out.println(next.toString());
            }
        }
        super.dispose();
    }
}
