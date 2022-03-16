package ui;

import model.Budget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class BudgetGUI extends JFrame implements MouseListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Budget budget;
    private JDesktopPane desktop;
    private JLabel income;
    private JInternalFrame infoPanel;

    public BudgetGUI() {
        budget = new Budget();
        desktop = new JDesktopPane();

        setContentPane(desktop);
        setTitle("CPSC 210: Budget Manager");
        setSize(WIDTH, HEIGHT);

        infoPanel = new JInternalFrame("INFO", false, false,
                false, false);
        infoPanel.setLayout(new FlowLayout());
        desktop.add(infoPanel);
        infoPanel.setVisible(true);
        infoPanel.reshape(20, 20, 500, 200);
        addIncome();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        desktop.setBackground(Color.magenta.darker().darker());
        setVisible(true);
    }


    // Helper to centre main application window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    private void addIncome() {
        income = new JLabel();
        income.setText("Income: " + budget.getIncome());
        income.setOpaque(true);
        income.setVisible(true);
        infoPanel.add(income);
        income.setBounds(0, 0, 100, 30);

        JButton editButton = new JButton();
        editButton.setSize(30, 30);
        editButton.setText("Edit");
        editButton.setVisible(true);
        infoPanel.add(editButton);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Click");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
