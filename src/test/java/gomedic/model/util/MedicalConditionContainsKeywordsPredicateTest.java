package gomedic.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gomedic.model.person.patient.Patient;
import gomedic.model.tag.Tag;
import gomedic.testutil.modelbuilder.PatientBuilder;

public class MedicalConditionContainsKeywordsPredicateTest {


    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MedicalConditionContainsKeywordsPredicate<Patient> firstPredicate =
                new MedicalConditionContainsKeywordsPredicate<Patient>(firstPredicateKeywordList);
        MedicalConditionContainsKeywordsPredicate<Patient> secondPredicate =
                new MedicalConditionContainsKeywordsPredicate<Patient>(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        MedicalConditionContainsKeywordsPredicate<Patient> firstPredicateCopy =
                new  MedicalConditionContainsKeywordsPredicate<Patient>(firstPredicateKeywordList);
        assertEquals(firstPredicateCopy, firstPredicate);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different Patient -> returns false
        assertNotEquals(secondPredicate, firstPredicate);
    }

    @Test
    public void test_medicalConditionContainsKeywords_returnsTrue() {

        Set<Tag> medicalConditions = new HashSet<>();
        medicalConditions.add(new Tag("Heart failure"));
        medicalConditions.add(new Tag("Hypothermia"));

        // One keyword
        MedicalConditionContainsKeywordsPredicate<Patient> predicate =
                new  MedicalConditionContainsKeywordsPredicate<Patient>(Collections.singletonList("Hypothermia"));
        assertTrue(predicate.test(new PatientBuilder().withMedicalConditions(medicalConditions).build()));

        // Multiple keywords
        predicate = new MedicalConditionContainsKeywordsPredicate<Patient>(Arrays.asList("Heart", "Hypothermia"));
        assertTrue(predicate.test(new PatientBuilder().withMedicalConditions(medicalConditions).build()));

        // Only one matching keyword
        predicate = new MedicalConditionContainsKeywordsPredicate<Patient>(Arrays.asList("Heart", "Covid"));
        assertTrue(predicate.test(new PatientBuilder().withMedicalConditions(medicalConditions).build()));

    }

    @Test
    public void test_medicalConditionDoesNotContainKeywords_returnsFalse() {
        Set<Tag> medicalConditions = new HashSet<>();
        medicalConditions.add(new Tag("Heart failure"));
        medicalConditions.add(new Tag("Hypothermia"));

        // Zero keywords
        MedicalConditionContainsKeywordsPredicate<Patient> predicate =
                new MedicalConditionContainsKeywordsPredicate<Patient>(Collections.emptyList());
        assertFalse(predicate.test(new PatientBuilder().withMedicalConditions(medicalConditions).build()));

        // Non-matching keyword
        predicate = new MedicalConditionContainsKeywordsPredicate<Patient>(Arrays.asList("Covid"));
        assertFalse(predicate.test(new PatientBuilder().withMedicalConditions(medicalConditions).build()));

    }

}
