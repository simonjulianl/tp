package gomedic.logic.commands.listcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_DOCTOR;
import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.model.Model;
import gomedic.model.ModelItem;

public class ListDoctorCommand extends Command {
    public static final String COMMAND_WORD = "list" + " " + PREFIX_TYPE_DOCTOR;

    public static final String MESSAGE_SUCCESS = "Listed all doctor sorted by id";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setModelBeingShown(ModelItem.DOCTOR);
        model.updateFilteredDoctorList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
