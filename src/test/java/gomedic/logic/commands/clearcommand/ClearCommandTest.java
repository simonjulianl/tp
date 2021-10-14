package gomedic.logic.commands.clearcommand;

import static gomedic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import gomedic.model.AddressBook;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
