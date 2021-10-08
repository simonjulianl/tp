package gomedic.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gomedic.commons.exceptions.IllegalValueException;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.AbstractPerson;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.doctor.DoctorId;

/**
 * Jackson-friendly version of {@link Doctor}.
 */
public class JsonAdaptedDoctor {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Doctor's %s field is missing!";

    private final String name;
    private final String phone;
    private final String id;
    private final String department;

    /**
     * Constructs a {@code JsonAdaptedDoctor} with the given details.
     */
    @JsonCreator
    public JsonAdaptedDoctor(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("id") String id, @JsonProperty("department") String department) {
        this.name = name;
        this.phone = phone;
        this.id = id;
        this.department = department;
    }

    /**
     * Converts a given source to a JsonAdaptedDoctor.
     */
    public JsonAdaptedDoctor(AbstractPerson source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        id = source.getId().toString();
        department = ((Doctor) source).getDepartment().departmentName;
    }

    /**
     * Converts this Jackson-friendly adapted doctor object into the model's {@code Doctor} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted doctor.
     */
    public Doctor toModelType() throws IllegalValueException {
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
                    MISSING_FIELD_MESSAGE_FORMAT, DoctorId.class.getSimpleName()));
        }
        if (!DoctorId.isValidDoctorId(id)) {
            throw new IllegalValueException(DoctorId.MESSAGE_CONSTRAINTS);
        }
        final DoctorId modelId = new DoctorId(id);

        if (department == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Department.class.getSimpleName()));
        }

        if (!Department.isValidDepartmentName(department)) {
            throw new IllegalValueException(Department.MESSAGE_CONSTRAINTS);
        }
        final Department modelDepartment = new Department(department);

        return new Doctor(modelName, modelPhone, modelId, modelDepartment);
    }
}
