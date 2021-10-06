package gomedic.model.person;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.IdTest;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.testutil.ArbitraryPerson;

class AbstractPersonTest {
    private static AbstractPerson person;
    private static Id id;
    private static Name name;
    private static Phone phone;

    @BeforeAll
    public static void setUp() {
        id = new IdTest.TestId(100, 'P');
        name = new Name("John Doe");
        phone = new Phone("87654321");

        // ArbitraryPerson is a testutil class representing the abstract person class
        person = new ArbitraryPerson(name, phone, id);
    }

    @Test
    void constructor_anyNull_throwsNullArgumentException() {
        assertThrows(NullPointerException.class, () -> new ArbitraryPerson(null, null, null));
    }

    @Test
    void getId() {
        assertEquals(id, person.getId());
    }

    @Test
    void getName() {
        assertEquals(name, person.getName());
    }

    @Test
    void getPhone() {
        assertEquals(phone, person.getPhone());
    }

    @Test
    void testHashCode() {
        int hash = Objects.hash(name, phone, id);
        assertEquals(hash, person.hashCode());
    }

    @Test
    void isSamePerson_differentId_returnsTrue() {
        Id diffId = new IdTest.TestId(10, 'Z');
        AbstractPerson personDiffId = new ArbitraryPerson(name, phone, diffId);

        // Test if 2 people with the same name and phone number but different ids are the same,
        // since isSamePerson is a weak equality comparison; We take the name and phone number of a person
        // to assert this weaker equality (similar to the Javascript concept of abstract equality)
        assertTrue(person.isSamePerson(personDiffId));
    }

    @Test
    void isSamePerson_differentName_returnsFalse() {
        Name diffName = new Name("John Smith");
        AbstractPerson personDiffName = new ArbitraryPerson(diffName, phone, id);

        // People with the different name, with the same remaining fields are different people
        assertFalse(person.isSamePerson(personDiffName));
    }

    @Test
    void isSamePerson_differentPhone_returnsFalse() {
        Phone diffPhone = new Phone("99999999");
        AbstractPerson personDiffPhone = new ArbitraryPerson(name, diffPhone, id);

        // People with the different phone, with the same remaining fields are different people
        // This is because multiple people can share the same name, so we use their phone numbers
        // to uniquely identify them as well
        assertFalse(person.isSamePerson(personDiffPhone));
    }

    @Test
    void testEquals() {
        Id diffId = new IdTest.TestId(420, 'L');
        Name diffName = new Name("Johnny");
        Phone diffPhone = new Phone("12345678");

        AbstractPerson personDiffId = new ArbitraryPerson(name, phone, diffId);
        AbstractPerson personDiffName = new ArbitraryPerson(diffName, phone, id);
        AbstractPerson personDiffPhone = new ArbitraryPerson(name, diffPhone, id);
        AbstractPerson personAllSameFields = new ArbitraryPerson(name, phone, id);

        // Not the same if any of the identity fields are different
        assertNotEquals(person, personDiffId);
        assertNotEquals(person, personDiffName);
        assertNotEquals(person, personDiffPhone);

        // Same if other doctor is a different instance but all identity and data fields are the same
        assertEquals(person, personAllSameFields);
    }

    @Test
    void testToString() {
        assertEquals("John Doe;"
                + " Phone: 87654321", person.toString());
    }
}
