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
import gomedic.model.commonfield.exceptions.MaxAddressBookCapacityReached;
import gomedic.testutil.modelbuilder.ActivityBuilder;

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
    void add_maxCapacity_throwsMaxCapacityReached() {
        String startTime = "%02d/%02d/%04d 15:00";
        String endTime = "%02d/%02d/%04d 16:00";

        Runnable r = () -> {
            for (int day = 1; day < 28; day++) {
                for (int month = 1; month < 12; month++) {
                    for (int year = 2000; year < 2100; year++) {
                        Activity a = new ActivityBuilder()
                                .withId(uniqueActivityList.getNewActivityId())
                                .withTitle("test")
                                .withStartTime(String.format(startTime, day, month, year))
                                .withEndTime(String.format(endTime, day, month, year))
                                .build();

                        uniqueActivityList.add(a);
                    }
                }
            }
        };

        assertThrows(MaxAddressBookCapacityReached.class, r::run);

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
        uniqueActivityList.setActivities(expectedUniqueActivityList.asUnmodifiableSortedByIdObservableList());
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
        assertEquals(getTypicalActivities(), uniqueActivityList.asUnmodifiableSortedByIdObservableList());
    }

    @Test
    public void asUnmodifiableSortedList_typicalList_sortedByStartingTime() {
        uniqueActivityList.setActivities(getTypicalActivities());
        Activity prev = null;
        for (Activity a : uniqueActivityList.asUnmodifiableSortedByStartTimeList()) {
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
                UnsupportedOperationException.class, () -> uniqueActivityList
                        .asUnmodifiableSortedByIdObservableList().remove(0)
        );
    }

    @Test
    void getLastActivityId_validInput_testsPassed() {
        uniqueActivityList.setActivities(getTypicalActivities());
        assertEquals(2, uniqueActivityList.getNewActivityId());
    }

    @Test
    void getLastActivityId_emptyListInput_testsPassed() {
        assertEquals(1, uniqueActivityList.getNewActivityId());
    }
}
