package gomedic.testutil.modelbuilder;

import java.time.LocalDateTime;

import gomedic.model.activity.Activity;
import gomedic.model.activity.ActivityId;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Time;

/**
 * A utility class to help with building Activity objects.
 */
public class ActivityBuilder {

    public static final LocalDateTime DEFAULT_START_TIME = LocalDateTime.of(2020, 10, 10, 5, 5, 5);
    public static final LocalDateTime DEFAULT_END_TIME = DEFAULT_START_TIME.plusDays(1);
    public static final String DEFAULT_TITLE = "Meeting";
    public static final String DEFAULT_DESCRIPTION = "123, Jurong West Ave 6, #08-111";
    private static Integer id = 1;

    private ActivityId aid;
    private Time startTime;
    private Time endTime;
    private Title title;
    private Description description;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public ActivityBuilder() {
        aid = new ActivityId(id++);
        startTime = new Time(DEFAULT_START_TIME);
        endTime = new Time(DEFAULT_END_TIME);
        title = new Title(DEFAULT_TITLE);
        description = new Description(DEFAULT_DESCRIPTION);
    }


    /**
     * Sets the {@code Title} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withStartTime(LocalDateTime startTime) {
        this.startTime = new Time(startTime);
        return this;
    }

    /**
     * Sets the {@code withEndTime} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withEndTime(LocalDateTime endTime) {
        this.endTime = new Time(endTime);
        return this;
    }

    /**
     * Sets the {@code withEndTime} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code ActivityId} of the {@code id} that we are building.
     */
    public ActivityBuilder withId(int id) {
        aid = new ActivityId(id);
        return this;
    }


    public Activity build() {
        return new Activity(aid, startTime, endTime, title, description);
    }
}
