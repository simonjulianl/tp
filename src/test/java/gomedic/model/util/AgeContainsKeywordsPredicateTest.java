package gomedic.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gomedic.model.person.patient.Patient;
import gomedic.testutil.modelbuilder.PatientBuilder;


public class AgeContainsKeywordsPredicateTest {


    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AgeContainsKeywordsPredicate<Patient> firstPredicate =
                new AgeContainsKeywordsPredicate<Patient>(firstPredicateKeywordList);
        AgeContainsKeywordsPredicate<Patient> secondPredicate =
                new AgeContainsKeywordsPredicate<Patient>(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        AgeContainsKeywordsPredicate<Patient> firstPredicateCopy =
                new AgeContainsKeywordsPredicate<Patient>(firstPredicateKeywordList);
        assertEquals(firstPredicateCopy, firstPredicate);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different Activity -> returns false
        assertNotEquals(secondPredicate, firstPredicate);
    }

    @Test
    public void test_ageContainsKeywords_returnsTrue() {
        // One keyword
        AgeContainsKeywordsPredicate<Patient> predicate =
                new AgeContainsKeywordsPredicate<Patient>(Collections.singletonList("1"));
        assertTrue(predicate.test(new PatientBuilder().withAge("13").build()));

        // Multiple keywords
        predicate = new AgeContainsKeywordsPredicate<Patient>(Arrays.asList("1", "13"));
        assertTrue(predicate.test(new PatientBuilder().withAge("13").build()));

        // Only one matching keyword
        predicate = new AgeContainsKeywordsPredicate<Patient>(Arrays.asList("1", "13", "23"));
        assertTrue(predicate.test(new PatientBuilder().withAge("13").build()));

    }

    @Test
    public void test_ageDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AgeContainsKeywordsPredicate<Patient> predicate =
                new AgeContainsKeywordsPredicate<Patient>(Collections.emptyList());
        assertFalse(predicate.test(new PatientBuilder().withAge("13").build()));

        // Non-matching keyword
        predicate = new AgeContainsKeywordsPredicate<Patient>(Arrays.asList("23"));
        assertFalse(predicate.test(new PatientBuilder().withAge("13").build()));

        // Number in word format
        predicate = new AgeContainsKeywordsPredicate<Patient>(Arrays.asList("Thirteen"));
        assertFalse(predicate.test(new PatientBuilder().withAge("13").build()));

    }


}
