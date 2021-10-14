package gomedic.logic.commands.editcommand;

import static gomedic.logic.commands.CommandTestUtil.DESC_MEETING;
import static gomedic.logic.commands.CommandTestUtil.DESC_PAPER_REVIEW;
import static gomedic.logic.commands.CommandTestUtil.assertCommandFailure;
import static gomedic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static gomedic.logic.commands.CommandTestUtil.showActivityAtIndex;
import static gomedic.testutil.TypicalActivities.MEETING;
import static gomedic.testutil.TypicalActivities.PAPER_REVIEW;
import static gomedic.testutil.TypicalIndexes.INDEX_FIRST;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.commons.core.index.Index;
import gomedic.logic.commands.clearcommand.ClearCommand;
import gomedic.model.AddressBook;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;
import gomedic.model.activity.Activity;
import gomedic.model.activity.ActivityId;
import gomedic.model.commonfield.Id;
import gomedic.testutil.editdescriptorbuilder.EditActivityDescriptorBuilder;
import gomedic.testutil.modelbuilder.ActivityBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditActivityCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Activity editedActivity =
                new ActivityBuilder()
                        .withStartTime("15/07/2000 15:00")
                        .withEndTime("15/07/2000 16:00")
                        .withTitle("just a title")
                        .withDescription("some descriptions")
                        .build();
        EditActivityCommand.EditActivityDescriptor descriptor =
                new EditActivityDescriptorBuilder(editedActivity).build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(editedActivity.getActivityId(), descriptor);

        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setActivity(model.getFilteredActivityList().get(0), editedActivity);

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someConflictingTiming_throwsConflictingTiming() {
        Index indexLastActivity = Index.fromOneBased(model.getFilteredActivityList().size());
        Activity lastActivity = model.getFilteredActivityList().get(indexLastActivity.getZeroBased());
        Id targetId = lastActivity.getActivityId();

        EditActivityCommand.EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder()
                .withStartTime(MEETING.getStartTime().toString())
                .withEndTime(MEETING.getEndTime().toString())
                .build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(targetId, descriptor);

        assertCommandFailure(editActivityCommand, model, Messages.MESSAGE_CONFLICTING_ACTIVITY);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Activity editedActivity = model.getFilteredActivityList().get(INDEX_FIRST.getZeroBased());
        Id targetId = editedActivity.getActivityId();
        EditActivityCommand editActivityCommand =
                new EditActivityCommand(targetId, new EditActivityCommand.EditActivityDescriptor());


        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showActivityAtIndex(model, INDEX_FIRST);

        Activity activityInFilteredList = model.getFilteredActivityList().get(INDEX_FIRST.getZeroBased());
        Activity editedActivity = new ActivityBuilder()
                .withId(activityInFilteredList.getActivityId().getIdNumber())
                .withTitle(activityInFilteredList.getTitle().toString())
                .withStartTime(activityInFilteredList.getStartTime().toString())
                .withEndTime(activityInFilteredList.getEndTime().toString())
                .withDescription(PAPER_REVIEW.getDescription().toString()).build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(activityInFilteredList.getActivityId(),
                new EditActivityDescriptorBuilder()
                        .withDescription(PAPER_REVIEW.getDescription().toString())
                        .build());

        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setActivity(model.getFilteredActivityList().get(0), editedActivity);

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidDoctorIndexUnfilteredList_failure() {
        // Test with doctor id of 999 since it is not in typical address book
        Id invalidId = new ActivityId(999);
        EditActivityCommand.EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder()
                .withTitle(MEETING.getTitle().toString())
                .build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(invalidId, descriptor);

        assertCommandFailure(editActivityCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_ID);
    }

    @Test
    public void equals() {
        // Use the details of first doctor in typical doctors list as the source of comparison
        final EditActivityCommand standardCommand =
                new EditActivityCommand(MEETING.getActivityId(), DESC_MEETING);

        // same values -> returns true
        EditActivityCommand.EditActivityDescriptor copyDescriptor =
                new EditActivityCommand.EditActivityDescriptor(DESC_MEETING);
        EditActivityCommand commandWithSameValues =
                new EditActivityCommand(MEETING.getActivityId(), copyDescriptor);
        assertEquals(commandWithSameValues, standardCommand);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);

        // different types -> returns false
        assertNotEquals(new ClearCommand(), standardCommand);

        // different index -> returns false
        assertNotEquals(new EditActivityCommand(PAPER_REVIEW.getActivityId(), DESC_MEETING), standardCommand);

        // different descriptor -> returns false
        assertNotEquals(new EditActivityCommand(MEETING.getActivityId(), DESC_PAPER_REVIEW), standardCommand);
    }

}
