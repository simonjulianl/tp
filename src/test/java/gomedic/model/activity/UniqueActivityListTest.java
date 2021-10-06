package gomedic.model.activity;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalActivities.CONFLICTING_MEETING;
import static gomedic.testutil.TypicalActivities.DUPLICATE_ACTIVITY;
import static gomedic.testutil.TypicalActivities.MEETING;
import static gomedic.testutil.TypicalActivities.PAPER_REVIEW;
import static gomedic.testutil.TypicalActivities.PAST_ACTIVITY;
import static gomedic.testutil.TypicalActivities.getTypicalActivities;
import static gomedic.testutil.TypicalActivities.getTypicalActivitiesConflicting;
import static gomedic.testutil.TypicalActivities.getTypicalActivitiesDuplicate;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import gomedic.model.activity.exceptions.ActivityNotFoundException;
import gomedic.model.activity.exceptions.ConflictingActivityException;
import gomedic.model.activity.exceptions.DuplicateActivityFoundException;

class UniqueActivityListTest {
    private final UniqueActivityList uniqueActivityList = new UniqueActivityList();

    @Test
    public void contains_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.contains(null));
        assertThrows(NullPointerException.class, () -> uniqueActivityList.containsConflicting(null));
    }

    @Test
    public void contains_activityNotInList_returnsFalse() {
        assertFalse(uniqueActivityList.contains(MEETING));
    }

    @Test
    void add_normal_returnsTrue() {
        uniqueActivityList.add(MEETING);
        assertTrue(uniqueActivityList.contains(MEETING));
    }

    @Test
    void add_duplicate_throwsDuplicateActivityException() {
        uniqueActivityList.add(MEETING);
        assertTrue(uniqueActivityList.contains(MEETING));
        assertThrows(DuplicateActivityFoundException.class, () -> uniqueActivityList.add(MEETING));
        assertThrows(DuplicateActivityFoundException.class, () -> uniqueActivityList.add(DUPLICATE_ACTIVITY));
    }

    @Test
    void containsConflicting_variousMeeting_testPassed() {
        assertFalse(uniqueActivityList.containsConflicting(MEETING));

        uniqueActivityList.add(MEETING);
        assertTrue(uniqueActivityList.contains(MEETING));
        assertTrue(uniqueActivityList.containsConflicting(CONFLICTING_MEETING));
        assertFalse(uniqueActivityList.containsConflicting(PAST_ACTIVITY));
        assertFalse(uniqueActivityList.containsConflicting(PAPER_REVIEW));

        uniqueActivityList.add(PAST_ACTIVITY);
        assertFalse(uniqueActivityList.containsConflicting(PAPER_REVIEW));
    }

    @Test
    public void remove_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.remove(null));
    }

    @Test
    public void remove_activityDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(ActivityNotFoundException.class, () -> uniqueActivityList.remove(MEETING));
    }

    @Test
    public void remove_activityInList_doesNotThrow() {
        uniqueActivityList.add(MEETING);
        assertDoesNotThrow(() -> uniqueActivityList.remove(MEETING));
    }

    @Test
    public void setActivities_nullUniqueActivityList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.setActivities((UniqueActivityList) null));
    }

    @Test
    public void setActivities_uniqueActivityList_replacesOwnList() {
        uniqueActivityList.add(PAPER_REVIEW);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(MEETING);
        uniqueActivityList.setActivities(expectedUniqueActivityList);
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivities_list_replacesOwnList() {
        uniqueActivityList.add(PAPER_REVIEW);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(MEETING);
        uniqueActivityList.setActivities(expectedUniqueActivityList.asUnmodifiableObservableList());
        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void setActivities_listDuplicate_replacesOwnList() {
        assertThrows(DuplicateActivityFoundException.class, () -> uniqueActivityList.setActivities(
                getTypicalActivitiesDuplicate()));
    }

    @Test
    public void setActivities_listConflicting_replacesOwnList() {
        assertThrows(ConflictingActivityException.class, () -> uniqueActivityList.setActivities(
                getTypicalActivitiesConflicting()));
    }

    @Test
    public void iterator_ableToBeIterated_testPassed() {
        assertDoesNotThrow(() -> uniqueActivityList.forEach(it -> System.out.println(1)));
    }

    @Test
    public void testEquals_sameBackingList_returnsTrue() {
        uniqueActivityList.add(PAPER_REVIEW);
        UniqueActivityList expectedUniqueActivityList = new UniqueActivityList();
        expectedUniqueActivityList.add(PAPER_REVIEW);

        assertEquals(expectedUniqueActivityList, uniqueActivityList);
    }

    @Test
    public void asUnmodifiableObservableList() {
        uniqueActivityList.setActivities(getTypicalActivities());
        assertEquals(getTypicalActivities(), uniqueActivityList.asUnmodifiableObservableList());
    }

    @Test
    public void asUnmodifiableSortedList_typicalList_sortedByStartingTime() {
        uniqueActivityList.setActivities(getTypicalActivities());
        Activity prev = null;
        for (Activity a : uniqueActivityList.asUnmodifiableSortedList()) {
            if (prev == null) {
                prev = a;
            } else {
                assertTrue(a.getStartTime().isAfter(prev.getEndTime()));
            }
        }
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class, () -> uniqueActivityList.asUnmodifiableObservableList().remove(0)
        );
    }
}
