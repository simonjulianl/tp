package gomedic.logic;

import java.nio.file.Path;

import gomedic.commons.core.GuiSettings;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.Model;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.activity.Activity;
import gomedic.model.person.Person;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of activities sorted by id */
    ObservableList<Activity> getFilteredActivityList();

    /**
     * Returns the user prefs' address root file path.
     */
    Path getAddressBookRootFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the integer showing current item being shown.
     * 0 -> activity
     * 1 -> person
     */
    ObservableValue<Integer> getModelBeingShown();
}
