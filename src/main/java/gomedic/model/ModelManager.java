package gomedic.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import gomedic.commons.core.GuiSettings;
import gomedic.commons.core.LogsCenter;
import gomedic.commons.util.CollectionUtil;
import gomedic.model.activity.Activity;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.patient.Patient;
import gomedic.model.person.patient.PatientId;
import gomedic.model.userprofile.UserProfile;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Doctor> filteredDoctors;
    private final FilteredList<Patient> filteredPatients;
    // used the ordinal value 0 -> Activity, 1 -> Doctor, 2 -> Patient, for simplicity.
    private final ObjectProperty<Integer> internalModelItemBeingShown =
            new SimpleIntegerProperty(ModelItem.ACTIVITY_ID.ordinal()).asObject();
    private final ObservableValue<Integer> modelItemBeingShown = internalModelItemBeingShown; // immutable
    private final FilteredList<Activity> filteredActivitiesById;
    private final FilteredList<Activity> filteredActivitiesByStartTime;
    private final ObjectProperty<Patient> internalPatientToView = new SimpleObjectProperty<>(null);
    private final ObservableValue<Patient> patientToView = internalPatientToView;

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
        filteredDoctors = new FilteredList<>(this.addressBook.getDoctorListSortedById());
        filteredPatients = new FilteredList<>(this.addressBook.getPatientListSortedById());
        filteredActivitiesById = new FilteredList<>(this.addressBook.getActivityListSortedById());
        filteredActivitiesByStartTime = new FilteredList<>(this.addressBook.getActivityListSortedByStartTime());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    //=========== UserPrefs ==================================================================================

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

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public void updateFilteredDoctorList(Predicate<? super Doctor> predicate) {
        requireNonNull(predicate);
        filteredDoctors.setPredicate(predicate);
    }

    @Override
    public void updateFilteredPatientList(Predicate<? super Patient> predicate) {
        requireNonNull(predicate);
        filteredPatients.setPredicate(predicate);
    }

    @Override
    public void updateFilteredActivitiesList(Predicate<? super Activity> predicate) {
        requireNonNull(predicate);
        filteredActivitiesById.setPredicate(predicate);
        filteredActivitiesByStartTime.setPredicate(predicate);
    }

    @Override
    public void setUserProfile(UserProfile userProfile) {
        requireNonNull(userProfile);
        addressBook.setUserProfile(userProfile.copy());
    }

    @Override
    public UserProfile getUserProfile() {
        return addressBook.getUserProfile();
    }

    @Override
    public ObservableValue<UserProfile> getObservableUserProfile() {
        return addressBook.getObservableUserProfile();
    }

    @Override
    public ObservableValue<Integer> getModelBeingShown() {
        return modelItemBeingShown;
    }

    @Override
    public void setModelBeingShown(ModelItem modelItem) {
        requireNonNull(modelItem);
        internalModelItemBeingShown.setValue(modelItem.ordinal());
    }

    @Override
    public void addDoctor(Doctor doctor) {
        requireNonNull(doctor);
        addressBook.addDoctor(doctor);
        updateFilteredDoctorList(PREDICATE_SHOW_ALL_ITEMS);
    }

    @Override
    public boolean hasNewDoctorId() {
        return addressBook.hasNewDoctorId();
    }

    @Override
    public int getNewDoctorId() {
        return addressBook.getNewDoctorId();
    }

    @Override
    public boolean hasDoctor(Doctor doctor) {
        requireNonNull(doctor);
        return addressBook.hasDoctor(doctor);
    }

    @Override
    public void deleteDoctor(Doctor target) {
        addressBook.removeDoctor(target);
    }

    @Override
    public void setDoctor(Doctor oldDoctor, Doctor replacementDoctor) {
        addressBook.setDoctor(oldDoctor, replacementDoctor);
    }

    @Override
    public void addPatient(Patient patient) {
        requireNonNull(patient);
        addressBook.addPatient(patient);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_ITEMS);
    }

    @Override
    public boolean hasNewPatientId() {
        return addressBook.hasNewPatientId();
    }

    @Override
    public int getNewPatientId() {
        return addressBook.getNewPatientId();
    }

    @Override
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return addressBook.hasPatient(patient);
    }

    @Override
    public void addActivity(Activity activity) {
        requireNonNull(activity);
        addressBook.addActivity(activity);
        updateFilteredActivitiesList(PREDICATE_SHOW_ALL_ITEMS);
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
    public void deleteActivity(Activity target) {
        addressBook.removeActivity(target);
    }

    @Override
    public void deletePatientAssociatedAppointments(Patient associatedPatient) {
        PatientId id = associatedPatient.getId();
        FilteredList<Activity> associatedAppointments = addressBook.getActivityListSortedById()
                .filtered(x -> id.equals(x.getPatientId()));
        while (!associatedAppointments.isEmpty()) {
            deleteActivity(associatedAppointments.get(0));
        }
    }

    @Override
    public void setActivity(Activity oldActivity, Activity replacementActivity) {
        addressBook.setActivity(oldActivity, replacementActivity);
    }

    @Override
    public void deletePatient(Patient target) {
        addressBook.removePatient(target);
    }

    @Override
    public void setPatient(Patient oldPatient, Patient replacementPatient) {
        addressBook.setPatient(oldPatient, replacementPatient);
    }

    @Override
    public void setViewPatient(Patient target) {
        internalPatientToView.setValue(target);
    }

    @Override
    public ObservableValue<Patient> getViewPatient() {
        return patientToView;
    }

    @Override
    public boolean hasConflictingActivity(Activity activity) {
        requireNonNull(activity);
        return addressBook.hasConflictingActivity(activity);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Doctor} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Doctor> getFilteredDoctorList() {
        return filteredDoctors;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return filteredPatients;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Activity} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Activity> getFilteredActivityListById() {
        return filteredActivitiesById;
    }

    @Override
    public ObservableList<Activity> getFilteredActivityListByStartTime() {
        return filteredActivitiesByStartTime;
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
                && filteredDoctors.equals(other.filteredDoctors)
                && filteredPatients.equals(other.filteredPatients)
                && filteredActivitiesById.equals(other.filteredActivitiesById)
                && filteredActivitiesByStartTime.equals(other.filteredActivitiesByStartTime);
    }

}
