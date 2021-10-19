package gomedic.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import gomedic.model.person.patient.Patient;
import gomedic.testutil.modelbuilder.PatientBuilder;

public class GenderContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        GenderContainsKeywordsPredicate<Patient> firstPredicate =
                new GenderContainsKeywordsPredicate<Patient>(firstPredicateKeywordList);
        GenderContainsKeywordsPredicate<Patient> secondPredicate =
                new GenderContainsKeywordsPredicate<Patient>(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        GenderContainsKeywordsPredicate<Patient> firstPredicateCopy =
                new GenderContainsKeywordsPredicate<Patient>(firstPredicateKeywordList);
        assertEquals(firstPredicateCopy, firstPredicate);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different Patient -> returns false
        assertNotEquals(secondPredicate, firstPredicate);
    }

    @Test
    public void test_genderContainsKeywords_returnsTrue() {
        // One keyword
        GenderContainsKeywordsPredicate<Patient> predicate =
                new GenderContainsKeywordsPredicate<Patient>(Collections.singletonList("M"));
        assertTrue(predicate.test(new PatientBuilder().withGender("M").build()));

        // Only one matching keyword
        predicate = new GenderContainsKeywordsPredicate<Patient>(Arrays.asList("M", "F"));
        assertTrue(predicate.test(new PatientBuilder().withGender("M").build()));

    }

    @Test
    public void test_genderDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GenderContainsKeywordsPredicate<Patient> predicate =
                new GenderContainsKeywordsPredicate<Patient>(Collections.emptyList());
        assertFalse(predicate.test(new PatientBuilder().withGender("M").build()));

        // Non-matching keyword
        predicate = new GenderContainsKeywordsPredicate<Patient>(Arrays.asList("F"));
        assertFalse(predicate.test(new PatientBuilder().withGender("M").build()));

        // Gender in wrong format
        predicate = new GenderContainsKeywordsPredicate<Patient>(Arrays.asList("Male"));
        assertFalse(predicate.test(new PatientBuilder().withGender("M").build()));

    }
}
