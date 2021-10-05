package gomedic.model.activity.exceptions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ActivityNotFoundExceptionTest {
    @Test
    public void constructor() {
        assertDoesNotThrow(ActivityNotFoundException::new);
        assertEquals("Activity not found.", new ActivityNotFoundException().getMessage());
    }
}
