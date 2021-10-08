package gomedic.model.person.patient;

import static gomedic.model.person.patient.Gender.isValidGender;
import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GenderTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_textNotValid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Gender("ABC"));
    }

    @Test
    public void toString_validString_testPassed() {
        String text = "M";
        assertEquals(new Gender(text).toString(), text);
    }

    @Test
    void hashCode_inputs_testsPassed() {
        String text = "M";
        assertEquals(new Gender(text).hashCode(), text.hashCode());
    }

    @Test
    void equals_inputs_testsPassed() {
        String text = "M";
        assertEquals(new Gender(text), new Gender(text));
    }

    @Test
    void isValidTitle_testsPassed() {
        assertTrue(isValidGender("M"));
        assertFalse(isValidGender("ABC"));
    }
}
