package gomedic.model.person.patient;

import static gomedic.model.person.patient.Weight.isValidWeight;
import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WeightTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Weight(null));
    }

    @Test
    public void constructor_textContainsNonNumericCharacter_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Weight("12a"));
    }

    @Test
    public void constructor_tooBigNumberInTest_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Weight("800"));
    }

    @Test
    public void toString_validString_testPassed() {
        String text = "123";
        assertEquals(new Weight(text).toString(), text);
    }

    @Test
    void hashCode_inputs_testsPassed() {
        String text = "123";
        assertEquals(new Weight(text).hashCode(), text.hashCode());
    }

    @Test
    void equals_inputs_testsPassed() {
        String text = "123";
        assertEquals(new Weight(text), new Weight(text));
    }

    @Test
    void isValidTitle_testsPassed() {
        assertTrue(isValidWeight("123"));
        assertFalse(isValidWeight("ABC"));
    }
}
