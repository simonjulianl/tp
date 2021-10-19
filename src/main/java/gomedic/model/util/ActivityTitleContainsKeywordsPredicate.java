package gomedic.model.util;

import java.util.List;
import java.util.function.Predicate;

import gomedic.commons.util.StringUtil;
import gomedic.model.activity.Activity;

/**
 * Tests that an Activity's title or description matches any of the keywords given.
 */
public class ActivityTitleContainsKeywordsPredicate<T extends Activity> implements Predicate<T> {
    private final List<String> keywords;

    public ActivityTitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(T activity) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(activity.getTitle().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(activity.getDescription().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof ActivityTitleContainsKeywordsPredicate) {
            @SuppressWarnings("unchecked")
            ActivityTitleContainsKeywordsPredicate<T> otherKeywords = (ActivityTitleContainsKeywordsPredicate<T>) other;

            return keywords.equals(otherKeywords.keywords);
        }

        return false;
    }

}
