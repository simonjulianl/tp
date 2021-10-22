package gomedic.model.util;

import java.util.List;
import java.util.function.Predicate;

import gomedic.commons.util.StringUtil;
import gomedic.model.person.patient.Patient;

/**
 * Tests that a Patient's height matches any of the keywords given.
 */
public class HeightContainsKeywordsPredicate<T extends Patient> implements Predicate<T> {
    private final List<String> keywords;

    public HeightContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test(T person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getHeight().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof HeightContainsKeywordsPredicate) {
            @SuppressWarnings("unchecked")
            HeightContainsKeywordsPredicate<T> otherKeywords = (HeightContainsKeywordsPredicate<T>) other;

            return keywords.equals(otherKeywords.keywords);
        }

        return false;
    }

}
