package gomedic.model;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Logger;

import gomedic.commons.core.GuiSettings;
import gomedic.commons.core.LogsCenter;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    public static final String ROOT_FOLDER = "data";
    private Path addressBookDataFileRootPath = Paths.get(ROOT_FOLDER, "addressbook.json");
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
        setAddressBookDataFileRootPath(newUserPrefs.getAddressBookRootFilePath());
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
        return "Gui Settings : " + guiSettings
                + "\nLocal data file location : " + addressBookDataFileRootPath;
    }
}
