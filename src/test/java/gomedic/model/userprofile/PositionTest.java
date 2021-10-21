package gomedic.model.userprofile;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PositionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Position(null));
    }

    @Test
    public void constructor_invalidPosition_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Position(invalidName));
    }

    @Test
    public void isValidPosition() {
        // null name
        assertThrows(NullPointerException.class, () -> Position.isValidPositionName(null));

        // invalid name
        assertFalse(Position.isValidPositionName("")); // empty string
        assertFalse(Position.isValidPositionName(" ")); // spaces only
        assertFalse(Position.isValidPositionName("^")); // only non-alphanumeric characters
        assertFalse(Position.isValidPositionName("senior resident*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Position.isValidPositionName("senior resident")); // alphabets only
        assertTrue(Position.isValidPositionName("12345")); // numbers only
        assertTrue(Position.isValidPositionName("senior resident 2")); // alphanumeric characters
        assertTrue(Position.isValidPositionName("Senior resident")); // with capital letters
    }

    @Test
    public void toString_samePositionName_testPassed() {
        String positionName = "senior resident";
        Position position = new Position(positionName);
        assertEquals(position.toString(), positionName);
    }

    @Test
    public void equals_differentInstanceSameName_isEqual() {
        String positionName = "senior resident";
        Position position = new Position(positionName);
        Position otherPosition = new Position(positionName);
        assertEquals(otherPosition, position);
    }
}
