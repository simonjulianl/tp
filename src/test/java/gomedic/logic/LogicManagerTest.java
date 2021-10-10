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
import gomedic.logic.commands.addcommand.AddPersonCommand;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.logic.commands.listcommand.ListPersonCommand;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.ModelManager;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.UserPrefs;
import gomedic.model.activity.Activity;
import gomedic.model.person.Person;
import gomedic.model.person.doctor.Doctor;
import gomedic.storage.JsonAddressBookStorage;
import gomedic.storage.JsonUserPrefsStorage;
import gomedic.storage.StorageManager;
import gomedic.testutil.Assert;
import gomedic.testutil.TypicalPersons;
import gomedic.testutil.modelbuilder.ActivityBuilder;
import gomedic.testutil.modelbuilder.DoctorBuilder;
import gomedic.testutil.modelbuilder.PersonBuilder;

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
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        String listCommand = ListPersonCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListPersonCommand.MESSAGE_SUCCESS, model);
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
    public void executeAddPerson_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addPersonCommand = AddPersonCommand.COMMAND_WORD
                + CommandTestUtil.NAME_DESC_AMY
                + CommandTestUtil.PHONE_DESC_AMY
                + CommandTestUtil.EMAIL_DESC_AMY
                + CommandTestUtil.ADDRESS_DESC_AMY;
        Person expectedPerson = new PersonBuilder(TypicalPersons.AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addPersonCommand, CommandException.class, expectedMessage, expectedModel);
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
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addDoctorCommand, CommandException.class, expectedMessage, expectedModel);
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
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addActivityCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredDoctorList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredDoctorList().remove(0));
    }

    @Test
    public void getFilteredActivityList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredActivityList().remove(0));
    }

    @Test
    void getModelBeingShown_defaultValue_testPassed() {
        assertEquals(ModelItem.ACTIVITY.ordinal(), logic.getModelBeingShown().getValue());
    }

    @Test
    void getModelBeingShown_executeOtherCommand_testPassed() throws Exception {
        String listCommand = ListPersonCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListPersonCommand.MESSAGE_SUCCESS, model);
        assertEquals(ModelItem.PERSON.ordinal(), logic.getModelBeingShown().getValue());
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
