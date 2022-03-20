package ui;

import model.Amount;
import model.Budget;
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
    private JPanel addAmountPane;

    private final JDesktopPane desktop;
    private JInternalFrame infoPanel;
    private JMenuBar menuBar;
    private JMenu fileHandler;
    private JMenu addMenu;
    private GridBagConstraints gc;

    private ArrayList<JInternalFrame> amounts;
    private ArrayList<JInternalFrame> ranges;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    public static final String JSON_STORE = "./data/budget.json";

    public BudgetGUI() {
        budget = new Budget();
        desktop = new JDesktopPane();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setContentPane(desktop);
        setTitle("CPSC 210: Budget Manager");
        setSize(WIDTH, HEIGHT);

        amounts = new ArrayList<JInternalFrame>();
        ranges = new ArrayList<JInternalFrame>();

        configureInfoPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        Color goodColor = Color.getHSBColor(.29F, .19F, .38F);
        desktop.setBackground(goodColor);
        setVisible(true);
    }

    private void configureInfoPanel() {
        infoPanel = new JInternalFrame("INFO", false, false, false);
        desktop.add(infoPanel);
        GridBagLayout layout = new GridBagLayout();
        infoPanel.setLayout(layout);
        gc = new GridBagConstraints();
        infoPanel.setVisible(true);
        infoPanel.reshape(10, 20, 780, 500);
        addIncome();
        addSavings();
        menuBar = new JMenuBar();
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
        addAmount.addActionListener(new AddAmountAction());
        JMenuItem addRange = new JMenuItem("New range");
        addMenu.add(addAmount);
        addMenu.add(addRange);
    }

    private void addSaveAndLoad() {
        JMenuItem save = new JMenuItem("Save");
        save.getAccessibleContext().setAccessibleDescription("Save budget to file");
        save.addActionListener(new SaveAction());
        JMenuItem load = new JMenuItem("Load");
        load.getAccessibleContext().setAccessibleDescription("Load budget from file");
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
        income.setText("INCOME: " + budget.getIncome());
        savings.setText("SAVINGS: " + budget.getSavings());
    }

    private void addIncome() {
        income = new JLabel();
        income.setText("INCOME: " + budget.getIncome());
        income.setOpaque(true);
        income.setVisible(true);
        gc.gridx = 0;
        gc.gridy = 0;
        infoPanel.add(income, gc);

        JButton editButton = new JButton(new EditIncomeAction());
        editButton.setSize(30, 30);
        editButton.setText("Edit");
        editButton.setVisible(true);
        gc.gridx = 1;
        infoPanel.add(editButton);
    }

    private void addSavings() {
        savings = new JLabel();
        savings.setText("SAVINGS: " + budget.getSavings());
        savings.setVisible(true);
        savings.setVerticalAlignment(0);
        gc.gridx = 2;
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

        JLabel valText;
        JLabel nameText;
        JButton editButton;

        public AmountPane(Amount amount) {
            super("", false, false, false, false);
            setLayout(new FlowLayout());
            nameText = new JLabel("NAME: " + amount.getTitle());
            valText = new JLabel("VALUE: " + amount.getValString());
            editButton = new JButton("EDIT");
            this.add(nameText);
            this.add(valText);
            this.add(editButton);
            reshape(300, 300, 200, 200);
            gc.gridx = 0;
            gc.gridy = 2;
            infoPanel.add(this, gc);
            setVisible(true);
        }

        private class EditAction extends AbstractAction {
            public EditAction() {
                super("Edit amount");

            }
        }

    }

    private class AddAmountAction extends AbstractAction {

        public AddAmountAction() {
            super("Add amount");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Amount newAmount = new Amount("", 0);
            amounts.add(new AmountPane(newAmount));
            displayUpdate();
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
