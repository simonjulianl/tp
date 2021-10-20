package gomedic.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import gomedic.commons.core.GuiSettings;
import gomedic.commons.core.LogsCenter;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.logic.parser.AddressBookParser;
import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.Model;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.activity.Activity;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.patient.Patient;
import gomedic.model.userprofile.UserProfile;
import gomedic.storage.Storage;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";


    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public UserProfile getUserProfile() {
        return model.getUserProfile().copy();
    }

    @Override
    public ObservableValue<UserProfile> getObservableUserProfile() {
        return model.getObservableUserProfile();
    }

    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return model.getFilteredPatientList();
    }

    @Override
    public ObservableList<Doctor> getFilteredDoctorList() {
        return model.getFilteredDoctorList();
    }

    @Override
    public ObservableList<Activity> getFilteredActivityListById() {
        return model.getFilteredActivityListById();
    }

    @Override
    public ObservableList<Activity> getFilteredActivityListByStartTime() {
        return model.getFilteredActivityListByStartTime();
    }

    @Override
    public Path getAddressBookRootFilePath() {
        return model.getAddressBookDataRootFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableValue<Integer> getModelBeingShown() {
        return model.getModelBeingShown();
    }
}
