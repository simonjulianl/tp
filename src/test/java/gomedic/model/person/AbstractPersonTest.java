package gomedic.model.person;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    void testEquals() {
        Id diffId = new IdTest.TestId(420, 'L');
        Name diffName = new Name("Johnny");
        Phone diffPhone = new Phone("12345678");

        AbstractPerson personDiffId = new ArbitraryPerson(name, phone, diffId);
        AbstractPerson personDiffName = new ArbitraryPerson(diffName, phone, id);
        AbstractPerson personDiffPhone = new ArbitraryPerson(name, diffPhone, id);
        AbstractPerson personAllSameFields = new ArbitraryPerson(name, phone, id);

        assertNotEquals(person, personDiffId); // Person is not same if id is not same
        assertEquals(person, personDiffName); // Person with different names are the same if id is the same
        assertEquals(person, personDiffPhone); // Person with different phone numbers are the same if id is the same

        // Same if other person is different instance with the same id and data fields
        assertEquals(person, personAllSameFields);
    }

    @Test
    void testToString() {
        assertEquals("John Doe;"
                + " Phone: 87654321", person.toString());
    }
}
