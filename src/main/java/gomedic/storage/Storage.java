package gomedic.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import gomedic.commons.exceptions.DataConversionException;
import gomedic.model.ReadOnlyAddressBook;
import gomedic.model.ReadOnlyUserPrefs;
import gomedic.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookDataFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
