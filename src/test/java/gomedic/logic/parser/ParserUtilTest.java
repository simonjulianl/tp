package gomedic.logic.parser;

import static gomedic.testutil.Assert.assertThrows;
import static gomedic.testutil.TypicalIndexes.INDEX_FIRST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.activity.ActivityId;
import gomedic.model.activity.Description;
import gomedic.model.activity.Title;
import gomedic.model.commonfield.Id;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.commonfield.Time;
import gomedic.model.person.doctor.Department;
import gomedic.model.person.doctor.DoctorId;
import gomedic.model.person.patient.Age;
import gomedic.model.person.patient.BloodType;
import gomedic.model.person.patient.Gender;
import gomedic.model.person.patient.Height;
import gomedic.model.person.patient.PatientId;
import gomedic.model.person.patient.Weight;
import gomedic.model.tag.Tag;

public class ParserUtilTest {
    public static final String VALID_TITLE_MEETING = "MEETING";
    public static final String VALID_START_TIME_MEETING = "15/07/2000 15:00";
    public static final String VALID_MEETING_DESCRIPTION = "SOME LONG DESCRIPTION";

    public static final String INVALID_START_TIME_MEETING = "15/07/2000-15:00";
    public static final String INVALID_END_TIME_MEETING = "15/07/2000 16-00";
    public static final String INVALID_TITLE_MEETING = "MEETING".repeat(100);
    public static final String INVALID_DESCRIPTION = "SOME LONG DESCRIPTION".repeat(1000);

    public static final String VALID_DEPARTMENT = "Neurology";
    public static final String INVALID_DEPARTMENT = "Neuro**logy";

    public static final String VALID_AGE = "45";
    public static final String INVALID_AGE = "1000";
    public static final String VALID_BLOODTYPE = "AB";
    public static final String INVALID_BLOODTYPE = "ABC";
    public static final String VALID_GENDER = "M";
    public static final String INVALID_GENDER = "ABC";
    public static final String VALID_HEIGHT = "175";
    public static final String INVALID_HEIGHT = "1000";
    public static final String VALID_WEIGHT = "76";
    public static final String INVALID_WEIGHT = "1000";
    public static final String VALID_MEDICALCONDITIONS = "heart failure";
    public static final String INVALID_MEDICALCONDITIONS = "heart**";

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String VALID_ACTIVITY_ID = "A001";
    private static final String VALID_DOCTOR_ID = "D001";
    private static final String VALID_PATIENT_ID = "P001";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class,
                ParserUtil.MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex(
                        Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone(null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseDepartment_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDepartment(null));
    }

    @Test
    public void parseDepartment_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDepartment(INVALID_DEPARTMENT));
    }

    @Test
    public void parseDepartment_validValueWithoutWhitespace_returnsDepartment() throws Exception {
        Department expectedDepartment = new Department(VALID_DEPARTMENT);
        assertEquals(expectedDepartment, ParserUtil.parseDepartment(VALID_DEPARTMENT));
    }

    @Test
    public void parseDepartment_validValueWithWhitespace_returnsTrimmedDepartment() throws Exception {
        String departmentWithWhitespace = WHITESPACE + VALID_DEPARTMENT + WHITESPACE;
        Department expectedDepartment = new Department(VALID_DEPARTMENT);
        assertEquals(expectedDepartment, ParserUtil.parseDepartment(departmentWithWhitespace));
    }

    @Test
    public void parseAge_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAge(null));
    }

    @Test
    public void parseAge_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAge(INVALID_AGE));
    }

    @Test
    public void parseAge_validValueWithoutWhitespace_returnsAge() throws Exception {
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(VALID_AGE));
    }

    @Test
    public void parseAge_validValueWithWhitespace_returnsTrimmedAge() throws Exception {
        String ageWithWhitespace = WHITESPACE + VALID_AGE + WHITESPACE;
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(ageWithWhitespace));
    }

    @Test
    public void parseBloodType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseBloodType(null));
    }

    @Test
    public void parseBloodType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseBloodType(INVALID_BLOODTYPE));
    }

    @Test
    public void parseBloodType_validValueWithoutWhitespace_returnsBloodType() throws Exception {
        BloodType expectedBloodType = new BloodType(VALID_BLOODTYPE);
        assertEquals(expectedBloodType, ParserUtil.parseBloodType(VALID_BLOODTYPE));
    }

    @Test
    public void parseBloodType_validValueWithWhitespace_returnsTrimmedBloodType() throws Exception {
        String bloodTypeWithWhitespace = WHITESPACE + VALID_BLOODTYPE + WHITESPACE;
        BloodType expectedBloodType = new BloodType(VALID_BLOODTYPE);
        assertEquals(expectedBloodType, ParserUtil.parseBloodType(bloodTypeWithWhitespace));
    }

    @Test
    public void parseGender_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGender(null));
    }

    @Test
    public void parseGender_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGender(INVALID_GENDER));
    }

    @Test
    public void parseGender_validValueWithoutWhitespace_returnsGender() throws Exception {
        Gender expectedGender = new Gender(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(VALID_GENDER));
    }

    @Test
    public void parseGender_validValueWithWhitespace_returnsTrimmedGender() throws Exception {
        String genderWithWhitespace = WHITESPACE + VALID_GENDER + WHITESPACE;
        Gender expectedGender = new Gender(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(genderWithWhitespace));
    }

    @Test
    public void parseHeight_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseHeight(null));
    }

    @Test
    public void parseHeight_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseHeight(INVALID_HEIGHT));
    }

    @Test
    public void parseHeight_validValueWithoutWhitespace_returnsHeight() throws Exception {
        Height expectedHeight = new Height(VALID_HEIGHT);
        assertEquals(expectedHeight, ParserUtil.parseHeight(VALID_HEIGHT));
    }

    @Test
    public void parseHeight_validValueWithWhitespace_returnsTrimmedHeight() throws Exception {
        String genderWithWhitespace = WHITESPACE + VALID_HEIGHT + WHITESPACE;
        Height expectedHeight = new Height(VALID_HEIGHT);
        assertEquals(expectedHeight, ParserUtil.parseHeight(genderWithWhitespace));
    }

    @Test
    public void parseWeight_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWeight(null));
    }

    @Test
    public void parseWeight_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWeight(INVALID_WEIGHT));
    }

    @Test
    public void parseWeight_validValueWithoutWhitespace_returnsWeight() throws Exception {
        Weight expectedWeight = new Weight(VALID_WEIGHT);
        assertEquals(expectedWeight, ParserUtil.parseWeight(VALID_WEIGHT));
    }

    @Test
    public void parseWeight_validValueWithWhitespace_returnsTrimmedWeight() throws Exception {
        String genderWithWhitespace = WHITESPACE + VALID_WEIGHT + WHITESPACE;
        Weight expectedWeight = new Weight(VALID_WEIGHT);
        assertEquals(expectedWeight, ParserUtil.parseWeight(genderWithWhitespace));
    }

    @Test
    public void parseTitle_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle(null));
    }

    @Test
    public void parseTitle_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTitle(INVALID_TITLE_MEETING));
    }

    @Test
    public void parseTitle_validValueWithoutWhitespace_returnsTitle() throws Exception {
        Title expectedTitle = new Title(VALID_TITLE_MEETING);
        assertEquals(expectedTitle, ParserUtil.parseTitle(VALID_TITLE_MEETING));
    }

    @Test
    public void parseTitle_validValueWithWhitespace_returnsTrimmedTitle() throws Exception {
        String titleWithWhitespace = WHITESPACE + VALID_TITLE_MEETING + WHITESPACE;
        Title expectedTitle = new Title(VALID_TITLE_MEETING);
        assertEquals(expectedTitle, ParserUtil.parseTitle(titleWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription(null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description expectedDescription = new Description(VALID_MEETING_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_MEETING_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedTitle() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_MEETING_DESCRIPTION + WHITESPACE;
        Description expectedDescription = new Description(VALID_MEETING_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(descriptionWithWhitespace));
    }

    @Test
    public void parseTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTime(null));
    }

    @Test
    public void parseTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_START_TIME_MEETING));
        assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_END_TIME_MEETING));
    }

    @Test
    public void parseTime_validValueWithoutWhitespace_returnsTime() throws Exception {
        Time expectedTime = new Time(VALID_START_TIME_MEETING);
        assertEquals(expectedTime, ParserUtil.parseTime(VALID_START_TIME_MEETING));
    }

    @Test
    public void parseTime_validValueWithWhitespace_returnsTrimmedTime() throws Exception {
        String timeWithWhitespace = WHITESPACE + VALID_START_TIME_MEETING + WHITESPACE;
        Time expectedTime = new Time(VALID_START_TIME_MEETING);
        assertEquals(expectedTime, ParserUtil.parseTime(timeWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseMedicalCondition_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMedicalConditions(null));
    }

    @Test
    public void parseMedicalCondition_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseMedicalCondition_validValueWithoutWhitespace_returnsMedicalCondition() throws Exception {
        Tag expectedMedicalCondition = new Tag(VALID_TAG_1);
        assertEquals(expectedMedicalCondition, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseMedicalCondition_validValueWithWhitespace_returnsTrimmedMedicalCondition() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedMedicalCondition = new Tag(VALID_TAG_1);
        assertEquals(expectedMedicalCondition, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseMedicalConditions_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMedicalConditions(null));
    }

    @Test
    public void parseMedicalConditions_collectionWithInvalidMedicalConditions_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMedicalConditions(Arrays
                .asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseMedicalConditions_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseMedicalConditions(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseMedicalConditions_collectionWithValidMedicalConditions_returnsMedicalConditionSet()
            throws Exception {
        Set<Tag> actualMedicalConditionSet = ParserUtil.parseMedicalConditions(Arrays
                .asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedMedicalConditionSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedMedicalConditionSet, actualMedicalConditionSet);
    }

    @Test
    void parseId_validString_testPassed() throws Exception {
        Id validActivityId = new ActivityId(VALID_ACTIVITY_ID);
        Id validDoctorId = new DoctorId(VALID_DOCTOR_ID);
        Id validPatientId = new PatientId(VALID_PATIENT_ID);

        assertEquals(validActivityId, ParserUtil.parseId(VALID_ACTIVITY_ID));
        assertEquals(validDoctorId, ParserUtil.parseId(VALID_DOCTOR_ID));
        assertEquals(validPatientId, ParserUtil.parseId(VALID_PATIENT_ID));
    }

    @Test
    void parseId_invalidString_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseId("B001"));
        assertThrows(ParseException.class, () -> ParserUtil.parseId("X001"));
        assertThrows(ParseException.class, () -> ParserUtil.parseId("Z9999"));
    }
}
