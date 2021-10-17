package gomedic.logic.commands.deletecommand;

import static gomedic.testutil.TypicalIndexes.INDEX_FIRST;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;
import gomedic.model.activity.Activity;
import gomedic.model.activity.ActivityId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteActivityCommand}.
 */
public class DeleteActivityCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Activity activityToDelete = model.getFilteredActivityListById().get(INDEX_FIRST.getZeroBased());
        DeleteActivityCommand deleteActivityCommand = new DeleteActivityCommand(activityToDelete.getActivityId());

        String expectedMessage = String.format(DeleteActivityCommand.MESSAGE_DELETE_ACTIVITY_SUCCESS, activityToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteActivity(activityToDelete);

        CommandTestUtil.assertCommandSuccess(deleteActivityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        DeleteActivityCommand deleteActivityCommand = new DeleteActivityCommand(new ActivityId(999));

        CommandTestUtil.assertCommandFailure(deleteActivityCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_ID);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showActivityAtIndex(model, INDEX_FIRST);

        Activity activityToDelete = model.getFilteredActivityListById().get(INDEX_FIRST.getZeroBased());
        DeleteActivityCommand deleteActivityCommand = new DeleteActivityCommand(activityToDelete.getActivityId());

        String expectedMessage = String.format(DeleteActivityCommand.MESSAGE_DELETE_ACTIVITY_SUCCESS, activityToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteActivity(activityToDelete);
        showNoActivity(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteActivityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteActivityCommand deleteFirstCommand = new DeleteActivityCommand(new ActivityId(999));

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same id -> returns true
        DeleteActivityCommand deleteFirstCommandCopy = new DeleteActivityCommand(new ActivityId(999));
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoActivity(Model model) {
        model.updateFilteredActivitiesList(p -> false);

        assertTrue(model.getFilteredActivityListById().isEmpty());
    }
}
