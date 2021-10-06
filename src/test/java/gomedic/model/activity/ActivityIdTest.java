package gomedic.model.activity;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import gomedic.model.commonfield.IdTest;

public class ActivityIdTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ActivityId((Integer) null));
        assertThrows(NullPointerException.class, () -> new ActivityId((String) null));
    }

    @Test
    public void constructor_invalidNumber_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ActivityId(1000));
    }

    @Test
    public void toString_validActivityId_testPassed() {
        assertEquals(new ActivityId(100).toString(), "A100");
    }

    @Test
    void isValidId_validInputId_testsPassed() {
        // null activity id
        assertThrows(NullPointerException.class, () -> new ActivityId((Integer) null));

        // invalid activity id
        assertFalse(ActivityId.isValidActivityId(new IdTest.TestId(999, 'C'))); // wrong prefix
        assertFalse(ActivityId.isValidActivityId(new IdTest.TestId(10, 'D'))); // wrong prefix

        // valid activity id
        assertTrue(ActivityId.isValidActivityId(new ActivityId(100))); // activity id
        assertTrue(ActivityId.isValidActivityId(new IdTest.TestId(50, 'A'))); // other id with same prefix
    }

    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    @Test
    void equals_inputs_testsPassed() {
        assertEquals(new ActivityId(100), new ActivityId(100));
        assertEquals(new ActivityId(100), new IdTest.TestId(100, 'A'));
    }

    @Test
    void isValidActivityId_stringInput_testsPassed() {
        // invalid activity id
        assertFalse(ActivityId.isValidActivityId("test")); // no integer
        assertFalse(ActivityId.isValidActivityId("B100")); // wrong prefix

        // valid activity id
        assertTrue(ActivityId.isValidActivityId(new ActivityId(100))); // activity id
        assertTrue(ActivityId.isValidActivityId(new IdTest.TestId(50, 'A'))); // other id with same prefix
    }
}
