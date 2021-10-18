package gomedic.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import gomedic.model.activity.Activity;
import gomedic.model.activity.UniqueActivityList;
import gomedic.model.person.UniquePersonList;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.patient.Patient;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueActivityList activities;
    private final UniquePersonList<Doctor> doctors;
    private final UniquePersonList<Patient> patients;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

    {
        activities = new UniqueActivityList();
        doctors = new UniquePersonList<>();
        patients = new UniquePersonList<>();
    }

    /**
     * Creates an AddressBook using the data in {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public AddressBook() {
    }

    //// list overwrite operations

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setActivities(newData.getActivityListSortedById());
        setDoctors(newData.getDoctorListSortedById());
        setPatients(newData.getPatientListSortedById());
    }


    /**
     * Replaces the contents of the activity list with {@code activities}.
     * {@code activities} must not contain duplicate and conflicting activities.
     */
    public void setActivities(List<Activity> activities) {
        this.activities.setActivities(activities);
    }

    /**
     * Replaces the contents of the doctor list with {@code doctors}.
     * {@code doctors} must not contain duplicate doctors.
     */
    public void setDoctors(List<Doctor> doctors) {
        this.doctors.setPersons(doctors);
    }

    /**
     * Replaces the contents of the patient list with {@code patients}.
     * {@code patients} must not contain duplicate patients.
     */
    public void setPatients(List<Patient> patients) {
        this.patients.setPersons(patients);
    }

    //// person and activity-level operations

    /**
     * Returns true if a doctor with the same identity as {@code doctor} exists in the address book.
     */
    public boolean hasDoctor(Doctor doctor) {
        requireNonNull(doctor);
        return doctors.contains(doctor);
    }

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the address book.
     */
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return patients.contains(patient);
    }

    /**
     * Returns true if an activity with the same id as {@code activities} exists in the address book.
     */
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return activities.contains(activity);
    }

    /**
     * Returns true if there exists another conflicting activity
     * in terms of timing in the addressbook.
     */
    public boolean hasConflictingActivity(Activity activity) {
        requireNonNull(activity);
        return activities.containsConflicting(activity);
    }

    /**
     * Adds a doctor to the address book.
     * The doctor must not already exist in the address book.
     */
    public void addDoctor(Doctor d) {
        doctors.add(d);
    }

    /**
     * Adds a patient to the address book.
     * The patient must not already exist in the address book.
     */
    public void addPatient(Patient p) {
        patients.add(p);
    }

    /**
     * Adds an activity to the address book.
     * The activity must not be duplicate and conflicting.
     */
    public void addActivity(Activity a) {
        activities.add(a);
    }

    /**
     * Returns a new activity id.
     */
    public int getNewActivityId() {
        return activities.getNewActivityId();
    }

    /**
     * Checks if there is an available new doctor id for assignment.
     */
    public boolean hasNewDoctorId() {
        return doctors.hasNewId();
    }

    /**
     * Returns a new doctor id.
     */
    public int getNewDoctorId() {
        return doctors.getNewId();
    }

    /**
     * Checks if there is an available new patient id for assignment.
     */
    public boolean hasNewPatientId() {
        return patients.hasNewId();
    }

    /**
     * Returns a new patient id.
     */
    public int getNewPatientId() {
        return patients.getNewId();
    }

    /**
     * Replaces the given activity {@code target} in the list with {@code editedActivity}.
     * {@code target} must exist in the address book.
     * The activity's id must not be the same as another existing activity in the address book (other than the one
     * which is being replaced).
     */
    public void setActivity(Activity target, Activity editedActivity) {
        requireNonNull(editedActivity);

        activities.setActivity(target, editedActivity);
    }

    /**
     * Replaces the given doctor {@code target} in the list with {@code editedDoctor}.
     * {@code target} must exist in the address book.
     * The doctor's id must not be the same as another existing doctor in the address book (other than the one
     * which is being replaced).
     */
    public void setDoctor(Doctor target, Doctor editedPerson) {
        requireNonNull(editedPerson);

        doctors.setPerson(target, editedPerson);
    }

    /**
     * Replaces the given patient {@code target} in the list with {@code editedPatient}.
     * {@code target} must exist in the address book.
     * The patient's id must not be the same as another existing patient in the address book (other than the one
     * which is being replaced).
     */
    public void setPatient(Patient target, Patient editedPerson) {
        requireNonNull(editedPerson);

        patients.setPerson(target, editedPerson);
    }

    /**
     * Remove the doctor based on the id.
     * Therefore, regardless whether the doctor has different fields,
     * as long as the id is the same, it would be treated as equal.
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeDoctor(Doctor key) {
        doctors.remove(key);
    }

    /**
     * Remove the patient based on the id.
     * Therefore, regardless whether the patient has different fields,
     * as long as the id is the same, it would be treated as equal.
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePatient(Patient key) {
        patients.remove(key);
    }

    /**
     * Remove the activity based on the id.
     * Therefore, regardless whether the activity has different titles/fields,
     * as long as the id is the same, it would be treated as equal.
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //// util methods

    @Override
    public String toString() {
        return activities.asUnmodifiableSortedByIdObservableList().size() + " activities; "
                + doctors.asUnmodifiableSortedByIdObservableList().size() + " doctors; "
                + patients.asUnmodifiableSortedByIdObservableList().size() + " patients";

        // TODO: refine later
    }

    @Override
    public ObservableList<Doctor> getDoctorListSortedById() {
        return doctors.asUnmodifiableSortedByIdObservableList();
    }

    @Override
    public ObservableList<Patient> getPatientListSortedById() {
        return patients.asUnmodifiableSortedByIdObservableList();
    }

    @Override
    public ObservableList<Activity> getActivityListSortedById() {
        return activities.asUnmodifiableSortedByIdObservableList();
    }

    @Override
    public ObservableList<Activity> getActivityListSortedByStartTime() {
        return activities.asUnmodifiableSortedByStartTimeList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && getActivityListSortedById().equals(((AddressBook) other).getActivityListSortedById())
                && getDoctorListSortedById().equals(((AddressBook) other).getDoctorListSortedById())
                && getPatientListSortedById().equals(((AddressBook) other).getPatientListSortedById()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(activities, doctors, patients);
    }
}
