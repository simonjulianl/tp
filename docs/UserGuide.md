---
layout: page 
title: User Guide
---

GoMedic is a **cross-platform desktop application written in Java and designed for doctors and medical residents to
manage contacts and patient details**. We aim GoMedic to be used by someone who can type fast and take advantage of the
optimized features for Command Line Interface.

GoMedic is bootstrapped using SE-EDU Address Book 3 and inherits some of its features such as `clear`, parameter
formatting, etc.

* Table of Contents 
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `gomedic.jar` from [here](https://github.com/AY2122S1-CS2103T-T15-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your GoMedic.

4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app
   contains some sample data.<br> 
   ![Ui-activity](images/Ui-activity.png)
   
5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   Some example commands you can try:

    * **`list t/patient`** : Lists all patients.

    * **`add t/patient n/John-Doe a/30 g/M h/174 w/72 b/O p/12345678 o/heart-failure o/diabetes`** : Adds a contact
      named `John Doe` to the Address Book.

    * **`delete t/patient i/P001`** : Deletes the patient whose id is P001.

    * **`clear`** : Deletes all contacts including patients, doctors, and activities.

    * **`exit`** : Exits the app.
6. **Address Book, GoMedic Address Book, and GoMedic** refers to the same term, which is just the application itself. 
7. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Words in `LOWER_CASE` are required flags to be written together with the command. <br>
  e.g. in `add t/activity`, `t/activity` is a flag that must be written as it is and should not be changed or treated as
  a parameter.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of
  the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be
  ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* There are fixed multiple valid date and time formats (GMT+8 24-Hour Time format):
    1. dd/MM/yyyy HH:mm (e.g. 15/09/2022 13:00)
    2. dd-MM-yyyy HH:mm (e.g. 15-09-2022 13:00)
    3. yyyy-MM-dd HH:mm (e.g. 2022-09-15 13:00)

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Suggestions

GoMedic will suggest up to the 5 closest command to be executed if you make a typo and send an invalid command.

### Adding a new patient's details: `add t/patient`

Adds a new patient into the GoMedic application.

Format: `add t/patient n/NAME a/AGE g/GENDER h/HEIGHT w/WEIGHT b/BLOOD_TYPE p/PHONE_NUMBER [o/MEDICAL_CONDITION]...`

The parameters are : 

* `n/NAME` indicates the full name of the patient, first name and last name are separated by `space`.
* `a/AGE` is greater than or equal to 0.
* `g/GENDER` is chosen from one of 3 choices, `M/F/O` where `M` is for Male, `F` is for Female, and `O` is for Others.
* `h/HEIGHT` is the height of patient in centimeters rounded to the nearest integer.
* `w/WEIGHT` is the weight of patient in kilograms rounded to the nearest integer.
* `b/BLOOD_TYPE` is chosen from one of the 4 choices, `A/B/AB/O`.
* `p/PHONE_NUMBER` must be 8-digit Singapore phone number.
* `m/MEDICAL_CONDITION` is the list of patient's past/pre-existing medical conditions. For medical condition that has multiple
  words, use `space` to combine the words, e.g. `heart failure`. 

Examples:

* `add t/patient n/John Doe a/30 g/M h/174 w/72 b/O p/12345678 m/heart failure `
* `add t/patient n/Tom Doe a/20 g/M h/167 w/61 b/AB p/12341234`

### Display full details of a patient: `view t/patient`

Displays the full details of a doctor

Format: `view t/patient i/PATIENT_ID`

The parameters are : 

* `i/PATIENT_ID` indicates the ID number of the doctor which is assigned when a new doctor is added.

Examples:

* `view t/patient i/P001`

### Deleting an existing patient: `delete t/patient`

Deletes a patient from the GoMedic application.

Format: `delete t/patient i/PATIENT_ID`

The parameters are :

* `i/PATIENT_ID` indicates the ID number of the doctor which is assigned when a new doctor is added.

Notes: 

* Patient ID can be obtained by listing all the patients or search for a certain patients with available filters.
* Patient ID is **unique** (i.e. every patient will be assigned to a unique ID, hence this guarantees
  1 `delete t/patient` command will not delete 2 patients at once).
* Invalid Patient ID being supplied would be flagged by GoMedic, and do not cause changes to any existing patients.

Examples:

* `delete t/patient i/P001`

### Updating an existing patient: `edit t/patient`

Edits a patient's details from the GoMedic application.

Format: `edit t/patient i/PATIENT_ID [OPTIONAL_PARAMETER]...`

The parameters are:

* `n/NAME` indicates the full name of the patient, first name and last name are separated by `-`.
* `a/AGE` is greater than or equal to 0.
* `g/GENDER` is chosen from one of 3 choices, `M/F/O` where `M` is for Male, `F` is for Female, and `O` is for Others.
* `h/HEIGHT` is the height of patient in centimeters rounded to the nearest integer.
* `w/WEIGHT` is the weight of patient in kilograms rounded to the nearest integer.
* `b/BLOOD_TYPE` is chosen from one of the 4 choices, `A/B/AB/O`.
* `p/PHONE_NUMBER` must be 8-digit Singapore phone number.

NOTE: TO COMPLETE - when you edit tags, you will replace all of the current tags
The optional parameters are: 
* `o/description` is the list of patient's past/pre-existing medical conditions to be **added**. For medical condition that has
  multiple words, use `space` to combine the words, e.g. `heart failure`. To separate between conditions, use more tags `o/`.
* `do/description` is the list of patient's past/pre-existing medical conditions to be **deleted**. For medical
  condition that has multiple words, use `space` to combine the words, e.g. `heart failure`. 

Notes: 
* Patient ID can be obtained by listing all the patients or search for a certain patients with available filters.
* Patient ID is **unique** (i.e. every patient will be assigned to a unique ID, hence this guarantees
  1 `delete t/patient` command will not delete 2 patients at once).
* Invalid Patient ID being supplied would be flagged by GoMedic, and do not cause changes to any existing patients.
* Invalid `OPTIONAL_TO_DELETE` conditions supplied would be flagged by GoMedic, and do not cause changes to the existing
  patient.

Examples:

* `edit t/patient i/P123 n/John Doe a/30 g/M`
* `edit t/patient i/P003 n/Tom Doe a/20 g/M h/167 w/61 b/AB p/12341234 do/diabetes`
* `edit t/patient i/P003 n/Tom Doe a/20 g/M h/167 w/61 b/AB p/12341234 o/fever o/headache do/diabetes do/heart feailure`

### Viewing the list of patients `list t/patient`

List all existing patients’ previews in the GoMedic application.

Format: `list t/patient`

Examples:

* `list t/patient`

### Adding a new doctor's details: `add t/doctor`

Adds a new doctor into the GoMedic application.

Format: `add t/doctor n/NAME p/PHONE_NUMBER de/DEPARTMENT`

The parameters are: 

* `n/NAME` indicates the full name of the doctor, first name and last name are separated by `space`.
* `p/PHONE_NUMBER` must be 8-digit Singapore phone number.
* `de/DEPARTMENT` is the name of the department where the doctor serves in String.

Examples:

* `add t/doctor n/Timmy Tom p/98765432 de/neurology`
* `add t/doctor n/John White p/12312312 de/cardiology`

### Display full details of a doctor: `view t/doctor`

Displays the full details of a doctor

Format: `view t/doctor i/DOCTOR_ID`

The parameters are: 

* `i/DOCTOR_ID` indicates the ID number of the doctor which is assigned when a new doctor is added.

Examples:

* `view t/doctor i/D001`

### Deleting an existing doctor: `delete t/doctor`

Deletes a doctor from the GoMedic application.

Format: `delete t/doctor i/DOCTOR_ID`

The parameters are:

* `i/DOCTOR_ID` indicates the ID number of the doctor which is assigned when a new doctor is added.

Notes: 

* Doctor ID can be obtained by listing all the doctors or search for a certain doctors with available filters.
* Doctor ID is **unique** (i.e. every doctor will be assigned to a unique ID, hence this guarantees 1 `delete t/doctor`
  command will not delete 2 doctors at once).
* Invalid Doctor ID being supplied would be flagged by GoMedic, and do not cause changes to any existing doctors.

Examples:

* `delete t/doctor i/D001`

### Updating an existing doctor: `edit t/doctor`

Edits a doctor's details from the GoMedic application.

Format: `edit t/doctor i/DOCTOR_ID [OPTIONAL_PARAMETER]...`

The parameters are:
* `i/DOCTOR_ID` indicates the ID number of the doctor which is assigned when a new doctor is added.

The parameters are:

* `n/NAME` indicates the full name of the doctor, first name and last name are separated by `-`.
* `p/PHONE_NUMBER` must be 8-digit Singapore phone number.
* `d/DEPARTMENT` is the name of the department where the doctor serves in String.

Examples:

* `add t/doctor i/D123 p/11112222`
* `add t/doctor i/D101 de/orthopaedics`

### Viewing the list of doctors `list t/doctor`

Format: `list t/doctor`

List all existing patients’ previews in the GoMedic application.

Examples:

* `list t/doctor`

### Tagging an activity: `tag t/activity`

Tags a specific activity with the tags specified in the command.

Format: `tag t/activity i/ACTIVITY_ID [ta/TAG_DESCRIPTION]...`

The parameters are: 

* `i/ACTIVITY_ID` indicates the ID number of the activity to be tagged.

The optional parameters are:

* `ta/TAG_DESCRIPTION` indicates a series of tag to be added into the activity. In the case there is no `ta/TAG_DESCRIPTION` parameter found, this command does nothing to the activity identified by its id.

Notes:
* Activity ID can be obtained by listing all the activities or a search for certain activities within a certain time
  frame.
* Activity ID is **unique** (i.e. every activity will be assigned to a unique ID, hence this guarantees
  1 `tag t/activity` command will not tag 2 activities at once).
* Invalid Activity IDs supplied would be flagged by GoMedic, and do not cause changes to any existing activities.

_Tagging for doctors and patients coming soon..._

Examples:

* `tag t/activity i/A420 ta/important ta/NUS ta/schoolwork`
* `tag t/activity i/A421 ta/important`

### Find results that contain keyword: `find t/CATEGORY [OPTIONAL_PARAMETERS]...`

Searches for doctors, patients and activities that contain the specified keyword as a substring in any of their details.
If more than 1 keyword is specified, results that contain at least 1 of the keywords will be returned (i.e. `OR` search)
E.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Users can specify additional optional parameters to limit the keyword matching to the fields that is associated with each
flag. Parameters will only match results that contain the associated field (E.g. n/Hans will not return any `activities`
since `activities` do not have a `name` field.)

Format: `find t/CATEGORY [OPTIONAL_PARAMETERS]...`

The parameters are:
* `t/CATEGORY`: Searches for matches within this category.
    * Valid values are:
        * doctor
        * patient
        * activity
        * all
  

The optional parameters are:

* `n/NAME`: Matches the name field (Valid for: `Patients`, `Doctors`)
* `p/PHONE_NUMBER`: Matches the phone number field (Valid for: `Patients`, `Doctors`)
* `a/AGE`: Matches the age field (Valid for: `Patients`)
* `g/GENDER`: Matches the gender field; The only valid keywords for this field are `M/F/O` (Valid for: `Patients`)
* `h/HEIGHT`: Matches the height field (Valid for: `Patients`)
* `w/WEIGHT`: Matches the weight field (Valid for: `Patients`)
* `b/BLOOD_TYPE`: Matches the blood type field; The only valid keywords for this field are `A/B/AB/O`
  (Valid for: `Patients`)
* `o/MEDICAL_CONDITION`: Limits the keyword search to the list of medical conditions of a patient (Valid
  for: `Patients`)
* `de/DEPARTMENT`: Matches the department field (Valid for: `Doctors`)
* `ti/TITLE`: Matches the title field or description field (Valid for: `Activities`)
* `ta/TAG_DESCRIPTION`: Matches results that contain the specified tag in its list of tags (Valid for: `Activities`,
  _Tagging for `Doctors` and `Patients` coming soon_)
    
* `all/KEYWORD` : Matches any field with the keyword specified

Note: 
* Keyword is case-insensitive for convenience (“dia” will match diabetic patients even if the user stored the patient's
  condition as “Diabetes”)
* Parameters can be repeated (e.g. `find n/Hans n/Bo` will return both `Hans Gruber` and `Bo Yang`)
* If the optional parameters `all` is  specified, the keyword will match any fields. E.g. `find t/all all/dia` will return:
    1. Doctor Claudia, whose name matches `dia`
    2. Patient Jaryl, whose medical condition, `diabetes`, matches `dia`
    3. Doctor Tom, whose specialty, `Pediatrics`, matches `dia`
    4. Patient Lydia, whose name matches `dia`

Examples:

* `find t/all o/diabetes a/42 n/Jaryl`
* `find t/activity ta/important ti/tutorial`
* `find t/all all/dia`

### Adding a new activity: `add t/activity`

Adds a new activity into your GoMedic scheduler.

Format: `add t/activity s/START_TIME e/END_TIME ti/TITLE [d/DESCRIPTION]`

The parameters are:
* `s/START_TIME` the starting time of the activity, must be one of the accepted date time format. 
* `e/END_TIME` the ending time of the activity, must be one of the accepted date time format.
* `ti/TITLE` the title of the activity.
* `d/DESCRIPTION` the description of the activity.

Note: 
* `START_TIME` and `END_TIME` must follow one of the formats specified.
* `START_TIME` is strictly less than `END_TIME`.
* Clashing activity (including partial overlap with other activities) would be considered as invalid
  activity (i.e. not to be added).
* `TITLE` ideally should be very short so that it can be displayed in the list without being truncated.

Examples:

* `add t/activity s/2022-09-15-14-00 e/15/09/2022 15:00 ti/Meeting with Mr. X d/about a certain paper`
* `add t/activity s/15/09/2022 14:00 e/15/09/2022 15:00 ti/Meeting with Mr. Y`

### Deleting an existing activity: `delete t/activity`

Delete a certain existing activity

Format: `delete t/activity i/ACTIVITY_ID`

The parameters are: 
* `i/ACTIVITY_ID` indicates the ID number of the activity which is assigned when a new activity is added.

Note: 
* Activity ID can be obtained by listing all the activities or search for a certain activities within a certain time
  frame.
* Activity ID is **unique** (i.e. every activity will be assigned to a unique ID, hence this guarantees
  1 `delete t/activity` command will not delete 2 activities at once).
* Invalid Activity ID being supplied would be flagged by GoMedic, and do not cause changes to any existing activities.

Examples:

* `delete t/activity i/A123`

### List all activities: `list t/activity`

List all existing (past, present and future) activities that exist in GoMedic.

Format: `list t/activity`

* Activities would be displayed in ascending `START_TIME` (The past activities would be at the top if any and Future
  activities at the bottom).
* The summary or description that are too long would be truncated.
* The `START_TIME` and `END_TIME` would be displayed in `dd-MM-yyyy HH:mm` GMT+8 24-Hour format.

:bulb: **Tip:** _There are other upcoming `list` commands that can list future activities only and past activities only._

Examples:

* `list t/activity`

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

GoMedic data are saved in the hard disk automatically after any command that changes the data. There is no need to save
manually.

### Editing the data file

Currently, all the data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome
to update data directly by editing that data file.

In the subsequent releases, the patient data can be found at `[JAR file location]/data/patients.json`, doctors data
at `[JAR file location]/data/doctors.json`, and finally all activities at `[JAR file location]/data/activities.json`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, GoMedic will discard all data and start with an empty data file at the next run.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous GoMedic home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary
* `{type}` indicates one of these three values `t/activity`,`t/patient`, `t/doctor` and `{type}_id` means `ACTIVITY_ID` for `{type} = t/activity`
* `{PARAMETERS}` indicates the mandatory parameters as specified in the [Features](#features) section.

Action        | Format, Examples |
--------------|------------------ |
**Add**       | `add {type} {PARAMETERS}`<br> e.g., `add t/doctor n/Timmy Tom p/98765432 de/neurology`
**Delete**    | `delete {type} i/{type}_ID` <br> e.g., `delete t/patient i/P003`<br>
**Edit**      | `edit {type} i/{type}_ID [OPTIONAL PARAMETER]...`<br>e.g.,`edit t/patient i/P123 n/John Doe a/30 g/M`<br>
**Find**      | `find [OPTIONAL_PARAMATERS]...`<br> e.g., `find ta/important ti/tutorial`<br>
**View**      | `view t/doctor i/DOCTOR_ID`, `view t/patient i/PATIENT_ID`<br> e.g., `view t/patient i/P003`
**Tag**       | `tag t/activity  i/ACTIVITY_ID [ta/TAG_DESCRIPTION]...` <br> e.g., `tag t/activity i/A420 ta/important ta/NUS ta/schoolwork`
**Clear**     | `clear`<br>
**List**      | `list {type}`<br>
**Exit**      | `exit`<br>
**Help**      | `help`<br>
