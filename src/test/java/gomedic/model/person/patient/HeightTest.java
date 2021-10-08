package gomedic.model.person.patient;

import static gomedic.model.person.patient.Height.isValidHeight;
import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HeightTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Height(null));
    }

    @Test
    public void constructor_textContainsNonNumericCharacter_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Height("12a"));
    }

    @Test
    public void constructor_tooBigNumberInTest_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Height("400"));
    }

    @Test
    public void toString_validString_testPassed() {
        String text = "123";
        assertEquals(new Height(text).toString(), text);
    }

    @Test
    void hashCode_inputs_testsPassed() {
        String text = "123";
        assertEquals(new Height(text).hashCode(), text.hashCode());
    }

    @Test
    void equals_inputs_testsPassed() {
        String text = "123";
        assertEquals(new Height(text), new Height(text));
    }

    @Test
    void isValidTitle_testsPassed() {
        assertTrue(isValidHeight("123"));
        assertFalse(isValidHeight("ABC"));
    }
}
