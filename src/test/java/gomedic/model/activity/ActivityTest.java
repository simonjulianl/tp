package gomedic.model.activity;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gomedic.model.commonfield.Time;

class ActivityTest {
    private static Activity activity;
    private static ActivityId id;
    private static Time startTime;
    private static Time endTime;
    private static Title title;
    private static Description desc;

    @BeforeAll
    public static void setUp() {
        id = new ActivityId(1);
        startTime = new Time(LocalDateTime.of(2021, 10, 5, 5, 5));
        endTime = new Time(startTime.time.plusDays(1));
        title = new Title("Testing title");
        desc = new Description("Some dummy description");

        activity = new Activity(id, startTime, endTime, title, desc);
    }

    @Test
    void constructor_anyNull_throwsNullArgumentException() {
        assertThrows(NullPointerException.class, () -> new Activity(null, null, null, null, null));
    }

    @Test
    void constructor_flippedTime_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Activity(id, endTime, startTime, title, desc));
    }

    @Test
    void constructor_sameTime_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Activity(id, startTime, startTime, title, desc));
    }

    @Test
    void getActivityId() {
        assertEquals(id, activity.getActivityId());
    }

    @Test
    void getStartTime() {
        assertEquals(startTime, activity.getStartTime());
    }

    @Test
    void getEndTime() {
        assertEquals(endTime, activity.getEndTime());
    }

    @Test
    void getTitle() {
        assertEquals(title, activity.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals(desc, activity.getDescription());
    }

    @Test
    void testHashCode() {
        int hash = Objects.hash(id, startTime, endTime, desc, title);
        assertEquals(hash, activity.hashCode());
    }

    @Test
    void testEquals() {
        Activity otherAct = new Activity(new ActivityId(20), startTime, endTime, title, desc);
        Activity diffFields = new Activity(id, startTime, endTime, new Title("another"), desc);

        // equal regardless of fields as long as their ids are the same.
        assertEquals(activity, diffFields);
        assertEquals(activity, activity);

        // not equal based on id.
        assertNotEquals(activity, otherAct);
    }

    @Test
    void testToString() {
        assertEquals("A001; "
                + "Title: Testing title;"
                + " Desc: Some dummy description; "
                + "Start Time: 5 October 2021, 05:05"
                + "; End Time: 6 October 2021, 05:05", activity.toString());
    }

    @Test
    void isConflicting_fullPartialOverlapped_testPassed() {
        Activity activityOne = new Activity(id,
                startTime,
                new Time(endTime.time.plusHours(1)),
                title,
                desc);

        assertTrue(activityOne.isConflicting(activity));
        assertTrue(activity.isConflicting(activityOne));
        assertTrue(activity.isConflicting(activity));

        // not conflicting
        Activity activityPast = new Activity(id,
                new Time(startTime.time.minusDays(2)),
                new Time(startTime.time.minusDays(1)),
                title,
                desc);

        assertFalse(activityPast.isConflicting(activity));
        assertFalse(activityPast.isConflicting(activityOne));
        assertFalse(activity.isConflicting(activityPast));
    }

    @Test
    void isConflicting_edgeCases_testPassed() {
        Activity activityOne = new Activity(new ActivityId(2),
                startTime,
                endTime,
                title,
                desc);

        assertTrue(activityOne.isConflicting(activity));
        assertTrue(activity.isConflicting(activityOne));

        Activity activityTwo = new Activity(new ActivityId(2),
                new Time(endTime.time.minusHours(1)),
                endTime,
                title,
                desc);

        assertTrue(activity.isConflicting(activityTwo));
        assertTrue(activityTwo.isConflicting(activity));

        Activity activityThree = new Activity(new ActivityId(2),
                startTime,
                new Time(endTime.time.plusHours(1)),
                title,
                desc);

        assertTrue(activity.isConflicting(activityThree));
        assertTrue(activityThree.isConflicting(activity));

        // overlapping
        Activity activityFour = new Activity(new ActivityId(2),
                new Time(endTime.time.minusHours(1)),
                new Time(endTime.time.plusHours(1)),
                title,
                desc);

        assertTrue(activity.isConflicting(activityFour));
        assertTrue(activityFour.isConflicting(activity));
    }
}