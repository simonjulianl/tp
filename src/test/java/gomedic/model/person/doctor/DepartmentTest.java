package gomedic.model.person.doctor;

import static gomedic.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DepartmentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Department(null));
    }

    @Test
    public void constructor_invalidDepartment_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Department(invalidName));
    }

    @Test
    public void isValidDepartment() {
        // null name
        assertThrows(NullPointerException.class, () -> Department.isValidDepartmentName(null));

        // invalid name
        assertFalse(Department.isValidDepartmentName("")); // empty string
        assertFalse(Department.isValidDepartmentName(" ")); // spaces only
        assertFalse(Department.isValidDepartmentName("^")); // only non-alphanumeric characters
        assertFalse(Department.isValidDepartmentName("Xray Centre 1*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Department.isValidDepartmentName("pediatrics")); // alphabets only
        assertTrue(Department.isValidDepartmentName("12345")); // numbers only
        assertTrue(Department.isValidDepartmentName("xray centre 2")); // alphanumeric characters
        assertTrue(Department.isValidDepartmentName("Xray Centre")); // with capital letters
        assertTrue(Department.isValidDepartmentName("New Pediatrics School of John Doe the 2nd")); // long names
    }

    @Test
    public void toString_sameDepartmentName_testPassed() {
        String departmentName = "xray centre";
        Department department = new Department(departmentName);
        assertEquals(department.toString(), departmentName);
    }

    @Test
    public void equals_differentInstanceSameName_isEqual() {
        String departmentName = "xray centre";
        Department department = new Department(departmentName);
        Department otherDepartment = new Department(departmentName);
        assertEquals(otherDepartment, department);
    }
}
