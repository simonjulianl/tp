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
    void isSamePerson_differentId_returnsFalse() {
        Id diffId = new IdTest.TestId(10, 'Z');
        AbstractPerson personDiffId = new ArbitraryPerson(name, phone, diffId);

        // Persons with the different id are different people
        assertFalse(person.isSamePerson(personDiffId));
    }

    @Test
    void isSamePerson_differentName_returnsTrue() {
        Name diffName = new Name("John Smith");
        AbstractPerson personDiffName = new ArbitraryPerson(diffName, phone, id);

        // People with the same id are the same person, even with different names
        assertTrue(person.isSamePerson(personDiffName));
    }

    @Test
    void isSamePerson_differentPhone_returnsTrue() {
        Phone diffPhone = new Phone("99999999");
        AbstractPerson personDiffPhone = new ArbitraryPerson(name, diffPhone, id);

        // People with the same id are the same person, even with different phone numbers
        assertTrue(person.isSamePerson(personDiffPhone));
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
