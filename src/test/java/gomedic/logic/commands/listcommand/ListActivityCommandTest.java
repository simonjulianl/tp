package gomedic.logic.commands.listcommand;

import static gomedic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static gomedic.logic.commands.CommandTestUtil.showActivityAtIndex;
import static gomedic.testutil.TypicalIndexes.INDEX_FIRST;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gomedic.logic.commands.Command;
import gomedic.logic.commands.exceptions.CommandException;
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
        assertCommandSuccess(new ListActivityCommand(ListActivityCommand.Sort.ID,
                        ListActivityCommand.Period.ALL), model,
                ListActivityCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showActivityAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListActivityCommand(ListActivityCommand.Sort.ID,
                        ListActivityCommand.Period.ALL), model,
                ListActivityCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFilteredByStartTime_showsEverything() throws CommandException {
        showActivityAtIndex(model, INDEX_FIRST);
        Command command = new ListActivityCommand(ListActivityCommand.Sort.START,
                ListActivityCommand.Period.ALL);
        assertDoesNotThrow(() -> command.execute(model));
    }

    @Test
    public void execute_listIsFilteredById_showsEverything() {
        showActivityAtIndex(model, INDEX_FIRST);
        Command command = new ListActivityCommand(ListActivityCommand.Sort.START,
                ListActivityCommand.Period.ALL);
        Command finalCommand = command;
        assertDoesNotThrow(() -> finalCommand.execute(model));

        command = new ListActivityCommand(ListActivityCommand.Sort.START,
                ListActivityCommand.Period.TODAY);
        Command finalCommand1 = command;
        assertDoesNotThrow(() -> finalCommand1.execute(model));

        command = new ListActivityCommand(ListActivityCommand.Sort.START,
                ListActivityCommand.Period.WEEK);
        Command finalCommand2 = command;
        assertDoesNotThrow(() -> finalCommand2.execute(model));

        command = new ListActivityCommand(ListActivityCommand.Sort.START,
                ListActivityCommand.Period.MONTH);
        Command finalCommand3 = command;
        assertDoesNotThrow(() -> finalCommand3.execute(model));


        command = new ListActivityCommand(ListActivityCommand.Sort.START,
                ListActivityCommand.Period.YEAR);
        Command finalCommand4 = command;
        assertDoesNotThrow(() -> finalCommand3.execute(model));
    }
}
