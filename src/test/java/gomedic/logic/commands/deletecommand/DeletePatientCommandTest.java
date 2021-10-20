package gomedic.logic.commands.deletecommand;

import static gomedic.testutil.TypicalIndexes.INDEX_FIRST;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;
import gomedic.model.person.patient.Patient;
import gomedic.model.person.patient.PatientId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePatientCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Patient patientToDelete = model.getFilteredPatientList().get(INDEX_FIRST.getZeroBased());
        DeletePatientCommand deletePatientCommand = new DeletePatientCommand(patientToDelete.getId());

        String expectedMessage = String.format(DeletePatientCommand.MESSAGE_DELETE_PATIENT_SUCCESS, patientToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePatient(patientToDelete);
        expectedModel.deletePatientAssociatedAppointments(patientToDelete);
        CommandTestUtil.assertCommandSuccess(deletePatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        DeletePatientCommand deletePatientCommand = new DeletePatientCommand(new PatientId(999));

        CommandTestUtil.assertCommandFailure(deletePatientCommand, model, Messages.MESSAGE_INVALID_PATIENT_ID);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showPatientAtIndex(model, INDEX_FIRST);

        Patient patientToDelete = model.getFilteredPatientList().get(INDEX_FIRST.getZeroBased());
        DeletePatientCommand deletePatientCommand = new DeletePatientCommand(patientToDelete.getId());

        String expectedMessage = String.format(DeletePatientCommand.MESSAGE_DELETE_PATIENT_SUCCESS, patientToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePatient(patientToDelete);
        expectedModel.deletePatientAssociatedAppointments(patientToDelete);
        showNoPatient(expectedModel);
        CommandTestUtil.assertCommandSuccess(deletePatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeletePatientCommand deleteFirstCommand = new DeletePatientCommand(new PatientId(999));

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same id -> returns true
        DeletePatientCommand deleteFirstCommandCopy = new DeletePatientCommand(new PatientId(999));
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPatient(Model model) {
        model.updateFilteredPatientList(p -> false);

        assertTrue(model.getFilteredPatientList().isEmpty());
    }
}
