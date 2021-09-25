---
layout: page 
title: User Guide
---

GoMedic is a **cross-platform desktop application written in Java and designed for doctors and medical residents to
manage contacts and patient details**. We aim GoMedic to be used by someone who can type fast and take advantage of the
optimized features for Command Line Interface.

* Table of Contents {:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `gomedic.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app
   contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   Some example commands you can try:

    * **`list`** : Lists all contacts.

    * **`add`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact
      named `John Doe` to the Address Book.

    * **`delete`**`3` : Deletes the 3rd contact shown in the current list.

    * **`clear`** : Deletes all contacts.

    * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

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
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

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
    3. yyyy-MM-dd-HH-mm (e.g. 2022-09-15-13-00)

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a new patient's details: `add t/patient`

Adds a new patient into the GoMedic application.

Format: `add t/patient n/NAME a/AGE g/GENDER h/HEIGHT w/WEIGHT b/BLOOD_TYPE c/CONTACT_NUMBER o/[OPTIONAL...]`

* `NAME` indicates the full name of the patient, first name and last name are separated by `-`.
* `AGE` is greater than or equal to 0.
* `GENDER` is chosen from one of 3 choices, `M/F/O` where `M` is for Male, `F` is for Female, and `O` is for Others.
* `HEIGHT` is the height of patient in centimeters rounded to the nearest integer.
* `WEIGHT` is the weight of patient in kilograms rounded to the nearest integer.
* `BLOOD_TYPE` is chosen from one of the 4 choices, `A/B/AB/O`.
* `CONTACT_NUMBER` must be 8-digit Singapore phone number.
* `OPTIONAL` is the list of patient's past/pre-existing medical conditions. For medical condition that has multiple
words, use `-` to combine the words, e.g. `heart-failure`. To separate between conditions, use `/`.

Examples:

* `add t/patient n/John-Doe a/30 g/M h/174 w/72 b/O c/12345678 o/heart-failure/diabetes`
* `add t/patient n/Tom-Doe a/20 g/M h/167 w/61 b/AB c/12341234`

### Deleting an existing patient: `delete t/patient`

Deletes a patient from the GoMedic application.

Format: `delete t/patient i/PATIENT_ID`

* Patient ID can be obtained by listing all the patients or search for a certain patients with available filters.
* Patient ID is **unique** (i.e. every patient will be assigned to a unique ID, hence this guarantees
  1 `delete t/patient` command will not delete 2 patients at once).
* Invalid Patient ID being supplied would be flagged by GoMedic, and do not cause changes to any existing patients.

Examples:

* `delete t/patient i/P001`

### Updating an existing patient: `update t/patient`

Updates a patient's details from the GoMedic application.

Format: 
`update t/patient i/PATIENT_ID [OPTIONAL_FLAG]/INPUT...`

* `n/NAME` indicates the full name of the patient, first name and last name are separated by `-`.
* `a/AGE` is greater than or equal to 0.
* `g/GENDER` is chosen from one of 3 choices, `M/F/O` where `M` is for Male, `F` is for Female, and `O` is for Others.
* `h/HEIGHT` is the height of patient in centimeters rounded to the nearest integer.
* `w/WEIGHT` is the weight of patient in kilograms rounded to the nearest integer.
* `b/BLOOD_TYPE` is chosen from one of the 4 choices, `A/B/AB/O`.
* `c/CONTACT_NUMBER` must be 8-digit Singapore phone number.
* `o/[OPTIONAL...]` is the list of patient's past/pre-existing medical conditions. For medical condition that has
  multiple words, use `-` to combine the words, e.g. `heart-failure`. To separate between conditions, use `/`.
* `do/[OPTIONAL_TO_DELETE...]` is the list of patient's past/pre-existing medical conditions to delete. For medical
  condition that has multiple words, use `-` to combine the words, e.g. `heart-failure`. To separate between conditions, use `/`.
* Patient ID can be obtained by listing all the patients or search for a certain patients with available filters.
* Patient ID is **unique** (i.e. every patient will be assigned to a unique ID, hence this guarantees
  1 `delete t/patient` command will not delete 2 patients at once).
* Invalid Patient ID being supplied would be flagged by GoMedic, and do not cause changes to any existing patients.
* Invalid `OPTIONAL_TO_DELETE` conditions supplied would be flagged by GoMedic, and do not cause changes to the
existing patient.

Examples:

* `update t/patient i/P123 n/John-Doe a/30 g/M`
* `update t/patient i/P003 n/Tom-Doe a/20 g/M h/167 w/61 b/AB c/12341234 do/diabetes`

### Adding a new doctor's details: `add t/doctor`

Adds a new doctor into the GoMedic application.

Format: `add t/doctor n/NAME c/CONTACT_NUMBER s/DEPARTMENT`

* `NAME` indicates the full name of the doctor, first name and last name are separated by `-`.
* `CONTACT_NUMBER` must be 8-digit Singapore phone number.
* `DEPARTMENT` is the name of the department where the doctor serves in String.

Examples:

* `add t/doctor n/Timmy-Tom c/98765432 s/neurology`
* `add t/doctor n/John-White c/12312312 s/cardiology`

### Deleting an existing doctor: `delete t/doctor`

Deletes a doctor from the GoMedic application.

Format: `delete t/doctor i/DOCTOR_ID`

* Doctor ID can be obtained by listing all the doctors or search for a certain doctors with available filters.
* Doctor ID is **unique** (i.e. every doctor will be assigned to a unique ID, hence this guarantees
  1 `delete t/doctor` command will not delete 2 doctors at once).
* Invalid Doctor ID being supplied would be flagged by GoMedic, and do not cause changes to any existing doctors.

Examples:

* `delete t/doctor i/D001`

### Updating an existing doctor: `update t/doctor`

Updates a doctor's details from the GoMedic application.

Format: `update t/doctor [OPTIONAL_FLAG/]INPUT...`

* `n/NAME` indicates the full name of the doctor, first name and last name are separated by `-`.
* `c/CONTACT_NUMBER` must be 8-digit Singapore phone number.
* `d/DEPARTMENT` is the name of the department where the doctor serves in String.

Examples:

* `add t/doctor i/D123 c/11112222`
* `add t/doctor i/D101 s/orthopaedics`

### Adding a new activity: `add t/activity`

Adds a new activity into your GoMedic scheduler.

Format: `add t/activity s/START_TIME e/END_TIME ti/TITLE [d/DESCRIPTION]`

* `START_TIME` and `END_TIME` must follow one of the formats specified.
* `START_TIME` is strictly less than `END_TIME`.
* Clashing activity (including partial overlap with other activities or appointments) would be considered as invalid
  activity (i.e. not to be added).
* `TITLE` ideally should be very short so that it can be displayed in the list without being truncated.

**Tip:** Try to pack as many keywords as possible inside the description while keep it concise so that you can search it
later.

Examples:

* `add t/activity s/2022-09-15-14-00 e/15/09/2022 15:00 ti/Meeting with Mr. X d/about a certain paper`
* `add t/activity s/15/09/2022 14:00 e/15/09/2022 15:00 ti/Meeting with Mr. Y`

### Deleting an existing activity: `delete t/activity`

Delete a certain existing activity

Format: `delete t/activity i/ACTIVITY_ID`

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

:bulb: **Tip:** There are other upcoming `list` commands that can list future activities only and past activities only.

Examples:

* `list t/activity`

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to
save manually.

### Editing the data file

AddressBook data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to
update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------

**
Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br>
e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**
Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br>
e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
