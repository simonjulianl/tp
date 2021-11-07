package gomedic.logic.commands.findcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_DOCTOR;
import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.person.doctor.Doctor;



/**
 * Finds and lists all entries of doctors in GoMedic whose fields contains any of the
 * argument keywords in the corresponding fields.
 * Keyword matching is case insensitive.
 */
public class FindDoctorCommand extends Command {
    public static final String COMMAND_WORD = "find" + " " + PREFIX_TYPE_DOCTOR;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all doctors"
            + " whose specified fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: t/doctor [FIELD]/KEYWORDS ...\n"
            + "Example: " + COMMAND_WORD + " n/alice lee"
            + "\nOptions for FIELD: n, p, de";

    private final Predicate<Doctor> predicate;

    public FindDoctorCommand(Predicate<Doctor> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredDoctorList(predicate);

        model.setModelBeingShown(ModelItem.DOCTOR);
        return new CommandResult(
                String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredDoctorList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindDoctorCommand // instanceof handles nulls
                && predicate.equals(((FindDoctorCommand) other).predicate)); // state check
    }

}
