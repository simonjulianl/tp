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

import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.exceptions.DuplicatePersonException;
import gomedic.model.person.exceptions.PersonNotFoundException;
import gomedic.testutil.modelbuilder.DoctorBuilder;

class UniqueAbstractPersonListTest {
    private final UniqueAbstractPersonList uniqueAbstractPersonList = new UniqueAbstractPersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAbstractPersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueAbstractPersonList.contains(MAIN_DOCTOR));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueAbstractPersonList.add(MAIN_DOCTOR);
        assertTrue(uniqueAbstractPersonList.contains(MAIN_DOCTOR));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueAbstractPersonList.add(MAIN_DOCTOR);

        // Doctor is same even if department changes
        Doctor editedMainDoctor = new DoctorBuilder(MAIN_DOCTOR).withDepartment("Cardiology").build();
        assertTrue(uniqueAbstractPersonList.contains(editedMainDoctor));

        // Doctor is same even if phone number changes
        editedMainDoctor = new DoctorBuilder(MAIN_DOCTOR).withPhone("99999999").build();
        assertTrue(uniqueAbstractPersonList.contains(editedMainDoctor));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAbstractPersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueAbstractPersonList.add(MAIN_DOCTOR);
        assertThrows(DuplicatePersonException.class, () -> uniqueAbstractPersonList.add(MAIN_DOCTOR));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAbstractPersonList.setPerson(null, MAIN_DOCTOR));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAbstractPersonList.setPerson(MAIN_DOCTOR, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueAbstractPersonList.setPerson(MAIN_DOCTOR, MAIN_DOCTOR));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueAbstractPersonList.add(MAIN_DOCTOR);
        uniqueAbstractPersonList.setPerson(MAIN_DOCTOR, MAIN_DOCTOR);
        UniqueAbstractPersonList expectedUniqueAbstractPersonList = new UniqueAbstractPersonList();
        expectedUniqueAbstractPersonList.add(MAIN_DOCTOR);
        assertEquals(expectedUniqueAbstractPersonList, uniqueAbstractPersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueAbstractPersonList.add(MAIN_DOCTOR);
        Doctor editedDoctor = new DoctorBuilder(MAIN_DOCTOR).withDepartment("Cardiology").build();
        uniqueAbstractPersonList.setPerson(MAIN_DOCTOR, editedDoctor);
        UniqueAbstractPersonList expectedUniqueAbstractPersonList = new UniqueAbstractPersonList();
        expectedUniqueAbstractPersonList.add(editedDoctor);
        assertEquals(expectedUniqueAbstractPersonList, uniqueAbstractPersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueAbstractPersonList.add(MAIN_DOCTOR);
        uniqueAbstractPersonList.setPerson(MAIN_DOCTOR, OTHER_DOCTOR);
        UniqueAbstractPersonList expectedUniqueAbstractPersonList = new UniqueAbstractPersonList();
        expectedUniqueAbstractPersonList.add(OTHER_DOCTOR);
        assertEquals(expectedUniqueAbstractPersonList, uniqueAbstractPersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueAbstractPersonList.add(MAIN_DOCTOR);
        uniqueAbstractPersonList.add(OTHER_DOCTOR);
        assertThrows(DuplicatePersonException.class, () -> uniqueAbstractPersonList.setPerson(MAIN_DOCTOR,
                OTHER_DOCTOR));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAbstractPersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueAbstractPersonList.remove(MAIN_DOCTOR));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueAbstractPersonList.add(MAIN_DOCTOR);
        uniqueAbstractPersonList.remove(MAIN_DOCTOR);
        UniqueAbstractPersonList expectedUniqueAbstractPersonList = new UniqueAbstractPersonList();
        assertEquals(expectedUniqueAbstractPersonList, uniqueAbstractPersonList);
    }

    @Test
    public void setPersons_nullUniqueAbstractPersonList_throwsNullPointerException() {
        assertThrows(
                NullPointerException.class, () -> uniqueAbstractPersonList.setPersons((UniqueAbstractPersonList) null));
    }

    @Test
    public void setPersons_uniqueAbstractPersonList_replacesOwnListWithProvidedUniqueAbstractPersonList() {
        uniqueAbstractPersonList.add(MAIN_DOCTOR);
        UniqueAbstractPersonList expectedUniqueAbstractPersonList = new UniqueAbstractPersonList();
        expectedUniqueAbstractPersonList.add(OTHER_DOCTOR);
        uniqueAbstractPersonList.setPersons(expectedUniqueAbstractPersonList);
        assertEquals(expectedUniqueAbstractPersonList, uniqueAbstractPersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(
                NullPointerException.class, () -> uniqueAbstractPersonList.setPersons((List<AbstractPerson>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueAbstractPersonList.add(MAIN_DOCTOR);
        List<AbstractPerson> personList = Collections.singletonList(OTHER_DOCTOR);
        uniqueAbstractPersonList.setPersons(personList);
        UniqueAbstractPersonList expectedUniqueAbstractPersonList = new UniqueAbstractPersonList();
        expectedUniqueAbstractPersonList.add(OTHER_DOCTOR);
        assertEquals(expectedUniqueAbstractPersonList, uniqueAbstractPersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<AbstractPerson> listWithDuplicatePersons = Arrays.asList(MAIN_DOCTOR, MAIN_DOCTOR);
        assertThrows(
                DuplicatePersonException.class, () -> uniqueAbstractPersonList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class, () ->
                        uniqueAbstractPersonList.asUnmodifiableObservableList().remove(0)
        );
    }
}
