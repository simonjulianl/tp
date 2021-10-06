package gomedic.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gomedic.model.activity.Activity;
import gomedic.testutil.modelbuilder.ActivityBuilder;

public class TypicalActivities {
    // TODO : use the parser to convert string into local date time
    public static final Activity MEETING = new ActivityBuilder().withTitle("Meeting me")
            .withDescription("today at somewhere")
            .withStartTime(LocalDateTime.now())
            .withEndTime(LocalDateTime.now().plusHours(5)).build();

    public static final Activity CONFLICTING_MEETING = new ActivityBuilder().withTitle("Another Meeting")
            .withDescription("today at somewhere")
            .withStartTime(LocalDateTime.now().plusHours(1))
            .withEndTime(LocalDateTime.now().plusHours(5)).build();

    public static final Activity PAPER_REVIEW = new ActivityBuilder().withTitle("Paper Review with me")
            .withDescription("someone is attending this")
            .withStartTime(LocalDateTime.now().plusDays(1))
            .withEndTime(LocalDateTime.now().plusDays(1).plusHours(5)).build();

    public static final Activity PAST_ACTIVITY = new ActivityBuilder().withTitle("playing games")
            .withDescription("at someone house")
            .withStartTime(LocalDateTime.now().minusDays(2))
            .withEndTime(LocalDateTime.now().minusDays(1)).build();

    public static final Activity DUPLICATE_ACTIVITY = new ActivityBuilder().withTitle("playing games")
            .withDescription("at someone house")
            .withId(1)
            .withStartTime(LocalDateTime.now().minusDays(2))
            .withEndTime(LocalDateTime.now().minusDays(1)).build();

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

