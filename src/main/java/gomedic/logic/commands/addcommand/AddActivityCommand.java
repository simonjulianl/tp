package gomedic.logic.commands.addcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static gomedic.logic.parser.CliSyntax.PREFIX_END_TIME;
import static gomedic.logic.parser.CliSyntax.PREFIX_START_TIME;
import static gomedic.logic.parser.CliSyntax.PREFIX_TITLE;
import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_ACTIVITY;
import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import gomedic.commons.util.CollectionUtil;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.activity.Activity;
import gomedic.model.activity.ActivityId;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Time;

/**
 * Adds an activity to the address book
 */
public class AddActivityCommand extends Command {
    public static final String COMMAND_WORD = "add" + " " + PREFIX_TYPE_ACTIVITY;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an activity to the address book. "
            + "Parameters: "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME "
            + PREFIX_TITLE + "TITLE "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_START_TIME + "15/09/2022 14:00 "
            + PREFIX_END_TIME + "15/09/2022 15:00 "
            + PREFIX_TITLE + "Meeting with Mr. Y "
            + PREFIX_DESCRIPTION + "Discussing the future of CS2103T-T15 Group!";

    public static final String MESSAGE_SUCCESS = "New activity added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACTIVITY =
            "This Activity already exists in the address book, duplicate id";
    public static final String MESSAGE_CONFLICTING_ACTIVITY =
            "There exists an activity that overlaps with this activity's timing.";

    private final Time startTime;
    private final Time endTime;
    private final Title title;
    private final Description description;


    /**
     * Creates an AddCommand to add the specified {@code Activity}.
     * Because we need id, and we can only obtain the model when we execute it,
     * we do not pass activity as the constructor.
     */
    public AddActivityCommand(Time startTime, Time endTime, Title title, Description description) {
        CollectionUtil.requireAllNonNull(startTime, endTime, title, description);
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Activity toAdd;

        try {
            toAdd = new Activity(
                    new ActivityId(model.getNewActivityId()),
                    startTime,
                    endTime,
                    title,
                    description);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }


        if (model.hasActivity(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        if (model.hasConflictingActivity(toAdd)) {
            throw new CommandException(MESSAGE_CONFLICTING_ACTIVITY);
        }

        model.addActivity(toAdd);
        model.setViewPatient(null);
        model.setModelBeingShown(ModelItem.ACTIVITY_ID);
        model.updateFilteredActivitiesList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddActivityCommand // instanceof handles nulls
                && startTime.equals(((AddActivityCommand) other).startTime)
                && endTime.equals(((AddActivityCommand) other).endTime)
                && title.equals(((AddActivityCommand) other).title)
                && description.equals(((AddActivityCommand) other).description));
    }
}
