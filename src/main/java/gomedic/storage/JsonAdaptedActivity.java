package gomedic.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gomedic.commons.exceptions.IllegalValueException;
import gomedic.model.activity.Activity;
import gomedic.model.activity.ActivityId;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Time;

public class JsonAdaptedActivity {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Activity's %s field is missing!";

    private final String id;
    private final String title;
    private final String description;
    private final String startTime;
    private final String endTime;

    /**
     * Constructs a {@code JsonAdaptedActivity} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedActivity(@JsonProperty("id") String id,
                               @JsonProperty("title") String title,
                               @JsonProperty("description") String description,
                               @JsonProperty("startTime") String startTime,
                               @JsonProperty("endTime") String endTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Activity} into this class for Jackson use.
     */
    public JsonAdaptedActivity(Activity source) {
        id = source.getActivityId().toString();
        title = source.getTitle().toString();
        description = source.getDescription().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Activity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Activity toModelType() throws IllegalValueException {
        if (id == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, ActivityId.class.getSimpleName()));
        }
        if (!ActivityId.isValidActivityId(id)) {
            throw new IllegalValueException(ActivityId.MESSAGE_CONSTRAINTS);
        }

        final ActivityId modelId = new ActivityId(id);

        if (title == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }

        final Title modelTitle = new Title(title);

        if (description == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(title)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }

        final Description modelDescription = new Description(description);

        // should check for end and start time
        // for now, lets use some dummy stuff
        // TODO : use the real date time parser

        Time modelStartTime = new Time(LocalDateTime.now());
        Time modelEndTime = new Time(LocalDateTime.now().plusHours(1));

        return new Activity(modelId, modelStartTime, modelEndTime, modelTitle, modelDescription);
    }
}
