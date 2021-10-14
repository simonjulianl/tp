package gomedic.logic.commands.listcommand;

import static gomedic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static gomedic.logic.commands.CommandTestUtil.showActivityAtIndex;
import static gomedic.testutil.TypicalIndexes.INDEX_FIRST;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;

class ListActivityCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListActivityCommand(), model, ListActivityCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showActivityAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListActivityCommand(), model, ListActivityCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
