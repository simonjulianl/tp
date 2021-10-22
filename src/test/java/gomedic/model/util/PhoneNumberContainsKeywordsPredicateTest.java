package gomedic.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.patient.Patient;
import gomedic.testutil.modelbuilder.DoctorBuilder;
import gomedic.testutil.modelbuilder.PatientBuilder;

public class PhoneNumberContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PhoneNumberContainsKeywordsPredicate<Doctor> firstPredicate =
                new PhoneNumberContainsKeywordsPredicate<Doctor>(firstPredicateKeywordList);
        PhoneNumberContainsKeywordsPredicate<Doctor> secondPredicate =
                new PhoneNumberContainsKeywordsPredicate<Doctor>(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        PhoneNumberContainsKeywordsPredicate<Doctor> firstPredicateCopy =
                new PhoneNumberContainsKeywordsPredicate<Doctor>(firstPredicateKeywordList);
        assertEquals(firstPredicateCopy, firstPredicate);

        // different types -> returns false
        assertNotEquals(firstPredicate, 1);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different Doctor -> returns false
        assertNotEquals(secondPredicate, firstPredicate);
    }

    @Test
    public void test_phoneNumberContainsKeywords_returnsTrue() {
        // One keyword
        PhoneNumberContainsKeywordsPredicate<Doctor> predicate =
                new PhoneNumberContainsKeywordsPredicate<Doctor>(Collections.singletonList("9637"));
        assertTrue(predicate.test(new DoctorBuilder().withPhone("96376374").build()));

        // Multiple keywords
        predicate = new PhoneNumberContainsKeywordsPredicate<Doctor>(Arrays.asList("9637", "637"));
        assertTrue(predicate.test(new DoctorBuilder().withPhone("96376374").build()));

        // Only one matching keyword
        predicate = new PhoneNumberContainsKeywordsPredicate<Doctor>(Arrays.asList("9637", "9000"));
        assertTrue(predicate.test(new DoctorBuilder().withPhone("96376374").build()));

        // Patient test
        PhoneNumberContainsKeywordsPredicate<Patient> predicatePatient =
                new PhoneNumberContainsKeywordsPredicate<Patient>(Collections.singletonList("9637"));
        assertTrue(predicatePatient.test(new PatientBuilder().withPhone("96376374").build()));

    }

    @Test
    public void test_phoneNumberDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneNumberContainsKeywordsPredicate<Doctor> predicate =
                new PhoneNumberContainsKeywordsPredicate<Doctor>(Collections.emptyList());
        assertFalse(predicate.test(new DoctorBuilder().withPhone("96376374").build()));

        // Non-matching keyword
        predicate = new PhoneNumberContainsKeywordsPredicate<Doctor>(Arrays.asList("9000"));
        assertFalse(predicate.test(new DoctorBuilder().withPhone("96376374").build()));
    }

}
