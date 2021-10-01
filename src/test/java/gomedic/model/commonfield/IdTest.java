package gomedic.model.commonfield;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TestId(null, null));
    }

    @Test
    public void constructor_invalidId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TestId(1000, 'Z'));
    }

    @Test
    public void constructor_invalidPrefix_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TestId(150, 'a'));
        assertThrows(IllegalArgumentException.class, () -> new TestId(150, '!'));
    }

    @Test
    void isValidId_validInput_testsPassed() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Id.isValidId(null, null));

        // invalid Id numbers
        assertFalse(Id.isValidId(0, 'Z')); // id < 1
        assertFalse(Id.isValidId(1000, 'Z')); // id > 999
        assertFalse(Id.isValidId(1, 'a')); // char not A-Z but still an alphabet
        assertFalse(Id.isValidId(1, '!')); // char not A-Z

        // valid Id numbers
        assertTrue(Id.isValidId(1, 'Z')); // id >= 1, valid prefix
        assertTrue(Id.isValidId(999, 'Z')); // edge prefix Z and id 999
        assertTrue(Id.isValidId(50, 'A')); // edge prefix A
        assertTrue(Id.isValidId(50, 'K')); // common case prefix and id
    }

    /**
     * Mock id class to be tested.
     */
    public static class TestId extends Id {

        /**
         * Constructs a {@code Id}.
         *
         * @param id Integer from 0-999.
         * @param prefix Char from A-Z only.
         */
        public TestId(Integer id, Character prefix) {
            super(id, prefix);
        }
    }
}
