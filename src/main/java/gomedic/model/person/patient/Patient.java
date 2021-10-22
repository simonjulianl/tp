package gomedic.model.person.patient;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import gomedic.commons.util.CollectionUtil;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.Person;
import gomedic.model.tag.Tag;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {

    //data fields
    private final Age age;
    private final BloodType bloodType;
    private final Gender gender;
    private final Height height;
    private final Weight weight;
    private final Set<Tag> medicalConditions = new HashSet<>();

    /**
     * Every field must be present and not null.
     *
     * @param name Name of the patient.
     * @param phone Phone number of the patient.
     * @param id Id of the patient.
     * @param age Age of the patient.
     * @param bloodType BloodType of the patient.
     * @param gender Gender of the patient.
     * @param height Height of the patient in cm.
     * @param weight Weight of the patient in kg.
     * @param medicalConditions Set of medical conditions of the patient.
     */
    public Patient(Name name, Phone phone, PatientId id, Age age, BloodType bloodType, Gender gender,
                   Height height, Weight weight, Set<Tag> medicalConditions) {
        super(name, phone, id);
        CollectionUtil.requireAllNonNull(name, phone, id, age, bloodType, gender, height, weight);
        this.age = age;
        this.bloodType = bloodType;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.medicalConditions.addAll(medicalConditions);
    }

    @Override
    public PatientId getId() {
        return (PatientId) super.getId();
    }

    /**
     * Returns Age of the patient.
     *
     * @return Age of the patient.
     */
    public Age getAge() {
        return age;
    }

    /**
     * Returns BloodType of the patient.
     *
     * @return BloodType of the patient.
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * Returns Gender of the patient.
     *
     * @return Gender of the patient.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Returns Height of the patient.
     *
     * @return Height of the patient.
     */
    public Height getHeight() {
        return height;
    }

    /**
     * Returns Weight of the patient.
     *
     * @return Weight of the patient.
     */
    public Weight getWeight() {
        return weight;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getMedicalConditions() {
        return Collections.unmodifiableSet(medicalConditions);
    }

    /**
     * Returns true if both patients have the same id.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return super.equals(otherPatient);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getId(), age, bloodType, gender, height, weight, medicalConditions);
    }

    /**
     * Returns a String representation of a {@code Patient}, using identity fields of its super class, as well as
     * the patient's Age, BloodType, Gender, Height, Weight, and Medical conditions field.
     *
     * @return a String representation of a {@code Patient}.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString())
                .append("; Age: ")
                .append(getAge())
                .append("; Blood type: ")
                .append(getBloodType())
                .append("; Gender: ")
                .append(getGender())
                .append("; Height: ")
                .append(getHeight())
                .append("; Weight: ")
                .append(getWeight());

        Set<Tag> medicalConditions = getMedicalConditions();
        if (!medicalConditions.isEmpty()) {
            builder.append("; Medical conditions: ");
            var string = medicalConditions
                    .stream()
                    .map(it -> it.toString().substring(1, it.toString().length() - 1))
                    .collect(Collectors.joining(","));
            builder.append(string);
        }

        return builder.toString();
    }
}
