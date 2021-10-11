package gomedic.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import gomedic.commons.core.GuiSettings;
import gomedic.model.activity.Activity;
import gomedic.model.person.Person;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.patient.Patient;
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
     * Adds the given doctor.
     * {@code doctor} must not already exist in the address book.
     */
    void addDoctor(Doctor doctor);

    /**
     * Checks if there is a new doctor id available for assignment.
     */
    boolean hasNewDoctorId();

    /**
     * Get a new available unique doctor id.
     */
    int getNewDoctorId();

    /**
     * Returns true if a doctor with same id exists in the addressbook.
     */
    boolean hasDoctor(Doctor doctor);

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
     * Adds the given patient.
     * {@code patient} must not already exist in the address book.
     */
    void addPatient(Patient patient);

    /**
     * Checks if there is a new patient id available for assignment.
     */
    boolean hasNewPatientId();

    /**
     * Get a new available unique patient id.
     */
    int getNewPatientId();

    /**
     * Returns true if a patient with same id exists in the addressbook.
     */
    boolean hasPatient(Patient patient);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered doctor list */
    ObservableList<Doctor> getFilteredDoctorList();

    /** Returns an unmodifiable view of the filtered patient list */
    ObservableList<Patient> getFilteredPatientList();

    /** Returns an unmodifiable view of the filtered activity list */
    ObservableList<Activity> getFilteredActivityList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<? super Person> predicate);

    /**
     * Updates the filter of the filtered doctor list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDoctorList(Predicate<? super Doctor> predicate);

    /**
     * Updates the filter of the filtered activities list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredActivitiesList(Predicate<? super Activity> predicate);

    /**
     * Returns the integer showing current item being shown.
     * 0 -> activity
     * 1 -> doctor
     * 2 -> patient
     * 3 -> person
     */
    ObservableValue<Integer> getModelBeingShown();

    /**
     * Sets the model being shown.
     */
    void setModelBeingShown(ModelItem modelItem);
}
