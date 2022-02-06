package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BudgetApp {

    private Budget currentBudget;
    private Scanner input;

    public BudgetApp() {
        runBudget();
    }

    // MODIFIES: this
    // processes user input
    private void runBudget() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "i":
                changeIncome();
                break;
            case "c":
                chooseCategory(0);
                break;
            case "n":
                chooseCategory(1);
                break;
            case "s":
                System.out.println("Savings: " + currentBudget.getSavings());
                break;
            case "a":
                newCategory();
        }
    }


    private void newCategory() {
        System.out.println("Please enter a title for the new category");
        String answer = input.next();
        currentBudget.addCategory(answer);
    }

    // 0 = editCategory
    // 1 = nameCategory
    // 2 = removeCategory
    private void chooseCategory(int nextAction) {
        if (currentBudget.getCategories().isEmpty()) {
            System.out.println("No categories to choose from.");
        } else {
            Category subject;
            System.out.println("Enter category selection:");
            List<String> names = namesList();
            String answer = input.next().toLowerCase();
            if (names.contains(answer)) {
                subject = currentBudget.getCategories().get(names.indexOf(answer));
                if (nextAction == 0) {
                    editCategory(subject);
                } else if (nextAction == 1) {
                    nameCategory(subject);
                } else {
                    currentBudget.removeCategory(subject);
                }
            } else {
                System.out.println("Answer not valid");
                chooseCategory(nextAction);
            }
        }
    }

    private List<String> namesList() {
        List<String> names = new ArrayList<>();
        for (Category c : currentBudget.getCategories()) {
            names.add(c.getTitle().toLowerCase());
        }
        return names;
    }

    private void nameCategory(Category category) {
        System.out.println("Enter new name for " + category.getTitle());
        String answer = input.next();
        category.setTitle(answer);
    }

    private void editCategory(Category category) {
        System.out.println("Please input new value for " + category.getTitle());
        int answer = input.nextInt();
        if (answer >= 0) {
            category.setAmount(answer);
        } else {
            System.out.println("Answer not valid.");
            editCategory(category);
        }
    }

    private void changeIncome() {
        System.out.println("Please enter a value to add to income");
        int answer = input.nextInt();
        if ((currentBudget.getIncome() + answer) < 0) {
            System.out.println("Answer not valid");
            changeIncome();
        } else {
            currentBudget.addIncome(answer);
            System.out.println("New income: " + currentBudget.getIncome());
        }
    }

    private void displayMenu() {
        showInfo();
        System.out.println("Controls: ");
        System.out.println("i: change income");
        System.out.println("c: edit a spending category amount");
        System.out.println("n: edit a spending category name");
        System.out.println("a: add a new spending category");
        System.out.println("r: remove a spending category");
        System.out.println("s: view savings");
        System.out.println("q: quit");
    }

    private void showInfo() {
        System.out.println("Current Budget: ");
        System.out.println("Income: " + currentBudget.getIncome());
        if (currentBudget.getCategories().isEmpty()) {
            System.out.println("No categories created yet");
        } else {
            System.out.println("Spending categories: ");
            for (Category c : currentBudget.getCategories()) {
                System.out.println(c.getTitle() + ": " + c.getAmount());
            }
        }
    }

    private void init() {
        currentBudget = new Budget();
        input = new Scanner(System.in);
    }
}
