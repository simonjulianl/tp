package gomedic.logic.commands.addcommand;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalActivities.CONFLICTING_MEETING;
import static gomedic.testutil.TypicalActivities.MEETING;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.GuiSettings;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.AddressBook;
import gomedic.model.Model;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.ReadOnlyUserPrefs;
import gomedic.model.activity.Activity;
import gomedic.model.person.Person;
import gomedic.testutil.modelbuilder.ActivityBuilder;
import javafx.collections.ObservableList;

class AddActivityCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
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
        ModelStub modelStub = new ModelStubWithActivity(validActivity);

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
        ModelStub modelStub = new ModelStubWithActivity(validActivity);

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
     * A default model stub that have all the methods failing.
     */
    private static class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookDataRootFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookDataRootFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addActivity(Activity activity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getNewActivityId() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasActivity(Activity activity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasConflictingActivity(Activity activity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Activity> getFilteredActivityList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<? super Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single activity.
     */
    private static class ModelStubWithActivity extends AddActivityCommandTest.ModelStub {
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
    private static class ModelStubAcceptingActivityAdded extends AddActivityCommandTest.ModelStub {
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
