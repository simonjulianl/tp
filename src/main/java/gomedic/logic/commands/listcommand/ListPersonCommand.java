package gomedic.logic.commands.listcommand;

import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.model.Model;
import gomedic.model.ModelItem;

/**
 * Lists all persons in the address book to the user.
 */
public class ListPersonCommand extends Command {

    public static final String COMMAND_WORD = "list t/person";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setModelBeingShown(ModelItem.PERSON);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
