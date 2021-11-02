package gomedic.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.logic.commands.addcommand.AddActivityCommand;
import gomedic.logic.commands.addcommand.AddDoctorCommand;
import gomedic.logic.commands.addcommand.AddPatientCommand;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.logic.commands.listcommand.ListActivityCommand;
import gomedic.logic.commands.listcommand.ListDoctorCommand;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.ModelManager;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.UserPrefs;
import gomedic.model.activity.Activity;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.patient.Patient;
import gomedic.model.util.SampleDataUtil;
import gomedic.storage.JsonAddressBookStorage;
import gomedic.storage.JsonUserPrefsStorage;
import gomedic.storage.StorageManager;
import gomedic.testutil.Assert;
import gomedic.testutil.TypicalPersons;
import gomedic.testutil.modelbuilder.ActivityBuilder;
import gomedic.testutil.modelbuilder.DoctorBuilder;
import gomedic.testutil.modelbuilder.PatientBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private final Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, Messages.getSuggestions(invalidCommand));
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        Assert.assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    @Test
    public void executeDeleteActivity_commandExecutionError_throwsCommandException() {
        String deleteActivity = "delete t/activity A001";
        assertCommandException(deleteActivity, Messages.MESSAGE_INVALID_ACTIVITY_ID);
    }

    @Test
    public void executeDeleteDoctor_commandExecutionError_throwsCommandException() {
        String deleteDoctor = "delete t/doctor D001";
        assertCommandException(deleteDoctor, Messages.MESSAGE_INVALID_DOCTOR_ID);
    }
    @Test
    public void executeDeletePatient_commandExecutionError_throwsCommandException() {
        String deletePatient = "delete t/patient P001";
        assertCommandException(deletePatient, Messages.MESSAGE_INVALID_PATIENT_ID);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListActivityCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListActivityCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void executeAddDoctor_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add doctor command
        String addDoctorCommand = AddDoctorCommand.COMMAND_WORD
                + CommandTestUtil.VALID_DESC_NAME_MAIN_DOCTOR
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_DOCTOR
                + CommandTestUtil.VALID_DESC_DEPARTMENT_MAIN_DOCTOR;

        Doctor expectedDoctor = new DoctorBuilder(TypicalPersons.MAIN_DOCTOR).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addDoctor(expectedDoctor);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE;
        assertCommandFailure(addDoctorCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void executeAddPatient_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add patient command
        String addPatientCommand = AddPatientCommand.COMMAND_WORD
                + CommandTestUtil.VALID_DESC_NAME_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_PHONE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_AGE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_BLOODTYPE_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_GENDER_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_HEIGHT_MAIN_PATIENT
                + CommandTestUtil.VALID_DESC_WEIGHT_MAIN_PATIENT;

        Patient expectedPatient = new PatientBuilder(TypicalPersons.MAIN_PATIENT).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPatient(expectedPatient);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE;
        assertCommandFailure(addPatientCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void executeAddActivity_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addActivityCommand = AddActivityCommand.COMMAND_WORD
                + CommandTestUtil.VALID_DESC_START_TIME_MEETING
                + CommandTestUtil.VALID_DESC_END_TIME_MEETING
                + CommandTestUtil.VALID_DESC_TITLE_MEETING
                + CommandTestUtil.VALID_DESC_MEETING_DESCRIPTION;
        Activity expectedActivity = new ActivityBuilder().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addActivity(expectedActivity);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE;
        assertCommandFailure(addActivityCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredDoctorList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredDoctorList().remove(0));
    }

    @Test
    public void getFilteredPatientList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPatientList().remove(0));
    }

    @Test
    public void getFilteredActivityList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredActivityListById().remove(0));
    }

    @Test
    void getModelBeingShown_defaultValue_testPassed() {
        assertEquals(ModelItem.ACTIVITY_ID.ordinal(), logic.getModelBeingShown().getValue());
    }

    @Test
    void getModelBeingShown_executeOtherCommand_testPassed() throws Exception {
        String listCommand = ListDoctorCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListDoctorCommand.MESSAGE_SUCCESS, model);
        assertEquals(ModelItem.DOCTOR.ordinal(), logic.getModelBeingShown().getValue());
    }

    @Test
    void getUserProfile_defaultValue_testPassed() {
        assertEquals(SampleDataUtil.getSampleUserProfile(), logic.getUserProfile());
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonAddressBookIoExceptionThrowingStub extends JsonAddressBookStorage {
        private JsonAddressBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
