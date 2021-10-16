package gomedic.logic.commands.editcommand;

import static gomedic.logic.commands.CommandTestUtil.DESC_MAIN_PATIENT;
import static gomedic.logic.commands.CommandTestUtil.DESC_OTHER_PATIENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import gomedic.testutil.TypicalPersons;
import gomedic.testutil.editdescriptorbuilder.EditPatientDescriptorBuilder;

public class EditPatientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPatientCommand.EditPatientDescriptor descriptorWithSameValues =
            new EditPatientCommand.EditPatientDescriptor(DESC_MAIN_PATIENT);
        assertEquals(descriptorWithSameValues, DESC_MAIN_PATIENT);

        // same object -> returns true
        assertEquals(DESC_MAIN_PATIENT, DESC_MAIN_PATIENT);

        // null -> returns false
        assertNotEquals(DESC_MAIN_PATIENT, null);

        // different types -> returns false
        assertNotEquals(DESC_MAIN_PATIENT, 5);

        // different values -> returns false
        assertNotEquals(DESC_MAIN_PATIENT, DESC_OTHER_PATIENT);

        // different name -> returns false
        EditPatientCommand.EditPatientDescriptor editedMainPatient =
            new EditPatientDescriptorBuilder(DESC_MAIN_PATIENT)
                .withName(TypicalPersons.OTHER_PATIENT.getName().fullName)
                .build();
        assertNotEquals(editedMainPatient, DESC_MAIN_PATIENT);

        // different phone -> returns false
        editedMainPatient = new EditPatientDescriptorBuilder(DESC_MAIN_PATIENT)
            .withPhone(TypicalPersons.OTHER_PATIENT.getPhone().value).build();
        assertNotEquals(editedMainPatient, DESC_MAIN_PATIENT);

        // different age -> returns false
        editedMainPatient = new EditPatientDescriptorBuilder(DESC_MAIN_PATIENT)
            .withAge(TypicalPersons.OTHER_PATIENT.getAge().age).build();
        assertNotEquals(editedMainPatient, DESC_MAIN_PATIENT);

        // different bloodType -> returns false
        editedMainPatient = new EditPatientDescriptorBuilder(DESC_MAIN_PATIENT)
            .withBloodType(TypicalPersons.OTHER_PATIENT.getBloodType().bloodType).build();
        assertNotEquals(editedMainPatient, DESC_MAIN_PATIENT);

        // different gender -> returns false
        editedMainPatient = new EditPatientDescriptorBuilder(DESC_MAIN_PATIENT)
            .withGender(TypicalPersons.OTHER_PATIENT.getGender().gender).build();
        assertNotEquals(editedMainPatient, DESC_MAIN_PATIENT);

        // different height -> returns false
        editedMainPatient = new EditPatientDescriptorBuilder(DESC_MAIN_PATIENT)
            .withHeight(TypicalPersons.OTHER_PATIENT.getHeight().height).build();
        assertNotEquals(editedMainPatient, DESC_MAIN_PATIENT);

        // different weight -> returns false
        editedMainPatient = new EditPatientDescriptorBuilder(DESC_MAIN_PATIENT)
            .withWeight(TypicalPersons.OTHER_PATIENT.getWeight().weight).build();
        assertNotEquals(editedMainPatient, DESC_MAIN_PATIENT);

        // different medicalConditions -> returns false
        editedMainPatient = new EditPatientDescriptorBuilder(DESC_MAIN_PATIENT)
            .withMedicalConditions("diabetes").build();
        assertNotEquals(editedMainPatient, DESC_MAIN_PATIENT);
    }
}
