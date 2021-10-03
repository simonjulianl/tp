package gomedic.model.activity.exceptions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class ConflictingActivityExceptionTest {
    @Test
    public void constructor() {
        assertDoesNotThrow(ConflictingActivityException::new);
    }
}
