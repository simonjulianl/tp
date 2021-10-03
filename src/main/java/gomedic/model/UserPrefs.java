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

    private final Path addressBookActivityFilePath = Paths.get("data", "activities.json");
    private Path addressBookPersonFilePath = Paths.get("data", "persons.json");
    private GuiSettings guiSettings = new GuiSettings();

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookPersonFilePath(newUserPrefs.getAddressBookPersonFilePath());
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
        return addressBookPersonFilePath;
    }

    public void setAddressBookPersonFilePath(Path addressBookPersonFilePath) {
        requireNonNull(addressBookPersonFilePath);
        this.addressBookPersonFilePath = addressBookPersonFilePath;
    }

    @Override
    public Path getAddressBookActivityFilePath() {
        return addressBookActivityFilePath;
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
                && addressBookPersonFilePath.equals(o.addressBookPersonFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookPersonFilePath);
    }

    @Override
    public String toString() {
        return "Gui Settings : " + guiSettings
                + "\nLocal data file location : " + addressBookPersonFilePath;
    }
}
