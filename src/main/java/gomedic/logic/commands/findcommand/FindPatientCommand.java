package gomedic.logic.commands.findcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_PATIENT;
import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.person.patient.Patient;


/**
 * Finds and lists all entries of patients in GoMedic whose fields contains any of the
 * argument keywords in the corresponding fields.
 * Keyword matching is case insensitive.
 */
public class FindPatientCommand extends Command {
    public static final String COMMAND_WORD = "find" + " " + PREFIX_TYPE_PATIENT;


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all patients whose specified"
            + " fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: t/patient [FIELD]/KEYWORDS ...\n"
            + "Example: " + COMMAND_WORD + " n/alice lee"
            + "\nOptions for FIELD: n, p, a, g, h, w, b, m";

    private final Predicate<Patient> predicate;

    public FindPatientCommand(Predicate<Patient> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredPatientList(predicate);

        model.setModelBeingShown(ModelItem.PATIENT);
        return new CommandResult(
                String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredPatientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPatientCommand // instanceof handles nulls
                && predicate.equals(((FindPatientCommand) other).predicate)); // state check
    }

}
