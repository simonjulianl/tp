package gomedic.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import gomedic.commons.core.GuiSettings;
import gomedic.model.activity.Activity;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.patient.Patient;
import gomedic.model.userprofile.UserProfile;
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
     * Replaces the user profile with the profile supplied {@code userProfile}.
     *
     * @param userProfile the new user profile to be set.
     */
    void setUserProfile(UserProfile userProfile);

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
     * Deletes the given doctor.
     * The doctor must exist in the address book.
     */
    void deleteDoctor(Doctor target);

    /**
     * Sets a doctor in the model with another doctor.
     * The doctor must exist in the address book.
     */
    void setDoctor(Doctor oldDoctor, Doctor replacementDoctor);

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
     * Deletes the given activity.
     * The activity must exist in the address book.
     */
    void deleteActivity(Activity target);

    /**
     * Deletes appointments associated with a deleted patient.
     * The patient may or may not have an appointment registered.
     */
    void deletePatientAssociatedAppointments(Patient associatedPatient);

    /**
     * Sets an activity in the model with another activity.
     * The activity must exist in the address book.
     */
    void setActivity(Activity oldActivity, Activity replacementActivity);

    /**
     * Returns true if there is another conflicting activity.
     */
    boolean hasConflictingActivity(Activity activity);

    /**
     * Deletes the given patient.
     * The patient must exist in the address book.
     */
    void deletePatient(Patient target);

    /**
     * Sets a patient in the model with another patient.
     * The patient must exist in the address book.
     */
    void setPatient(Patient oldPatient, Patient replacementPatient);

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

    /** Returns a copy of the user profile */
    UserProfile getUserProfile();

    /** Returns an unmodifiable observable of the user profile */
    ObservableValue<UserProfile> getObservableUserProfile();

    /**
     * Returns patient to be viewed.
     *
     * @return Patient to be viewed.
     */
    ObservableValue<Patient> getViewPatient();

    /**
     * Sets patientToView with the correct patient details.
     *
     * @param target Target patient with the correct details.
     */
    void setViewPatient(Patient target);

    /** Returns an unmodifiable view of the filtered doctor list */
    ObservableList<Doctor> getFilteredDoctorList();

    /** Returns an unmodifiable view of the filtered patient list */
    ObservableList<Patient> getFilteredPatientList();

    /** Returns an unmodifiable view of the filtered activity list sorted by id*/
    ObservableList<Activity> getFilteredActivityListById();

    /** Returns an unmodifiable view of the filtered activity list sorted by start time*/
    ObservableList<Activity> getFilteredActivityListByStartTime();

    /**
     * Updates the filter of the filtered doctor list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDoctorList(Predicate<? super Doctor> predicate);

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPatientList(Predicate<? super Patient> predicate);

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
     */
    ObservableValue<Integer> getModelBeingShown();

    /**
     * Sets the model being shown.
     */
    void setModelBeingShown(ModelItem modelItem);
}
