package gomedic.logic.commands.listcommand;

import static gomedic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static gomedic.logic.commands.CommandTestUtil.showDoctorAtIndex;
import static gomedic.testutil.TypicalIndexes.INDEX_FIRST;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;

public class ListDoctorCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListDoctorCommand(), model, ListDoctorCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showDoctorAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListDoctorCommand(), model, ListDoctorCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
