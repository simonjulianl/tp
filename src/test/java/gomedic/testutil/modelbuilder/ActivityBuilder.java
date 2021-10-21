package gomedic.testutil.modelbuilder;

import gomedic.model.activity.Activity;
import gomedic.model.activity.ActivityId;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Time;
import gomedic.model.person.patient.PatientId;

/**
 * A utility class to help with building Activity objects.
 */
public class ActivityBuilder {

    public static final String DEFAULT_START_TIME = "15/09/2022 13:00";
    public static final String DEFAULT_END_TIME = "15/09/2022 14:00";
    public static final String DEFAULT_TITLE = "Meeting";
    public static final String DEFAULT_DESCRIPTION = "123, Jurong West Ave 6, #08-111";
    public static final int DEFAULT_ID = 1;

    private PatientId pId;
    private ActivityId aId;
    private Time startTime;
    private Time endTime;
    private Title title;
    private Description description;
    private boolean isAppointment;

    /**
     * Creates a {@code ActivityBuilder} with the default details. Default state is activity, unless specified.
     */
    public ActivityBuilder() {
        aId = new ActivityId(DEFAULT_ID);
        startTime = new Time(DEFAULT_START_TIME);
        endTime = new Time(DEFAULT_END_TIME);
        title = new Title(DEFAULT_TITLE);
        description = new Description(DEFAULT_DESCRIPTION);
        pId = null;
        isAppointment = false;
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
    public ActivityBuilder withStartTime(String startTime) {
        this.startTime = new Time(startTime);
        return this;
    }

    /**
     * Sets the {@code withEndTime} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withEndTime(String endTime) {
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
        aId = new ActivityId(id);
        return this;
    }

    /**
     * Sets the {@code ActivityId} of the {@code id} that we are building.
     */
    public ActivityBuilder withPatientId(int id) {
        pId = new PatientId(id);
        isAppointment = true;
        return this;
    }

    /**
     * Returns an Activity object of type activity or appointment.
     *
     * @return Activity object.
     */
    public Activity build() {
        return isAppointment
                ? new Activity(aId, pId, startTime, endTime, title, description)
                : new Activity(aId, startTime, endTime, title, description);
    }
}
