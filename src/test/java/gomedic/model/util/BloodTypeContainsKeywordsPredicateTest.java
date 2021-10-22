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

public class BloodTypeContainsKeywordsPredicateTest {


    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        BloodTypeContainsKeywordsPredicate<Patient> firstPredicate =
                new BloodTypeContainsKeywordsPredicate<Patient>(firstPredicateKeywordList);
        BloodTypeContainsKeywordsPredicate<Patient> secondPredicate =
                new BloodTypeContainsKeywordsPredicate<Patient>(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        BloodTypeContainsKeywordsPredicate<Patient> firstPredicateCopy =
                new BloodTypeContainsKeywordsPredicate<Patient>(firstPredicateKeywordList);
        assertEquals(firstPredicateCopy, firstPredicate);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different Patient -> returns false
        assertNotEquals(secondPredicate, firstPredicate);
    }

    @Test
    public void test_bloodTypeContainsKeywords_returnsTrue() {
        // One keyword
        BloodTypeContainsKeywordsPredicate<Patient> predicate =
                new BloodTypeContainsKeywordsPredicate<Patient>(Collections.singletonList("A"));
        assertTrue(predicate.test(new PatientBuilder().withBloodType("AB").build()));

        // Multiple keywords
        predicate = new BloodTypeContainsKeywordsPredicate<Patient>(Arrays.asList("A", "B"));
        assertTrue(predicate.test(new PatientBuilder().withBloodType("AB").build()));

        // Only one matching keyword
        predicate = new BloodTypeContainsKeywordsPredicate<Patient>(Arrays.asList("A", "B"));
        assertTrue(predicate.test(new PatientBuilder().withBloodType("A").build()));

    }

    @Test
    public void test_bloodTypeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        BloodTypeContainsKeywordsPredicate<Patient> predicate =
                new BloodTypeContainsKeywordsPredicate<Patient>(Collections.emptyList());
        assertFalse(predicate.test(new PatientBuilder().withBloodType("A").build()));

        // Non-matching keyword
        predicate = new BloodTypeContainsKeywordsPredicate<Patient>(Arrays.asList("B"));
        assertFalse(predicate.test(new PatientBuilder().withBloodType("A").build()));

        // Blood type in wrong format
        predicate = new BloodTypeContainsKeywordsPredicate<Patient>(Arrays.asList("APlus"));
        assertFalse(predicate.test(new PatientBuilder().withBloodType("A").build()));

    }


}
