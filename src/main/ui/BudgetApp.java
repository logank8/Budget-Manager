package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// Budget application
public class BudgetApp {
    public static final String JSON_STORE = "./data/budget.json";
    private Budget currentBudget;
    private Scanner input;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // EFFECTS: runs budget app
    public BudgetApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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
        System.out.println("Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if ("i".equals(command)) { // edit income
            changeIncome();
        } else if ("e".equals(command)) { // edit category
            chooseCategory(0);
        } else if ("s".equals(command)) { // show savings
            System.out.println("Savings: " + currentBudget.getSavings());
        } else if ("a".equals(command)) { // add new category
            newCategory();
        } else if ("r".equals(command)) { // remove category
            chooseCategory(1);
        } else if ("f".equals(command)) { // save
            saveBudget();
        } else if ("l".equals(command)) { // load
            loadBudget();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: currentBudget income
    // EFFECTS: adds user int input to income & outputs new income value
    private void changeIncome() {
        System.out.println("Please enter a value to add to income");
        int answer;
        answer = input.nextInt();
        if ((currentBudget.getIncome() + answer) < 0) {
            System.out.println("Answer not valid");
            changeIncome();
        } else {
            currentBudget.addIncome(answer);
            System.out.println("New income is " + currentBudget.getIncome());
        }
    }

    // EFFECTS: processes user input for category selection; then:
    // nextAction 0 = editCategory
    // nextAction 1 = removeCategory
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
                } else {
                    removeCategory(subject);
                }
            } else {
                System.out.println("Answer not valid");
                chooseCategory(nextAction);
            }
        }
    }


    // MODIFIES: currentBudget
    // EFFECTS: processes user input for new category and then moves to edit
    private void newCategory() {
        System.out.println("Would you like to create a new amount or new range?");
        System.out.println("r: new range");
        System.out.println("a: new amount");
        String answer = input.next();
        if (answer.equals("a")) {
            Amount amount = new Amount("the amount", 0);
            currentBudget.addAmount(amount);
            editAmount(amount);
        } else if (answer.equals("r")) {
            Range range = new Range("the range", 0, 1);
            currentBudget.addRange(range);
            editRange(range);
        } else {
            System.out.println("Answer not valid.");
            newCategory();
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
        editRangeName(range);
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

    private void editRangeName(Range range) {
        System.out.println("Enter a new name for " + range.getTitle());
        String newName = input.next();
        if (allNames().contains(newName.toLowerCase())) {
            System.out.println("A category already has that name.");
            editRangeName(range);
        } else {
            range.setTitle(newName);
        }
    }

    // REQUIRES: amount object not null
    // MODIFIES: amount
    // EFFECTS: processes user input for new value for amount
    private void editAmount(Amount amount) {
        System.out.println("Enter a new name for " + amount.getTitle());
        String newName = input.next();
        if (allNames().contains(newName.toLowerCase())) {
            System.out.println("A category already has that name.");
            editAmount(amount);
        } else {
            amount.setTitle(newName);
            System.out.println("Please input new value for " + amount.getTitle());
            int answer = input.nextInt();
            if (answer >= 0) {
                amount.setAmount(answer);
            } else {
                System.out.println("Answer not valid.");
                editAmount(amount);
            }
        }
    }

    // EFFECTS: saves current budget
    private void saveBudget() {
        try {
            jsonWriter.open();
            jsonWriter.write(currentBudget);
            jsonWriter.close();
            System.out.println("Saved current budget to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + JSON_STORE);
        }
    }

    // MODIFIES: currentBudget
    // EFFECTS: loads budget from json
    private void loadBudget() {
        try {
            currentBudget = jsonReader.read();
            System.out.println("Loaded current budget from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
    }

    // EFFECTS: outputs controls
    private void displayMenu() {
        showInfo();
        System.out.println("Controls: ");
        System.out.println("i: edit income");
        System.out.println("e: edit a spending category");
        System.out.println("a: add a new spending category");
        System.out.println("r: remove a spending category");
        System.out.println("s: view savings");
        System.out.println("f: save budget to file");
        System.out.println("l: load budget from file");
        System.out.println("q: quit");
    }

    // EFFECTS: outputs budget income, ranges, and amounts
    private void showInfo() {
        System.out.println("CURRENT BUDGET: ");
        System.out.println("Income: " + currentBudget.getIncome());
        if (currentBudget.getCategories().isEmpty()) {
            System.out.println("No categories created yet");
        } else {
            if (currentBudget.getRanges().isEmpty()) {
                System.out.println("No ranges created yet");
            } else {
                System.out.println("Ranges: ");
                for (Range r : currentBudget.getRanges()) {
                    System.out.println(r.getTitle() + ": " + r.getRange());
                }
            }
            if (currentBudget.getAmounts().isEmpty()) {
                System.out.println("No amounts created yet");
            } else {
                System.out.println("Amounts: ");
                for (Amount a : currentBudget.getAmounts()) {
                    System.out.println(a.getTitle() + ": " + a.getAmount());
                }
            }
        }
    }

    // EFFECTS: creates corresponding list of titles of every category
    private List<String> allNames() {
        List<String> names = new LinkedList<>();
        for (Category c : currentBudget.getCategories()) {
            names.add(c.getTitle().toLowerCase());
        }
        return names;
    }

    // EFFECTS: creates corresponding list of titles of every range
    private List<String> rangeNames() {
        List<String> names = new LinkedList<>();
        for (Range r : currentBudget.getRanges()) {
            names.add(r.getTitle().toLowerCase());
        }
        return names;
    }

    // EFFECTS: creates corresponding list of titles of every amount
    private List<String> amountNames() {
        List<String> names = new LinkedList<>();
        for (Amount a : currentBudget.getAmounts()) {
            names.add(a.getTitle().toLowerCase());
        }
        return names;
    }

    // EFFECTS: initializes budget and scanner
    private void init() {
        currentBudget = new Budget();
        input = new Scanner(System.in);
    }
}
