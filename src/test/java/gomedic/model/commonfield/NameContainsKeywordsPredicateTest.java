package gomedic.model.commonfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import gomedic.model.person.doctor.Doctor;
import gomedic.model.util.NameContainsKeywordsPredicate;
import gomedic.testutil.modelbuilder.DoctorBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate<Doctor> firstPredicate =
                new NameContainsKeywordsPredicate<Doctor>(firstPredicateKeywordList);
        NameContainsKeywordsPredicate<Doctor> secondPredicate =
                new NameContainsKeywordsPredicate<Doctor>(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        NameContainsKeywordsPredicate<Doctor> firstPredicateCopy =
                new NameContainsKeywordsPredicate<Doctor>(firstPredicateKeywordList);
        assertEquals(firstPredicateCopy, firstPredicate);

        // different types -> returns false
        assertNotEquals(firstPredicate, 1);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different Doctor -> returns false
        assertNotEquals(secondPredicate, firstPredicate);
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate<Doctor> predicate =
                new NameContainsKeywordsPredicate<Doctor>(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new DoctorBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate<Doctor>(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new DoctorBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate<Doctor>(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new DoctorBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate<Doctor>(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new DoctorBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate<Doctor> predicate =
                new NameContainsKeywordsPredicate<Doctor>(Collections.emptyList());
        assertFalse(predicate.test(new DoctorBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate<Doctor>(Arrays.asList("Carol"));
        assertFalse(predicate.test(new DoctorBuilder().withName("Alice Bob").build()));
    }
}
