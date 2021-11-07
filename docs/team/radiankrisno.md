---
layout: page
title: Radian Krisno's Project Portfolio Page
---

### Project: GoMedic

GoMedic is a **cross-platform desktop application written in Java and designed for doctors and medical residents to
manage contacts and patient details**. We aim for GoMedic to be used by someone who can type fast and take advantage of the
optimized features for Command Line Interface.

Given below are some of my notable contributions to the project.

* **New Feature**: Implement the `Patient` model
    * What it does: Allows the user to save the details of his patients.
    * Justification: This feature is the core feature of GoMedic, allowing the user to store important details of
      patients within GoMedic. These include the name, contact number, age, blood type, gender, height, weight, and medical conditions of the patient. With this information,
      the user is also able to make use of the patient's detail in GoMedic to quickly generate a referral letter to him or her,
      through the `Referral` command (which is done by my teammate, Simon).
    * Highlights: Along with the `Patient` model, I also add commands that support the usual `CRUD` methods for patients including
      `add`, `edit`, `delete`, and `list`. The `Patient` model is also incorporated in the `Appointment` model which represents an appointment
      of the user with the patient (which is done by my teammate, Seng Leng).
    * Notable PRs: [PR #111](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/111), [PR #115](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/115),
      [PR #124](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/124), [PR #125](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/125),
      [PR #128](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/128), [PR #133](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/133),
      [PR #144](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/144), [PR #155](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/155),
    * Credits:
        * SE-EDU AB3 for creating the project template, the architecture used for adding these new commands are very similar to `CRUD` commands for `Person` in SE-EDU AB3.

* **New Feature**: Enhanced `clear` command to allow the clearance of only patients, doctors, and activities
    * What it does: Allows the user to clear only records of patients, doctors, or activities in GoMedic.
    * Justification: This feature is really helpful as sometimes you only want to discard information about patients, doctors, or activities.
      This feature is also very helpful during manual testing.
    * Highlights: `clear` command handles all the expected causation, e.g. when you clear all patients, all appointments related to the patients will be removed as well (which is done by my teammate, Seng Leng).
    * Notable PRs: [PR #138](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/138)

* **Ui Feature**: Implement the `view` command to see patient's details.
    * What it does: Allows the user to view a patient's details in GoMedic as the tabular form patient table only shows patient's id, name, contact, age, and gender.
    * Justification: This feature allows user to see important details of a patient like list of medical conditions the patient is suffering from and all of that specific
      patient's appointments with the user.
    * Highlights: I created a new `Ui` for the patient view along with all the logic.
    * Notable PRs: [PR #163](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/163)

* **Ui Enhancement**: Implement UI component that displays the list of `Patient` stored in GoMedic in a tabular form, showing only some details.
    * What it does: Creates a tabular view of the `Patient` that users add into GoMedic.
    * Credits: This command heavily uses [**JavaFX**](https://docs.oracle.com/javafx/2/). The format for this
      tabular view of `Patient` closely follows the format and implementation done by my teammate, Simon,
      who referenced this [blog](http://tutorials.jenkov.com/javafx/tableview.html) when creating his `TableView` for `Activity` .

* **Overall Code Contribution**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=AY2122S1-CS2103-F09-1%2Ftp%5Bmaster%5D&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=radiankrisno&tabRepo=AY2122S1-CS2103T-T15-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
    * Opened issues that sought to improve to testing coverage , e.g. [Issue #164](https://github.com/AY2122S1-CS2103T-T15-1/tp/issues/164)
    * Manage the [releases](https://github.com/AY2122S1-CS2103T-T15-1/tp/releases) of GoMedic
    
* **Notable Documentation**:
    * User Guide:
        * Wrote the [patients related features](https://ay2122s1-cs2103t-t15-1.github.io/tp/UserGuide.html#31-patients-related-features) section.
        * Contribute in writing the [doctors related features](https://ay2122s1-cs2103t-t15-1.github.io/tp/UserGuide.html#32-doctors-related-features) section.
    * Developer Guide:
        * Revamped the [Logic Component](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#logic-component)
          segment of the original AB3 DG. This included updating the Class Diagram, as well as adding more details about the implementation of the `Logic` class in general.
        * Wrote the section about the implementation of the [view feature](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#view-feature).
        * Wrote [Introduction](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#introduction)
        * Wrote [Use Case 6, 7, and 8](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#use-cases)
        * Wrote the manual testing instructions for [patients related features](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#viewing-a-patient), [clear features](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#clearing-records-in-gomedic), and reformat manual testing [groupings](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#appendix-instructions-for-manual-testing)

* **Review/Mentoring contributions**:
    * Review ~34 out of ~120 PRs created. The List of PRs reviewed and their comments can be found [here](https://github.com/AY2122S1-CS2103T-T15-1/tp/pulls?q=is%3Apr+reviewed-by%3Aradiankrisno).

* **Community**:
    * Reported bugs and suggestions for other teams during [PE-D](https://github.com/radiankrisno/ped).
