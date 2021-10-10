package gomedic.logic.commands.listcommand;

import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.model.Model;
import gomedic.model.ModelItem;

/**
 * Lists all activities in the address book to the user.
 */
public class ListActivityCommand extends Command {

    public static final String COMMAND_WORD = "list t/activity";

    public static final String MESSAGE_SUCCESS = "Listed all activities sorted by id";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setModelBeingShown(ModelItem.ACTIVITY);
        model.updateFilteredActivitiesList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
