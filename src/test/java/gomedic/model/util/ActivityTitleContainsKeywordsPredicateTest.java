package gomedic.model.util;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import gomedic.model.activity.Activity;
import gomedic.testutil.modelbuilder.ActivityBuilder;

public class ActivityTitleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ActivityTitleContainsKeywordsPredicate<Activity> firstPredicate =
                new ActivityTitleContainsKeywordsPredicate<Activity>(firstPredicateKeywordList);
        ActivityTitleContainsKeywordsPredicate<Activity> secondPredicate =
                new ActivityTitleContainsKeywordsPredicate<Activity>(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        ActivityTitleContainsKeywordsPredicate<Activity> firstPredicateCopy =
                new ActivityTitleContainsKeywordsPredicate<Activity>(firstPredicateKeywordList);
        assertEquals(firstPredicateCopy, firstPredicate);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different Activity -> returns false
        assertNotEquals(secondPredicate, firstPredicate);
    }

    @Test
    public void test_activityTitleContainsKeywords_returnsTrue() {
        // One keyword
        ActivityTitleContainsKeywordsPredicate<Activity> predicate =
                new ActivityTitleContainsKeywordsPredicate<Activity>(Collections.singletonList("Chiropractor"));
        assertTrue(predicate.test(new ActivityBuilder().withTitle("Meeting with Chiropractor").build()));

        // Multiple keywords
        predicate = new ActivityTitleContainsKeywordsPredicate<Activity>(Arrays.asList("Meeting", "Chiropractor"));
        assertTrue(predicate.test(new ActivityBuilder().withTitle("Meeting with Chiropractor").build()));

        // Only one matching keyword
        predicate = new ActivityTitleContainsKeywordsPredicate<Activity>(Arrays.asList("Meeting", "Cardiologist"));
        assertTrue(predicate.test(new ActivityBuilder().withTitle("Meeting with Chiropractor").build()));

        // Mixed-case keywords
        predicate = new ActivityTitleContainsKeywordsPredicate<Activity>(Arrays.asList("MEETinG", "ChiROPRactor"));
        assertTrue(predicate.test(new ActivityBuilder().withTitle("Meeting with Chiropractor").build()));

    }

    @Test
    public void test_activityTitleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ActivityTitleContainsKeywordsPredicate<Activity> predicate =
                new ActivityTitleContainsKeywordsPredicate<Activity>(Collections.emptyList());
        assertFalse(predicate.test(new ActivityBuilder().withTitle("Meeting with Chiropractor").build()));

        // Non-matching keyword
        predicate = new ActivityTitleContainsKeywordsPredicate<Activity>(Arrays.asList("Cardiologist"));
        assertFalse(predicate.test(new ActivityBuilder().withTitle("Meeting with Chiropractor").build()));
    }

}
