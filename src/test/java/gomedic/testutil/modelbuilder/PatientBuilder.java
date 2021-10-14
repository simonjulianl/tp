package gomedic.testutil.modelbuilder;

import java.util.HashSet;
import java.util.Set;

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
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {
    public static final String DEFAULT_NAME = "Dohn Joe";
    public static final String DEFAULT_PHONE = "12345678";
    public static final String DEFAULT_AGE = "40";
    public static final String DEFAULT_BLOODTYPE = "AB";
    public static final String DEFAULT_GENDER = "M";
    public static final String DEFAULT_HEIGHT = "176";
    public static final String DEFAULT_WEIGHT = "86";
    public static final Set<Tag> DEFAULT_MEDICAL_CONDITIONS = new HashSet<>();
    private static final int DEFAULT_ID = 1;

    private Name name;
    private Phone phone;
    private Age age;
    private BloodType bloodType;
    private Gender gender;
    private Height height;
    private Weight weight;
    private PatientId pid;
    private Set<Tag> medicalConditions;

    /**
     * Creates a {@code PatientBuilder} with the default details.
     */
    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        age = new Age(DEFAULT_AGE);
        bloodType = new BloodType(DEFAULT_BLOODTYPE);
        gender = new Gender(DEFAULT_GENDER);
        height = new Height(DEFAULT_HEIGHT);
        weight = new Weight(DEFAULT_WEIGHT);
        pid = new PatientId(DEFAULT_ID);
        medicalConditions = new HashSet<>();
        medicalConditions.addAll(DEFAULT_MEDICAL_CONDITIONS);
        medicalConditions.add(new Tag("heart failure"));
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        phone = patientToCopy.getPhone();
        age = patientToCopy.getAge();
        bloodType = patientToCopy.getBloodType();
        gender = patientToCopy.getGender();
        weight = patientToCopy.getWeight();
        height = patientToCopy.getHeight();
        pid = (PatientId) patientToCopy.getId();
        medicalConditions = patientToCopy.getMedicalConditions();
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */

    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets the {@code BloodType} of the {@code Patient} that we are building.
     */
    public PatientBuilder withBloodType(String bloodType) {
        this.bloodType = new BloodType(bloodType);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Patient} that we are building.
     */
    public PatientBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Height} of the {@code Patient} that we are building.
     */
    public PatientBuilder withHeight(String height) {
        this.height = new Height(height);
        return this;
    }

    /**
     * Sets the {@code Weight} of the {@code Patient} that we are building.
     */
    public PatientBuilder withWeight(String weight) {
        this.weight = new Weight(weight);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withMedicalConditions(Set<Tag> medicalConditions) {
        this.medicalConditions = new HashSet<>(medicalConditions);
        return this;
    }

    /**
     * Sets the {@code PatientId} of the {@code Patient} that we are building.
     */
    public PatientBuilder withId(int id) {
        this.pid = new PatientId(id);
        return this;
    }

    public Patient build() {
        return new Patient(name, phone, pid, age, bloodType, gender, height, weight, medicalConditions);
    }

}
