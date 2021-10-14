package gomedic.logic.commands.editcommand;

import static gomedic.logic.commands.CommandTestUtil.DESC_MAIN_DOCTOR;
import static gomedic.logic.commands.CommandTestUtil.DESC_OTHER_DOCTOR;
import static gomedic.logic.commands.CommandTestUtil.assertCommandFailure;
import static gomedic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static gomedic.logic.commands.CommandTestUtil.showDoctorAtIndex;
import static gomedic.testutil.TypicalIndexes.INDEX_FIRST;
import static gomedic.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import gomedic.commons.core.Messages;
import gomedic.commons.core.index.Index;
import gomedic.logic.commands.clearcommand.ClearCommand;
import gomedic.model.AddressBook;
import gomedic.model.Model;
import gomedic.model.ModelManager;
import gomedic.model.UserPrefs;
import gomedic.model.commonfield.Id;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.doctor.DoctorId;
import gomedic.testutil.TypicalPersons;
import gomedic.testutil.editdescriptorbuilder.EditDoctorDescriptorBuilder;
import gomedic.testutil.modelbuilder.DoctorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditDoctorCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Doctor editedDoctor =
                new DoctorBuilder()
                        .withName("Different Name")
                        .withPhone("00000000")
                        .withDepartment("Different department")
                        .build();
        EditDoctorCommand.EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder(editedDoctor).build();
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(editedDoctor.getId(), descriptor);

        String expectedMessage = String.format(EditDoctorCommand.MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setDoctor(model.getFilteredDoctorList().get(0), editedDoctor);

        assertCommandSuccess(editDoctorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        // Edits some fields of the last doctor (in typicalDoctors)
        // with the fields of the 2nd doctor (in typicalDoctors)
        Index indexLastDoctor = Index.fromOneBased(model.getFilteredDoctorList().size());
        Doctor lastDoctor = model.getFilteredDoctorList().get(indexLastDoctor.getZeroBased());
        Id targetId = lastDoctor.getId();

        DoctorBuilder doctorInList = new DoctorBuilder(lastDoctor);
        Doctor editedDoctor = doctorInList.withName(TypicalPersons.OTHER_DOCTOR.getName().fullName)
                .withPhone(TypicalPersons.OTHER_DOCTOR.getPhone().value).build();

        EditDoctorCommand.EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder()
                .withName(TypicalPersons.OTHER_DOCTOR.getName().fullName)
                .withPhone(TypicalPersons.OTHER_DOCTOR.getPhone().value).build();
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(targetId, descriptor);

        String expectedMessage = String.format(EditDoctorCommand.MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setDoctor(lastDoctor, editedDoctor);

        assertCommandSuccess(editDoctorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Doctor editedDoctor = model.getFilteredDoctorList().get(INDEX_FIRST.getZeroBased());
        Id targetId = editedDoctor.getId();
        EditDoctorCommand editDoctorCommand =
                new EditDoctorCommand(targetId, new EditDoctorCommand.EditDoctorDescriptor());


        String expectedMessage = String.format(EditDoctorCommand.MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editDoctorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showDoctorAtIndex(model, INDEX_FIRST);

        Doctor doctorInFilteredList = model.getFilteredDoctorList().get(INDEX_FIRST.getZeroBased());
        Doctor editedDoctor = new DoctorBuilder(doctorInFilteredList)
                .withName(TypicalPersons.OTHER_DOCTOR.getName().fullName).build();
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(TypicalPersons.MAIN_DOCTOR.getId(),
                new EditDoctorDescriptorBuilder().withName(TypicalPersons.OTHER_DOCTOR.getName().fullName).build());

        String expectedMessage = String.format(EditDoctorCommand.MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setDoctor(model.getFilteredDoctorList().get(0), editedDoctor);

        assertCommandSuccess(editDoctorCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidDoctorIndexUnfilteredList_failure() {
        // Test with doctor id of 999 since it is not in typical address book
        Id invalidId = new DoctorId(999);
        EditDoctorCommand.EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder()
                .withName(TypicalPersons.OTHER_DOCTOR.getName().fullName)
                .build();
        EditDoctorCommand editDoctorCommand = new EditDoctorCommand(invalidId, descriptor);

        assertCommandFailure(editDoctorCommand, model, Messages.MESSAGE_INVALID_DOCTOR_ID);
    }

    @Test
    public void equals() {
        // Use the details of first doctor in typical doctors list as the source of comparison
        final EditDoctorCommand standardCommand =
                new EditDoctorCommand(TypicalPersons.MAIN_DOCTOR.getId(), DESC_MAIN_DOCTOR);

        // same values -> returns true
        EditDoctorCommand.EditDoctorDescriptor copyDescriptor =
                new EditDoctorCommand.EditDoctorDescriptor(DESC_MAIN_DOCTOR);
        EditDoctorCommand commandWithSameValues =
                new EditDoctorCommand(TypicalPersons.MAIN_DOCTOR.getId(), copyDescriptor);
        assertEquals(commandWithSameValues, standardCommand);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);

        // different types -> returns false
        assertNotEquals(new ClearCommand(), standardCommand);

        // different index -> returns false
        assertNotEquals(new EditDoctorCommand(TypicalPersons.OTHER_DOCTOR.getId(), DESC_OTHER_DOCTOR), standardCommand);

        // different descriptor -> returns false
        assertNotEquals(new EditDoctorCommand(TypicalPersons.MAIN_DOCTOR.getId(), DESC_OTHER_DOCTOR), standardCommand);
    }

}
