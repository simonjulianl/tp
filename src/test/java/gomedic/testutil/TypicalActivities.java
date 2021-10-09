package gomedic.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gomedic.model.activity.Activity;
import gomedic.testutil.modelbuilder.ActivityBuilder;

public class TypicalActivities {
    public static final Activity MEETING = new ActivityBuilder()
            .withId(1)
            .withTitle("Meeting me")
            .withDescription("today at somewhere")
            .withStartTime("15/09/2022 13:00")
            .withEndTime("15/09/2022 15:00").build();

    public static final Activity CONFLICTING_MEETING = new ActivityBuilder()
            .withId(2)
            .withTitle("Another Meeting")
            .withDescription("today at somewhere")
            .withStartTime("15/09/2022 13:00")
            .withEndTime("15/09/2022 14:00").build();

    public static final Activity PAPER_REVIEW = new ActivityBuilder()
            .withId(3)
            .withTitle("Paper Review with me")
            .withDescription("someone is attending this")
            .withStartTime("16/09/2022 13:00")
            .withEndTime("17/09/2022 13:00").build();

    public static final Activity PAST_ACTIVITY = new ActivityBuilder()
            .withId(4)
            .withTitle("playing games")
            .withDescription("at someone house")
            .withStartTime("15/09/2020 13:00")
            .withEndTime("15/09/2020 14:00").build();

    public static final Activity DUPLICATE_ACTIVITY = new ActivityBuilder()
            .withId(1)
            .withTitle("playing games")
            .withDescription("at someone house")
            .withStartTime("15/09/2023 13:00")
            .withEndTime("15/09/2024 13:00").build();

    private TypicalActivities() {
    } // prevents instantiation

    /**
     * @return a list of activity including the conflicting one.
     */
    public static List<Activity> getTypicalActivitiesConflicting() {
        return new ArrayList<>(Arrays.asList(MEETING, CONFLICTING_MEETING, PAPER_REVIEW, PAST_ACTIVITY));
    }

    /**
     * @return a list of activity.
     */
    public static List<Activity> getTypicalActivities() {
        return new ArrayList<>(Arrays.asList(MEETING, PAPER_REVIEW, PAST_ACTIVITY));
    }

    /**
     * @return a list of activity including the duplicate one.
     */
    public static List<Activity> getTypicalActivitiesDuplicate() {
        return new ArrayList<>(Arrays.asList(MEETING, DUPLICATE_ACTIVITY, PAPER_REVIEW, PAST_ACTIVITY));
    }
}

