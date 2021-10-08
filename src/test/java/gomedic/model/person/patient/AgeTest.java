package gomedic.model.person.patient;

import static gomedic.model.person.patient.Age.isValidAge;
import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AgeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void constructor_textContainsNonNumericCharacter_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Age("12a"));
    }

    @Test
    public void constructor_tooBigNumberInTest_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Age("200"));
    }

    @Test
    public void toString_validString_testPassed() {
        String text = "123";
        assertEquals(new Age(text).toString(), text);
    }

    @Test
    void hashCode_inputs_testsPassed() {
        String text = "123";
        assertEquals(new Age(text).hashCode(), text.hashCode());
    }

    @Test
    void equals_inputs_testsPassed() {
        String text = "123";
        assertEquals(new Age(text), new Age(text));
    }

    @Test
    void isValidTitle_testsPassed() {
        assertTrue(isValidAge("123"));
        assertFalse(isValidAge("200"));
    }
}
