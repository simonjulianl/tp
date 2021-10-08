package gomedic.model.commonfield;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time((String) null));
        assertThrows(NullPointerException.class, () -> new Time((LocalDateTime) null));
    }

    @Test
    public void constructor_invalidFormat_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Time("30/11/2022"));
        assertThrows(IllegalArgumentException.class, () -> new Time("13:00"));
        assertThrows(IllegalArgumentException.class, () -> new Time("time"));
        assertThrows(IllegalArgumentException.class, () -> new Time("30-11-2022"));
    }

    @Test
    public void constructor_invalidDateDay_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Time("32/12/2022 13:00"));
        assertThrows(IllegalArgumentException.class, () -> new Time("-1-12-2022 13:00"));
        assertThrows(IllegalArgumentException.class, () -> new Time("00-01-2022 13:00"));
        assertThrows(IllegalArgumentException.class, () -> new Time("ab-12-2015 13:00"));
    }

    @Test
    public void constructor_invalidDateMonth_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Time("15/13/2022 13:00"));
        assertThrows(IllegalArgumentException.class, () -> new Time("15-13-2022 13:00"));
        assertThrows(IllegalArgumentException.class, () -> new Time("15-1-2022 13:00"));
        assertThrows(IllegalArgumentException.class, () -> new Time("2022-13-15 13:00"));
    }

    @Test
    public void constructor_invalidHour_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Time("15/12/2022 25:00"));
        assertThrows(IllegalArgumentException.class, () -> new Time("15/12/2022 -1:00"));
    }

    @Test
    public void constructor_invalidMinute_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Time("15/12/2022 13:61"));
        assertThrows(IllegalArgumentException.class, () -> new Time("15/12/2022 13:1"));
    }

    @Test
    public void constructor_validInput_testPassed() {
        assertDoesNotThrow(() -> new Time("15/12/2022 13:00"));
        assertDoesNotThrow(() -> new Time("15-12-2022 13:00"));
        assertDoesNotThrow(() -> new Time("2022-12-15 13:00"));
        assertDoesNotThrow(() -> new Time(LocalDateTime.now()));
    }

    @Test
    public void constructor_incompleteDigit_throwsInvalidArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Time("15/12/2022 13:0"));
        assertThrows(IllegalArgumentException.class, () -> new Time("15-12-2022 5:00"));
        assertThrows(IllegalArgumentException.class, () -> new Time("2022-12-5 13:00"));
        assertThrows(IllegalArgumentException.class, () -> new Time("2022-1-15 13:00"));
    }

    @Test
    public void toString_manualInput_testPassed() {
        Time time = new Time("05/10/2020 05:50");
        assertEquals("5 October 2020, 05:50", time.toString());
        assertEquals("5 October 2020", time.toDateString());
        assertEquals("05:50", time.toTimeString());
    }

    @Test
    public void toString_localDateTime_testPassed() {
        Time time = new Time(LocalDateTime.of(2020, 10, 5, 5, 50));
        assertEquals("5 October 2020, 05:50", time.toString());
        assertEquals("5 October 2020", time.toDateString());
        assertEquals("05:50", time.toTimeString());
    }

    @Test
    public void hashCode_validInput_testPassed() {
        LocalDateTime ldt = LocalDateTime.of(2020, 10, 5, 5, 50);
        Time time = new Time(ldt);
        assertEquals(ldt.hashCode(), time.hashCode());
    }

    @Test
    public void equalsDate_localDateTime_testPassed() {
        Time time = new Time(LocalDateTime.of(2020, 10, 5, 5, 50));
        assertTrue(time.equalsDate(time));
        assertFalse(time.isAfter(time));
        assertFalse(time.isBefore(time));
    }

    @Test
    public void isAfterIsBefore_localDateTime_testPassed() {
        Time timeFirst = new Time(LocalDateTime.of(2020, 10, 5, 5, 50));
        Time time = new Time(LocalDateTime.of(2021, 10, 5, 5, 50));
        assertTrue(time.isAfter(timeFirst));
        assertTrue(timeFirst.isBefore(time));
    }
}
