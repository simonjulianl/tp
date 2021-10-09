package gomedic.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import gomedic.commons.util.CollectionUtil;
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
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
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
