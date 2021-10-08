package gomedic.model.person.patient;

import static gomedic.model.person.patient.BloodType.isValidBloodType;
import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BloodTypeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BloodType(null));
    }

    @Test
    public void constructor_textNotValid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new BloodType("ABC"));
    }

    @Test
    public void toString_validString_testPassed() {
        String text = "AB";
        assertEquals(new BloodType(text).toString(), text);
    }

    @Test
    void hashCode_inputs_testsPassed() {
        String text = "AB";
        assertEquals(new BloodType(text).hashCode(), text.hashCode());
    }

    @Test
    void equals_inputs_testsPassed() {
        String text = "AB";
        assertEquals(new BloodType(text), new BloodType(text));
    }

    @Test
    void isValidTitle_testsPassed() {
        assertTrue(isValidBloodType("AB"));
        assertFalse(isValidBloodType("ABC"));
    }
}
