package gomedic.logic.commands.addcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static gomedic.logic.parser.CliSyntax.PREFIX_END_TIME;
import static gomedic.logic.parser.CliSyntax.PREFIX_ID;
import static gomedic.logic.parser.CliSyntax.PREFIX_START_TIME;
import static gomedic.logic.parser.CliSyntax.PREFIX_TITLE;
import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_APPOINTMENT;
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
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.Time;
import gomedic.model.person.patient.Patient;
import gomedic.model.person.patient.PatientId;
import javafx.collections.ObservableList;

/**
 * Adds an activity to the address book
 */
public class AddAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "add" + " " + PREFIX_TYPE_APPOINTMENT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the address book. "
            + "Parameters: "
            + PREFIX_ID + "PATIENT_ID "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME "
            + PREFIX_TITLE + "TITLE "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "P001 "
            + PREFIX_START_TIME + "15/09/2022 14:00 "
            + PREFIX_END_TIME + "15/09/2022 15:00 "
            + PREFIX_TITLE + "Appointment with P001 "
            + PREFIX_DESCRIPTION + "Follow-up from tuesday's appointment.";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACTIVITY =
            "This Activity already exists in the address book, duplicate id";
    public static final String MESSAGE_CONFLICTING_ACTIVITY =
            "There exists an activity that overlaps with this activity's timing of this activity.";
    public static final String MESSAGE_PATIENT_NOT_FOUND =
            "No such patient exists within your records.";
    public static final String MESSAGE_DOCTOR_NOT_FOUND =
            "No such doctor exists within your records.";
    public static final String MESSAGE_FAIL_TO_GENERATE_REFERRAL =
            "So sorry, GoMedic cannot generate the referral :(";

    private final PatientId patientId;
    private final Time startTime;
    private final Time endTime;
    private final Title title;
    private final Description description;


    /**
     * Creates an AddCommand to add the specified {@code Activity}.
     * Because we need id, and we can only obtain the model when we execute it,
     * we do not pass activity as the constructor.
     */
    public AddAppointmentCommand(Id patientId, Time startTime, Time endTime, Title title, Description description) {
        CollectionUtil.requireAllNonNull(patientId, startTime, endTime, title, description);
        this.patientId = new PatientId(patientId.getIdNumber());
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Patient> listOfPatients = model.getFilteredPatientList();
        // patient does not exist in directory
        if (listOfPatients.isEmpty()
                || listOfPatients.filtered(x -> x.getId().getIdNumber() == patientId.getIdNumber()).isEmpty()) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Activity toAdd = new Activity(
                new ActivityId(model.getNewActivityId()),
                patientId,
                startTime,
                endTime,
                title,
                description);

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
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && patientId.equals(((AddAppointmentCommand) other).patientId)
                && startTime.equals(((AddAppointmentCommand) other).startTime)
                && endTime.equals(((AddAppointmentCommand) other).endTime)
                && title.equals(((AddAppointmentCommand) other).title)
                && description.equals(((AddAppointmentCommand) other).description));
    }
}
