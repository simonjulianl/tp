package gomedic.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import gomedic.commons.core.GuiSettings;
import gomedic.model.activity.Activity;
import gomedic.model.person.Person;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Object> PREDICATE_SHOW_ALL_ITEMS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookDataRootFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookDataRootFilePath(Path addressBookDataRootFilePath);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given activity.
     * {@code activity} must not already exist and not conflicting
     * with any activity in the address book.
     */
    void addActivity(Activity activity);

    /**
     * Get a new unique activity id which is just last id number + 1;
     */
    int getNewActivityId();

    /**
     * Returns true if an activity with same id exists in the addressbook.
     */
    boolean hasActivity(Activity activity);

    /**
     * Returns true if there is another conflicting activity.
     */
    boolean hasConflictingActivity(Activity activity);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered activity list */
    ObservableList<Activity> getFilteredActivityList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<? super Person> predicate);

    /**
     * Updates the filter of the filtered activities list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredActivitiesList(Predicate<? super Activity> predicate);

    /**
     * Returns the integer showing current item being shown.
     * 0 -> activity
     * 1 -> person
     */
    ObservableValue<Integer> getModelBeingShown();

    /**
     * Sets the model being shown.
     */
    void setModelBeingShown(ModelItem modelItem);
}
