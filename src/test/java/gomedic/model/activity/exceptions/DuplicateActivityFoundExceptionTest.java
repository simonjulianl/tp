package gomedic.model.activity.exceptions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DuplicateActivityFoundExceptionTest {
    @Test
    public void constructor_isRuntimeException() {
        assertDoesNotThrow(DuplicateActivityFoundException::new);
        assertEquals("Operation would result in duplicate activity as the existing activity exists already.",
                new DuplicateActivityFoundException().getMessage());

    }
}
