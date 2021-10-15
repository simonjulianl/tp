package gomedic.logic.commands.editcommand;

import static gomedic.logic.commands.CommandTestUtil.DESC_MAIN_PATIENT;
import static gomedic.logic.commands.CommandTestUtil.DESC_OTHER_PATIENT;
import static gomedic.logic.commands.CommandTestUtil.assertCommandFailure;
import static gomedic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static gomedic.logic.commands.CommandTestUtil.showPatientAtIndex;
import static gomedic.testutil.TypicalIndexes.INDEX_FIRST;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.commons.core.index.Index;
import gomedic.logic.commands.clearcommand.ClearCommand;
import gomedic.model.AddressBook;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;
import gomedic.model.commonfield.Id;
import gomedic.model.person.patient.Patient;
import gomedic.model.person.patient.PatientId;
import gomedic.model.tag.Tag;
import gomedic.testutil.TypicalPersons;
import gomedic.testutil.editdescriptorbuilder.EditPatientDescriptorBuilder;
import gomedic.testutil.modelbuilder.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditPatientCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Patient editedPatient =
            new PatientBuilder()
                .withName("Different Name")
                .withPhone("00000000")
                .withAge("140")
                .withBloodType("A")
                .withGender("F")
                .withHeight("173")
                .withWeight("54")
                .withMedicalConditions(new HashSet<Tag>())
                .build();
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(editedPatient).build();
        EditPatientCommand editPatientCommand = new EditPatientCommand(editedPatient.getId(), descriptor);

        String expectedMessage = String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);

        assertCommandSuccess(editPatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        // Edits some fields of the last patient (in typicalPatients)
        // with the fields of the 2nd patient (in typicalPatients)
        Index indexLastPatient = Index.fromOneBased(model.getFilteredPatientList().size());
        Patient lastPatient = model.getFilteredPatientList().get(indexLastPatient.getZeroBased());
        Id targetId = lastPatient.getId();

        PatientBuilder patientInList = new PatientBuilder(lastPatient);
        Patient editedPatient = patientInList.withName(TypicalPersons.OTHER_PATIENT.getName().fullName)
            .withPhone(TypicalPersons.OTHER_PATIENT.getPhone().value).build();

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
            .withName(TypicalPersons.OTHER_PATIENT.getName().fullName)
            .withPhone(TypicalPersons.OTHER_PATIENT.getPhone().value).build();
        EditPatientCommand editPatientCommand = new EditPatientCommand(targetId, descriptor);

        String expectedMessage = String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(lastPatient, editedPatient);

        assertCommandSuccess(editPatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Patient editedPatient = model.getFilteredPatientList().get(INDEX_FIRST.getZeroBased());
        Id targetId = editedPatient.getId();
        EditPatientCommand editPatientCommand =
            new EditPatientCommand(targetId, new EditPatientCommand.EditPatientDescriptor());


        String expectedMessage = String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editPatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPatientAtIndex(model, INDEX_FIRST);

        Patient patientInFilteredList = model.getFilteredPatientList().get(INDEX_FIRST.getZeroBased());
        Patient editedPatient = new PatientBuilder(patientInFilteredList)
            .withName(TypicalPersons.OTHER_PATIENT.getName().fullName).build();
        EditPatientCommand editPatientCommand = new EditPatientCommand(TypicalPersons.MAIN_PATIENT.getId(),
            new EditPatientDescriptorBuilder().withName(TypicalPersons.OTHER_PATIENT.getName().fullName).build());

        String expectedMessage = String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);

        assertCommandSuccess(editPatientCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidPatientIndexUnfilteredList_failure() {
        // Test with patient id of 999 since it is not in typical address book
        Id invalidId = new PatientId(999);
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
            .withName(TypicalPersons.OTHER_PATIENT.getName().fullName)
            .build();
        EditPatientCommand editPatientCommand = new EditPatientCommand(invalidId, descriptor);

        assertCommandFailure(editPatientCommand, model, Messages.MESSAGE_INVALID_PATIENT_ID);
    }

    @Test
    public void equals() {
        // Use the details of first patient in typical patients list as the source of comparison
        final EditPatientCommand standardCommand =
            new EditPatientCommand(TypicalPersons.MAIN_PATIENT.getId(), DESC_MAIN_PATIENT);

        // same values -> returns true
        EditPatientCommand.EditPatientDescriptor copyDescriptor =
            new EditPatientCommand.EditPatientDescriptor(DESC_MAIN_PATIENT);
        EditPatientCommand commandWithSameValues =
            new EditPatientCommand(TypicalPersons.MAIN_PATIENT.getId(), copyDescriptor);
        assertEquals(commandWithSameValues, standardCommand);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);

        // different types -> returns false
        assertNotEquals(new ClearCommand(), standardCommand);

        // different index -> returns false
        assertNotEquals(new EditPatientCommand(TypicalPersons.OTHER_PATIENT.getId(), DESC_OTHER_PATIENT),
            standardCommand);

        // different descriptor -> returns false
        assertNotEquals(new EditPatientCommand(TypicalPersons.MAIN_PATIENT.getId(), DESC_OTHER_PATIENT),
            standardCommand);
    }

}
