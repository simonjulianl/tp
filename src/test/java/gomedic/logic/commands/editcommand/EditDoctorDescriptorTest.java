package gomedic.logic.commands.editcommand;

import static gomedic.logic.commands.CommandTestUtil.DESC_MAIN_DOCTOR;
import static gomedic.logic.commands.CommandTestUtil.DESC_OTHER_DOCTOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import gomedic.testutil.TypicalPersons;
import gomedic.testutil.editdescriptorbuilder.EditDoctorDescriptorBuilder;

public class EditDoctorDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditDoctorCommand.EditDoctorDescriptor descriptorWithSameValues =
                new EditDoctorCommand.EditDoctorDescriptor(DESC_MAIN_DOCTOR);
        assertEquals(descriptorWithSameValues, DESC_MAIN_DOCTOR);

        // same object -> returns true
        assertEquals(DESC_MAIN_DOCTOR, DESC_MAIN_DOCTOR);

        // null -> returns false
        assertNotEquals(DESC_MAIN_DOCTOR, null);

        // different types -> returns false
        assertNotEquals(DESC_MAIN_DOCTOR, 5);

        // different values -> returns false
        assertNotEquals(DESC_MAIN_DOCTOR, DESC_OTHER_DOCTOR);

        // different name -> returns false
        EditDoctorCommand.EditDoctorDescriptor editedMainDoctor =
                new EditDoctorDescriptorBuilder(DESC_MAIN_DOCTOR)
                        .withName(TypicalPersons.OTHER_DOCTOR.getName().fullName)
                        .build();
        assertNotEquals(editedMainDoctor, DESC_MAIN_DOCTOR);

        // different phone -> returns false
        editedMainDoctor = new EditDoctorDescriptorBuilder(DESC_MAIN_DOCTOR)
                .withPhone(TypicalPersons.OTHER_DOCTOR.getPhone().value).build();
        assertNotEquals(editedMainDoctor, DESC_MAIN_DOCTOR);

        // different department -> returns false
        editedMainDoctor = new EditDoctorDescriptorBuilder(DESC_MAIN_DOCTOR)
                .withDepartment(TypicalPersons.OTHER_DOCTOR.getDepartment().departmentName).build();
        assertNotEquals(editedMainDoctor, DESC_MAIN_DOCTOR);
    }
}
