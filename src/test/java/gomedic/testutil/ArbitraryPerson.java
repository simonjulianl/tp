package gomedic.testutil;

import gomedic.commons.util.CollectionUtil;
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.AbstractPerson;

/**
 * Represents an arbitrary person class to test the abstract person class in gomedic.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ArbitraryPerson extends AbstractPerson {
    /**
     * Every field must be present and not null.
     */
    public ArbitraryPerson(Name name, Phone phone, Id id) {
        super(name, phone, id);
        CollectionUtil.requireAllNonNull(name, phone, id);
    }
}
