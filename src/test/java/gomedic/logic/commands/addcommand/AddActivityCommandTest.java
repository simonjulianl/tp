package gomedic.logic.commands.addcommand;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalActivities.CONFLICTING_MEETING;
import static gomedic.testutil.TypicalActivities.MEETING;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.AddressBook;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.activity.Activity;
import gomedic.testutil.modelbuilder.ActivityBuilder;

class AddActivityCommandTest {

    @Test
    public void constructor_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddActivityCommand(null, null, null, null));
    }

    @Test
    public void execute_activityAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingActivityAdded modelStub = new ModelStubAcceptingActivityAdded();
        Activity validActivity = new ActivityBuilder().build();

        CommandResult commandResult = new AddActivityCommand(
                validActivity.getStartTime(),
                validActivity.getEndTime(),
                validActivity.getTitle(),
                validActivity.getDescription()
        ).execute(modelStub);

        assertEquals(String.format(
                        AddActivityCommand.MESSAGE_SUCCESS, validActivity),
                commandResult.getFeedbackToUser());
        assertEquals(List.of(validActivity), modelStub.activitiesAdded);
    }

    @Test
    public void execute_duplicateActivity_throwsCommandException() {
        Activity validActivity = new ActivityBuilder().build();
        CommandTestUtil.ModelStub modelStub = new ModelStubWithActivity(validActivity);

        AddActivityCommand addActivityCommand = new AddActivityCommand(
                validActivity.getStartTime(),
                validActivity.getEndTime(),
                validActivity.getTitle(),
                validActivity.getDescription()
        );

        assertThrows(CommandException.class,
                AddActivityCommand.MESSAGE_DUPLICATE_ACTIVITY, () -> addActivityCommand.execute(modelStub));
    }

    @Test
    public void execute_conflictingActivity_throwsCommandException() {
        Activity validActivity = new ActivityBuilder()
                .withId(10)
                .build();
        CommandTestUtil.ModelStub modelStub = new ModelStubWithActivity(validActivity);

        AddActivityCommand addActivityCommand = new AddActivityCommand(
                CONFLICTING_MEETING.getStartTime(),
                CONFLICTING_MEETING.getEndTime(),
                CONFLICTING_MEETING.getTitle(),
                CONFLICTING_MEETING.getDescription()
        );

        assertThrows(CommandException.class,
                AddActivityCommand.MESSAGE_CONFLICTING_ACTIVITY, () -> addActivityCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddActivityCommand addMeetingCommand = new AddActivityCommand(
                MEETING.getStartTime(),
                MEETING.getEndTime(),
                MEETING.getTitle(),
                MEETING.getDescription());

        AddActivityCommand anotherAddMeetingCommand = new AddActivityCommand(
                CONFLICTING_MEETING.getStartTime(),
                CONFLICTING_MEETING.getEndTime(),
                CONFLICTING_MEETING.getTitle(),
                CONFLICTING_MEETING.getDescription());

        // same object -> returns true
        assertEquals(addMeetingCommand, addMeetingCommand);

        // different types -> returns false
        assertNotEquals(1, addMeetingCommand);

        // null -> returns false
        assertNotEquals(null, addMeetingCommand);

        // different activities -> returns false
        assertNotEquals(addMeetingCommand, anotherAddMeetingCommand);
    }

    /**
     * A Model stub that contains a single activity.
     */
    private static class ModelStubWithActivity extends CommandTestUtil.ModelStub {
        private final Activity activity;
        private int counter = 1;

        ModelStubWithActivity(Activity activity) {
            requireNonNull(activity);
            this.activity = activity;
        }

        @Override
        public boolean hasActivity(Activity activity) {
            requireNonNull(activity);
            return this.activity.equals(activity);
        }

        @Override
        public boolean hasConflictingActivity(Activity activity) {
            requireNonNull(activity);
            return this.activity.isConflicting(activity);
        }

        @Override
        public int getNewActivityId() {
            return counter++;
        }
    }

    /**
     * A Model stub that always accept the activity being added.
     */
    private static class ModelStubAcceptingActivityAdded extends CommandTestUtil.ModelStub {
        final ArrayList<Activity> activitiesAdded = new ArrayList<>();
        private int counter = 1;

        @Override
        public boolean hasActivity(Activity activity) {
            requireNonNull(activity);
            return activitiesAdded.stream().anyMatch(activity::equals);
        }

        @Override
        public void addActivity(Activity activity) {
            requireNonNull(activity);
            activitiesAdded.add(activity);
        }

        @Override
        public boolean hasConflictingActivity(Activity activity) {
            requireNonNull(activity);
            return activitiesAdded.stream().anyMatch(activity::isConflicting);
        }

        @Override
        public int getNewActivityId() {
            return counter++;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
