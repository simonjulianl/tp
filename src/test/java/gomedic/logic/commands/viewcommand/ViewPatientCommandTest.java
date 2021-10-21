package gomedic.logic.commands.viewcommand;

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
 * {@code ViewCommand}.
 */
public class ViewPatientCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Patient patientToView = model.getFilteredPatientList().get(INDEX_FIRST.getZeroBased());
        ViewPatientCommand viewPatientCommand = new ViewPatientCommand(patientToView.getId());

        String expectedMessage = String.format(ViewPatientCommand.MESSAGE_VIEW_PATIENT_SUCCESS, patientToView);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(viewPatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        ViewPatientCommand viewPatientCommand = new ViewPatientCommand(new PatientId(999));

        CommandTestUtil.assertCommandFailure(viewPatientCommand, model, Messages.MESSAGE_INVALID_PATIENT_ID);
    }

    @Test
    public void equals() {
        ViewPatientCommand viewFirstCommand = new ViewPatientCommand(new PatientId(999));

        // same object -> returns true
        assertEquals(viewFirstCommand, viewFirstCommand);

        // same id -> returns true
        ViewPatientCommand viewFirstCommandCopy = new ViewPatientCommand(new PatientId(999));
        assertEquals(viewFirstCommand, viewFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, viewFirstCommand);

        // null -> returns false
        assertNotEquals(null, viewFirstCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPatient(Model model) {
        model.updateFilteredPatientList(p -> false);

        assertTrue(model.getFilteredPatientList().isEmpty());
    }
}
