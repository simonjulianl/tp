package gomedic.model.commonfield;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidDateMonth_throwsIllegalArgumentException() {
        assertThrows(DateTimeException.class, () -> new Time(30, 2, 2021, 15, 30));
        assertThrows(DateTimeException.class, () -> new Time(32, 1, 2021, 15, 30));
        assertThrows(DateTimeException.class, () -> new Time(15, -1, 2021, 15, 30));
        assertThrows(DateTimeException.class, () -> new Time(15, 13, 2021, 15, 30));
    }

    @Test
    public void constructor_invalidHour_throwsIllegalArgumentException() {
        assertThrows(DateTimeException.class, () -> new Time(15, 2, 2021, 25, 30));
        assertThrows(DateTimeException.class, () -> new Time(15, 2, 2021, -1, 30));
    }

    @Test
    public void constructor_invalidMinute_throwsIllegalArgumentException() {
        assertThrows(DateTimeException.class, () -> new Time(15, 2, 2021, 15, 61));
        assertThrows(DateTimeException.class, () -> new Time(15, 2, 2021, 15, -1));
    }

    @Test
    public void constructor_validInput_testPassed() {
        assertDoesNotThrow(() -> new Time(5, 10, 2020, 5, 50));
        assertDoesNotThrow(() -> new Time(5, 12, 2022, 23, 59));
        assertDoesNotThrow(() -> new Time(5, 10, 2020, 0, 0));
        assertDoesNotThrow(() -> new Time(31, 10, 2020, 5, 15));
        assertDoesNotThrow(() -> new Time(LocalDateTime.now()));
    }

    @Test
    public void toString_manualInput_testPassed() {
        Time time = new Time(5, 10, 2020, 5, 50);
        assertEquals("5 October 2020 05:50", time.toString());
        assertEquals("5 October 2020", time.toDateString());
        assertEquals("05:50", time.toTimeString());
    }

    @Test
    public void toString_localDateTime_testPassed() {
        Time time = new Time(LocalDateTime.of(2020, 10, 5, 5, 50));
        assertEquals("5 October 2020 05:50", time.toString());
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
