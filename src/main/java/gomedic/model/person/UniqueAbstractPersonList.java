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
public class UniqueAbstractPersonList implements Iterable<AbstractPerson> {

    private final ObservableList<AbstractPerson> internalList = FXCollections.observableArrayList();
    private final ObservableList<AbstractPerson> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(AbstractPerson toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(AbstractPerson toAdd) {
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
    public void setPerson(AbstractPerson target, AbstractPerson editedPerson) {
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
    public void remove(AbstractPerson toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniqueAbstractPersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<AbstractPerson> persons) {
        CollectionUtil.requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<AbstractPerson> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<AbstractPerson> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAbstractPersonList // instanceof handles nulls
                && internalList.equals(((UniqueAbstractPersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<AbstractPerson> persons) {
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
