package gomedic.model;

import java.nio.file.Path;

import gomedic.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookPersonFilePath();

    Path getAddressBookActivityFilePath();
}
