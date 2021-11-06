package gomedic.logic.commands.addcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_AGE;
import static gomedic.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static gomedic.logic.parser.CliSyntax.PREFIX_GENDER;
import static gomedic.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static gomedic.logic.parser.CliSyntax.PREFIX_MEDICAL_CONDITIONS;
import static gomedic.logic.parser.CliSyntax.PREFIX_NAME;
import static gomedic.logic.parser.CliSyntax.PREFIX_PHONE;
import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_PATIENT;
import static gomedic.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import java.util.Set;

import gomedic.commons.util.CollectionUtil;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.patient.Age;
import gomedic.model.person.patient.BloodType;
import gomedic.model.person.patient.Gender;
import gomedic.model.person.patient.Height;
import gomedic.model.person.patient.Patient;
import gomedic.model.person.patient.PatientId;
import gomedic.model.person.patient.Weight;
import gomedic.model.tag.Tag;


/**
 * Adds a patient to the address book
 */
public class AddPatientCommand extends Command {
    public static final String COMMAND_WORD = "add" + " " + PREFIX_TYPE_PATIENT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient to the address book.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE_NUMBER "
            + PREFIX_AGE + "AGE "
            + PREFIX_BLOOD_TYPE + "BLOOD_TYPE "
            + PREFIX_GENDER + "GENDER "
            + PREFIX_HEIGHT + "HEIGHT "
            + PREFIX_WEIGHT + "WEIGHT "
            + "[" + PREFIX_MEDICAL_CONDITIONS + "MEDICAL_CONDITION]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Smith "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_AGE + "45 "
            + PREFIX_BLOOD_TYPE + "AB+ "
            + PREFIX_GENDER + "M "
            + PREFIX_HEIGHT + "175 "
            + PREFIX_WEIGHT + "70 "
            + PREFIX_MEDICAL_CONDITIONS + "heart failure "
            + PREFIX_MEDICAL_CONDITIONS + "diabetes";

    public static final String MESSAGE_SUCCESS = "New patient added: %1$s";
    public static final String MESSAGE_DUPLICATE_PATIENT =
            "This Patient already exists in the address book, duplicate id";
    public static final String MESSAGE_MAXIMUM_CAPACITY_EXCEEDED =
            "The maximum capacity for the number of patients that can be added in the address book has been reached; "
                    + "No more patients can be added.";

    private final Name name;
    private final Phone phone;
    private final Age age;
    private final BloodType bloodType;
    private final Gender gender;
    private final Height height;
    private final Weight weight;
    private final Set<Tag> medicalConditions;

    /**
     * Creates an AddCommand to add the specified {@code Patient}.
     * Because we need id, and we can only obtain the model when we execute it,
     * we do not pass activity as the constructor.
     */
    public AddPatientCommand(Name name, Phone phone, Age age, BloodType bloodType, Gender gender, Height height,
                             Weight weight, Set<Tag> medicalConditions) {
        CollectionUtil.requireAllNonNull(name, phone, age, bloodType, gender, height, weight, medicalConditions);
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.bloodType = bloodType;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.medicalConditions = medicalConditions;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasNewPatientId()) {
            throw new CommandException(MESSAGE_MAXIMUM_CAPACITY_EXCEEDED);
        }

        Patient toAdd = new Patient(name, phone, new PatientId(model.getNewPatientId()), age, bloodType, gender,
                height, weight, medicalConditions);

        if (model.hasPatient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PATIENT);
        }

        model.addPatient(toAdd);
        model.setViewPatient(null);
        model.setModelBeingShown(ModelItem.PATIENT);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPatientCommand // instanceof handles nulls
                && name.equals(((AddPatientCommand) other).name)
                && phone.equals(((AddPatientCommand) other).phone)
                && age.equals(((AddPatientCommand) other).age)
                && bloodType.equals(((AddPatientCommand) other).bloodType)
                && gender.equals(((AddPatientCommand) other).gender)
                && height.equals(((AddPatientCommand) other).height)
                && weight.equals(((AddPatientCommand) other).weight)
                && medicalConditions.equals(((AddPatientCommand) other).medicalConditions));
    }
}
