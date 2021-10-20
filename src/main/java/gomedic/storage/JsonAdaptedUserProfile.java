package gomedic.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gomedic.commons.exceptions.IllegalValueException;
import gomedic.model.activity.Description;
import gomedic.model.commonfield.Name;
import gomedic.model.userprofile.UserProfile;

/**
 * Jackson-friendly version of {@link gomedic.model.userprofile.UserProfile}.
 */
public class JsonAdaptedUserProfile {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "User Profile's %s field is missing!";

    private final String name;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedUserProfile} with the given details.
     */
    @JsonCreator
    public JsonAdaptedUserProfile(@JsonProperty("name") String name, @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Converts a given source to a JsonAdaptedUserProfile.
     */
    public JsonAdaptedUserProfile(UserProfile source) {
        name = source.getName().fullName;
        description = source.getDescription().toString();
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

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }

        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        return new UserProfile(modelName, modelDescription);
    }
}
