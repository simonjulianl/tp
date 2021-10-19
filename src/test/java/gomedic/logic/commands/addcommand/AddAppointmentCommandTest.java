package gomedic.logic.commands.addcommand;

import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.model.AddressBook;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.activity.Activity;
import gomedic.testutil.modelbuilder.ActivityBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalActivities.CONFLICTING_APPOINTMENT;
import static gomedic.testutil.TypicalActivities.APPOINTMENT;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AddAppointmentCommandTest {

    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddAppointmentCommand(null, null, null, null, null));
    }

    @Test
    public void execute_activityAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingActivityAdded modelStub = new ModelStubAcceptingActivityAdded();
        Activity validAppointment = new ActivityBuilder().withPatientId(1).build();

        CommandResult commandResult = new AddAppointmentCommand(
                validAppointment.getPatientId(),
                validAppointment.getStartTime(),
                validAppointment.getEndTime(),
                validAppointment.getTitle(),
                validAppointment.getDescription()
        ).execute(modelStub);

        assertEquals(String.format(
                        AddAppointmentCommand.MESSAGE_SUCCESS, validAppointment),
                commandResult.getFeedbackToUser());
        assertEquals(List.of(validAppointment), modelStub.activitiesAdded);
    }

    @Test
    public void equals() {
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(
                APPOINTMENT.getPatientId(),
                APPOINTMENT.getStartTime(),
                APPOINTMENT.getEndTime(),
                APPOINTMENT.getTitle(),
                APPOINTMENT.getDescription());

        AddAppointmentCommand anotherAddAppointmentCommand = new AddAppointmentCommand(
                CONFLICTING_APPOINTMENT.getPatientId(),
                CONFLICTING_APPOINTMENT.getStartTime(),
                CONFLICTING_APPOINTMENT.getEndTime(),
                CONFLICTING_APPOINTMENT.getTitle(),
                CONFLICTING_APPOINTMENT.getDescription());

        // same object -> returns true
        assertEquals(addAppointmentCommand, addAppointmentCommand);

        // different types -> returns false
        assertNotEquals(1, addAppointmentCommand);

        // null -> returns false
        assertNotEquals(null, addAppointmentCommand);

        // different activities -> returns false
        assertNotEquals(addAppointmentCommand, anotherAddAppointmentCommand);
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
