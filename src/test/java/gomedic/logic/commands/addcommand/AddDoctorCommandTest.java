package gomedic.logic.commands.addcommand;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalPersons.MAIN_DOCTOR;
import static gomedic.testutil.TypicalPersons.OTHER_DOCTOR;
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
import gomedic.model.person.doctor.Doctor;
import gomedic.testutil.modelbuilder.DoctorBuilder;

public class AddDoctorCommandTest {

    @Test
    public void constructor_nullDoctor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddDoctorCommand(null, null, null));
    }

    @Test
    public void execute_doctorAcceptedByModel_addSuccessful() throws Exception {
        AddDoctorCommandTest.ModelStubAcceptingDoctorAdded modelStub =
                new AddDoctorCommandTest.ModelStubAcceptingDoctorAdded();
        Doctor validDoctor = new DoctorBuilder().build();

        CommandResult commandResult = new AddDoctorCommand(
                validDoctor.getName(),
                validDoctor.getPhone(),
                validDoctor.getDepartment()
        ).execute(modelStub);

        assertEquals(String.format(
                AddDoctorCommand.MESSAGE_SUCCESS, validDoctor),
                commandResult.getFeedbackToUser());
        assertEquals(List.of(validDoctor), modelStub.doctorsAdded);
    }

    @Test
    public void execute_duplicateDoctor_throwsCommandException() {
        Doctor validDoctor = new DoctorBuilder().build();
        CommandTestUtil.ModelStub modelStub = new AddDoctorCommandTest.ModelStubWithDoctor(validDoctor);

        AddDoctorCommand addDoctorCommand = new AddDoctorCommand(
                validDoctor.getName(),
                validDoctor.getPhone(),
                validDoctor.getDepartment()
        );

        assertThrows(CommandException.class,
                AddDoctorCommand.MESSAGE_DUPLICATE_DOCTOR, () -> addDoctorCommand.execute(modelStub));
    }

    @Test
    public void execute_addNewDoctorIntoFullList_throwsCommandException() {
        Doctor validDoctor = new DoctorBuilder().build();
        CommandTestUtil.ModelStub modelStub = new AddDoctorCommandTest.ModelStubWithFullList();

        AddDoctorCommand addDoctorCommand = new AddDoctorCommand(
                validDoctor.getName(),
                validDoctor.getPhone(),
                validDoctor.getDepartment()
        );

        assertThrows(CommandException.class,
                AddDoctorCommand.MESSAGE_MAXIMUM_CAPACITY_EXCEEDED, () -> addDoctorCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddDoctorCommand addDoctorCommand = new AddDoctorCommand(
                MAIN_DOCTOR.getName(),
                MAIN_DOCTOR.getPhone(),
                MAIN_DOCTOR.getDepartment());

        AddDoctorCommand anotherAddDoctorCommand = new AddDoctorCommand(
                OTHER_DOCTOR.getName(),
                OTHER_DOCTOR.getPhone(),
                OTHER_DOCTOR.getDepartment());

        // same object -> returns true
        assertEquals(addDoctorCommand, addDoctorCommand);

        // different types -> returns false
        assertNotEquals(1, addDoctorCommand);

        // null -> returns false
        assertNotEquals(null, addDoctorCommand);

        // different doctors -> returns false
        assertNotEquals(addDoctorCommand, anotherAddDoctorCommand);
    }


    /**
     * A Model stub that contains a single doctor.
     */
    private static class ModelStubWithDoctor extends CommandTestUtil.ModelStub {
        private final Doctor doctor;
        private int counter = 1;

        ModelStubWithDoctor(Doctor doctor) {
            requireNonNull(doctor);
            this.doctor = doctor;
        }

        @Override
        public boolean hasDoctor(Doctor doctor) {
            requireNonNull(doctor);
            return this.doctor.equals(doctor);
        }

        @Override
        public boolean hasNewDoctorId() {
            return true;
        }

        @Override
        public int getNewDoctorId() {
            return counter++;
        }
    }

    /**
     * A Model stub that always accept the doctor being added.
     */
    private static class ModelStubAcceptingDoctorAdded extends CommandTestUtil.ModelStub {
        final ArrayList<Doctor> doctorsAdded = new ArrayList<>();
        private int counter = 1;

        @Override
        public boolean hasDoctor(Doctor doctor) {
            requireNonNull(doctor);
            return doctorsAdded.stream().anyMatch(doctor::equals);
        }

        @Override
        public void addDoctor(Doctor doctor) {
            requireNonNull(doctor);
            doctorsAdded.add(doctor);
        }

        @Override
        public boolean hasNewDoctorId() {
            return doctorsAdded.size() <= Id.MAXIMUM_ASSIGNABLE_IDS;
        }

        @Override
        public int getNewDoctorId() {
            return counter++;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the doctor being added.
     */
    private static class ModelStubWithFullList extends CommandTestUtil.ModelStub {

        // Returns false to simulate the fact that list is already filled to max
        @Override
        public boolean hasNewDoctorId() {
            return false;
        }

        @Override
        public int getNewDoctorId() {
            return 2;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
