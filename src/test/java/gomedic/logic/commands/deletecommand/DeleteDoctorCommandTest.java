package gomedic.logic.commands.deletecommand;

import static gomedic.testutil.TypicalIndexes.INDEX_FIRST;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.doctor.DoctorId;
import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.CommandTestUtil;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;
import gomedic.model.activity.Activity;
import gomedic.model.activity.ActivityId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteDoctorCommand}.
 */
public class DeleteDoctorCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Doctor doctorToDelete = model.getFilteredDoctorList().get(INDEX_FIRST.getZeroBased());
        DeleteDoctorCommand deleteDoctorCommand = new DeleteDoctorCommand(doctorToDelete.getId());

        String expectedMessage = String.format(DeleteDoctorCommand.MESSAGE_DELETE_DOCTOR_SUCCESS, doctorToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteDoctor(doctorToDelete);

        CommandTestUtil.assertCommandSuccess(deleteDoctorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        DeleteDoctorCommand deleteDoctorCommand = new DeleteDoctorCommand(new DoctorId(999));

        CommandTestUtil.assertCommandFailure(deleteDoctorCommand, model, Messages.MESSAGE_INVALID_DOCTOR_ID);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showDoctorAtIndex(model, INDEX_FIRST);

        Doctor doctorToDelete = model.getFilteredDoctorList().get(INDEX_FIRST.getZeroBased());
        DeleteDoctorCommand deleteDoctorCommand = new DeleteDoctorCommand(doctorToDelete.getId());

        String expectedMessage = String.format(DeleteDoctorCommand.MESSAGE_DELETE_DOCTOR_SUCCESS, doctorToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteDoctor(doctorToDelete);
        showNoDoctor(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteDoctorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteDoctorCommand deleteFirstCommand = new DeleteDoctorCommand(new DoctorId(999));

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same id -> returns true
        DeleteDoctorCommand deleteFirstCommandCopy = new DeleteDoctorCommand(new DoctorId(999));
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoDoctor(Model model) {
        model.updateFilteredDoctorList(p -> false);

        assertTrue(model.getFilteredDoctorList().isEmpty());
    }
}
