package gomedic.model.util;

import java.util.List;
import java.util.function.Predicate;

import gomedic.commons.util.StringUtil;
import gomedic.model.person.patient.Patient;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class WeightContainsKeywordsPredicate<T extends Patient> implements Predicate<T> {
    private final List<String> keywords;

    public WeightContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test(T person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getWeight().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof WeightContainsKeywordsPredicate) {
            @SuppressWarnings("unchecked")
            WeightContainsKeywordsPredicate<T> otherKeywords = (WeightContainsKeywordsPredicate<T>) other;

            return keywords.equals(otherKeywords.keywords);
        }

        return false;
    }

}
