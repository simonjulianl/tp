package gomedic.logic.commands.deletecommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_PATIENT;
import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import java.util.List;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.commonfield.Id;
import gomedic.model.person.patient.Patient;

/**
 * Deletes a patient identified using it's displayed index from the address book.
 */
public class DeletePatientCommand extends Command {

    public static final String COMMAND_WORD = "delete" + " " + PREFIX_TYPE_PATIENT;

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

        Patient patientToDelete = lastShownList
            .stream()
            .filter(patient -> patient
                .getId()
                .toString().equals(targetId.toString()))
            .findFirst()
            .orElse(null);

        if (patientToDelete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_ID);
        }
        model.deletePatient(patientToDelete);
        model.setViewPatient(null);
        model.setModelBeingShown(ModelItem.PATIENT);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_ITEMS);
        model.deletePatientAssociatedAppointments(patientToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PATIENT_SUCCESS, patientToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeletePatientCommand // instanceof handles nulls
            && targetId.equals(((DeletePatientCommand) other).targetId)); // state check
    }
}
