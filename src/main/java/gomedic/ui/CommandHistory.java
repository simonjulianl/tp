package gomedic.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import gomedic.commons.core.LogsCenter;

/**
 * Containers for all the commands that have been typed.
 */
public class CommandHistory {
    private final Logger logger = LogsCenter.getLogger(CommandHistory.class);
    private final List<String> historyOfCommands;
    private int pointer;

    /**
     * Initializes a new CommandHistory instance on initialization of the GoMedic app.
     */
    public CommandHistory() {
        historyOfCommands = new ArrayList<>();
        pointer = 0;
    }

    /**
     * Persists a user input command to history.
     *
     * @param command User input command.
     */
    public void addToHistory(String command) {
        requireNonNull(command);
        logger.info("new command added to history: " + command);
        historyOfCommands.add(command);
        pointer = historyOfCommands.size();
    }

    /**
     * Returns the previous command in the command storage stack.
     *
     * @return Command that has most recently been added to history that is not the current command.
     * @throws IndexOutOfBoundsException Throws an exception when index of pointer is invalid.
     */
    public String getPreviousCommand() throws IndexOutOfBoundsException {
        String command = historyOfCommands.get(--pointer);
        logger.info("retrieved previous command: " + command);
        return command;
    }

    /**
     * Returns the next command in the command storage stack.
     *
     * @return Command that is next on the stack.
     * @throws IndexOutOfBoundsException Throws an exception when index of pointer is invalid.
     */
    public String getNextCommand() throws IndexOutOfBoundsException {
        String command = historyOfCommands.get(++pointer);
        logger.info("retrieved next command: " + command);
        return command;
    }

    /**
     * Check whether this history has any next command.
     */
    public boolean hasNextCommand() {
        boolean hasNext = historyOfCommands.size() > 0 && pointer < historyOfCommands.size() - 1;
        boolean isPointerAtLastCommand = pointer == historyOfCommands.size() - 1;
        if (isPointerAtLastCommand) {
            pointer++;
        }

        return hasNext;
    }

    /**
     * Check whether this history has any prev command.
     */
    public boolean hasPreviousCommand() {
        return historyOfCommands.size() > 0 && pointer > 0;
    }
}
