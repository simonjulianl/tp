package gomedic.logic.commands.addcommand;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalActivities.APPOINTMENT;
import static gomedic.testutil.TypicalActivities.CONFLICTING_APPOINTMENT;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.model.AddressBook;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.activity.Activity;
import gomedic.model.person.patient.Patient;
import gomedic.testutil.TypicalPersons;
import gomedic.testutil.modelbuilder.ActivityBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        public ObservableList<Patient> getFilteredPatientList() {
            ObservableList<Patient> dummyList = FXCollections.observableArrayList();
            dummyList.add(TypicalPersons.MAIN_PATIENT);
            return dummyList;
        }

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
