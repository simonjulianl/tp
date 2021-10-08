package gomedic.model.person.patient;

import static gomedic.model.person.patient.PatientId.isValidPatientId;
import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import gomedic.model.activity.ActivityId;
import gomedic.model.commonfield.IdTest;

public class PatientIdTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PatientId((Integer) null));
        assertThrows(NullPointerException.class, () -> new PatientId((String) null));
    }

    @Test
    public void constructor_invalidNumber_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new PatientId(1000));
    }

    @Test
    public void constructor_validInput_testPassed() {
        assertEquals(new PatientId(100), new PatientId("P100"));
    }

    @Test
    public void toString_validPatientId_testPassed() {
        assertEquals(new PatientId(100).toString(), "P100");
    }

    @Test
    void isValidId_validInputId_testsPassed() {
        // null patient id
        assertThrows(NullPointerException.class, () -> new PatientId((Integer) null));

        // invalid patient id
        assertFalse(isValidPatientId(new IdTest.TestId(999, 'C'))); // wrong prefix
        assertFalse(isValidPatientId(new IdTest.TestId(10, 'D'))); // wrong prefix

        // valid patient id
        assertTrue(isValidPatientId(new PatientId(100))); // patient id
        assertTrue(isValidPatientId(new IdTest.TestId(50, 'P'))); // other id with same prefix
    }

    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    @Test
    void equals_inputs_testsPassed() {
        assertEquals(new PatientId(100), new PatientId(100));
        assertEquals(new PatientId(100), new IdTest.TestId(100, 'P'));
    }

    @Test
    void isValidPatientId_stringInput_testsPassed() {
        assertTrue(isValidPatientId(new PatientId(100))); // activity id
        assertTrue(isValidPatientId(new IdTest.TestId(50, 'P'))); // other id with same prefix
    }
}
