package gomedic.logic.commands.addcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static gomedic.logic.parser.CliSyntax.PREFIX_END_TIME;
import static gomedic.logic.parser.CliSyntax.PREFIX_START_TIME;
import static gomedic.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.activity.Activity;

/**
 * Adds an activity to the address book
 */
public class AddActivityCommand extends Command {
    public static final String COMMAND_WORD = "add t/activity";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an activity to the address book. "
            + "Parameters: "
            + PREFIX_START_TIME + "NAME "
            + PREFIX_END_TIME + "PHONE "
            + PREFIX_TITLE + "EMAIL "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_START_TIME + "15/09/2022 14:00"
            + PREFIX_END_TIME + "15/09/2022 15:00"
            + PREFIX_TITLE + "Meeting with Mr. Y"
            + PREFIX_DESCRIPTION + "Discussing the future of CS2103T-T15 Group!";

    public static final String MESSAGE_SUCCESS = "New activity added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACTIVITY =
            "This Activity already exists in the address book, duplicate id";
    public static final String MESSAGE_CONFLICTING_ACTIVITY =
            "There exists an activity that overlaps with this activity's timing of this activity.";

    private final Activity toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Activity}
     */
    public AddActivityCommand(Activity activity) {
        requireNonNull(activity);
        toAdd = activity;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasActivity(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        if (model.hasConflictingActivity(toAdd)) {
            throw new CommandException(MESSAGE_CONFLICTING_ACTIVITY);
        }

        model.addActivity(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddActivityCommand // instanceof handles nulls
                && toAdd.equals(((AddActivityCommand) other).toAdd));
    }
}
