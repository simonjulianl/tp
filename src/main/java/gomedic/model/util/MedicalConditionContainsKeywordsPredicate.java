package gomedic.model.util;

import java.util.List;
import java.util.function.Predicate;

import gomedic.commons.util.StringUtil;
import gomedic.model.person.patient.Patient;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class MedicalConditionContainsKeywordsPredicate<T extends Patient> implements Predicate<T> {
    private final List<String> keywords;

    public MedicalConditionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(T person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getMedicalConditions().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof MedicalConditionContainsKeywordsPredicate) {
            @SuppressWarnings("unchecked")
            MedicalConditionContainsKeywordsPredicate<T> otherKeywords = (MedicalConditionContainsKeywordsPredicate<T>) other;

            return keywords.equals(otherKeywords.keywords);
        }

        return false;
    }

}
