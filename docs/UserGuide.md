---
layout: page
title: User Guide
---

GoMedic is a **cross-platform desktop application written in Java and designed for doctors and medical residents to
manage contacts and patient details**. We aim for GoMedic to be used by someone who can type fast and take advantage of the
optimized features for Command Line Interface.

GoMedic is bootstrapped using SE-EDU Address Book 3 and inherits some of its features such as `clear`, parameter
formatting, etc.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

# Quick start

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

    * **`add t/patient n/John-Doe a/30 g/M h/174 w/72 b/O p/12345678 o/heart-failure o/diabetes`** : Adds a patient
      named `John Doe` to GoMedic.

    * **`delete t/patient P001`** : Deletes the patient whose id is P001.

    * **`clear`** : Deletes all contacts including patients, doctors, and activities.

    * **`exit`** : Exits the app.
6. **Address Book, GoMedic Address Book, and GoMedic** refers to the same term, which is just the application itself.
7. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

# Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Any mention of `{command}` refers to one of these values `add`, `delete`, `list`, `edit`, `clear`, `find`, `view`, `help`,
`profile`, `referral`, `help`, `exit`

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

* `{type}` indicates one of these three values `t/activity`,`t/patient`, `t/doctor` and `{type}_id` means `ACTIVITY_ID` for `{type} = t/activity`

</div>

## Patients Related Features

### Adding a new patient's details: `add t/patient`

Adds a new patient into the GoMedic application.

Format: `add t/patient n/NAME a/AGE g/GENDER h/HEIGHT w/WEIGHT b/BLOOD_TYPE p/PHONE_NUMBER [m/MEDICAL_CONDITION]...`

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

### Display full details of a patient: `view t/patient`

Displays the full details of a particular patient.

Format: `view t/patient i/PATIENT_ID`

The parameters are :

* `i/PATIENT_ID` indicates the ID of the patient to be viewed.

Examples:

* `view t/patient i/P001`

### Deleting an existing patient: `delete t/patient`

Deletes a patient from GoMedic.

Format: `delete t/patient PATIENT_ID`

The parameters are :

* `PATIENT_ID` indicates the ID of the patient to be deleted.

Notes:

* Patient ID can be obtained by listing all the patients or searching for a certain patient with available filters. (_See `find` command_)
* Patient ID is **unique** (i.e. every patient will be assigned to a unique ID, hence this guarantees
  1 `delete t/patient` command will not delete 2 patients at once).
* Invalid Patient ID being supplied would be flagged by GoMedic, and do not cause changes to any existing patients.

Examples:

* `delete t/patient P001`

### Updating an existing patient's details: `edit t/patient`

Edits a patient's details from the GoMedic application.

Format: `edit t/patient i/PATIENT_ID [OPTIONAL_PARAMETER]...`

The compulsory parameter is:
* `i/PATIENT_ID` indicates the ID of the patient to be edited.

The optional parameters are:

* `n/NAME` indicates the full name of the patient.
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

## Doctors Related Features

### Adding a new doctor's details: `add t/doctor`

Adds the details of a doctor into GoMedic.

Format: `add t/doctor n/NAME p/PHONE_NUMBER de/DEPARTMENT`

The parameters are:

* `n/NAME` indicates the full name of the doctor.
* `p/PHONE_NUMBER` must be 8-digit phone number.
* `de/DEPARTMENT` is the name of the doctor's department.

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

Deletes a doctor from GoMedic.

Format: `delete t/doctor DOCTOR_ID`

The parameters are:

* `DOCTOR_ID` indicates the ID of the doctor to be deleted.

Notes:

* Doctor ID can be obtained by listing all the doctors or searching for a certain doctor with the available filters. (_See `find` command_)
* Doctor ID is **unique** (i.e. every doctor will be assigned to a unique ID, hence this guarantees 1 `delete t/doctor`
  command will not delete 2 doctors at once).
* Invalid Doctor ID being supplied would be flagged by GoMedic, and do not cause changes to any existing doctors.

Examples:

* `delete t/doctor D001`

### Updating an existing doctor: `edit t/doctor`

Edits a doctor's details in GoMedic.

Format: `edit t/doctor i/DOCTOR_ID [OPTIONAL_PARAMETER]...`

The compulsory parameter is:
* `i/DOCTOR_ID` indicates the ID of the doctor to be edited.

The optional parameters are:

* `n/NAME` indicates the full name of the doctor.
* `p/PHONE_NUMBER` must be 8-digit phone number.
* `d/DEPARTMENT` is the name of the doctor's department.

Examples:

* `edit t/doctor i/D123 p/11112222`
* `edit t/doctor i/D101 de/orthopaedics`

### Viewing the list of doctors `list t/doctor`

Format: `list t/doctor`

List all existing doctors’ previews in GoMedic.

Examples:

* `list t/doctor`

## Activities Related Features

### Adding a new activity: `add t/activity`

Adds a new activity into your GoMedic scheduler.

The parameters are:

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

Format: `delete t/activity ACTIVITY_ID`

The parameters are: 
* `ACTIVITY_ID` indicates the ID of the activity to be deleted.

Note: 
* Activity ID can be obtained by listing all the activities or searching for a certain activities within a certain time
  frame. (_See `list t/activity` command_)
* Activity ID is **unique** (i.e. every activity will be assigned to a unique ID, hence this guarantees
  1 `delete t/activity` command will not delete 2 activities at once).
* Invalid Activity ID being supplied would be flagged by GoMedic, and do not cause changes to any existing activities.

Examples:

* `delete t/activity A123`

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

### Find results that contain keyword: `find t/CATEGORY [OPTIONAL_PARAMETERS]...`
## Finding entries: `find [OPTIONAL_PARAMETERS]...`

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
## General Utility Commands 

### Generating a referral: `referral`

Generates a referral letter for a patient in PDF format.

Format: `referral ti/TITLE di/DOCTOR_ID pi/PATIENT_ID [d/DESCRIPTION]`

The parameters are:

* `ti/TITLE` title of the referral document.
* `di/DOCTOR_ID` id of the doctor to be referred to.
* `pi/PATIENT_ID` id of the patient being referred.
* `d/DESCRIPTION` description of the patient's condition and further details.

Note: 

* the patient and doctor must both be present in the GoMedic App as a valid entry or the referral will not be
generated.

### Customizing your own profile: `profile`

Updates your profile on GoMedic.

Format: `profile n/NAME p/POSITION de/DEPARTMENT o/ORGANIZATION`

The parameters are:
* `n/NAME` the name of the user.
* `p/POSITION` the position held by the user. (E.g. Senior Consultant)
* `de/DEPARTMENT` the department that the user works in. (E.g. Department of Cardiology)
* `o/ORGANIZATION` the organization that the user works in. (E.g. National University Hospital)

Note:
* All parameters must be alphanumeric. Otherwise, it would be considered as an 
invalid entry, and the command will not be executed.

Example:
* `profile n/Jon Snow p/Senior Consultant de/Department of Cardiology o/NUH`


### Viewing help : `help`

Shows a message giving a brief explanation of each command term and the ability to copy the link to the user guide for 
more information.

![help message](images/helpMessage.png)

Format: `help`

### Clearing all entries : `clear [{type}]`

Clears all entries from the address book related to the specified type. If there is no type that is specified, all the data would be cleared.

Format: `clear [{type}]`

Examples: 

* `clear t/patient`: Clears all patient entries and the appointments associated with them
* `clear t/doctor`: Clears all doctor entries
* `clear t/activity`: Clears all activity and appointment entries
* `clear`: Clears all entries in GoMedic

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Suggestions

There are two types of erroneous inputs that we are expecting, one for single worded commands and one for two word commands.
Behaviour of each erroneous command is assumed to follow the convention specified above.


There will be up to 5 suggested commands for each erroneous input.

#### For single word commands in format `{command}`:

* errors such as `exi` will return `exit`,`edit t/patient`,`edit t/doctor`,`edit t/activity` in no particular order of importance.

#### For two word commands in format `{command} {type}`:

* errors such as `adl t/patit` will return `add t/patient`, `add t/activity` in no particular order of importance.

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
If the format for the user profile is invalid, the preset user profile will be used instead.
</div>

--------------------------------------------------------------------------------------------------------------------

# FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous GoMedic home folder.

--------------------------------------------------------------------------------------------------------------------

# Command summary
* `{PARAMETERS}` indicates the mandatory parameters as specified in the [Features](#features) section.

Action        | Format                                            | Examples                                             |                
--------------|---------------------------------------------------|----------------------------------------------------- |
**Add**       | `add {type} {PARAMETERS}`                         | `add t/doctor n/Timmy Tom p/98765432 de/neurology`   |
**Delete**    | `delete {type} {type}_ID`                         | `delete t/patient P003`                              |
**Edit**      | `edit {type} i/{type}_ID [OPTIONAL PARAMETER]...` | `edit t/patient i/P123 n/John Doe a/30 g/M`          |
**Find**      | `find [OPTIONAL_PARAMATERS]...`                   | `find ta/important ti/tutorial`                      |
**View**      | `view t/patient i/PATIENT_ID`                     | `view t/patient i/P003`                              |
**Clear**     | `clear`                                           |                                                      |
**List**      | `list {type}`                                     | `list t/patient`                                     |
**Exit**      | `exit`                                            |                                                      |
**Help**      | `help`                                            |                                                      |

