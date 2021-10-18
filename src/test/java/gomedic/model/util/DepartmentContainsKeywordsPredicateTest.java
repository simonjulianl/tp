package gomedic.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gomedic.model.person.patient.Patient;
import gomedic.testutil.modelbuilder.PatientBuilder;
import org.junit.jupiter.api.Test;

import gomedic.model.person.doctor.Doctor;
import gomedic.model.util.NameContainsKeywordsPredicate;
import gomedic.testutil.modelbuilder.DoctorBuilder;

public class DepartmentContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DepartmentContainsKeywordsPredicate<Doctor> firstPredicate =
                new DepartmentContainsKeywordsPredicate<Doctor>(firstPredicateKeywordList);
        DepartmentContainsKeywordsPredicate<Doctor> secondPredicate =
                new DepartmentContainsKeywordsPredicate<Doctor>(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        DepartmentContainsKeywordsPredicate<Doctor> firstPredicateCopy =
                new DepartmentContainsKeywordsPredicate<Doctor>(firstPredicateKeywordList);
        assertEquals(firstPredicateCopy, firstPredicate);

        // different types -> returns false
        assertNotEquals(firstPredicate, 1);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different Doctor -> returns false
        assertNotEquals(secondPredicate, firstPredicate);
    }

    @Test
    public void test_departmentContainsKeywords_returnsTrue() {
        // One keyword
        DepartmentContainsKeywordsPredicate<Doctor> predicate =
                new DepartmentContainsKeywordsPredicate<Doctor>(Collections.singletonList("Pain"));
        assertTrue(predicate.test(new DoctorBuilder().withDepartment("Pain Management and Medicine").build()));

        // Multiple keywords
        predicate = new DepartmentContainsKeywordsPredicate<Doctor>(Arrays.asList("Pain", "Management"));
        assertTrue(predicate.test(new DoctorBuilder().withDepartment("Pain Management and Medicine").build()));

        // Only one matching keyword
        predicate = new DepartmentContainsKeywordsPredicate<Doctor>(Arrays.asList("Pain", "Paediatrics"));
        assertTrue(predicate.test(new DoctorBuilder().withDepartment("Pain Management and Medicine").build()));

        // Mixed-case keywords
        predicate = new DepartmentContainsKeywordsPredicate<Doctor>(Arrays.asList("PAIn", "medIcIne"));
        assertTrue(predicate.test(new DoctorBuilder().withDepartment("Pain Management and Medicine").build()));

    }

    @Test
    public void test_departmentDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DepartmentContainsKeywordsPredicate<Doctor> predicate =
                new DepartmentContainsKeywordsPredicate<Doctor>(Collections.emptyList());
        assertFalse(predicate.test(new DoctorBuilder().withDepartment("Pain Management and Medicine").build()));

        // Non-matching keyword
        predicate = new DepartmentContainsKeywordsPredicate<Doctor>(Arrays.asList("Paediatrics"));
        assertFalse(predicate.test(new DoctorBuilder().withDepartment("Pain Management and Medicine").build()));
    }
}
