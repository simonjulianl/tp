package gomedic.logic.commands.listcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_PERIOD_FLAG;
import static gomedic.logic.parser.CliSyntax.PREFIX_SORT_FLAG;
import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_ACTIVITY;
import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.activity.Activity;
import gomedic.model.commonfield.Time;

/**
 * Lists all activities in the address book to the user.
 */
public class ListActivityCommand extends Command {

    public static final String COMMAND_WORD = "list" + " " + PREFIX_TYPE_ACTIVITY;

    public static final String MESSAGE_SUCCESS = "Listed all activities according the flags!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all the activities based on the flag "
            + "by default, all existing activities sorted by id would be shown!\n"
            + "Parameters: "
            + "[" + PREFIX_SORT_FLAG + "SORT_FLAG] "
            + "[" + PREFIX_PERIOD_FLAG + "PERIOD] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SORT_FLAG + "START "
            + PREFIX_PERIOD_FLAG + "TODAY ";

    private final Sort sortFlag;
    private final Period periodFlag;

    /**
     * Constructs a new list activity command.
     *
     * @param sortFlag to be sorted by id or start time.
     * @param periodFlag to show all, today, within one week, etc.
     */
    public ListActivityCommand(Sort sortFlag, Period periodFlag) {
        this.sortFlag = sortFlag;
        this.periodFlag = periodFlag;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        System.out.println("test");
        switch (sortFlag) {
        case ID:
            model.setModelBeingShown(ModelItem.ACTIVITY_ID);
            break;
        case START:
            model.setModelBeingShown(ModelItem.ACTIVITY_START_TIME);
            break;
        default:
            break;
        }

        Time yesterday = new Time(LocalDateTime.now().minusDays(1));

        switch (periodFlag) {
        case ALL:
            model.updateFilteredActivitiesList(PREDICATE_SHOW_ALL_ITEMS);
            break;
        case TODAY:
            model.updateFilteredActivitiesList(activity ->
                    isInBetween(activity, yesterday, new Time(LocalDateTime.now().plusDays(1))));
            break;
        case WEEK:
            model.updateFilteredActivitiesList(activity ->
                    isInBetween(activity, yesterday, new Time(LocalDateTime.now().plusDays(7))));
            break;
        case MONTH:
            model.updateFilteredActivitiesList(activity ->
                    isInBetween(activity, yesterday, new Time(LocalDateTime.now().plusMonths(1))));
            break;
        case YEAR:
            model.updateFilteredActivitiesList(activity ->
                    isInBetween(activity, yesterday, new Time(LocalDateTime.now().plusYears(1))));
            break;
        default:
            break;
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    // start and end are exclusive
    private boolean isInBetween(Activity activity, Time start, Time end) {
        return activity.getStartTime().isAfter(start) && activity.getStartTime().isBefore(end);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListActivityCommand)) {
            return false;
        }

        // state check
        ListActivityCommand e = (ListActivityCommand) other;
        return sortFlag.equals(e.sortFlag)
                && periodFlag.equals(e.periodFlag);
    }

    /**
     * Sorting flags that are available for this command.
     */
    public enum Sort {
        ID,
        START;

        public static final String MESSAGE_CONSTRAINTS =
                "The flag must be either ID (default) or START to sort by start time";
    }

    /**
     * Activity listing flags that will show all future activities within the stipulated period.
     * Today means present, Week means one week from today until next week and so on and so forth.
     */
    public enum Period {
        ALL,
        TODAY,
        WEEK,
        MONTH,
        YEAR;

        public static final String MESSAGE_CONSTRAINTS =
                "The flag must be either ALL (Default), TODAY, WEEK, MONTH, or YEAR ";
    }
}
