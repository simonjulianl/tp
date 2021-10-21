package gomedic.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gomedic.commons.exceptions.IllegalValueException;
import gomedic.model.commonfield.Name;
import gomedic.model.person.doctor.Department;
import gomedic.model.userprofile.Organization;
import gomedic.model.userprofile.Position;
import gomedic.model.userprofile.UserProfile;

/**
 * Jackson-friendly version of {@link gomedic.model.userprofile.UserProfile}.
 */
public class JsonAdaptedUserProfile {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "User Profile's %s field is missing!";

    private final String name;
    private final String position;
    private final String department;
    private final String organization;

    /**
     * Constructs a {@code JsonAdaptedUserProfile} with the given details.
     */
    @JsonCreator
    public JsonAdaptedUserProfile(@JsonProperty("name") String name,
                                  @JsonProperty("position") String position,
                                  @JsonProperty("department") String department,
                                  @JsonProperty("organization") String organization) {
        this.name = name;
        this.organization = organization;
        this.position = position;
        this.department = department;
    }

    /**
     * Converts a given source to a JsonAdaptedUserProfile.
     */
    public JsonAdaptedUserProfile(UserProfile source) {
        name = source.getName().fullName;
        position = source.getPosition().positionName;
        department = source.getDepartment().departmentName;
        organization = source.getOrganization().organizationName;
    }

    /**
     * Converts this Jackson-friendly adapted user profile object into the model's {@code UserProfile} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted user profile.
     */
    public UserProfile toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (position == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Position.class.getSimpleName()));
        }

        if (!Position.isValidPositionName(position)) {
            throw new IllegalValueException(Position.MESSAGE_CONSTRAINTS);
        }
        final Position modelPosition = new Position(position);

        if (department == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Department.class.getSimpleName()));
        }

        if (!Department.isValidDepartmentName(department)) {
            throw new IllegalValueException(Department.MESSAGE_CONSTRAINTS);
        }
        final Department modelDepartment = new Department(department);

        if (organization == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Organization.class.getSimpleName()));
        }

        if (!Organization.isValidOrganizationName(organization)) {
            throw new IllegalValueException(Organization.MESSAGE_CONSTRAINTS);
        }
        final Organization modelOrganization = new Organization(organization);

        return new UserProfile(modelName, modelPosition, modelDepartment, modelOrganization);
    }
}
