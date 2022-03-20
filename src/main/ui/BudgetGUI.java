package ui;

import exceptions.UnevenRangeException;
import model.Amount;
import model.Budget;
import model.Range;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;


public class BudgetGUI extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Budget budget;
    private JLabel income;
    private JLabel savings;

    private final JDesktopPane desktop;
    private JInternalFrame infoPanel;
    private JMenu fileHandler;
    private JMenu addMenu;
    private GridBagConstraints gc;
    private static final Color goodColor = Color.getHSBColor(0.03F, 0.03F, 0.94F);

    private final ArrayList<JInternalFrame> categories;
    private final ArrayList<AmountPane> amounts;
    private final ArrayList<RangePane> ranges;

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    public static final String JSON_STORE = "./data/budget.json";

    public BudgetGUI() {
        budget = new Budget();
        desktop = new JDesktopPane();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setContentPane(desktop);
        setTitle("Budget Manager");
        setSize(WIDTH, HEIGHT);

        categories = new ArrayList<JInternalFrame>();
        amounts = new ArrayList<AmountPane>();
        ranges = new ArrayList<RangePane>();

        configureInfoPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        Color goodColor = Color.getHSBColor(.29F, .19F, .38F);
        desktop.setBackground(goodColor);
        setVisible(true);
    }

    private void configureInfoPanel() {
        int panelCounter = 0;
        infoPanel = new JInternalFrame("INFO", false, false, false);
        infoPanel.setBackground(goodColor);
        desktop.add(infoPanel);
        GridBagLayout layout = new GridBagLayout();
        infoPanel.setLayout(layout);
        gc = new GridBagConstraints();
        infoPanel.setVisible(true);
        infoPanel.reshape(5, 20, 790, 120);
        addIncome();
        addSavings();
        JMenuBar menuBar = new JMenuBar();
        fileHandler = new JMenu("File...");
        addSaveAndLoad();
        menuBar.add(fileHandler);
        addMenu = new JMenu("Add...");
        optionsRangeAmount();
        menuBar.add(addMenu);
        menuBar.setVisible(true);
        infoPanel.setJMenuBar(menuBar);

    }

    private void optionsRangeAmount() {
        JMenuItem addAmount = new JMenuItem("New amount");
        addAmount.addActionListener(new AddAmountAction(this));
        JMenuItem addRange = new JMenuItem("New range");
        addRange.addActionListener(new AddRangeAction(this));
        addMenu.add(addAmount);
        addMenu.add(addRange);
    }

    private void addSaveAndLoad() {
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new SaveAction());
        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(new LoadAction());
        fileHandler.add(save);
        fileHandler.add(load);
    }


    // Helper to centre main application window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    private void displayUpdate() {
        for (AmountPane amount : amounts) {
            amount.displayUpdate();
        }
        for (RangePane range : ranges) {
            range.displayUpdate();
        }
        income.setText("INCOME: " + budget.getIncome());
        savings.setText("SAVINGS: " + budget.getSavings());
    }

    private void addIncome() {
        income = new JLabel();
        income.setText("INCOME: " + budget.getIncome() + "    ");
        income.setOpaque(true);
        income.setVisible(true);
        gc.weightx = 0.1;
        gc.gridx = 1;
        gc.gridy = 0;
        infoPanel.add(income, gc);

        JButton editButton = new JButton(new EditIncomeAction());
        editButton.setSize(30, 30);
        editButton.setText("Edit");
        editButton.setVisible(true);
        gc.gridy = 1;
        infoPanel.add(editButton, gc);
    }

    private void addSavings() {
        savings = new JLabel();
        savings.setText("SAVINGS: " + budget.getSavings());
        savings.setVisible(true);
        savings.setVerticalAlignment(SwingConstants.TOP);
        gc.weightx = 0.1;
        gc.gridx = 2;
        gc.gridy = 0;
        infoPanel.add(savings, gc);
    }

    private void saveBudget() {
        try {
            jsonWriter.open();
            jsonWriter.write(budget);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved file");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "ERROR: file not found");
        }
    }

    // MODIFIES: budget
    // EFFECTS: loads budget from json
    private void loadBudget() {
        try {
            budget = jsonReader.read();
            amounts.clear();
            ranges.clear();
            for (JInternalFrame panel : categories) {
                panel.setVisible(false);
            }
            categories.clear();
            for (Amount amount : budget.getAmounts()) {
                AmountPane amountPane = new AmountPane(amount, this);
                amounts.add(amountPane);
                categories.add(amountPane);
            }
            for (Range range : budget.getRanges()) {
                RangePane rangePane = new RangePane(range, this);
                ranges.add(rangePane);
                categories.add(rangePane);
            }
            displayUpdate();
            JOptionPane.showMessageDialog(null, "Loaded current budget from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file " + JSON_STORE);
        }
    }


    private class EditIncomeAction extends AbstractAction {

        public EditIncomeAction() {
            super("Edit Income");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog(new JFormattedTextField(),
                    "Please input new value for income");
            if (input != null) {
                try {
                    budget.setIncome(parseInt(input));
                    displayUpdate();
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(null, "ERROR: Integer value expected");
                }

            }
        }
    }

    private class AmountPane extends JInternalFrame {

        BudgetGUI parent;
        Amount amount;
        JLabel valText;
        JLabel nameText;
        JButton editButton;

        public AmountPane(Amount amount, BudgetGUI parent) {
            super("", false, false, false, false);
            setBackground(goodColor);
            this.amount = amount;
            this.parent = parent;
            setLayout(new GridLayout(3, 1));
            nameText = new JLabel("NAME: " + amount.getTitle());
            valText = new JLabel("VALUE: " + amount.getValString());
            editButton = new JButton("EDIT");
            editButton.addActionListener(new EditAction());
            this.add(nameText);
            this.add(valText);
            this.add(editButton);
            reshape(300, 300, 200, 150);
            desktop.add(this);
            setVisible(true);
        }

        public void displayUpdate() {
            nameText.setText("NAME: " + amount.getTitle());
            valText.setText("VALUE: " + amount.getValString());
        }

        private class EditAction extends AbstractAction {
            public EditAction() {
                super("Edit amount");
            }

            public void actionPerformed(ActionEvent e) {
                String ansInput = JOptionPane.showInputDialog(null,
                        "Enter a new name for the category");
                amount.setTitle(ansInput);
                String amountInput = JOptionPane.showInputDialog(null,
                        "Enter a new value for the category");
                try {
                    amount.setAmount(Integer.parseInt(amountInput));
                    parent.displayUpdate();
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(null, "ERROR: Integer value expected");
                }
            }
        }
    }

    private class RangePane extends JInternalFrame {

        BudgetGUI parent;
        Range range;
        JLabel valText;
        JLabel nameText;
        JButton editButton;
        JButton removeButton;

        public RangePane(Range range, BudgetGUI parent) {
            super("", false, false, false, false);
            setBackground(goodColor);
            this.range = range;
            this.parent = parent;
            setLayout(new GridLayout(4, 1));
            nameText = new JLabel("NAME: " + range.getTitle());
            valText = new JLabel("VALUE: " + range.getValString());
            editButton = new JButton("EDIT");
            editButton.addActionListener(new EditAction());
            removeButton = new JButton("REMOVE");
            removeButton.addActionListener(new RemoveAction(this));
            this.add(nameText);
            this.add(valText);
            this.add(editButton);
            this.add(removeButton);
            reshape(300, 300, 200, 160);
            desktop.add(this);
            setVisible(true);
        }

        public void displayUpdate() {
            nameText.setText("NAME: " + range.getTitle());
            valText.setText("VALUE: " + range.getValString());
        }

        private class EditAction extends AbstractAction {
            public EditAction() {
                super("Edit range");
            }

            public void actionPerformed(ActionEvent e) {
                String ansInput = JOptionPane.showInputDialog(null,
                        "Enter a new name for the category");
                range.setTitle(ansInput);

                String lowInput = JOptionPane.showInputDialog(null,
                        "Enter a new lower value for the range");
                try {
                    range.setLow(Integer.parseInt(lowInput));
                    String highInput = JOptionPane.showInputDialog(null,
                            "Enter a new higher value for the range");
                    try {
                        range.setHigh(Integer.parseInt(highInput));
                        parent.displayUpdate();
                    } catch (UnevenRangeException r) {
                        JOptionPane.showMessageDialog(null,
                                "ERROR: second value must be greater than first");
                    } catch (NumberFormatException e3) {
                        JOptionPane.showMessageDialog(null, "ERROR: Integer value expected");
                    }
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(null, "ERROR: Integer value expected");
                }
            }
        }

        private class RemoveAction extends AbstractAction {

            RangePane panel;

            public RemoveAction(RangePane panel) {
                super("Remove category");
                this.panel = panel;
            }

            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this?");
                if (confirm == 0) {
                    categories.remove(panel);
                    ranges.remove(panel);
                    panel.setVisible(false);
                }
            }
        }
    }

    private class AddAmountAction extends AbstractAction {

        BudgetGUI parent;

        public AddAmountAction(BudgetGUI parent) {
            super("Add amount");
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Amount newAmount = new Amount("", 0);
            budget.addAmount(newAmount);
            AmountPane amountPane = new AmountPane(newAmount, parent);
            amounts.add(amountPane);
            categories.add(amountPane);
            parent.displayUpdate();
        }
    }

    private class AddRangeAction extends AbstractAction {
        BudgetGUI parent;

        public AddRangeAction(BudgetGUI parent) {
            super("Add range");
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Range newRange = new Range("", 0, 1);
            budget.addRange(newRange);
            RangePane rangePane = new RangePane(newRange, parent);
            ranges.add(rangePane);
            categories.add(rangePane);
            parent.displayUpdate();
        }
    }

    private class SaveAction extends AbstractAction {
        public SaveAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            saveBudget();
        }
    }

    private class LoadAction extends AbstractAction {
        public LoadAction() {
            super("Load");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            loadBudget();
        }
    }
}
