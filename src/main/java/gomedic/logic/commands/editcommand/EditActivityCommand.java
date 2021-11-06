package gomedic.logic.commands.editcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static gomedic.logic.parser.CliSyntax.PREFIX_END_TIME;
import static gomedic.logic.parser.CliSyntax.PREFIX_ID;
import static gomedic.logic.parser.CliSyntax.PREFIX_START_TIME;
import static gomedic.logic.parser.CliSyntax.PREFIX_TITLE;
import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_ACTIVITY;
import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import gomedic.commons.core.Messages;
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
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.Time;
import gomedic.model.person.patient.PatientId;

/**
 * Edits the details of an existing doctor in the address book.
 */
public class EditActivityCommand extends Command {

    public static final String COMMAND_WORD = "edit" + " " + PREFIX_TYPE_ACTIVITY;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the activity identified "
            + "by the index number used in the displayed activity list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "[" + PREFIX_ID + "ID] "
            + "[" + PREFIX_START_TIME + "START_TIME] "
            + "[" + PREFIX_END_TIME + "END_TIME] "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "A001 "
            + PREFIX_START_TIME + "17/10/2021 14:00 "
            + PREFIX_END_TIME + "17/10/2021 15:00 "
            + PREFIX_TITLE + "Another new title";

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Must provide at least one field (other than ID).";

    private final Id targetId;
    private final EditActivityDescriptor editActivityDescriptor;

    /**
     * @param targetId of the activity in the filtered activity list to edit
     * @param editActivityDescriptor details to edit the activity with
     */
    public EditActivityCommand(Id targetId, EditActivityDescriptor editActivityDescriptor) {
        requireNonNull(targetId);
        requireNonNull(editActivityDescriptor);

        this.targetId = targetId;
        this.editActivityDescriptor = new EditActivityDescriptor(editActivityDescriptor);
    }

    /**
     * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
     * edited with {@code activityDoctorDescriptor}.
     */
    private static Activity createEditedActivity(Activity activityToEdit,
                                                 EditActivityDescriptor editActivityDescriptor) {
        requireNonNull(activityToEdit);

        ActivityId activityId = activityToEdit.getActivityId();
        Time startTime = editActivityDescriptor.getStartTime().orElse(activityToEdit.getStartTime());
        Time endTime = editActivityDescriptor.getEndTime().orElse(activityToEdit.getEndTime());
        Title title = editActivityDescriptor.getTitle().orElse(activityToEdit.getTitle());
        Description description = editActivityDescriptor.getDescription().orElse(activityToEdit.getDescription());

        if (activityToEdit.isAppointment()) {
            PatientId patientId = activityToEdit.getPatientId();
            return new Activity(activityId, patientId, startTime, endTime, title, description);
        }
        return new Activity(activityId, startTime, endTime, title, description);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Activity> lastShownList = model.getFilteredActivityListById();

        Activity activityToEdit = lastShownList
                .stream()
                .filter(activity -> activity.getActivityId().toString().equals(targetId.toString()))
                .findFirst()
                .orElse(null);

        if (activityToEdit == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_ID);
        }

        Activity editedActivity;

        try {
            editedActivity = createEditedActivity(activityToEdit, editActivityDescriptor);
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }

        boolean anyConflicting = model
                .getFilteredActivityListById()
                .stream()
                .anyMatch(it -> it.isConflicting(editedActivity) && !it.getActivityId()
                        .equals(editedActivity.getActivityId()));

        if (anyConflicting) {
            throw new CommandException(Messages.MESSAGE_CONFLICTING_ACTIVITY);
        }

        model.setActivity(activityToEdit, editedActivity);
        model.setViewPatient(null);
        model.updateFilteredActivitiesList(PREDICATE_SHOW_ALL_ITEMS);
        if (!(model.getModelBeingShown().getValue().equals(ModelItem.ACTIVITY_ID.ordinal())
                || model.getModelBeingShown().getValue().equals(ModelItem.ACTIVITY_START_TIME.ordinal()))) {
            model.setModelBeingShown(ModelItem.ACTIVITY_ID);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditActivityCommand)) {
            return false;
        }

        // state check
        EditActivityCommand e = (EditActivityCommand) other;
        return targetId.equals(e.targetId)
                && editActivityDescriptor.equals(e.editActivityDescriptor);
    }

    /**
     * Stores the details to edit the activity with. Each non-empty field value will replace the
     * corresponding field value of the doctor.
     */
    public static class EditActivityDescriptor {
        private Time startTime;
        private Time endTime;
        private Title title;
        private Description description;

        public EditActivityDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditActivityDescriptor(EditActivityDescriptor toCopy) {
            setStartTime(toCopy.startTime);
            setEndTime(toCopy.endTime);
            setTitle(toCopy.title);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(startTime, endTime, title, description);
        }

        public Optional<Time> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setStartTime(Time startTime) {
            this.startTime = startTime;
        }

        public Optional<Time> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        public void setEndTime(Time endTime) {
            this.endTime = endTime;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditActivityDescriptor)) {
                return false;
            }

            // state check
            EditActivityDescriptor e = (EditActivityDescriptor) other;

            return getStartTime().equals(e.getStartTime())
                    && getEndTime().equals(e.getEndTime())
                    && getTitle().equals(e.getTitle())
                    && getDescription().equals(e.getDescription());
        }
    }
}
