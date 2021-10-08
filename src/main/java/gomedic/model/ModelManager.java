package gomedic.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import gomedic.commons.core.GuiSettings;
import gomedic.commons.core.LogsCenter;
import gomedic.commons.util.CollectionUtil;
import gomedic.model.activity.Activity;
import gomedic.model.person.Person;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Activity> filteredActivities;

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredActivities = new FilteredList<>(this.addressBook.getActivityList());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookDataRootFilePath() {
        return userPrefs.getAddressBookRootFilePath();
    }

    @Override
    public void setAddressBookDataRootFilePath(Path addressBookDataRootFilePath) {
        requireNonNull(addressBookDataRootFilePath);
        userPrefs.setAddressBookDataFileRootPath(addressBookDataRootFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        requireNonNull(person);
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_ITEMS);
    }

    @Override
    public void updateFilteredPersonList(Predicate<? super Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void addActivity(Activity activity) {
        requireNonNull(activity);
        addressBook.addActivity(activity);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ITEMS);
    }

    private void updateFilteredActivityList(Predicate<? super Activity> predicate) {
        requireNonNull(predicate);
        filteredActivities.setPredicate(predicate);
    }

    @Override
    public int getNewActivityId() {
        return addressBook.getNewActivityId();
    }

    @Override
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return addressBook.hasActivity(activity);
    }

    @Override
    public boolean hasConflictingActivity(Activity activity) {
        requireNonNull(activity);
        return addressBook.hasConflictingActivity(activity);
    }

    //=========== Filtered Person List Accessors =============================================================

    @Override
    public void setPerson(Person target, Person editedPerson) {
        CollectionUtil.requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredActivities.equals(other.filteredActivities);
    }

}
