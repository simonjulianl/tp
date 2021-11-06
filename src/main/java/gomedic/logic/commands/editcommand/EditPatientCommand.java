package gomedic.logic.commands.editcommand;

import static gomedic.logic.parser.CliSyntax.PREFIX_AGE;
import static gomedic.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static gomedic.logic.parser.CliSyntax.PREFIX_GENDER;
import static gomedic.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static gomedic.logic.parser.CliSyntax.PREFIX_ID;
import static gomedic.logic.parser.CliSyntax.PREFIX_MEDICAL_CONDITIONS;
import static gomedic.logic.parser.CliSyntax.PREFIX_NAME;
import static gomedic.logic.parser.CliSyntax.PREFIX_PHONE;
import static gomedic.logic.parser.CliSyntax.PREFIX_TYPE_PATIENT;
import static gomedic.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static gomedic.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import gomedic.commons.core.Messages;
import gomedic.commons.util.CollectionUtil;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.ModelItem;
import gomedic.model.commonfield.Id;
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
 * Edits the details of an existing patient in the address book.
 */
public class EditPatientCommand extends Command {

    public static final String COMMAND_WORD = "edit" + " " + PREFIX_TYPE_PATIENT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the patient identified "
        + "by the index number used in the displayed patient list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: "
        + "[" + PREFIX_ID + "ID] "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_PHONE + "PHONE_NUMBER] "
        + "[" + PREFIX_AGE + "AGE] "
        + "[" + PREFIX_BLOOD_TYPE + "BLOOD_TYPE] "
        + "[" + PREFIX_GENDER + "GENDER] "
        + "[" + PREFIX_HEIGHT + "HEIGHT] "
        + "[" + PREFIX_WEIGHT + "WEIGHT] "
        + "[" + PREFIX_MEDICAL_CONDITIONS + "MEDICAL_CONDITION]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_ID + "P001 "
        + PREFIX_NAME + "John Snow "
        + PREFIX_PHONE + "91234567 "
        + PREFIX_AGE + "30 "
        + PREFIX_BLOOD_TYPE + "AB+ "
        + PREFIX_GENDER + "M "
        + PREFIX_HEIGHT + "185 "
        + PREFIX_WEIGHT + "85 "
        + PREFIX_MEDICAL_CONDITIONS + "diabetes";

    public static final String MESSAGE_EDIT_PATIENT_SUCCESS = "Edited Patient: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Must provide at least one field (other than ID).";

    private final Id targetId;
    private final EditPatientDescriptor editPatientDescriptor;

    /**
     * @param targetId of the patient in the filtered patient list to edit
     * @param editPatientDescriptor details to edit the patient with
     */
    public EditPatientCommand(Id targetId, EditPatientDescriptor editPatientDescriptor) {
        requireNonNull(targetId);
        requireNonNull(editPatientDescriptor);

        this.targetId = targetId;
        this.editPatientDescriptor = new EditPatientDescriptor(editPatientDescriptor);
    }

    /**
     * Creates and returns a {@code Patient} with the details of {@code patientToEdit}
     * edited with {@code editPatientDescriptor}.
     */
    private static Patient createEditedPatient(Patient patientToEdit, EditPatientDescriptor editPatientDescriptor) {
        requireNonNull(patientToEdit);

        PatientId patientId = patientToEdit.getId();
        Name updatedName = editPatientDescriptor.getName().orElse(patientToEdit.getName());
        Phone updatedPhone = editPatientDescriptor.getPhone().orElse(patientToEdit.getPhone());
        Age updatedAge = editPatientDescriptor.getAge().orElse(patientToEdit.getAge());
        BloodType updatedBloodType = editPatientDescriptor.getBloodType().orElse(patientToEdit.getBloodType());
        Gender updatedGender = editPatientDescriptor.getGender().orElse(patientToEdit.getGender());
        Height updatedHeight = editPatientDescriptor.getHeight().orElse(patientToEdit.getHeight());
        Weight updatedWeight = editPatientDescriptor.getWeight().orElse(patientToEdit.getWeight());
        Set<Tag> updatedMedicalConditions = editPatientDescriptor.getMedicalConditions()
            .orElse(patientToEdit.getMedicalConditions());

        return new Patient(updatedName, updatedPhone, patientId, updatedAge, updatedBloodType, updatedGender,
            updatedHeight, updatedWeight, updatedMedicalConditions);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        Patient patientToEdit = lastShownList
            .stream()
            .filter(patient -> patient.getId().toString().equals(targetId.toString()))
            .findFirst()
            .orElse(null);

        if (patientToEdit == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_ID);
        }

        Patient editedPatient = createEditedPatient(patientToEdit, editPatientDescriptor);

        model.setPatient(patientToEdit, editedPatient);
        model.setViewPatient(null);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_ITEMS);
        model.setModelBeingShown(ModelItem.PATIENT);
        return new CommandResult(String.format(MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPatientCommand)) {
            return false;
        }

        // state check
        EditPatientCommand e = (EditPatientCommand) other;
        return targetId.equals(e.targetId)
            && editPatientDescriptor.equals(e.editPatientDescriptor);
    }

    /**
     * Stores the details to edit the patient with. Each non-empty field value will replace the
     * corresponding field value of the patient.
     */
    public static class EditPatientDescriptor {
        private Name name;
        private Phone phone;
        private Age age;
        private BloodType bloodType;
        private Gender gender;
        private Height height;
        private Weight weight;
        private Set<Tag> medicalConditions;

        public EditPatientDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditPatientDescriptor(EditPatientDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setAge(toCopy.age);
            setBloodType(toCopy.bloodType);
            setGender(toCopy.gender);
            setHeight(toCopy.height);
            setWeight(toCopy.weight);
            setMedicalConditions(toCopy.medicalConditions);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, age, bloodType, gender, height, weight, medicalConditions);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
        }

        public void setAge(Age age) {
            this.age = age;
        }

        public Optional<BloodType> getBloodType() {
            return Optional.ofNullable(bloodType);
        }

        public void setBloodType(BloodType bloodType) {
            this.bloodType = bloodType;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Height> getHeight() {
            return Optional.ofNullable(height);
        }

        public void setHeight(Height height) {
            this.height = height;
        }

        public Optional<Weight> getWeight() {
            return Optional.ofNullable(weight);
        }

        public void setWeight(Weight weight) {
            this.weight = weight;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code medicalConditions} is null.
         */
        public Optional<Set<Tag>> getMedicalConditions() {
            return (medicalConditions != null) ? Optional.of(Collections.unmodifiableSet(medicalConditions))
                : Optional.empty();
        }

        /**
         * Sets {@code medicalConditions} to this object's {@code medicalConditions}.
         * A defensive copy of {@code medicalConditions} is used internally.
         */
        public void setMedicalConditions(Set<Tag> medicalConditions) {
            this.medicalConditions = (medicalConditions != null) ? new HashSet<>(medicalConditions) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPatientDescriptor)) {
                return false;
            }

            // state check
            EditPatientDescriptor e = (EditPatientDescriptor) other;

            return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getAge().equals(e.getAge())
                && getBloodType().equals(e.getBloodType())
                && getGender().equals(e.getGender())
                && getHeight().equals(e.getHeight())
                && getWeight().equals(e.getWeight())
                && getMedicalConditions().equals(e.getMedicalConditions());
        }
    }
}
