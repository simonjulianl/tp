package gomedic.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gomedic.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Patient}.
 */
public class JsonAdaptedPatient {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Patient's %s field is missing!";

    private final String name;
    private final String phone;
    private final String id;
    private final String age;
    private final String bloodtype;
    private final String gender;
    private final String height;
    private final String weight;
    private final List<JsonAdaptedTag> medicalConditions = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPatient} with the given details.
     */
    @JsonCreator
    public JsonAdaptedPatient(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("id") String id, @JsonProperty("age") String age,
                             @JsonProperty("blood type") String bloodtype, @JsonProperty("gender") String gender,
                             @JsonProperty("height") String height, @JsonProperty("weight") String weight,
                             @JsonProperty("medical conditions") List<JsonAdaptedTag> medicalConditions) {
        this.name = name;
        this.phone = phone;
        this.id = id;
        this.age = age;
        this.bloodtype = bloodtype;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        if (medicalConditions != null) {
            this.medicalConditions.addAll(medicalConditions);
        }
    }

    /**
     * Converts a given source to a JsonAdaptedPatient.
     */
    public JsonAdaptedPatient(Patient source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        id = source.getId().toString();
        age = source.getAge().age;
        bloodtype = source.getBloodType().bloodType;
        gender = source.getGender().gender;
        height = source.getHeight().height;
        weight = source.getWeight().weight;
        medicalConditions.addAll(source.getMedicalConditions().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted patient object into the model's {@code Patient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient.
     */
    public Patient toModelType() throws IllegalValueException {
        final List<Tag> patientMedicalConditions = new ArrayList<>();
        for (JsonAdaptedTag tag : medicalConditions) {
            patientMedicalConditions.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (id == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, PatientId.class.getSimpleName()));
        }
        if (!PatientId.isValidPatientId(id)) {
            throw new IllegalValueException(PatientId.MESSAGE_CONSTRAINTS);
        }
        final PatientId modelId = new PatientId(id);

        if (age == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Age.class.getSimpleName()));
        }

        if (!Age.isValidAge(age)) {
            throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
        }
        final Age modelAge = new Age(age);

        if (bloodtype == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                BloodType.class.getSimpleName()));
        }

        if (!BloodType.isValidBloodType(bloodtype)) {
            throw new IllegalValueException(BloodType.MESSAGE_CONSTRAINTS);
        }
        final BloodType modelBloodType = new BloodType(bloodtype);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Gender.class.getSimpleName()));
        }

        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        if (height == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Height.class.getSimpleName()));
        }

        if (!Height.isValidHeight(height)) {
            throw new IllegalValueException(Height.MESSAGE_CONSTRAINTS);
        }
        final Height modelHeight = new Height(height);

        if (weight == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Weight.class.getSimpleName()));
        }

        if (!Weight.isValidWeight(weight)) {
            throw new IllegalValueException(Weight.MESSAGE_CONSTRAINTS);
        }
        final Weight modelWeight = new Weight(weight);

        final Set<Tag> modelMedicalConditions = new HashSet<>(patientMedicalConditions);

        return new Patient(modelName, modelPhone, modelId, modelAge, modelBloodType, modelGender, modelHeight,
            modelWeight, modelMedicalConditions);
    }
}
