package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Budget application
public class BudgetApp {

    private Budget currentBudget;
    private Scanner input;

    // EFFECTS: runs budget app
    public BudgetApp() {
        runBudget();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
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
                newCategory(0);
                break;
            case "r":
                newCategory(1);
                break;
            case "e":
                chooseCategory(2);
                break;
        }
    }

    // EFFECTS: processes user input for title for new category
    // nextAction 0 = new amount
    // nextAction 1 = new range
    private void newCategory(int nextAction) {
        System.out.println("Please enter a title for the new category");
        String answer = input.next();
        if (nextAction == 0) {
            addAmount(answer);
        } else {
            addRange(answer);
        }
    }

    // REQUIRES: String name not ""
    // MODIFIES: this
    // EFFECTS: adds range and inputs range to editRange
    private void addRange(String name) {
        Range range = new Range(name, 0, 1);
        currentBudget.addRange(range);
        editRange(range);
    }

    // REQUIRES: String name not ""
    // MODIFIES: this
    // EFFECTS: adds amount with given value
    private void addAmount(String name) {
        System.out.println("Enter an amount for " + name);
        int answer = input.nextInt();
        currentBudget.addAmount(name, answer);
    }

    // EFFECTS: processes user input for category selection; then:
    // nextAction 0 = editCategory
    // nextAction 1 = nameCategory
    // nextAction 2 = removeCategory
    private void chooseCategory(int nextAction) {
        if (currentBudget.getCategories().isEmpty()) {
            System.out.println("No categories to choose from.");
        } else {
            Category subject;
            System.out.println("Enter category selection:");
            List<String> names = allNames();
            String answer = input.next().toLowerCase();
            if (names.contains(answer)) {
                subject = currentBudget.getCategories().get(names.indexOf(answer));
                if (nextAction == 0) {
                    editCategory(subject);
                } else if (nextAction == 1) {
                    nameCategory(subject);
                } else {
                    removeCategory(subject);
                }
            } else {
                System.out.println("Answer not valid");
                chooseCategory(nextAction);
            }
        }
    }

    // REQUIRES: object category not null
    // MODIFIES: this
    // EFFECTS: removes category from currentBudget categories list and ranges/amounts list depending on subclass
    private void removeCategory(Category category) {
        currentBudget.removeCategory(category);
        String categoryName = category.getTitle().toLowerCase();
        if (rangeNames().contains(categoryName)) {
            Range range = currentBudget.getRanges().get(rangeNames().indexOf(categoryName));
            currentBudget.removeRange(range);
        } else {
            Amount amount = currentBudget.getAmounts().get(amountNames().indexOf(categoryName));
            currentBudget.removeAmount(amount);
        }
    }

    // EFFECTS: creates corresponding list of titles of every category
    private List<String> allNames() {
        List<String> names = new ArrayList<>();
        for (Category c : currentBudget.getCategories()) {
            names.add(c.getTitle().toLowerCase());
        }
        return names;
    }

    // EFFECTS: creates corresponding list of titles of every range
    private List<String> rangeNames() {
        List<String> names = new ArrayList<>();
        for (Range r : currentBudget.getRanges()) {
            names.add(r.getTitle().toLowerCase());
        }
        return names;
    }

    // EFFECTS: creates corresponding list of titles of every amount
    private List<String> amountNames() {
        List<String> names = new ArrayList<>();
        for (Amount a : currentBudget.getAmounts()) {
            names.add(a.getTitle().toLowerCase());
        }
        return names;
    }

    // REQUIRES: category object not null
    // MODIFIES: category
    // EFFECTS: processes user input to change title of category
    private void nameCategory(Category category) {
        System.out.println("Enter new name for " + category.getTitle());
        String answer = input.next().toLowerCase();
        if (allNames().contains(answer)) {
            System.out.println("A category already has that name.");
        } else {
            category.setTitle(answer);
        }
    }

    // REQUIRES: category object not null
    // EFFECTS: finds if category is a range or amount, then call editRange or editAmount respectively
    private void editCategory(Category category) {
        String categoryName = category.getTitle().toLowerCase();
        if (rangeNames().contains(categoryName)) {
            Range range = currentBudget.getRanges().get(rangeNames().indexOf(categoryName));
            editRange(range);
        } else {
            Amount amount = currentBudget.getAmounts().get(amountNames().indexOf(categoryName));
            editAmount(amount);
        }
    }

    // REQUIRES: range object not null
    // MODIFIES: range
    // EFFECTS: processes user input for new lower & upper bounds for range
    private void editRange(Range range) {
        System.out.println("Enter a lower bound for " + range.getTitle());
        int answer = input.nextInt();
        if (answer >= 0) {
            range.setLow(answer);
            boolean done = false;
            while (!done) {
                System.out.println("Enter an upper bound for " + range.getTitle());
                int answer2 = input.nextInt();
                if (answer2 > answer) {
                    range.setHigh(answer2);
                    done = true;
                } else {
                    System.out.println("Answer not valid.");
                }
            }
        } else {
            System.out.println("Answer not valid.");
            editRange(range);
        }
    }

    // REQUIRES: amount object not null
    // MODIFIES: amount
    // EFFECTS: processes user input for new value for amount
    private void editAmount(Amount amount) {
        System.out.println("Please input new value for " + amount.getTitle());
        int answer = input.nextInt();
        if (answer >= 0) {
            amount.setAmount(answer);
        } else {
            System.out.println("Answer not valid.");
            editAmount(amount);
        }
    }

    // MODIFIES: income
    // EFFECTS: adds user int input to income & outputs new income value
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

    // EFFECTS: outputs controls
    private void displayMenu() {
        showInfo();
        System.out.println("Controls: ");
        System.out.println("i: change income");
        System.out.println("c: edit a spending category amount");
        System.out.println("n: edit a spending category name");
        System.out.println("r: add a new spending category (range)");
        System.out.println("a: add a new spending category (definite amount)");
        System.out.println("e: remove a spending category");
        System.out.println("s: view savings");
        System.out.println("q: quit");
    }

    // EFFECTS: outputs budget income, ranges, and amounts
    private void showInfo() {
        System.out.println("Current Budget: ");
        System.out.println("Income: " + currentBudget.getIncome());
        if (currentBudget.getCategories().isEmpty()) {
            System.out.println("No categories created yet");
        } else {
            System.out.println("Ranges: ");
            for (Range r : currentBudget.getRanges()) {
                System.out.println(r.getTitle() + ": " + r.getRange());
            }
            System.out.println("Definite amounts: ");
            for (Amount a : currentBudget.getAmounts()) {
                System.out.println(a.getTitle() + ": " + a.getAmount());
            }
        }
    }

    // EFFECTS: initializes budget and scanner
    private void init() {
        currentBudget = new Budget();
        input = new Scanner(System.in);
    }
}
