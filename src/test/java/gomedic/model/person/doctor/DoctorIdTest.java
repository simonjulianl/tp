package gomedic.model.person.doctor;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import gomedic.model.commonfield.IdTest;

public class DoctorIdTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DoctorId(null));
    }

    @Test
    public void constructor_invalidNumber_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new DoctorId(1000));
    }

    @Test
    public void toString_validDoctorId_testPassed() {
        assertEquals(new DoctorId(100).toString(), "D100");
    }

    @Test
    void isValidId_validInput_testsPassed() {
        // null doctor id
        assertThrows(NullPointerException.class, () -> new DoctorId(null));

        // invalid doctor id
        assertFalse(DoctorId.isValidDoctorId(new IdTest.TestId(999, 'C'))); // wrong prefix
        assertFalse(DoctorId.isValidDoctorId(new IdTest.TestId(10, 'A'))); // wrong prefix

        // valid doctor id
        assertTrue(DoctorId.isValidDoctorId(new DoctorId(100))); // doctor id
        assertTrue(DoctorId.isValidDoctorId(new IdTest.TestId(50, 'D'))); // other id with same prefix
    }

    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    @Test
    void equals_inputs_testsPassed() {
        assertEquals(new DoctorId(100), new DoctorId(100));
        assertEquals(new DoctorId(100), new IdTest.TestId(100, 'D'));
    }
}
