package ui;

import model.Budget;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Integer.parseInt;


public class BudgetGUI extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Budget budget;
    private JLabel income;
    private final JInternalFrame infoPanel;
    private final JMenu fileHandler;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    public static final String JSON_STORE = "./data/budget.json";

    public BudgetGUI() {
        budget = new Budget();
        JDesktopPane desktop = new JDesktopPane();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setContentPane(desktop);
        setTitle("CPSC 210: Budget Manager");
        setSize(WIDTH, HEIGHT);

        JMenuBar menuBar = new JMenuBar();
        fileHandler = new JMenu("File...");
        addSaveAndLoad();
        menuBar.add(fileHandler);


        infoPanel = new JInternalFrame("INFO", false, false,
                false, false);
        infoPanel.setLayout(new FlowLayout());
        desktop.add(infoPanel);
        infoPanel.setVisible(true);
        infoPanel.reshape(20, 20, 500, 200);
        addIncome();
        infoPanel.setJMenuBar(menuBar);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        desktop.setBackground(Color.magenta.darker().darker());
        setVisible(true);
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
        income.setText("Income: " + budget.getIncome());
    }

    private void addIncome() {
        income = new JLabel();
        income.setText("Income: " + budget.getIncome());
        income.setOpaque(true);
        income.setVisible(true);
        infoPanel.add(income);
        income.setBounds(0, 0, 100, 30);

        JButton editButton = new JButton(new EditIncomeAction());
        editButton.setSize(30, 30);
        editButton.setText("Edit");
        editButton.setVisible(true);
        infoPanel.add(editButton);
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
                    income.setText("Income: " + budget.getIncome());
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(null, "ERROR: Integer value expected");
                }

            }
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
