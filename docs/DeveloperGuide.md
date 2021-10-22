---
layout: page
title: Developer Guide
---

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**
 * The project is bootstrapped from [SE-EDU Address Book 3]("https://se-education.org/addressbook-level3/)
 * {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
   original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in
the [diagrams](https://github.com/AY2122S1-CS2103T-T15-1/tp/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML
Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit
diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes
called [`Main`](https://github.com/AY2122S1-CS2103T-T15-1/tp/tree/master/src/main/java/gomedic/Main.java)
and [`MainApp`](https://github.com/AY2122S1-CS2103T-T15-1/tp/tree/master/src/main/java/gomedic/MainApp.java). It
is responsible for,

* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding
  API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified
in [`Ui.java`](https://github.com/AY2122S1-CS2103T-T15-1/tp/tree/master/src/main/java/gomedic/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`
, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures
the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of
the [`MainWindow`](https://github.com/AY2122S1-CS2103T-T15-1/tp/tree/master/src/main/java/gomedic/ui/MainWindow.java)
is specified
in [`MainWindow.fxml`](https://github.com/AY2122S1-CS2103T-T15-1/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

The original Figma design for the `UI` component can be found [here](https://www.figma.com/file/zqo6peKfu0Wxeay679eVq9/cs2103t-tp?node-id=0%3A1)

### Logic component

**API** : [`Logic.java`](https://github.com/AY2122S1-CS2103T-T15-1/tp/tree/master/src/main/java/gomedic/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddPatientCommand`
   which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to add a patient).
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the
`execute("delete t/patient P001")` API call.

![Interactions Inside the Logic Component for the `delete t/patient P001` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as
  a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/AY2122S1-CS2103T-T15-1/tp/tree/master/src/main/java/gomedic/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which
  is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to
  this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as
  a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2122S1-CS2103T-T15-1/tp/tree/master/src/main/java/gomedic/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

* can save both address book data and user preference data in json format, and read them back into corresponding
  objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `gomedic.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Generating Medical Referral Feature 

This feature allows GoMedic users to generate medical referral for a uniquely identified patient identified by his/her `PatientId` to other
doctor that is already saved in the GoMedic application and would be uniquely identified by his/her 
`DoctorId`.

This feature can be accessed using `referral` command which has parameters of `ti/Title`, `di/DoctorId`, `pi/PatiendId` and optional 
description which can be added using `d/Description` flag. 

_This feature uses **iText Java Pdf writer library** to generate the medical referral document._  

**Workflow**

For illustration purposes, suppose the user enters the command:

`referral ti/Referral of Patient A di/D001 pi/P001 d/He is having internal bleeding, need urgent attention.`

<div markdown="span" class="alert alert-info">:information_source:
**Note:** the doctor id and patient id does not need to conform the `DXXX` and `PXXX` format in this case. However, should the supplied ids are invalid,
GoMedic would be unable to find the doctor and the patient, and would show the feedback patient/doctor not found message to the user.
</div>

Once the user enter the command is entered:

1. The `LogicManager` class would execute the input as `String`
2. The `AddressBookParser` is responsible for calling the relevant parser (in this case, `ReferralCommandParser`) according to the command keyword (which is `referral`)
3. The `ReferralCommandParser` would parse and check the parameters being supplied and eventually returning in to `LogicManager` 

![ReferralCommandCreation](images/referral/ReferralCommandCreation.png)

After the `LogicManager` receives the new `ReferralCommand` object, 

1. The `LogicManager` would calle the execute method of `ReferralCommand` and passes the `Model` 
2. The `ReferralCommand` then would call the appropriate data from the `Model` such as the `Doctor` and `Patient`
3. After the patient and doctor data are ready, the `Document` provided by the `iText` library would be created and `Doctor` and `Patient` field would replaces 
the placeholders in the medical referral template. 

![ReferralCommandCreation](images/referral/ReferralCommandExecution.png)

Finally, the pdf object is written into the `data/` folder whose filename is the same of that of the `title` (i.e. _title_.pdf) 

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo
history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the
following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()`
and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the
initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command
calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes
to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book
state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`
, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing
the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer`
once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once
to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such
as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`.
Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not
pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be
purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern
desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

## \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of patients and colleagues
* is a very busy man with lots of appointments and activities
* prefer desktop apps over other types
* can type fast and prefer CLI-formatted commands
* prefers typing to mouse interactions and is reasonably comfortable using CLI apps
* often forgets about his patient details and his schedule

**Value proposition**:

* manage patients contacts faster than a typical mouse/GUI driven app
* able to manage other doctors' details
* able to store sheer amount of patient details and retrieve them very fast
* able to compare patient medical histories very fast
* able to remind user of upcoming activities and appointments
* easy to use and would give suggestion on the closest command whenever typo is made

### User stories

**Priorities:**
* **High (must have)** - `* * *`
* **Medium (nice to have)** - `* *`
* **Low (unlikely to have)** - `*`

#### [EPIC] Basic CRUD Functionality for patients and doctors

| Priority | As a …​                                 | I want to …​                             | So that I can…​                                                        |
| -------- | ------------------------------------------ | --------------------------------------------| ---------------------------------------------------------------------- |
| `* * *`  | user                                       | add a new patient detail                    | retrieve and update them later                                                                        |
| `* * *`  | doctor                                     | add a new colleague detail                  | remember their contact number and office numbers
| `* * *`  | user                                       | delete an existing patient / doctor details | remove entries that I no longer need
| `* * *`  | user                                       | update my patient details                   | change the details without deleting and adding the info again
| `* * *`  | doctor                                     | update my colleague details                 | change the details without deleting and adding the info again
| `* * *`  | user                                       | view all my patient details in a list       | know my entire list of patients at a glance
| `* * *`  | user                                       | view all my colleague details in a list     | know my entire list of colleague at a glance

#### [EPIC] Scheduling

| Priority | As a …​                                 | I want to …​                                            | So that I can…​                                                        |
| -------- | ------------------------------------------ | -----------------------------------------------------------| ---------------------------------------------------------------------- |
| `* * *`  | busy user                                  | add a new appointment with one of my patient               | so that I can remember my appointments with them and be reminded of them in the future
| `* * *`  | busy user                                  | add new activities such as meeting with colleagues         |  so that I can remember my schedules today with and be reminded of them in the future
| `* * *`  | user                                       | delete existing appointments with my patients              | remove appointments that are no longer happening                |
| `* * *`  | user                                       | delete any existing activity                               | remove activities that are no longer happening and free my schedules up                                                 |
| `* *  `  | organized user                             | list all my future appointments with a certain patient     | plan my schedules and track the appointments                                                 |
| `* * *`  | organized user                             | list all my future activities                              | know my schedules and plan future activities accordingly                           |
| `*    `  | busy user                                  | be reminded of my patients' appointment 15 minutes before the schedule             | prepare myself for the appointment                         |
| `*    `  | busy user                                  | be reminded of my daily schedule when the app is started / at the start of the day |   know what I will be doing for the day and plan ahead                          |
| `* *  `  | forgetful user                             | search for specific activities and appointments within a specific time frame       | plan ahead and focus on those time slots only                         |
| `*    `  | organized user                             | change the reminder settings (minutes)                     | tailor it according to my preference                         |

#### [EPIC] Information Retrieval and Organization

| Priority | As a …​                                 | I want to …​                                                        | So that I can…​                                                        |
| -------- | ------------------------------------------ | -----------------------------------------------------------------------| --------------------------------------------------------------------------|
| `*    `  | experienced user                           | search for activities based on its title and description               | retrieve certain grouped activities very fast such as meetings and visitations
| `* * *`  | busy user                                  | search for patients whose details contain a user-specified substring   | retrieve certain patients that I don't really remember which fields where the details are stored at
| `* * *`  | busy user                                  | search for doctors whose details contain a user-specified substring    | retrieve my colleague details without any need to remember which fields the data are stored at

#### [EPIC] Misc Helpful Features

| Priority | As a …​                                 | I want to …​                                                        | So that I can…​                                                        |
| -------- | ------------------------------------------ | -----------------------------------------------------------------------| --------------------------------------------------------------------------|
| `* * *`  | new and forgetful user                     | pull up a list of commands                                             | pick the right commands quickly
| `* * *`  | new user                                   | sample entries in the app                                              | know how the app would look like when I would populate it with my data
| `* * *`  | new user                                   | have suggestions on typo that I made on commands                       | learn from my mistakes and correct it quickly
| `* *`    | fickle user                                | have the app accept multiple fixed ways to write dates and times       | do not need to remember the correct format all the time

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `GoMedic` and the **Actor** is the `user`, unless specified otherwise)

**Use Case: [UC1] - Adding a new patient record**

**MSS**

1. User requests to add a new patient record.
2. GoMedic shows confirmation about the new patient record being added, and displays the patient's full details.

   Use case ends.

**Extensions**

* 1a. Incomplete patient details are given by users

    * 1a1. GoMedic shows a feedback to the user about the missing data.

      Use Case ends.

**Use Case: [UC2] - Delete an existing patient record**

**MSS**

1. User requests to list all patients.
2. GoMedic shows a list of patients.
3. User requests to delete a specific person in the list.
4. GoMedic deletes the person.

   Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. GoMedic shows a feedback to the user about invalid index.

      Use case ends.

**Use Case: [UC3] - Command Suggestions**

**MSS**

1. User types in a certain command such as creating <u>new patient record (UC1)</u> and <u>deleting an existing patient
   record (UC2)</u> with typo.
2. GoMedic shows a list of suggested commands.
3. User retypes the command and requests GoMedic to perform certain action.
4. GoMedic performs the specified action.

   Use case ends.

**Extensions**

* 1a. Command is valid.

  Use case ends.

* 2a. User decides not to retype the commands.

  Use case ends.

* 3a. User input an invalid command.

  Use case resumes at step 1.

*{More to be added}*

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to be run without any installation required as long as the user has Java `11` installed.
3. Should be able to hold up to 1000 patients and colleagues without a noticeable delay (less than 2 seconds) in
   performance for typical usage.
4. Should be able to hold up to 200 future activities and future appointments, and be retrieved without a noticeable
   delay (less than 2 seconds) for typical searches.
5. Should be only used by a single user and do not require other users to make changes to the app such as making
   appointment or sharing activities.
6. The data should not be stored using a _DBMS_.
7. The data should be stored _locally_ and should be in a human _editable_ and easily modified text file.
8. The project is expected to adhere to a _biweekly version release_ using breadth-first incremental technique.
9. Should be less than **100 MB** in size for the software, and less than **15 MB** per file for each document.
10. The developer guide and user guide must be pdf-friendly (meaning no embedded video, animations, embedded PowerPoint,
    etc.).
11. Should be delivered to the user using a single JAR file.
12. Graphical User Interface (GUI) should work reasonable well for standard screen resolution of 1920 x 1080 and higher
    with screen scales 100% and 125%, and also usable for screen resolutions 1280 x 720 and higher with screen scales
    150%.
13. Should be written mainly using Object-oriented paradigm.
14. The app is not required to be able to interact with external pieces of hardware such as printer.
15. The data stored within the app should be encrypted for security purposes (to prevent the raw data being read by
    external parties).
16. The app is mainly used for users based in Singapore, and therefore some local terms are tolerable, and the app is
    not expected to operate in other languages except English.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X.
* **DBMS** : Database Management System such as MySQL, Oracle, PSQL, MongoDB, etc.
* **JAR** : Java Archive file format, which is typically used to aggregate many Java class files and associated metadata
  into one file for distribution.
* **Typical usage/searches** : Finding by keyword, name, medical histories, and any combination of the field manually.
* **Object-Oriented Paradigm** : programming paradigm that organizes software design around objects rather than
  functions and logic. For complete list of Features that OO design should have,
  please [visit this wikipedia page](https://en.wikipedia.org/wiki/Object-oriented_programming)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be
       optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.
       Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Finding a patient, doctor or activity
1. Searching for a person
    1. Prerequisite: List the patients, doctors, or activities based on which one you wish to see, using the `list` command.
    eg. `list t/doctor` or `list t/patient` or `list t/activity`.
       
    2. Test case: eg. `find t/patient n/Joe`
        Expected: All patients whose names contain the substring "Joe" (case-insensitive) will be displayed.
       
    3. Test case: eg. `find t/activity ti/Meeting`
        Expected: All activities whose title or description contains the substring "Meeting" (case-insensitive) will be displayed. 
       
    4. Other incorrect find commands to try: `find t\patient Joe` 
        Expected: Error message as a flag is not specified prior to the keyword. 
   

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
