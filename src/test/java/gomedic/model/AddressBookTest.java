package gomedic.model;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalActivities.CONFLICTING_MEETING;
import static gomedic.testutil.TypicalActivities.DUPLICATE_ACTIVITY;
import static gomedic.testutil.TypicalActivities.MEETING;
import static gomedic.testutil.TypicalActivities.PAST_ACTIVITY;
import static gomedic.testutil.TypicalActivities.getTypicalActivities;
import static gomedic.testutil.TypicalPersons.MAIN_DOCTOR;
import static gomedic.testutil.TypicalPersons.MAIN_PATIENT;
import static gomedic.testutil.TypicalPersons.getTypicalDoctors;
import static gomedic.testutil.TypicalPersons.getTypicalPatients;
import static gomedic.testutil.TypicalPersons.getTypicalPersons;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import gomedic.logic.commands.CommandTestUtil;
import gomedic.model.activity.Activity;
import gomedic.model.activity.exceptions.ActivityNotFoundException;
import gomedic.model.activity.exceptions.ConflictingActivityException;
import gomedic.model.activity.exceptions.DuplicateActivityFoundException;
import gomedic.model.person.Person;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.exceptions.DuplicatePersonException;
import gomedic.model.person.exceptions.PersonNotFoundException;
import gomedic.model.person.patient.Patient;
import gomedic.testutil.TypicalPersons;
import gomedic.testutil.modelbuilder.ActivityBuilder;
import gomedic.testutil.modelbuilder.DoctorBuilder;
import gomedic.testutil.modelbuilder.PatientBuilder;
import gomedic.testutil.modelbuilder.PersonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getDoctorList());
        assertEquals(Collections.emptyList(), addressBook.getPatientList());
        assertEquals(Collections.emptyList(), addressBook.getActivityList());
        assertEquals(Collections.emptyList(), addressBook.getActivityListSortedStartTime());
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

    // TODO REMOVE PERSON TESTS
    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(TypicalPersons.ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons, getTypicalActivities(), getTypicalDoctors(),
            getTypicalPatients());

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(TypicalPersons.ALICE);
        assertTrue(addressBook.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(TypicalPersons.ALICE);
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
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
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPatientList().remove(0));
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
        AddressBookStub newData = new AddressBookStub(getTypicalPersons(), getTypicalActivities(), getTypicalDoctors(),
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
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getDoctorList().remove(0));
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
        AddressBookStub newData = new AddressBookStub(getTypicalPersons(), getTypicalActivities(), newDoctors,
            getTypicalPatients());

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void getActivityList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getActivityList().remove(0));
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
        assertDoesNotThrow(addressBook::getActivityListSortedStartTime);
    }

    @Test
    public void getActivityListSortedStartTime_sortedCorrectly_returnsTrue() {
        // inserted sequentially
        addressBook.addActivity(PAST_ACTIVITY);
        addressBook.addActivity(MEETING);

        assertEquals(addressBook.getActivityList(), addressBook.getActivityListSortedStartTime());
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

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Activity> activities = FXCollections.observableArrayList();
        private final ObservableList<Doctor> doctors = FXCollections.observableArrayList();
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Activity> activities,
                        Collection<Doctor> doctors, Collection<Patient> patients) {
            this.persons.setAll(persons);
            this.activities.setAll(activities);
            this.doctors.setAll(doctors);
            this.patients.setAll(patients);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Doctor> getDoctorList() {
            return doctors;
        }

        @Override
        public ObservableList<Patient> getPatientList() {
            return patients;
        }

        @Override
        public ObservableList<Activity> getActivityList() {
            return activities;
        }

        @Override
        public ObservableList<Activity> getActivityListSortedStartTime() {
            return activities.sorted();
        }
    }
}
