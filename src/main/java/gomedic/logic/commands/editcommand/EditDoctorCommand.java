package gomedic.logic.commands.editcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static gomedic.logic.parser.CliSyntax.PREFIX_ID;
import static gomedic.logic.parser.CliSyntax.PREFIX_NAME;
import static gomedic.logic.parser.CliSyntax.PREFIX_PHONE;
import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_DOCTOR;
import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import gomedic.commons.core.Messages;
import gomedic.commons.util.CollectionUtil;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.doctor.DoctorId;

/**
 * Edits the details of an existing doctor in the address book.
 */
public class EditDoctorCommand extends Command {

    public static final String COMMAND_WORD = "edit" + " " + PREFIX_TYPE_DOCTOR;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the doctor identified "
            + "by the index number used in the displayed doctor list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "[" + PREFIX_ID + "ID] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_DEPARTMENT + "DEPARTMENT] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + " D001 "
            + PREFIX_NAME + "John Snow "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_DEPARTMENT + "Neurology";

    public static final String MESSAGE_EDIT_DOCTOR_SUCCESS = "Edited Doctor: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Must provide at least one field (other than ID).";

    private final Id targetId;
    private final EditDoctorDescriptor editDoctorDescriptor;

    /**
     * @param targetId of the doctor in the filtered doctor list to edit
     * @param editDoctorDescriptor details to edit the doctor with
     */
    public EditDoctorCommand(Id targetId, EditDoctorDescriptor editDoctorDescriptor) {
        requireNonNull(targetId);
        requireNonNull(editDoctorDescriptor);

        this.targetId = targetId;
        this.editDoctorDescriptor = new EditDoctorDescriptor(editDoctorDescriptor);
    }

    /**
     * Creates and returns a {@code Doctor} with the details of {@code doctorToEdit}
     * edited with {@code editDoctorDescriptor}.
     */
    private static Doctor createEditedDoctor(Doctor doctorToEdit, EditDoctorDescriptor editDoctorDescriptor) {
        requireNonNull(doctorToEdit);

        DoctorId doctorId = doctorToEdit.getId();
        Name updatedName = editDoctorDescriptor.getName().orElse(doctorToEdit.getName());
        Phone updatedPhone = editDoctorDescriptor.getPhone().orElse(doctorToEdit.getPhone());
        Department updatedDepartment = editDoctorDescriptor.getDepartment().orElse(doctorToEdit.getDepartment());

        return new Doctor(updatedName, updatedPhone, doctorId, updatedDepartment);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Doctor> lastShownList = model.getFilteredDoctorList();

        Doctor doctorToEdit = lastShownList
                .stream()
                .filter(doctor -> doctor.getId().toString().equals(targetId.toString()))
                .findFirst()
                .orElse(null);

        if (doctorToEdit == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_DOCTOR_ID);
        }

        Doctor editedDoctor = createEditedDoctor(doctorToEdit, editDoctorDescriptor);

        model.setDoctor(doctorToEdit, editedDoctor);
        model.setViewPatient(null);
        model.updateFilteredDoctorList(PREDICATE_SHOW_ALL_ITEMS);
        model.setModelBeingShown(ModelItem.DOCTOR);
        return new CommandResult(String.format(MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditDoctorCommand)) {
            return false;
        }

        // state check
        EditDoctorCommand e = (EditDoctorCommand) other;
        return targetId.equals(e.targetId)
                && editDoctorDescriptor.equals(e.editDoctorDescriptor);
    }

    /**
     * Stores the details to edit the doctor with. Each non-empty field value will replace the
     * corresponding field value of the doctor.
     */
    public static class EditDoctorDescriptor {
        private Name name;
        private Phone phone;
        private Department department;

        public EditDoctorDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditDoctorDescriptor(EditDoctorDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setDepartment(toCopy.department);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, department);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Department> getDepartment() {
            return Optional.ofNullable(department);
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDoctorDescriptor)) {
                return false;
            }

            // state check
            EditDoctorDescriptor e = (EditDoctorDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getDepartment().equals(e.getDepartment());
        }
    }
}
