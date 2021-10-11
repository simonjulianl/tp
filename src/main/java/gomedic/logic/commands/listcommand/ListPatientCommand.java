package gomedic.logic.commands.listcommand;

import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.model.Model;
import gomedic.model.ModelItem;

public class ListPatientCommand extends Command {
    public static final String COMMAND_WORD = "list t/patient";

    public static final String MESSAGE_SUCCESS = "Listed all patients sorted by id";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setModelBeingShown(ModelItem.PATIENT);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
