package gomedic.model.person.doctor;

import java.util.Objects;

import gomedic.commons.util.CollectionUtil;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.AbstractPerson;

/**
 * Represents a Doctor in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Doctor extends AbstractPerson {

    // data fields
    private final Department department;

    /**
     * Every field must be present and not null.
     */
    public Doctor(Name name, Phone phone, DoctorId id, Department department) {
        super(name, phone, id);
        CollectionUtil.requireAllNonNull(name, phone, id, department);
        this.department = department;

    }

    public Department getDepartment() {
        return department;
    }

    /**
     * Returns true if both doctors have the same id.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Doctor)) {
            return false;
        }

        Doctor otherDoctor = (Doctor) other;
        return super.equals(otherDoctor);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getId(), department);
    }

    /**
     * Returns a String representation of a {@code Doctor}, using identity fields of its super class, as well as
     * the doctor's Department field.
     *
     * @return a String representation of a {@code Doctor}.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString())
                .append("; Department: ")
                .append(getDepartment());

        return builder.toString();
    }
}
