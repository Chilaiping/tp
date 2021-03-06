package seedu.duke.commands;

import seedu.duke.data.ReadOnlyTransaction;
import seedu.duke.data.TransactionList;

public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Transaction deleted.";


    public DeleteCommand(int targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyTransaction target = getTargetTransaction();
            transactionList.removeTransaction(target);
            return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult("Out of index");
        } catch (TransactionList.TransactionNotFound transactionNotFound) {
            return new CommandResult("Transaction Not found");
        }
    }
}
