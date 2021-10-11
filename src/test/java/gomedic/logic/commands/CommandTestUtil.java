package gomedic.logic.commands;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import gomedic.commons.core.GuiSettings;
import gomedic.commons.core.index.Index;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.logic.parser.CliSyntax;
import gomedic.model.AddressBook;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.ReadOnlyUserPrefs;
import gomedic.model.activity.Activity;
import gomedic.model.activity.ActivityId;
import gomedic.model.person.Person;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.doctor.DoctorId;
import gomedic.model.person.patient.Patient;
import gomedic.model.person.patient.PatientId;
import gomedic.model.util.NameContainsKeywordsPredicate;
import gomedic.testutil.EditPersonDescriptorBuilder;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    /* valid constants declarations for name */
    public static final String NAME_DESC_AMY = " " + CliSyntax.PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + CliSyntax.PREFIX_NAME + VALID_NAME_BOB;

    /* valid constants declarations for phone */
    public static final String PHONE_DESC_AMY = " " + CliSyntax.PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + CliSyntax.PREFIX_PHONE + VALID_PHONE_BOB;

    /* valid constants declarations for email */
    public static final String EMAIL_DESC_AMY = " " + CliSyntax.PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + CliSyntax.PREFIX_EMAIL + VALID_EMAIL_BOB;

    /* valid constants declarations for address */
    public static final String ADDRESS_DESC_AMY = " " + CliSyntax.PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + CliSyntax.PREFIX_ADDRESS + VALID_ADDRESS_BOB;

    /* valid constants declarations for tags */
    public static final String TAG_DESC_FRIEND = " " + CliSyntax.PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + CliSyntax.PREFIX_TAG + VALID_TAG_HUSBAND;

    /* invalid constants declarations for common fields */
    public static final String INVALID_NAME_DESC = " " + CliSyntax.PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + CliSyntax.PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + CliSyntax.PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + CliSyntax.PREFIX_ADDRESS;
    // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + CliSyntax.PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /* valid constants declarations for doctor related fields */
    public static final String VALID_DESC_NAME_MAIN_DOCTOR = " " + CliSyntax.PREFIX_NAME + "John Doe";
    public static final String VALID_DESC_NAME_OTHER_DOCTOR = " " + CliSyntax.PREFIX_NAME + "Smith John";
    public static final String VALID_DESC_PHONE_MAIN_DOCTOR = " " + CliSyntax.PREFIX_PHONE + "85355255";
    public static final String VALID_DESC_PHONE_OTHER_DOCTOR = " " + CliSyntax.PREFIX_PHONE + "77777777";
    public static final String VALID_DESC_DEPARTMENT_MAIN_DOCTOR = " " + CliSyntax.PREFIX_DEPARTMENT + "Pediatrics";
    public static final String VALID_DESC_DEPARTMENT_OTHER_DOCTOR = " " + CliSyntax.PREFIX_DEPARTMENT + "ENT";

    /* invalid constants declarations for doctor related fields */
    public static final String INVALID_DESC_NAME_MAIN_DOCTOR = " " + CliSyntax.PREFIX_NAME + "John** Doe";
    public static final String INVALID_DESC_PHONE_MAIN_DOCTOR = " " + CliSyntax.PREFIX_PHONE + "not a number";
    public static final String INVALID_DESC_DEPARTMENT_MAIN_DOCTOR = " " + CliSyntax.PREFIX_DEPARTMENT + "Cardi**ology";

    /* valid constants declarations for patient related fields */
    public static final String VALID_DESC_NAME_MAIN_PATIENT = " " + CliSyntax.PREFIX_NAME + "Dohn Joe";
    public static final String VALID_DESC_NAME_OTHER_PATIENT = " " + CliSyntax.PREFIX_NAME + "Smith John";
    public static final String VALID_DESC_PHONE_MAIN_PATIENT = " " + CliSyntax.PREFIX_PHONE + "12345678";
    public static final String VALID_DESC_PHONE_OTHER_PATIENT = " " + CliSyntax.PREFIX_PHONE + "77777777";
    public static final String VALID_DESC_AGE_MAIN_PATIENT = " " + CliSyntax.PREFIX_AGE + "40";
    public static final String VALID_DESC_AGE_OTHER_PATIENT = " " + CliSyntax.PREFIX_AGE + "37";
    public static final String VALID_DESC_BLOODTYPE_MAIN_PATIENT = " " + CliSyntax.PREFIX_BLOODTYPE + "AB";
    public static final String VALID_DESC_BLOODTYPE_OTHER_PATIENT = " " + CliSyntax.PREFIX_BLOODTYPE + "B";
    public static final String VALID_DESC_GENDER_MAIN_PATIENT = " " + CliSyntax.PREFIX_GENDER + "M";
    public static final String VALID_DESC_GENDER_OTHER_PATIENT = " " + CliSyntax.PREFIX_GENDER + "M";
    public static final String VALID_DESC_HEIGHT_MAIN_PATIENT = " " + CliSyntax.PREFIX_HEIGHT + "176";
    public static final String VALID_DESC_HEIGHT_OTHER_PATIENT = " " + CliSyntax.PREFIX_HEIGHT + "186";
    public static final String VALID_DESC_WEIGHT_MAIN_PATIENT = " " + CliSyntax.PREFIX_WEIGHT + "86";
    public static final String VALID_DESC_WEIGHT_OTHER_PATIENT = " " + CliSyntax.PREFIX_WEIGHT + "77";
    public static final String VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT = " " + CliSyntax.PREFIX_MEDICALCONDITIONS
            + "heart failure";
    public static final String VALID_DESC_MEDICALCONDITIONS_OTHER_PATIENT = " " + CliSyntax.PREFIX_MEDICALCONDITIONS
            + "diabetes";

    /* invalid constants declarations for patient related fields */
    public static final String INVALID_DESC_NAME_MAIN_PATIENT = " " + CliSyntax.PREFIX_NAME + "John** Doe";
    public static final String INVALID_DESC_PHONE_MAIN_PATIENT = " " + CliSyntax.PREFIX_PHONE + "not a number";
    public static final String INVALID_DESC_AGE_MAIN_PATIENT = " " + CliSyntax.PREFIX_AGE + "not a number";
    public static final String INVALID_DESC_BLOODTYPE_MAIN_PATIENT = " " + CliSyntax.PREFIX_BLOODTYPE + "invalid";
    public static final String INVALID_DESC_GENDER_MAIN_PATIENT = " " + CliSyntax.PREFIX_GENDER + "invalid";
    public static final String INVALID_DESC_HEIGHT_MAIN_PATIENT = " " + CliSyntax.PREFIX_HEIGHT + "invalid";
    public static final String INVALID_DESC_WEIGHT_MAIN_PATIENT = " " + CliSyntax.PREFIX_WEIGHT + "invalid";
    public static final String INVALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT = " " + CliSyntax.PREFIX_MEDICALCONDITIONS
            + "invalid**";

    /* valid constants declarations for activity related fields */
    public static final String VALID_DESC_TITLE_MEETING =
            " " + CliSyntax.PREFIX_TITLE + "Meeting me";
    public static final String VALID_DESC_TITLE_PAPER_REVIEW =
            " " + CliSyntax.PREFIX_TITLE + "Paper Review";

    public static final String VALID_DESC_START_TIME_MEETING =
            " " + CliSyntax.PREFIX_START_TIME + "15-09-2022 13:00";
    public static final String VALID_DESC_END_TIME_MEETING =
            " " + CliSyntax.PREFIX_END_TIME + "15-09-2022 15:00";

    public static final String VALID_DESC_START_TIME_PAPER_REVIEW =
            " " + CliSyntax.PREFIX_START_TIME + "16/07/2000 15:00";
    public static final String VALID_DESC_END_TIME_PAPER_REVIEW =
            " " + CliSyntax.PREFIX_END_TIME + "16/07/2000 16:00";

    public static final String VALID_DESC_MEETING_DESCRIPTION =
            " " + CliSyntax.PREFIX_DESCRIPTION + "today at somewhere";

    /* invalid constants declarations for activity related fields */
    public static final String INVALID_DESC_START_TIME_MEETING =
            " " + CliSyntax.PREFIX_START_TIME + "15-13-2022 13:00";
    public static final String INVALID_DESC_END_TIME_MEETING =
            " " + CliSyntax.PREFIX_END_TIME + "15/09/2022 15-00";

    public static final String INVALID_DESC_TITLE_MEETING =
            " " + CliSyntax.PREFIX_TITLE + "MEETING".repeat(100);
    public static final String INVALID_DESC_TITLE_PAPER_REVIEW =
            " " + CliSyntax.PREFIX_TITLE + "PAPER REVIEW".repeat(100);

    public static final String INVALID_DESC_DESCRIPTION =
            " " + CliSyntax.PREFIX_DESCRIPTION + "SOME LONG DESCRIPTION".repeat(1000);

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);

            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(List.of(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the doctors at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showDoctorAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDoctorList().size());

        Doctor doctor = model.getFilteredDoctorList().get(targetIndex.getZeroBased());
        final DoctorId did = new DoctorId(doctor.getId().getIdNumber());
        model.updateFilteredDoctorList(doctor1 -> doctor1.getId().equals(did));

        assertEquals(1, model.getFilteredDoctorList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the patients at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPatientAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPatientList().size());

        Patient patient = model.getFilteredPatientList().get(targetIndex.getZeroBased());
        final PatientId pid = new PatientId(patient.getId().getIdNumber());
        model.updateFilteredPatientList(patient1 -> patient1.getId().equals(pid));

        assertEquals(1, model.getFilteredPatientList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the activity at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showActivityAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredActivityList().size());

        Activity activity = model.getFilteredActivityList().get(targetIndex.getZeroBased());
        final ActivityId aid = activity.getActivityId();
        model.updateFilteredActivitiesList(activity1 -> activity1.getActivityId().equals(aid));

        assertEquals(1, model.getFilteredActivityList().size());
    }

    /**
     * A default model stub that have all the methods failing.
     */
    public static class ModelStub implements Model {
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
        public void addDoctor(Doctor doctor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
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
        public boolean hasNewDoctorId() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getNewDoctorId() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasNewPatientId() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getNewPatientId() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasActivity(Activity activity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteActivity(Activity target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePatient(Patient target) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public boolean hasDoctor(Doctor doctor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDoctor(Doctor target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatient(Patient patient) {
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
        public ObservableList<Doctor> getFilteredDoctorList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Patient> getFilteredPatientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<? super Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDoctorList(Predicate<? super Doctor> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPatientList(Predicate<? super Patient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredActivitiesList(Predicate<? super Activity> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableValue<Integer> getModelBeingShown() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModelBeingShown(ModelItem modelItem) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
