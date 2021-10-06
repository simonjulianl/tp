package gomedic.model.person;

import java.util.Objects;

import gomedic.commons.util.CollectionUtil;
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;

/**
 * Represents a AbstractPerson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class AbstractPerson {
    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Id id;

    /**
     * Every field must be present and not null.
     */
    public AbstractPerson(Name name, Phone phone, Id id) {
        CollectionUtil.requireAllNonNull(name, phone, id);
        this.name = name;
        this.phone = phone;
        this.id = id;
    }
    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Id getId() {
        return id;
    }

    /**
     * Returns true if both persons have the same name and phone number.
     * We compare phone numbers as well as multiple people can share the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(AbstractPerson otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AbstractPerson)) {
            return false;
        }

        AbstractPerson otherPerson = (AbstractPerson) other;
        return isSamePerson(otherPerson)
                && otherPerson.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, id);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone());

        return builder.toString();
    }

}
