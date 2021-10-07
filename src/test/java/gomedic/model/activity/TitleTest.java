package gomedic.model.activity;

import static gomedic.model.activity.Title.isValidTitle;
import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TitleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_tooLongText_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Title("A".repeat(61)));
    }

    @Test
    public void toString_validString_testPassed() {
        String text = "test";
        assertEquals(new Title(text).toString(), text);
    }

    @Test
    void hashCode_inputs_testsPassed() {
        String text = "test";
        assertEquals(new Title(text).hashCode(), text.hashCode());
    }

    @Test
    void equals_inputs_testsPassed() {
        String text = "test";
        assertEquals(new Title(text), new Title(text));
    }

    @Test
    void contains_validInput_testsPassed() {
        String s = "some testing strings";
        Title d = new Title(s);

        assertFalse(d.contains("weird", "lmao"));

        // upper case and lower case
        assertTrue(d.contains("TESTING", "STRINGS"));
        assertTrue(d.contains("testing", "lmao"));
    }

    @Test
    void isValidTitle_testsPassed() {
        assertTrue(isValidTitle("a".repeat(10)));
        assertFalse(isValidTitle("a".repeat(61)));
    }
}
