package gomedic.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.commonfield.Id;
import gomedic.model.person.patient.Patient;

/**
 * Deletes a patient identified using it's displayed index from the address book.
 */
public class DeletePatientCommand extends Command {

    public static final String COMMAND_WORD = "delete t/patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the patient identified by the index shown in the patient list.\n"
        + "Parameters: INDEX (must be exactly the same as shown in the list, e.g. P001)\n"
        + "Example: " + COMMAND_WORD + " P001";

    public static final String MESSAGE_DELETE_PATIENT_SUCCESS = "Deleted Patient: %1$s";

    private final Id targetId;

    public DeletePatientCommand(Id targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        Patient personToDelete = lastShownList
            .stream()
            .filter(patient -> patient
                .getId()
                .toString().equals(targetId.toString()))
            .findFirst()
            .orElse(null);

        if (personToDelete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_ID);
        }
        model.deletePatient(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PATIENT_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeletePatientCommand // instanceof handles nulls
            && targetId.equals(((DeletePatientCommand) other).targetId)); // state check
    }
}
