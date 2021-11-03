---
layout: page
title: Simon Julian Lauw's Project Portfolio Page
---

### Project: GoMedic

GoMedic is a **cross-platform desktop application written in Java and designed for doctors and medical residents to
manage contacts and patient details**. We aim for GoMedic to be used by someone who can type fast and take advantage of the
optimized features for Command Line Interface.

GoMedic is bootstrapped using SE-EDU Address Book 3 and inherits some of its features such as `clear`, parameter
formatting, etc.

Given below are my contributions to the project.

* **New Feature**: Added a new model, `Activity` and basic `CRUD` method for it
    * What it does: allows the user to add, edit, delete and list activities. Activities consist of title, description, and time fields. Activity is the class that is used for appointments too (with an additional `PATIENT_ID`). 
    * Justification: This feature allows the target users to create their own activities and check for any duplicate and existing activity in **GoMedic**. Not only that, Activity is the class that is also used for appointment. 
    * Highlights: The activity class contains `Time` field, which can be used to detect for any conflicting activity. The implementation is fairly straightforward, but for any feature involving dates, it involves many edge cases such as invalid date, multiple formats, leap year, etc. 
    * Credits: 
      * SE-EDU AB3 for creating the project template, the architecture used for adding these new commands are very similar to `CRUD` commands for person in SE-EDU AB3.
      * Appointment features are done by my teammates, mostly by Seng Leng.

* **New Feature**: Enhanced `list` activities command.
    * What it does: This command allows the users to sort the activities by id or start time, and can show are the available activities for today, next week, next month and next year (or all activities by default).

* **New Feature**: Added a `Referral` command. 
    * What it does: allows user to create a medical referral letter in pdf to a certain patient using the templates available.
    * Justification: This feature helps users to not miss any details in the referral letters such as age, height, medical conditions, etc. Not only that, users can spend less time in writing referral letter by using this command.
    * Highlights: The activity class contains `Time` field, which can be used to detect for any conflicting activity. The implementation is fairly straightforward, but for any feature involving dates, it involves many edge cases such as invalid date, multiple formats, leap year, etc.
    * Credits:
        * SE-EDU AB3 for creating the project template, the architecture used for adding these new commands are very similar to `CRUD` commands for person in SE-EDU AB3.
        * Appointment features are done by my teammates, mostly by Seng Leng.
      
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=AY2122S1-CS2103-F09-1%2Ftp%5Bmaster%5D&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=simonjulianl&tabRepo=AY2122S1-CS2103T-T15-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
    * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
    * Updated the GUI color scheme (Pull requests [\#33](), [\#34]())
    * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
    * User Guide:
        * Added documentation for the features `delete` and `find` [\#72]()
        * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
    * Developer Guide:
        * Added implementation details of the `delete` feature.

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
    * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
    * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
    * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* **Tools**:
    * Integrated a third party library (iTextPdf) to the project ([\#42]())
    * Integrated a new Github plugin (CircleCI) to the team repo