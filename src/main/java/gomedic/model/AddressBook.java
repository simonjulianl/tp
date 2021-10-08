package gomedic.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import gomedic.model.activity.Activity;
import gomedic.model.activity.UniqueActivityList;
import gomedic.model.person.Person;
import gomedic.model.person.UniquePersonList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueActivityList activities;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

    {
        persons = new UniquePersonList();
        activities = new UniqueActivityList();
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public AddressBook() {
    }

    //// list overwrite operations

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setActivities(newData.getActivityList());
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the person list with {@code activities}.
     * {@code persons} must not contain duplicate and conflicting activities.
     */
    public void setActivities(List<Activity> activities) {
        this.activities.setActivities(activities);
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if an activity with the same id as {@code activities}
     * exists in the address book.
     */
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return activities.contains(activity);
    }

    /**
     * Returns true if there exists another conflicting activity
     * in terms of timing in the addressbook.
     */
    public boolean hasConflictingActivity(Activity activity) {
        requireNonNull(activity);
        return activities.containsConflicting(activity);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Adds an activity to the address book.
     * The activity must not be duplicate and conflicting.
     */
    public void addActivity(Activity a) {
        activities.add(a);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Remove the activity based on the id.
     * Therefore, regardless whether the activity has different titles/fields,
     * as long as the id is the same, it would be treated as equal.
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons; "
                + activities.asUnmodifiableObservableList().size() + " activities";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Activity> getActivityList() {
        return activities.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Activity> getActivityListSortedStartTime() {
        return activities.asUnmodifiableSortedList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons)
                && activities.equals(((AddressBook) other).activities));
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, activities);
    }
}
