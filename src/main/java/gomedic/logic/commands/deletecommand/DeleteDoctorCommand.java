package gomedic.logic.commands.deletecommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_DOCTOR;
import static java.util.Objects.requireNonNull;

import java.util.List;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.commonfield.Id;
import gomedic.model.person.doctor.Doctor;

/**
 * Deletes a doctor identified using it's displayed index from the address book.
 */
public class DeleteDoctorCommand extends Command {
    public static final String COMMAND_WORD = "delete" + " " + PREFIX_TYPE_DOCTOR;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the doctor identified by the index shown in the doctor list.\n"
            + "Parameters: INDEX (must be exactly the same as shown in the list, e.g. D001)\n"
            + "Example: " + COMMAND_WORD + " D001";

    public static final String MESSAGE_DELETE_DOCTOR_SUCCESS = "Deleted Doctor: %1$s";

    private final Id targetId;

    public DeleteDoctorCommand(Id targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Doctor> lastShownList = model.getFilteredDoctorList();

        Doctor doctorToDelete = lastShownList
                .stream()
                .filter(doctor -> doctor
                        .getId()
                        .toString().equals(targetId.toString()))
                .findFirst()
                .orElse(null);

        if (doctorToDelete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_DOCTOR_ID);
        }
        model.deleteDoctor(doctorToDelete);
        model.setViewPatient(null);
        return new CommandResult(String.format(MESSAGE_DELETE_DOCTOR_SUCCESS, doctorToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDoctorCommand // instanceof handles nulls
                && targetId.equals(((DeleteDoctorCommand) other).targetId)); // state check
    }
}
