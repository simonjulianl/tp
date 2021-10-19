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

public class WeightContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        WeightContainsKeywordsPredicate<Patient> firstPredicate =
                new WeightContainsKeywordsPredicate<Patient>(firstPredicateKeywordList);
        WeightContainsKeywordsPredicate<Patient> secondPredicate =
                new WeightContainsKeywordsPredicate<Patient>(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        WeightContainsKeywordsPredicate<Patient> firstPredicateCopy =
                new WeightContainsKeywordsPredicate<Patient>(firstPredicateKeywordList);
        assertEquals(firstPredicateCopy, firstPredicate);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different Activity -> returns false
        assertNotEquals(secondPredicate, firstPredicate);
    }

    @Test
    public void test_weightContainsKeywords_returnsTrue() {
        // One keyword
        WeightContainsKeywordsPredicate<Patient> predicate =
                new WeightContainsKeywordsPredicate<Patient>(Collections.singletonList("5"));
        assertTrue(predicate.test(new PatientBuilder().withWeight("53").build()));

        // Multiple keywords
        predicate = new WeightContainsKeywordsPredicate<Patient>(Arrays.asList("53", "1"));
        assertTrue(predicate.test(new PatientBuilder().withWeight("53").build()));

        // Only one matching keyword
        predicate = new WeightContainsKeywordsPredicate<Patient>(Arrays.asList("5", "4", "99"));
        assertTrue(predicate.test(new PatientBuilder().withWeight("53").build()));

    }

    @Test
    public void test_weightDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        WeightContainsKeywordsPredicate<Patient> predicate =
                new WeightContainsKeywordsPredicate<Patient>(Collections.emptyList());
        assertFalse(predicate.test(new PatientBuilder().withWeight("53").build()));

        // Non-matching keyword
        predicate = new WeightContainsKeywordsPredicate<Patient>(Arrays.asList("70"));
        assertFalse(predicate.test(new PatientBuilder().withWeight("53").build()));

        // Number in word format
        predicate = new WeightContainsKeywordsPredicate<Patient>(Arrays.asList("Fifty-three"));
        assertFalse(predicate.test(new PatientBuilder().withWeight("53").build()));

    }
}
