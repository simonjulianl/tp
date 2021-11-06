package gomedic.logic.commands.viewcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_PATIENT;
import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.commonfield.Id;
import gomedic.model.person.patient.Patient;

public class ViewPatientCommand extends Command {
    public static final String COMMAND_WORD = "view" + " " + PREFIX_TYPE_PATIENT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the detail of the patient with the specified id "
        + "Parameters: INDEX (must be exactly the same as shown in the list, e.g. P001)\n"
        + "Example: " + COMMAND_WORD + " P001";

    public static final String MESSAGE_VIEW_PATIENT_SUCCESS = "View patient with id: %1s";

    private final Id targetId;

    /**
     * @param targetId of the patient in the filtered patient list to edit
     */
    public ViewPatientCommand(Id targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Patient patientToView = getSpecifiedPatient(model);

        if (patientToView == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_ID);
        }

        model.setViewPatient(patientToView);
        model.setModelBeingShown(ModelItem.VIEW_PATIENT);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(String.format(MESSAGE_VIEW_PATIENT_SUCCESS, patientToView));
    }

    private Patient getSpecifiedPatient(Model model) {
        return model.getFilteredPatientList()
            .stream()
            .filter(patient -> patient.getId().equals(targetId))
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ViewPatientCommand // instanceof handles nulls
            && targetId.equals(((ViewPatientCommand) other).targetId)); // state check
    }
}
