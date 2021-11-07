package gomedic.logic.commands.findcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_ACTIVITY;
import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.activity.Activity;


/**
 * Finds and lists all entries of activities in GoMedic whose fields contains any of the
 * argument keywords in the corresponding fields.
 * Keyword matching is case insensitive.
 */
public class FindActivityCommand extends Command {

    public static final String COMMAND_WORD = "find" + " " + PREFIX_TYPE_ACTIVITY;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activities whose specified"
            + " fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: t/activity ti/KEYWORDS \n"
            + "Example: " + COMMAND_WORD + " ti/meeting tom on tuesday";

    private final Predicate<Activity> predicate;

    public FindActivityCommand(Predicate<Activity> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredActivitiesList(predicate);

        model.setModelBeingShown(ModelItem.ACTIVITY_ID);
        return new CommandResult(
                String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredActivityListById().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindActivityCommand // instanceof handles nulls
                && predicate.equals(((FindActivityCommand) other).predicate)); // state check
    }


}
