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

public class HeightContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        HeightContainsKeywordsPredicate<Patient> firstPredicate =
                new HeightContainsKeywordsPredicate<Patient>(firstPredicateKeywordList);
        HeightContainsKeywordsPredicate<Patient> secondPredicate =
                new HeightContainsKeywordsPredicate<Patient>(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        HeightContainsKeywordsPredicate<Patient> firstPredicateCopy =
                new HeightContainsKeywordsPredicate<Patient>(firstPredicateKeywordList);
        assertEquals(firstPredicateCopy, firstPredicate);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different Activity -> returns false
        assertNotEquals(secondPredicate, firstPredicate);
    }

    @Test
    public void test_heightContainsKeywords_returnsTrue() {
        // One keyword
        HeightContainsKeywordsPredicate<Patient> predicate =
                new HeightContainsKeywordsPredicate<Patient>(Collections.singletonList("1"));
        assertTrue(predicate.test(new PatientBuilder().withHeight("153").build()));

        // Multiple keywords
        predicate = new HeightContainsKeywordsPredicate<Patient>(Arrays.asList("1", "53"));
        assertTrue(predicate.test(new PatientBuilder().withHeight("153").build()));

        // Only one matching keyword
        predicate = new HeightContainsKeywordsPredicate<Patient>(Arrays.asList("1", "54", "99"));
        assertTrue(predicate.test(new PatientBuilder().withHeight("153").build()));

    }

    @Test
    public void test_heightDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        HeightContainsKeywordsPredicate<Patient> predicate =
                new HeightContainsKeywordsPredicate<Patient>(Collections.emptyList());
        assertFalse(predicate.test(new PatientBuilder().withHeight("153").build()));

        // Non-matching keyword
        predicate = new HeightContainsKeywordsPredicate<Patient>(Arrays.asList("170"));
        assertFalse(predicate.test(new PatientBuilder().withHeight("153").build()));

        // Number in word format
        predicate = new HeightContainsKeywordsPredicate<Patient>(Arrays.asList("Fifty-three"));
        assertFalse(predicate.test(new PatientBuilder().withHeight("153").build()));

    }

}
