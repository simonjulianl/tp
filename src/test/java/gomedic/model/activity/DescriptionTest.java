package gomedic.model.activity;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_tooLongText_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Description("A".repeat(1000)));
    }

    @Test
    public void toString_validString_testPassed() {
        String text = "test";
        assertEquals(new Description(text).toString(), text);
    }

    @Test
    void hashCode_inputs_testsPassed() {
        String text = "test";
        assertEquals(new Description(text).hashCode(), text.hashCode());
    }

    @Test
    void equals_inputs_testsPassed() {
        String text = "test";
        assertEquals(new Description(text), new Description(text));
    }

    @Test
    void contains_validInput_testsPassed() {
        String s = "some testing strings";
        Description d = new Description(s);

        assertFalse(d.contains("weird", "lmao"));

        // upper case and lower case
        assertTrue(d.contains("TESTING", "STRINGS"));
        assertTrue(d.contains("testing", "lmao"));
    }
}
