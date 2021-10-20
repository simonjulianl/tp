package gomedic.model;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalActivities.CONFLICTING_MEETING;
import static gomedic.testutil.TypicalActivities.DUPLICATE_ACTIVITY;
import static gomedic.testutil.TypicalActivities.MEETING;
import static gomedic.testutil.TypicalActivities.PAST_ACTIVITY;
import static gomedic.testutil.TypicalActivities.getTypicalActivities;
import static gomedic.testutil.TypicalPersons.MAIN_DOCTOR;
import static gomedic.testutil.TypicalPersons.MAIN_PATIENT;
import static gomedic.testutil.TypicalPersons.NOT_IN_TYPICAL_DOCTOR;
import static gomedic.testutil.TypicalPersons.NOT_IN_TYPICAL_PATIENT;
import static gomedic.testutil.TypicalPersons.OTHER_DOCTOR;
import static gomedic.testutil.TypicalPersons.OTHER_PATIENT;
import static gomedic.testutil.TypicalPersons.THIRD_DOCTOR;
import static gomedic.testutil.TypicalPersons.THIRD_PATIENT;
import static gomedic.testutil.TypicalPersons.getTypicalDoctors;
import static gomedic.testutil.TypicalPersons.getTypicalPatients;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import gomedic.model.activity.Activity;
import gomedic.model.activity.exceptions.ActivityNotFoundException;
import gomedic.model.activity.exceptions.ConflictingActivityException;
import gomedic.model.activity.exceptions.DuplicateActivityFoundException;
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.exceptions.MaxAddressBookCapacityReached;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.exceptions.DuplicatePersonException;
import gomedic.model.person.exceptions.PersonNotFoundException;
import gomedic.model.person.patient.Patient;
import gomedic.model.userprofile.UserProfile;
import gomedic.model.util.SampleDataUtil;
import gomedic.testutil.TypicalPersons;
import gomedic.testutil.modelbuilder.ActivityBuilder;
import gomedic.testutil.modelbuilder.DoctorBuilder;
import gomedic.testutil.modelbuilder.PatientBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals((new SimpleObjectProperty<>(SampleDataUtil.getSampleUserProfile())).getValue(),
                addressBook.getObservableUserProfile().getValue());
        assertEquals(Collections.emptyList(), addressBook.getDoctorListSortedById());
        assertEquals(Collections.emptyList(), addressBook.getPatientListSortedById());
        assertEquals(Collections.emptyList(), addressBook.getActivityListSortedById());
        assertEquals(Collections.emptyList(), addressBook.getActivityListSortedByStartTime());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = TypicalPersons.getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void addPatient_newPatient_returnsTrue() {
        addressBook.addPatient(MAIN_PATIENT);
        assertTrue(addressBook.hasPatient(MAIN_PATIENT));
    }

    @Test
    public void addPatient_duplicatePatient_throwsDuplicatePersonException() {
        addressBook.addPatient(MAIN_PATIENT);
        assertThrows(DuplicatePersonException.class, () -> addressBook.addPatient(MAIN_PATIENT));
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPatient(null));
    }

    @Test
    public void hasPatient_patientNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPatient(MAIN_PATIENT));
    }

    @Test
    public void hasPatient_patientInAddressBook_returnsTrue() {
        addressBook.addPatient(MAIN_PATIENT);
        assertTrue(addressBook.hasPatient(MAIN_PATIENT));
    }

    @Test
    public void hasPatient_patientWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPatient(MAIN_PATIENT);
        Patient editedPatient = new PatientBuilder(MAIN_PATIENT).withAge("40")
                .build();
        assertTrue(addressBook.hasPatient(editedPatient));
    }

    @Test
    public void getPatientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPatientListSortedById().remove(0));
    }

    @Test
    void removePatient_nonExistentPatient_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.removePatient(MAIN_PATIENT));
    }

    @Test
    void removePatient_existingPatient_doesNotThrow() {
        addressBook.addPatient(MAIN_PATIENT);
        assertDoesNotThrow(() -> addressBook.removePatient(MAIN_PATIENT));
    }

    @Test
    public void resetData_withDuplicatePatients_throwsDuplicatePersonException() {
        // Two patients with the same id
        List<Patient> newPatients = Arrays.asList(MAIN_PATIENT, MAIN_PATIENT);
        AddressBookStub newData = new AddressBookStub(getTypicalActivities(), getTypicalDoctors(),
            newPatients);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void addDoctor_newDoctor_returnsTrue() {
        addressBook.addDoctor(MAIN_DOCTOR);
        assertTrue(addressBook.hasDoctor(MAIN_DOCTOR));
    }

    @Test
    public void addDoctor_duplicateDoctor_throwsDuplicatePersonException() {
        addressBook.addDoctor(MAIN_DOCTOR);
        assertThrows(DuplicatePersonException.class, () -> addressBook.addDoctor(MAIN_DOCTOR));
    }

    @Test
    public void hasDoctor_nullDoctor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasDoctor(null));
    }

    @Test
    public void hasDoctor_doctorNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasDoctor(MAIN_DOCTOR));
    }

    @Test
    public void hasDoctor_doctorInAddressBook_returnsTrue() {
        addressBook.addDoctor(MAIN_DOCTOR);
        assertTrue(addressBook.hasDoctor(MAIN_DOCTOR));
    }

    @Test
    public void hasDoctor_doctorWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addDoctor(MAIN_DOCTOR);
        Doctor editedDoctor = new DoctorBuilder(MAIN_DOCTOR).withDepartment(MAIN_DOCTOR.getDepartment() + "s")
            .build();
        assertTrue(addressBook.hasDoctor(editedDoctor));
    }

    @Test
    public void getDoctorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getDoctorListSortedById().remove(0));
    }

    @Test
    void removeDoctor_nonExistentDoctor_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.removeDoctor(MAIN_DOCTOR));
    }

    @Test
    void removeDoctor_existingDoctor_doesNotThrow() {
        addressBook.addDoctor(MAIN_DOCTOR);
        assertDoesNotThrow(() -> addressBook.removeDoctor(MAIN_DOCTOR));
    }

    @Test
    public void resetData_withDuplicateDoctors_throwsDuplicatePersonException() {
        // Two doctors with the same id
        List<Doctor> newDoctors = Arrays.asList(MAIN_DOCTOR, MAIN_DOCTOR);
        AddressBookStub newData = new AddressBookStub(getTypicalActivities(), newDoctors,
            getTypicalPatients());

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void getActivityList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getActivityListSortedById().remove(0));
    }

    @Test
    public void addActivity_newActivity_returnsTrue() {
        addressBook.addActivity(MEETING);
        assertTrue(addressBook.hasActivity(MEETING));
    }

    @Test
    public void addActivity_duplicateActivity_throwsDuplicateActivityException() {
        addressBook.addActivity(MEETING);
        assertThrows(DuplicateActivityFoundException.class, () -> addressBook.addActivity(DUPLICATE_ACTIVITY));
    }

    @Test
    public void addActivity_conflictingActivity_throwsConflictingActivityException() {
        addressBook.addActivity(MEETING);
        assertThrows(ConflictingActivityException.class, () -> addressBook.addActivity(CONFLICTING_MEETING));
    }

    @Test
    public void hasActivity_duplicateIdNewActivity_returnsTrue() {
        addressBook.addActivity(MEETING);
        Activity meeting = new ActivityBuilder().withId(1).build();
        assertTrue(addressBook.hasActivity(meeting));
    }

    @Test
    public void hasActivity_emptyList_returnsFalse() {
        assertFalse(addressBook.hasActivity(MEETING));
    }

    @Test
    public void getActivityListSortedStartTime_call_returnsTrue() {
        assertDoesNotThrow(addressBook::getActivityListSortedByStartTime);
    }

    @Test
    public void getActivityListSortedStartTime_sortedCorrectly_returnsTrue() {
        // inserted sequentially
        addressBook.addActivity(PAST_ACTIVITY);
        addressBook.addActivity(MEETING);

        assertEquals(List.of(PAST_ACTIVITY, MEETING), addressBook.getActivityListSortedByStartTime());
    }

    @Test
    void removeActivity_nonExistentActivity_throwsActivityNotFound() {
        assertThrows(ActivityNotFoundException.class, () -> addressBook.removeActivity(MEETING));
    }

    @Test
    void removeActivity_existentActivity_doesNotThrow() {
        addressBook.addActivity(MEETING);
        assertDoesNotThrow(() -> addressBook.removeActivity(MEETING));
    }

    @Test
    void removeActivity_existentActivitySameId_doesNotThrow() {
        addressBook.addActivity(MEETING);
        Activity meeting = new ActivityBuilder().withId(1).build();
        assertDoesNotThrow(() -> addressBook.removeActivity(meeting));
    }

    @Test
    void getNewActivityId_emptyList_return1() {
        assertEquals(1, addressBook.getNewActivityId());
    }

    @Test
    void getNewActivityId_oneItemList_return2() {
        addressBook.addActivity(MEETING);
        assertEquals(2, addressBook.getNewActivityId());
    }

    @Test
    void hasNewDoctorId_emptyList_returnsTrue() {
        assertTrue(addressBook.hasNewDoctorId());
    }

    @Test
    void hasNewDoctorId_oneItemInList_returnsTrue() {
        addressBook.addDoctor(MAIN_DOCTOR);
        assertTrue(addressBook.hasNewDoctorId());
    }

    @Test
    void hasNewDoctorId_maxItemInList_returnsFalse() {
        for (int i = 1; i <= Id.MAXIMUM_ASSIGNABLE_IDS; i++) {
            Doctor toAdd = new DoctorBuilder().withId(i).build();
            addressBook.addDoctor(toAdd);
        }
        assertFalse(addressBook.hasNewDoctorId());
    }

    @Test
    void getNewDoctorId_emptyList_returns1() {
        assertEquals(1, addressBook.getNewDoctorId());
    }

    @Test
    void getNewDoctorId_twoItemList_returns3() {
        addressBook.addDoctor(MAIN_DOCTOR);
        addressBook.addDoctor(OTHER_DOCTOR);
        assertEquals(3, addressBook.getNewDoctorId());
    }

    @Test
    void getNewDoctorId_fourItemListRemoveId2_returns2() {
        addressBook.addDoctor(MAIN_DOCTOR);
        addressBook.addDoctor(OTHER_DOCTOR);
        addressBook.addDoctor(THIRD_DOCTOR);
        addressBook.addDoctor(NOT_IN_TYPICAL_DOCTOR);
        addressBook.removeDoctor(OTHER_DOCTOR);
        assertEquals(2, addressBook.getNewDoctorId());
    }

    @Test
    void getNewDoctorId_maxListSize_throwsMaxListCapacityExceededException() {
        for (int i = 1; i <= Id.MAXIMUM_ASSIGNABLE_IDS; i++) {
            Doctor toAdd = new DoctorBuilder().withId(i).build();
            addressBook.addDoctor(toAdd);
        }
        assertThrows(MaxAddressBookCapacityReached.class, addressBook::getNewDoctorId);
    }

    @Test
    void hasNewPatientId_emptyList_returnsTrue() {
        assertTrue(addressBook.hasNewPatientId());
    }

    @Test
    void hasNewPatientId_oneItemInList_returnsTrue() {
        addressBook.addPatient(MAIN_PATIENT);
        assertTrue(addressBook.hasNewPatientId());
    }

    @Test
    void hasNewPatientId_maxItemInList_returnsFalse() {
        for (int i = 1; i <= Id.MAXIMUM_ASSIGNABLE_IDS; i++) {
            Patient toAdd = new PatientBuilder().withId(i).build();
            addressBook.addPatient(toAdd);
        }
        assertFalse(addressBook.hasNewPatientId());
    }

    @Test
    void getNewPatientId_emptyList_returns1() {
        assertEquals(1, addressBook.getNewPatientId());
    }

    @Test
    void getNewPatientId_twoItemList_returns3() {
        addressBook.addPatient(MAIN_PATIENT);
        addressBook.addPatient(OTHER_PATIENT);
        assertEquals(3, addressBook.getNewPatientId());
    }

    @Test
    void getNewPatientId_fourItemListRemoveId2_returns2() {
        addressBook.addPatient(MAIN_PATIENT);
        addressBook.addPatient(OTHER_PATIENT);
        addressBook.addPatient(THIRD_PATIENT);
        addressBook.addPatient(NOT_IN_TYPICAL_PATIENT);
        addressBook.removePatient(OTHER_PATIENT);
        assertEquals(2, addressBook.getNewPatientId());
    }

    @Test
    void getNewPatientId_maxListSize_throwsMaxListCapacityExceededException() {
        for (int i = 1; i <= Id.MAXIMUM_ASSIGNABLE_IDS; i++) {
            Patient toAdd = new PatientBuilder().withId(i).build();
            addressBook.addPatient(toAdd);
        }
        assertThrows(MaxAddressBookCapacityReached.class, addressBook::getNewPatientId);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObjectProperty<UserProfile> userProfile = new SimpleObjectProperty<>();
        private final ObservableList<Activity> activities = FXCollections.observableArrayList();
        private final ObservableList<Doctor> doctors = FXCollections.observableArrayList();
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();

        AddressBookStub(Collection<Activity> activities,
                        Collection<Doctor> doctors, Collection<Patient> patients) {
            this.userProfile.setValue(SampleDataUtil.getSampleUserProfile());
            this.activities.setAll(activities);
            this.doctors.setAll(doctors);
            this.patients.setAll(patients);
        }

        AddressBookStub(UserProfile userProfile, Collection<Activity> activities,
                        Collection<Doctor> doctors, Collection<Patient> patients) {
            this.userProfile.setValue(userProfile);
            this.activities.setAll(activities);
            this.doctors.setAll(doctors);
            this.patients.setAll(patients);
        }

        @Override
        public UserProfile getUserProfile() {
            return userProfile.getValue();
        }

        @Override
        public ObservableValue<UserProfile> getObservableUserProfile() {
            return userProfile;
        }

        @Override
        public ObservableList<Doctor> getDoctorListSortedById() {
            return doctors;
        }

        @Override
        public ObservableList<Patient> getPatientListSortedById() {
            return patients;
        }

        @Override
        public ObservableList<Activity> getActivityListSortedById() {
            return activities;
        }

        @Override
        public ObservableList<Activity> getActivityListSortedByStartTime() {
            return activities.sorted();
        }
    }
}
