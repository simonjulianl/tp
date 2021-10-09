package gomedic.logic.commands;

import static gomedic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static gomedic.logic.commands.CommandTestUtil.showPersonAtIndex;
import static gomedic.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gomedic.logic.commands.listcommand.ListPersonCommand;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListPersonCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListPersonCommand(), model, ListPersonCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListPersonCommand(), model, ListPersonCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
