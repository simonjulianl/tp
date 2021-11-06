package gomedic.logic.commands.addcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static gomedic.logic.parser.CliSyntax.PREFIX_NAME;
import static gomedic.logic.parser.CliSyntax.PREFIX_PHONE;
import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_DOCTOR;
import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import gomedic.commons.util.CollectionUtil;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.doctor.DoctorId;


/**
 * Adds an doctor to the address book
 */
public class AddDoctorCommand extends Command {
    public static final String COMMAND_WORD = "add" + " " + PREFIX_TYPE_DOCTOR;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a doctor to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_DEPARTMENT + "DEPARTMENT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Smith "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_DEPARTMENT + "Cardiology";

    public static final String MESSAGE_SUCCESS = "New doctor added: %1$s";
    public static final String MESSAGE_DUPLICATE_DOCTOR =
            "This Doctor already exists in the address book, duplicate id";
    public static final String MESSAGE_MAXIMUM_CAPACITY_EXCEEDED =
            "The maximum capacity for the number of doctors that can be added in the address book has been reached; "
                    + "No more doctors can be added.";

    private final Name name;
    private final Phone phone;
    private final Department department;

    /**
     * Creates an AddCommand to add the specified {@code Doctor}.
     * Because we need id, and we can only obtain the model when we execute it,
     * we do not pass activity as the constructor.
     */
    public AddDoctorCommand(Name name, Phone phone, Department department) {
        CollectionUtil.requireAllNonNull(name, phone, department);
        this.name = name;
        this.phone = phone;
        this.department = department;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasNewDoctorId()) {
            throw new CommandException(MESSAGE_MAXIMUM_CAPACITY_EXCEEDED);
        }

        Doctor toAdd = new Doctor(name, phone, new DoctorId(model.getNewDoctorId()), department);

        if (model.hasDoctor(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DOCTOR);
        }

        model.addDoctor(toAdd);
        model.setViewPatient(null);
        model.setModelBeingShown(ModelItem.DOCTOR);
        model.updateFilteredDoctorList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDoctorCommand // instanceof handles nulls
                && name.equals(((AddDoctorCommand) other).name)
                && phone.equals(((AddDoctorCommand) other).phone)
                && department.equals(((AddDoctorCommand) other).department));
    }
}
