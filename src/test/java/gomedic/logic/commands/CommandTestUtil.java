package gomedic.logic.commands;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalActivities.MEETING;
import static gomedic.testutil.TypicalActivities.PAPER_REVIEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import gomedic.commons.core.GuiSettings;
import gomedic.commons.core.index.Index;
import gomedic.logic.commands.editcommand.EditActivityCommand;
import gomedic.logic.commands.editcommand.EditDoctorCommand;
import gomedic.logic.commands.editcommand.EditPatientCommand;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.logic.parser.CliSyntax;
import gomedic.model.AddressBook;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.ReadOnlyUserPrefs;
import gomedic.model.activity.Activity;
import gomedic.model.activity.ActivityId;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.doctor.DoctorId;
import gomedic.model.person.patient.Patient;
import gomedic.model.person.patient.PatientId;
import gomedic.model.userprofile.UserProfile;
import gomedic.testutil.TypicalPersons;
import gomedic.testutil.editdescriptorbuilder.EditActivityDescriptorBuilder;
import gomedic.testutil.editdescriptorbuilder.EditDoctorDescriptorBuilder;
import gomedic.testutil.editdescriptorbuilder.EditPatientDescriptorBuilder;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    /* invalid constants declarations for common fields */
    public static final String INVALID_NAME_DESC = " " + CliSyntax.PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + CliSyntax.PREFIX_PHONE + "911a"; // 'a' not allowed in phones

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

    /* valid constant declarations for user profile related fields */
    public static final String VALID_DESC_NAME_MAIN_PROFILE = " " + CliSyntax.PREFIX_NAME + "John Smith";
    public static final String VALID_DESC_POSITION_MAIN_PROFILE = " " + CliSyntax.PREFIX_POSITION + "Senior Resident";
    public static final String VALID_DESC_DEPARTMENT_MAIN_PROFILE = " " + CliSyntax.PREFIX_DEPARTMENT + "Cardiology";
    public static final String VALID_DESC_ORGANIZATION_MAIN_PROFILE = " " + CliSyntax.PREFIX_ORGANIZATION + "NUH";
    public static final String VALID_DESC_NAME_OTHER_PROFILE = " " + CliSyntax.PREFIX_NAME + "new name";
    public static final String VALID_DESC_POSITION_OTHER_PROFILE = " " + CliSyntax.PREFIX_POSITION + "new position";
    public static final String VALID_DESC_DEPARTMENT_OTHER_PROFILE =
            " " + CliSyntax.PREFIX_DEPARTMENT + "new department";
    public static final String VALID_DESC_ORGANIZATION_OTHER_PROFILE =
            " " + CliSyntax.PREFIX_ORGANIZATION + "new organization";

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
    public static final String VALID_DESC_BLOODTYPE_MAIN_PATIENT = " " + CliSyntax.PREFIX_BLOOD_TYPE + "AB+";
    public static final String VALID_DESC_BLOODTYPE_OTHER_PATIENT = " " + CliSyntax.PREFIX_BLOOD_TYPE + "B+";
    public static final String VALID_DESC_GENDER_MAIN_PATIENT = " " + CliSyntax.PREFIX_GENDER + "M";
    public static final String VALID_DESC_GENDER_OTHER_PATIENT = " " + CliSyntax.PREFIX_GENDER + "M";
    public static final String VALID_DESC_HEIGHT_MAIN_PATIENT = " " + CliSyntax.PREFIX_HEIGHT + "176";
    public static final String VALID_DESC_HEIGHT_OTHER_PATIENT = " " + CliSyntax.PREFIX_HEIGHT + "186";
    public static final String VALID_DESC_WEIGHT_MAIN_PATIENT = " " + CliSyntax.PREFIX_WEIGHT + "86";
    public static final String VALID_DESC_WEIGHT_OTHER_PATIENT = " " + CliSyntax.PREFIX_WEIGHT + "77";
    public static final String VALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT = " " + CliSyntax.PREFIX_MEDICAL_CONDITIONS
            + "heart failure";
    public static final String VALID_DESC_MEDICALCONDITIONS_OTHER_PATIENT = " " + CliSyntax.PREFIX_MEDICAL_CONDITIONS
            + "diabetes";

    /* invalid constants declarations for patient related fields */
    public static final String INVALID_DESC_NAME_MAIN_PATIENT = " " + CliSyntax.PREFIX_NAME + "John** Doe";
    public static final String INVALID_DESC_PHONE_MAIN_PATIENT = " " + CliSyntax.PREFIX_PHONE + "not a number";
    public static final String INVALID_DESC_AGE_MAIN_PATIENT = " " + CliSyntax.PREFIX_AGE + "not a number";
    public static final String INVALID_DESC_BLOODTYPE_MAIN_PATIENT = " " + CliSyntax.PREFIX_BLOOD_TYPE + "invalid";
    public static final String INVALID_DESC_GENDER_MAIN_PATIENT = " " + CliSyntax.PREFIX_GENDER + "invalid";
    public static final String INVALID_DESC_HEIGHT_MAIN_PATIENT = " " + CliSyntax.PREFIX_HEIGHT + "invalid";
    public static final String INVALID_DESC_WEIGHT_MAIN_PATIENT = " " + CliSyntax.PREFIX_WEIGHT + "invalid";
    public static final String INVALID_DESC_MEDICALCONDITIONS_MAIN_PATIENT = " " + CliSyntax.PREFIX_MEDICAL_CONDITIONS
            + "invalid**";

    /* for listing activities */
    public static final String VALID_PERIOD_FLAG_TODAY = " " + CliSyntax.PREFIX_PERIOD_FLAG + "TODAY";
    public static final String VALID_PERIOD_FLAG_ALL = " " + CliSyntax.PREFIX_PERIOD_FLAG + "ALL";
    public static final String VALID_SORT_FLAG_START = " " + CliSyntax.PREFIX_SORT_FLAG + "START";
    public static final String VALID_SORT_FLAG_ID = " " + CliSyntax.PREFIX_SORT_FLAG + "ID";

    public static final String INVALID_PERIOD_FLAG = " " + CliSyntax.PREFIX_PERIOD_FLAG + "TOMORROW";
    public static final String INVALID_SORT_FLAG = " " + CliSyntax.PREFIX_SORT_FLAG + "end time";

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
    public static final String VALID_DESC_PAPER_REVIEW_DESCRIPTION =
            " " + CliSyntax.PREFIX_DESCRIPTION + "someone is attending this";

    /* valid constant declarations for appointment related fields */
    public static final String VALID_PATIENT_ID =
            " " + CliSyntax.PREFIX_ID + "P001";

    public static final String VALID_DESC_TITLE_APPOINTMENT =
            " " + CliSyntax.PREFIX_TITLE + "Meeting me";

    public static final String VALID_DESC_START_TIME_APPOINTMENT =
            " " + CliSyntax.PREFIX_START_TIME + "20-09-2022 13:00";

    public static final String VALID_DESC_END_TIME_APPOINTMENT =
            " " + CliSyntax.PREFIX_END_TIME + "21-09-2022 15:00";

    public static final String VALID_DESC_APPOINTMENT_DESCRIPTION =
            " " + CliSyntax.PREFIX_DESCRIPTION + "today at somewhere";

    /* invalid constants declarations for activity related fields */
    public static final String INVALID_PATIENT_ID =
            " " + CliSyntax.PREFIX_ID + "X001";
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

    /* Referral tests utility */
    public static final String VALID_PATIENT_REFERRAL_ID =
            " " + CliSyntax.PREFIX_PATIENT_ID + "P001";
    public static final String VALID_DOCTOR_REFERRAL_ID =
            " " + CliSyntax.PREFIX_DOCTOR_ID + "D001";
    public static final String INVALID_PATIENT_REFERRAL_ID =
            " " + CliSyntax.PREFIX_PATIENT_ID + "X001";

    public static final EditDoctorCommand.EditDoctorDescriptor DESC_MAIN_DOCTOR;
    public static final EditDoctorCommand.EditDoctorDescriptor DESC_OTHER_DOCTOR;

    public static final EditPatientCommand.EditPatientDescriptor DESC_MAIN_PATIENT;
    public static final EditPatientCommand.EditPatientDescriptor DESC_OTHER_PATIENT;

    public static final EditActivityCommand.EditActivityDescriptor DESC_MEETING;
    public static final EditActivityCommand.EditActivityDescriptor DESC_PAPER_REVIEW;


    static {
        DESC_MAIN_DOCTOR = new EditDoctorDescriptorBuilder().withName(TypicalPersons.MAIN_DOCTOR.getName().fullName)
                .withPhone(TypicalPersons.MAIN_DOCTOR.getPhone().value)
                .withDepartment(TypicalPersons.MAIN_DOCTOR.getDepartment().departmentName).build();
        DESC_OTHER_DOCTOR = new EditDoctorDescriptorBuilder().withName(TypicalPersons.OTHER_DOCTOR.getName().fullName)
                .withPhone(TypicalPersons.OTHER_DOCTOR.getPhone().value)
                .withDepartment(TypicalPersons.OTHER_DOCTOR.getDepartment().departmentName).build();
        DESC_MAIN_PATIENT = new EditPatientDescriptorBuilder().withName(TypicalPersons.MAIN_PATIENT.getName().fullName)
                .withPhone(TypicalPersons.MAIN_PATIENT.getPhone().value)
                .withAge(TypicalPersons.MAIN_PATIENT.getAge().age)
                .withBloodType(TypicalPersons.MAIN_PATIENT.getBloodType().bloodType)
                .withGender(TypicalPersons.MAIN_PATIENT.getGender().gender)
                .withHeight(TypicalPersons.MAIN_PATIENT.getHeight().height)
                .withWeight(TypicalPersons.MAIN_PATIENT.getWeight().weight)
                .withMedicalConditions("heart failure").build();
        DESC_OTHER_PATIENT = new EditPatientDescriptorBuilder()
                .withName(TypicalPersons.OTHER_PATIENT.getName().fullName)
                .withPhone(TypicalPersons.OTHER_PATIENT.getPhone().value)
                .withAge(TypicalPersons.OTHER_PATIENT.getAge().age)
                .withBloodType(TypicalPersons.OTHER_PATIENT.getBloodType().bloodType)
                .withGender(TypicalPersons.OTHER_PATIENT.getGender().gender)
                .withHeight(TypicalPersons.OTHER_PATIENT.getHeight().height)
                .withWeight(TypicalPersons.OTHER_PATIENT.getWeight().weight)
                .withMedicalConditions("heart failure").build();
        DESC_MEETING = new EditActivityDescriptorBuilder()
                .withStartTime(MEETING.getStartTime().toString())
                .withEndTime(MEETING.getEndTime().toString())
                .withTitle(MEETING.getTitle().toString())
                .withDescription(MEETING.getDescription().toString()).build();
        DESC_PAPER_REVIEW = new EditActivityDescriptorBuilder()
                .withStartTime(PAPER_REVIEW.getStartTime().toString())
                .withEndTime(PAPER_REVIEW.getEndTime().toString())
                .withTitle(PAPER_REVIEW.getTitle().toString())
                .withDescription(PAPER_REVIEW.getDescription().toString()).build();
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
     * - the address book, filtered activity list and selected activity in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Activity> expectedFilteredList = new ArrayList<>(actualModel.getFilteredActivityListById());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredActivityListById());
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
        assertTrue(targetIndex.getZeroBased() < model.getFilteredActivityListById().size());

        Activity activity = model.getFilteredActivityListById().get(targetIndex.getZeroBased());
        final ActivityId aid = activity.getActivityId();
        model.updateFilteredActivitiesList(activity1 -> activity1.getActivityId().equals(aid));

        assertEquals(1, model.getFilteredActivityListById().size());
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
        public void setUserProfile(UserProfile userProfile) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UserProfile getUserProfile() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableValue<UserProfile> getObservableUserProfile() {
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
        public void setDoctor(Doctor oldDoctor, Doctor replacement) {
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
        public void deletePatientAssociatedAppointments(Patient associatedPatient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setActivity(Activity oldActivity, Activity replacementActivity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePatient(Patient target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPatient(Patient oldPatient, Patient replacement) {
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
        public ObservableList<Activity> getFilteredActivityListById() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Activity> getFilteredActivityListByStartTime() {
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
        public void updateFilteredDoctorList(Predicate<? super Doctor> predicate) {
            // noop
        }

        @Override
        public void updateFilteredPatientList(Predicate<? super Patient> predicate) {
            // noop
        }

        @Override
        public void updateFilteredActivitiesList(Predicate<? super Activity> predicate) {
            // noop
        }

        @Override
        public ObservableValue<Integer> getModelBeingShown() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModelBeingShown(ModelItem modelItem) {
            // noop
        }

        @Override
        public ObservableValue<Patient> getViewPatient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setViewPatient(Patient target) {
            // noop
        }
    }
}
