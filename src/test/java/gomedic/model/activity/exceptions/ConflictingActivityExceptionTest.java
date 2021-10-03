package gomedic.model.activity.exceptions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ConflictingActivityExceptionTest {
    @Test
    public void constructor() {
        assertDoesNotThrow(ConflictingActivityException::new);
        assertEquals("There is partial/full overlap in the activity timing",
                new ConflictingActivityException().getMessage());
    }
}
