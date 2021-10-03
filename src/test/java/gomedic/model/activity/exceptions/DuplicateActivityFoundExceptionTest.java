package gomedic.model.activity.exceptions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class DuplicateActivityFoundExceptionTest {
    @Test
    public void constructor_isRuntimeException() {
        assertDoesNotThrow(DuplicateActivityFoundException::new);
    }
}
