package seedu.duke;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.duke.commands.Command;
import seedu.duke.commands.CommandResult;
import seedu.duke.commands.ExitCommand;

import seedu.duke.data.ReadOnlyTransaction;
import seedu.duke.data.TransactionList;
import seedu.duke.utilities.Parser;
import seedu.duke.utilities.Storage;
import seedu.duke.utilities.Ui;


public class NusExpenses {

    // Version info of the program.
    public static final String VERSION = "NUS Expenses Tracker - Version 1.0";

    private Ui ui;
    //private Storage storage;
    private TransactionList transactionList;

    //Expenses list shown to the user recently.
    private List<? extends ReadOnlyTransaction> lastShownList = Collections.emptyList();


    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String... args) {

        //Start the Expense Tracker.
        new NusExpenses().run();

    }


    public void run() {
        start();
        runLoopTillIssuedExit();
        exit();
    }

    //Setup and prints the welcome message.
    private void start() {
        this.ui = new Ui();
        this.transactionList = new TransactionList();
        ui.showWelcomeMessage(VERSION);
    }

    //Prints the Goodbye messages and exits.
    private void exit() {
        ui.showGoodbyeMessage();
        System.exit(0);
    }

    private void runLoopTillIssuedExit() {
        Command command;
        do {
            String userCommandText = ui.readUserCommand();
            command = new Parser().parseCommand(userCommandText);
            CommandResult result = executeCommand(command);
            recordResult(result);
            ui.showResultToUser(result);

        } while (!ExitCommand.isExit(command));
    }

    private void recordResult(CommandResult result) {
        final Optional<List<? extends ReadOnlyTransaction>> transactionList = result.getRelevantTransactions();
        if (transactionList.isPresent()) {
            lastShownList = transactionList.get();
        }
    }

    private CommandResult executeCommand(Command command) {
        try {
            command.setData(transactionList, lastShownList);
            CommandResult result = command.execute();
            return result;
        } catch (Exception e) {
            ui.showToUser("An error has occurred! Please reach out to Proj Team @https://ay2021s1-tic4001-4.github.io/tp/AboutUs.html");
            ui.showToUser(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}