package gomedic.model.person;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalPersons.MAIN_DOCTOR;
import static gomedic.testutil.TypicalPersons.OTHER_DOCTOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.exceptions.MaxAddressBookCapacityReached;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.exceptions.DuplicatePersonException;
import gomedic.model.person.exceptions.PersonNotFoundException;
import gomedic.testutil.modelbuilder.DoctorBuilder;

class UniquePersonListTest {
    private final UniquePersonList<Doctor> uniquePersonList = new UniquePersonList<>();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(MAIN_DOCTOR));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(MAIN_DOCTOR);
        assertTrue(uniquePersonList.contains(MAIN_DOCTOR));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(MAIN_DOCTOR);
        // Doctor is same even if name changes
        Doctor editedMainDoctor = new DoctorBuilder(MAIN_DOCTOR).withName("Smith Joe").build();
        assertTrue(uniquePersonList.contains(editedMainDoctor));

        // Doctor is same even if department changes
        editedMainDoctor = new DoctorBuilder(MAIN_DOCTOR).withDepartment("Cardiology").build();
        assertTrue(uniquePersonList.contains(editedMainDoctor));

        // Doctor is same even if phone number changes
        editedMainDoctor = new DoctorBuilder(MAIN_DOCTOR).withPhone("99999999").build();
        assertTrue(uniquePersonList.contains(editedMainDoctor));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(MAIN_DOCTOR);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(MAIN_DOCTOR));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, MAIN_DOCTOR));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(MAIN_DOCTOR, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(MAIN_DOCTOR, MAIN_DOCTOR));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(MAIN_DOCTOR);
        uniquePersonList.setPerson(MAIN_DOCTOR, MAIN_DOCTOR);
        UniquePersonList<Doctor> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(MAIN_DOCTOR);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(MAIN_DOCTOR);
        Doctor editedDoctor = new DoctorBuilder(MAIN_DOCTOR).withDepartment("Cardiology").build();
        uniquePersonList.setPerson(MAIN_DOCTOR, editedDoctor);
        UniquePersonList<Doctor> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(editedDoctor);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(MAIN_DOCTOR);
        uniquePersonList.setPerson(MAIN_DOCTOR, OTHER_DOCTOR);
        UniquePersonList<Doctor> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(OTHER_DOCTOR);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(MAIN_DOCTOR);
        uniquePersonList.add(OTHER_DOCTOR);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(MAIN_DOCTOR,
                OTHER_DOCTOR));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(MAIN_DOCTOR));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(MAIN_DOCTOR);
        uniquePersonList.remove(MAIN_DOCTOR);
        UniquePersonList<Doctor> expectedUniquePersonList = new UniquePersonList<>();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullUniqueAbstractPersonList_throwsNullPointerException() {
        assertThrows(
                NullPointerException.class, () ->
                        uniquePersonList.setPersons((UniquePersonList<Doctor>) null));
    }

    @Test
    public void setPersons_uniqueAbstractPersonList_replacesOwnListWithProvidedUniqueAbstractPersonList() {
        uniquePersonList.add(MAIN_DOCTOR);
        UniquePersonList<Doctor> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(OTHER_DOCTOR);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(
                NullPointerException.class, () -> uniquePersonList.setPersons((List<Doctor>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(MAIN_DOCTOR);
        List<Doctor> personList = Collections.singletonList(OTHER_DOCTOR);
        uniquePersonList.setPersons(personList);
        UniquePersonList<Doctor> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(OTHER_DOCTOR);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Doctor> listWithDuplicatePersons = Arrays.asList(MAIN_DOCTOR, MAIN_DOCTOR);
        assertThrows(
                DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class, () ->
                        uniquePersonList.asUnmodifiableSortedByIdObservableList().remove(0)
        );
    }

    @Test
    void hasNewId_emptyList_returnsTrue() {
        assertTrue(uniquePersonList.hasNewId());
    }

    @Test
    void hasNewId_oneLessThanMaxCapacity_returnsTrue() {
        for (int i = 1; i < Id.MAXIMUM_ASSIGNABLE_IDS; i++) {
            Doctor toAdd = new DoctorBuilder().withId(i).build();
            uniquePersonList.add(toAdd);
        }
        assertTrue(uniquePersonList.hasNewId());
    }

    @Test
    void hasNewId_listAtMaxCapacity_returnsFalse() {
        for (int i = 1; i <= Id.MAXIMUM_ASSIGNABLE_IDS; i++) {
            Doctor toAdd = new DoctorBuilder().withId(i).build();
            uniquePersonList.add(toAdd);
        }
        assertFalse(uniquePersonList.hasNewId());
    }

    @Test
    void getNewId_emptyList_returns1() {
        assertEquals(1, uniquePersonList.getNewId());
    }

    @Test
    void getNewId_fourAlreadyInList_returns5() {
        for (int i = 1; i <= 4; i++) {
            Doctor toAdd = new DoctorBuilder().withId(i).build();
            uniquePersonList.add(toAdd);
        }

        assertEquals(5, uniquePersonList.getNewId());
    }

    @Test
    void getNewId_fourAlreadyInListRemoveId2_returns2() {
        Doctor toRemove = new DoctorBuilder().withId(2).build();

        for (int i = 1; i <= 4; i++) {
            Doctor toAdd = new DoctorBuilder().withId(i).build();
            uniquePersonList.add(toAdd);
        }

        uniquePersonList.remove(toRemove);

        assertEquals(2, uniquePersonList.getNewId());
    }

    @Test
    void getNewId_listContainsOneLessThanMax_returnsMax() {
        for (int i = 1; i < Id.MAXIMUM_ASSIGNABLE_IDS; i++) {
            Doctor toAdd = new DoctorBuilder().withId(i).build();
            uniquePersonList.add(toAdd);
        }

        assertEquals(Id.MAXIMUM_ASSIGNABLE_IDS, uniquePersonList.getNewId());
    }

    @Test
    void getNewId_listContainsMax_throwsMaxAddressBookCapacityReached() {
        for (int i = 1; i <= Id.MAXIMUM_ASSIGNABLE_IDS; i++) {
            Doctor toAdd = new DoctorBuilder().withId(i).build();
            uniquePersonList.add(toAdd);
        }

        assertThrows(MaxAddressBookCapacityReached.class, uniquePersonList::getNewId);
    }
}
