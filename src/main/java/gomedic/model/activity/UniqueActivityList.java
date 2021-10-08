package gomedic.model.activity;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import gomedic.commons.util.CollectionUtil;
import gomedic.model.activity.exceptions.ActivityNotFoundException;
import gomedic.model.activity.exceptions.ConflictingActivityException;
import gomedic.model.activity.exceptions.DuplicateActivityFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of activities that enforces uniqueness between its elements and does not allow nulls.
 * An activity is considered unique by comparing its activity id solely which is compared using {@code Id#equal(other)}.
 * Not only that, the activity list would enforce its elements to be not conflicting with each other as defined
 * in {@code Activity#isConflicting(Activity)}. Therefore, the elements are not overlapping
 * and uniquely identified by its id.
 * <p>
 * Supports  a minimal set of list operations.
 */
public class UniqueActivityList implements Iterable<Activity> {

    private final ObservableList<Activity> internalList = FXCollections.observableArrayList();
    private final ObservableList<Activity> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns the new activity id available in this list.
     * If it's an empty list, return id 1.
     */
    public int getNewActivityId() {
        try {
            return internalList
                    .get(internalList.size() - 1)
                    .getActivityId()
                    .getIdNumber() + 1;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Adds an activity to the list.
     * The person must not already exist in the list.
     */
    public void add(Activity toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateActivityFoundException();
        }

        if (containsConflicting(toAdd)) {
            throw new ConflictingActivityException();
        }

        internalList.add(toAdd);
    }

    /**
     * Returns true if the list contains an equivalent activity as the given argument.
     */
    public boolean contains(Activity toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains conflicting activity.
     */
    public boolean containsConflicting(Activity toCheck) {
        requireNonNull(toCheck);
        if (contains(toCheck)) {
            return internalList.stream().filter(toCheck::isConflicting).count() > 1;
        } else {
            return internalList.stream().anyMatch(toCheck::isConflicting);
        }
    }

    /**
     * Removes the equivalent activity from the list.
     * The activity must exist in the list.
     */
    public void remove(Activity toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ActivityNotFoundException();
        }
    }

    public void setActivities(UniqueActivityList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code activities}.
     * {@code activities} must not contain duplicate and conflicting activities.
     */
    public void setActivities(List<Activity> activities) {
        CollectionUtil.requireAllNonNull(activities);
        if (!checkActivitiesAreUnique(activities)) {
            throw new DuplicateActivityFoundException();
        }

        if (!checkActivitiesAreConflicting(activities)) {
            throw new ConflictingActivityException();
        }

        internalList.setAll(activities);
    }

    /**
     * Returns true if {@code activities} contains only unique activities.
     */
    private boolean checkActivitiesAreUnique(List<Activity> activities) {
        for (int i = 0; i < activities.size() - 1; i++) {
            for (int j = i + 1; j < activities.size(); j++) {
                Activity first = activities.get(i);
                Activity second = activities.get(j);

                if (first.equals(second)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns true if {@code activities} contains only not conflicting activities.
     */
    private boolean checkActivitiesAreConflicting(List<Activity> activities) {
        for (int i = 0; i < activities.size() - 1; i++) {
            for (int j = i + 1; j < activities.size(); j++) {
                Activity first = activities.get(i);
                Activity second = activities.get(j);

                if (first.isConflicting(second)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public Iterator<Activity> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueActivityList // instanceof handles nulls
                && internalList.equals(((UniqueActivityList) other).internalList));
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Activity> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     * Returned list is sorted by starting the start time.
     */
    public ObservableList<Activity> asUnmodifiableSortedList() {
        return internalUnmodifiableList
                .sorted((activity, otherAct) ->
                        activity.getStartTime() == otherAct.getStartTime()
                                ? 0
                                : activity
                                .getStartTime()
                                .isBefore(otherAct.getStartTime()) ? -1 : 1);
    }
}
