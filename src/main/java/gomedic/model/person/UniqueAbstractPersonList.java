package gomedic.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import gomedic.commons.util.CollectionUtil;
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.exceptions.MaxAddressBookCapacityReached;
import gomedic.model.person.exceptions.DuplicatePersonException;
import gomedic.model.person.exceptions.PersonNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code AbstractPerson#equals(AbstractPerson)}. As such,
 * adding and updating of persons uses AbstractPerson#equals(AbstractPerson) for equality so as to ensure that
 * the person being added or updated is unique in terms of identity in the UniqueAbstractPersonList. Removal of a
 * person uses AbstractPerson#equals(Object) so as to ensure that the person with exactly the same
 * fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see AbstractPerson#equals(Object)
 */
public class UniqueAbstractPersonList<T extends AbstractPerson> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    /**
     * Checks if there is an available id that can be assigned to a new entry to the list.
     *
     * @return true if there is an available id to be assigned.
     */
    public boolean hasNewId() {
        return internalList.size() < Id.MAXIMUM_ASSIGNABLE_IDS;
    }

    /**
     * Returns a new available id.
     * If it's an empty list, return id 1.
     */
    public int getNewId() {
        if (!hasNewId()) {
            // Ideally this exception should never be triggered; Always use this::hasNewId before calling this method
            throw new MaxAddressBookCapacityReached();
        }

        if (internalList.size() == 0) {
            return 1;
        }

        int counter = 1;
        // Find first id that does not exist in the list starting from D001
        for (Iterator<Integer> it = internalList
                .stream()
                .map(entry -> entry.getId().getIdNumber())
                .sorted()
                .iterator(); it.hasNext(); counter++) {
            int id = it.next();
            if (id != counter) {
                return counter;
            }
        }

        // If all are in order, return the next possible id
        return counter;
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(T target, T editedPerson) {
        CollectionUtil.requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniqueAbstractPersonList<? extends T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<? extends T> persons) {
        CollectionUtil.requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableSortedByIdObservableList() {
        return internalUnmodifiableList
            .sorted((abstractPerson, otherAbstractPerson) ->
                abstractPerson.getId() == otherAbstractPerson.getId()
                    ? 0
                    : abstractPerson.getId().getIdNumber()
                    < otherAbstractPerson.getId().getIdNumber() ? -1 : 1);
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof UniqueAbstractPersonList) {
            @SuppressWarnings("unchecked")
            UniqueAbstractPersonList<T> otherList = (UniqueAbstractPersonList<T>) other;
            return internalList.equals(otherList.internalList);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<? extends T> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).equals(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
