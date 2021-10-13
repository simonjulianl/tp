package gomedic.logic.commands.addcommand;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalPersons.MAIN_PATIENT;
import static gomedic.testutil.TypicalPersons.OTHER_PATIENT;
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
import gomedic.model.commonfield.Id;
import gomedic.model.person.patient.Patient;
import gomedic.testutil.modelbuilder.PatientBuilder;

public class AddPatientCommandTest {

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new AddPatientCommand(null, null, null, null, null, null,
                null, null));
    }

    @Test
    public void execute_patientAcceptedByModel_addSuccessful() throws Exception {
        AddPatientCommandTest.ModelStubAcceptingPatientAdded modelStub =
            new AddPatientCommandTest.ModelStubAcceptingPatientAdded();
        Patient validPatient = new PatientBuilder().build();

        CommandResult commandResult = new AddPatientCommand(
            validPatient.getName(),
            validPatient.getPhone(),
            validPatient.getAge(),
            validPatient.getBloodType(),
            validPatient.getGender(),
            validPatient.getHeight(),
            validPatient.getWeight(),
            validPatient.getMedicalConditions()
        ).execute(modelStub);

        assertEquals(String.format(
            AddPatientCommand.MESSAGE_SUCCESS, validPatient),
            commandResult.getFeedbackToUser());
        assertEquals(List.of(validPatient), modelStub.patientsAdded);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient validPatient = new PatientBuilder().build();
        CommandTestUtil.ModelStub modelStub = new AddPatientCommandTest.ModelStubWithPatient(validPatient);

        AddPatientCommand addPatientCommand = new AddPatientCommand(
            validPatient.getName(),
            validPatient.getPhone(),
            validPatient.getAge(),
            validPatient.getBloodType(),
            validPatient.getGender(),
            validPatient.getHeight(),
            validPatient.getWeight(),
            validPatient.getMedicalConditions()
        );

        assertThrows(CommandException.class,
            AddPatientCommand.MESSAGE_DUPLICATE_PATIENT, () -> addPatientCommand.execute(modelStub));
    }

    @Test
    public void execute_addNewPatientIntoFullList_throwsCommandException() {
        Patient validPatient = new PatientBuilder().build();
        CommandTestUtil.ModelStub modelStub = new AddPatientCommandTest.ModelStubWithFullList();

        AddPatientCommand addPatientCommand = new AddPatientCommand(
            validPatient.getName(),
            validPatient.getPhone(),
            validPatient.getAge(),
            validPatient.getBloodType(),
            validPatient.getGender(),
            validPatient.getHeight(),
            validPatient.getWeight(),
            validPatient.getMedicalConditions()
        );

        assertThrows(CommandException.class,
            AddPatientCommand.MESSAGE_MAXIMUM_CAPACITY_EXCEEDED, () -> addPatientCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddPatientCommand addPatientCommand = new AddPatientCommand(
            MAIN_PATIENT.getName(),
            MAIN_PATIENT.getPhone(),
            MAIN_PATIENT.getAge(),
            MAIN_PATIENT.getBloodType(),
            MAIN_PATIENT.getGender(),
            MAIN_PATIENT.getHeight(),
            MAIN_PATIENT.getWeight(),
            MAIN_PATIENT.getMedicalConditions());

        AddPatientCommand anotherAddPatientCommand = new AddPatientCommand(
            OTHER_PATIENT.getName(),
            OTHER_PATIENT.getPhone(),
            OTHER_PATIENT.getAge(),
            OTHER_PATIENT.getBloodType(),
            OTHER_PATIENT.getGender(),
            OTHER_PATIENT.getHeight(),
            OTHER_PATIENT.getWeight(),
            OTHER_PATIENT.getMedicalConditions());

        // same object -> returns true
        assertEquals(addPatientCommand, addPatientCommand);

        // different types -> returns false
        assertNotEquals(1, addPatientCommand);

        // null -> returns false
        assertNotEquals(null, addPatientCommand);

        // different patients -> returns false
        assertNotEquals(addPatientCommand, anotherAddPatientCommand);
    }


    /**
     * A Model stub that contains a single patient.
     */
    private static class ModelStubWithPatient extends CommandTestUtil.ModelStub {
        private final Patient patient;
        private int counter = 1;

        ModelStubWithPatient(Patient patient) {
            requireNonNull(patient);
            this.patient = patient;
        }

        @Override
        public boolean hasPatient(Patient patient) {
            requireNonNull(patient);
            return this.patient.equals(patient);
        }

        @Override
        public boolean hasNewPatientId() {
            return true;
        }

        @Override
        public int getNewPatientId() {
            return counter++;
        }
    }

    /**
     * A Model stub that always accept the patient being added.
     */
    private static class ModelStubAcceptingPatientAdded extends CommandTestUtil.ModelStub {
        final ArrayList<Patient> patientsAdded = new ArrayList<>();
        private int counter = 1;

        @Override
        public boolean hasPatient(Patient patient) {
            requireNonNull(patient);
            return patientsAdded.stream().anyMatch(patient::equals);
        }

        @Override
        public void addPatient(Patient patient) {
            requireNonNull(patient);
            patientsAdded.add(patient);
        }

        @Override
        public boolean hasNewPatientId() {
            return patientsAdded.size() <= Id.MAXIMUM_ASSIGNABLE_IDS;
        }

        @Override
        public int getNewPatientId() {
            return counter++;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the patient being added.
     */
    private static class ModelStubWithFullList extends CommandTestUtil.ModelStub {

        // Returns false to simulate the fact that list is already filled to max
        @Override
        public boolean hasNewPatientId() {
            return false;
        }

        @Override
        public int getNewPatientId() {
            return 2;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
