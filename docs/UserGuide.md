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

* Activity ID can be obtained by listing all the activities or a search for certain activities within a certain time
  frame.
* Activity ID is **unique** (i.e. every activity will be assigned to a unique ID, hence this guarantees
  1 `delete t/activity` command will not delete 2 activities at once).
* Invalid Activity IDs supplied would be flagged by GoMedic, and do not cause changes to any existing activities.

Examples:

* `delete t/activity i/A123`

### List all activities: `tag t/activity`

List all existing (past, present and future) activities that exist in GoMedic.

Format: `list t/activity`

* Activities would be displayed in ascending `START_TIME` (The past activities would be at the top if any and Future
  activities at the bottom).
* The summary or description that are too long would be truncated.
* The `START_TIME` and `END_TIME` would be displayed in `dd-MM-yyyy HH:mm` GMT+8 24-Hour format.

:bulb: **Tip:** There are other upcoming `list` commands that can list future activities only and past activities only.

Examples:

* `list t/activity`

### Tagging an activity: `tag t/activity`

Tags a specific activity with the tags specified in the command.

Format: `tag t/activity i/ACTIVITY_ID [ta/TAG_DESCRIPTION]...`

* Activity ID can be obtained by listing all the activities or a search for certain activities within a certain time
  frame.
* Activity ID is **unique** (i.e. every activity will be assigned to a unique ID, hence this guarantees
  1 `tag t/activity` command will not tag 2 activities at once).
* Invalid Activity IDs supplied would be flagged by GoMedic, and do not cause changes to any existing activities.

_Tagging for doctors and patients coming soon..._

Examples:

* `tag t/activity i/A420 ta/important ta/NUS ta/schoolwork`
* `tag t/activity i/A421 ta/important`

### Find results that contain keyword: `find [OPTIONAL_FLAG/]KEYWORD...`

Searches for doctors, patients and activities that contain the specified keyword as a substring in any of their details.
If more than 1 keyword is specified, results that contain at least 1 of the keywords will be returned (i.e. `OR` search)
E.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Users can specify additional optional flags to limit the keyword matching to the fields that is associated with
each flag. Flags will only match results that contain the associated field (e.g. n/Hans will not return any `activities` 
since `activities` do not have a `name` field.

The flags are:
* `n/NAME`: Matches the name field (Valid for: `Patients`, `Doctors`)
* `c/CONTACT_NUMBER`: Matches the contact number field (Valid for: `Patients`, `Doctors`)
* `a/AGE`: Matches the age field (Valid for: `Patients`)
* `g/GENDER`: Matches the gender field; The only valid keywords for this field are `M/F/O` (Valid for: `Patients`)
* `h/HEIGHT`: Matches the height field (Valid for: `Patients`)
* `w/WEIGHT`: Matches the weight field (Valid for: `Patients`)
* `b/BLOOD_TYPE`: Matches the blood type field; The only valid keywords for this field are `A/B/AB/O` 
(Valid for: `Patients`)
* `m/MEDICAL_CONDITION`: Limits the keyword search to the list of medical conditions of a patient (Valid for: `Patients`)
* `s/DEPARTMENT`: Matches the department field (Valid for: `Doctors`)
* `ti/TITLE`: Matches the title field (Valid for: `Activities`)
* `ta/TAG_DESCRIPTION`: Matches results that contain the specified tag in its list of tags (Valid for: `Activities`, 
_Tagging for `Doctors` and `Patients` coming soon_)

Format: `find [OPTIONAL_FLAG/]KEYWORD...`

* Keyword is case-insensitive for convenience (“dia” will match diabetic patients even if the user stored the 
patient's condition as “Diabetes”). 
* Flags can be repeated (e.g. `find n/Hans n/Bo` will return both `Hans Gruber` and `Bo Yang`)
* If the optional flag is not specified, the keyword will match any fields.
E.g. `find dia` will return:
    1. Doctor Claudia, whose name matches `dia`
    2. Patient Jaryl, whose medical condition, `diabetes`, matches `dia`
    3. Doctor Tom, whose specialty, `Pediatrics`, matches `dia`
    4. Patient Lydia, whose name matches `dia`

Examples:
* `find m/diabetes a/42 n/Jaryl`
* `find ta/important ti/tutorial`
* `find dia`

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:

* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list.
  The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567`
  and `johndoe@example.com` respectively.
* `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search). e.g. `Hans Bo` will return `Hans Gruber`
  , `Bo Yang`

Examples:

* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

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
