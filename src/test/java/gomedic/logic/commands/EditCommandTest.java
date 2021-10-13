package gomedic.logic.commands;

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
import gomedic.testutil.EditDoctorDescriptorBuilder;
import gomedic.testutil.TypicalPersons;
import gomedic.testutil.modelbuilder.DoctorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Doctor editedDoctor = new DoctorBuilder().build();
        EditCommand.EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder(editedDoctor).build();
        EditCommand editCommand = new EditCommand(editedDoctor.getId(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setDoctor(model.getFilteredDoctorList().get(0), editedDoctor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastDoctor = Index.fromOneBased(model.getFilteredDoctorList().size());
        Doctor lastDoctor = model.getFilteredDoctorList().get(indexLastDoctor.getZeroBased());
        Id targetId = lastDoctor.getId();

        DoctorBuilder doctorInList = new DoctorBuilder(lastDoctor);
        Doctor editedDoctor = doctorInList.withName(TypicalPersons.OTHER_DOCTOR.getName().fullName)
                .withPhone(TypicalPersons.OTHER_DOCTOR.getPhone().value).build();

        EditCommand.EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder()
                .withName(TypicalPersons.OTHER_DOCTOR.getName().fullName)
                .withPhone(TypicalPersons.OTHER_DOCTOR.getPhone().value).build();
        EditCommand editCommand = new EditCommand(targetId, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setDoctor(lastDoctor, editedDoctor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Doctor editedDoctor = model.getFilteredDoctorList().get(INDEX_FIRST.getZeroBased());
        Id targetId = editedDoctor.getId();
        EditCommand editCommand = new EditCommand(targetId, new EditCommand.EditDoctorDescriptor());


        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showDoctorAtIndex(model, INDEX_FIRST);

        Doctor doctorInFilteredList = model.getFilteredDoctorList().get(INDEX_FIRST.getZeroBased());
        Doctor editedDoctor = new DoctorBuilder(doctorInFilteredList)
                .withName(TypicalPersons.OTHER_DOCTOR.getName().fullName).build();
        EditCommand editCommand = new EditCommand(TypicalPersons.MAIN_DOCTOR.getId(),
                new EditDoctorDescriptorBuilder().withName(TypicalPersons.OTHER_DOCTOR.getName().fullName).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setDoctor(model.getFilteredDoctorList().get(0), editedDoctor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidDoctorIndexUnfilteredList_failure() {
        // Test with doctor id of 999 since it is not in typical address book
        Id invalidId = new DoctorId(999);
        EditCommand.EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder()
                .withName(TypicalPersons.OTHER_DOCTOR.getName().fullName)
                .build();
        EditCommand editCommand = new EditCommand(invalidId, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_DOCTOR_ID);
    }

    @Test
    public void equals() {

        final EditCommand standardCommand = new EditCommand(TypicalPersons.MAIN_DOCTOR.getId(), DESC_MAIN_DOCTOR);

        // same values -> returns true
        EditCommand.EditDoctorDescriptor copyDescriptor = new EditCommand.EditDoctorDescriptor(DESC_MAIN_DOCTOR);
        EditCommand commandWithSameValues = new EditCommand(TypicalPersons.MAIN_DOCTOR.getId(), copyDescriptor);
        assertEquals(commandWithSameValues, standardCommand);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);

        // different types -> returns false
        assertNotEquals(new ClearCommand(), standardCommand);

        // different index -> returns false
        assertNotEquals(new EditCommand(TypicalPersons.OTHER_DOCTOR.getId(), DESC_OTHER_DOCTOR), standardCommand);

        // different descriptor -> returns false
        assertNotEquals(new EditCommand(TypicalPersons.MAIN_DOCTOR.getId(), DESC_OTHER_DOCTOR), standardCommand);
    }

}
