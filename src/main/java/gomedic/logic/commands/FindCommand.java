package gomedic.logic.commands;

import static java.util.Objects.requireNonNull;

import gomedic.commons.core.Messages;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.util.NameContainsKeywordsPredicate;

/**
 * Finds and lists all entries in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {
    // NOTE: CURRENTLY, FINDCOMMAND ONLY FINDS THE NAMES OF THE DOCTORS WHOSE NAME MATCHES THE KEYWORD ARGUMENTS
    // TODO: CHANGE FIND COMMAND TO WORK ON ALL TYPES OF ENTRIES AS WELL AS IMPLEMENT FEATURE TO FILTER BASED ON
    //          SPECIFIC PREFIXES
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all doctors whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate<Doctor> predicate;

    public FindCommand(NameContainsKeywordsPredicate<Doctor> predicate) {
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
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
