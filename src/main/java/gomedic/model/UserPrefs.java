package gomedic.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import gomedic.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    public static final String ROOT_FOLDER = "data";
    private Path addressBookDataFileRootPath = Paths.get(ROOT_FOLDER);
    private final Path addressBookPersonsFilePath = Paths.get(
            getAddressBookRootFilePath().toString(),
            "persons.json");
    private final Path addressBookActivityFilePath = Paths.get(
            getAddressBookRootFilePath().toString(),
            "activities.json");
    private GuiSettings guiSettings = new GuiSettings();


    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookDataFileRootPath(newUserPrefs.getAddressBookPersonFilePath());
    }

    public void setAddressBookDataFileRootPath(Path addressBookDataFileRootPath) {
        requireNonNull(addressBookDataFileRootPath);
        this.addressBookDataFileRootPath = addressBookDataFileRootPath;
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    @Override
    public Path getAddressBookPersonFilePath() {
        return addressBookPersonsFilePath;
    }

    @Override
    public Path getAddressBookActivityFilePath() {
        return addressBookActivityFilePath;
    }

    @Override
    public Path getAddressBookRootFilePath() {
        return addressBookDataFileRootPath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && addressBookDataFileRootPath.equals(o.addressBookDataFileRootPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookDataFileRootPath);
    }

    @Override
    public String toString() {
        // TODO : Integrate Activity file path
        return "Gui Settings : " + guiSettings
                + "\nLocal data file location : " + addressBookDataFileRootPath;
    }
}
