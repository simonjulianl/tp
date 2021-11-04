---
layout: page
title: Simon Julian's Project Portfolio Page
---

### Project: GoMedic

GoMedic is a **cross-platform desktop application written in Java and designed for doctors and medical residents to
manage contacts and patient details**. We aim for GoMedic to be used by someone who can type fast and take advantage of the
optimized features for Command Line Interface. 

Given below are some of my notable contributions to the project.

* **New Feature**: Added a new model, `Activity` and basic `CRUD` method for it
    * What it does: allows the user to add, edit, delete and list activities. Activities consist of title, description, and time fields. Activity is the class that is used for appointments too (with an additional `PATIENT_ID`). 
    * Justification: This feature allows the target users to create their own activities and check for any duplicate and existing activity in **GoMedic**. Not only that, Activity is the class that is also used for appointment. 
    * Highlights: The activity class contains `Time` field, which can be used to detect for any conflicting activity. The implementation is fairly straightforward, but for any feature involving dates, it involves many edge cases such as invalid date, multiple formats, leap year, etc. 
    * Credits: 
      * SE-EDU AB3 for creating the project template, the architecture used for adding these new commands are very similar to `CRUD` commands for person in SE-EDU AB3.
      * Appointment features are done by my teammates, mostly by Seng Leng.

* **New Feature**: Enhanced `list` activities command (Notable PRs : [PR #154](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/154)).
    * What it does: This command allows the users to sort the activities by id or start time, and can show are the available activities for today, next week, next month and next year (or all activities by default).

* **New Feature**: Added a `Referral` command (Notable PRs : [PR #168](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/168)). 
    * What it does: allows user to create a medical referral letter in pdf to a certain patient using the templates available.
    * Justification: This feature helps users to not miss any details in the referral letters such as age, height, medical conditions, etc. 
    * Credits: This command heavily uses [**iTextPdf**](https://itextpdf.com/en) Java Pdf generator to format and write the pdf documents.

<div style="page-break-after: always;"></div>

* **Ui Feature**: Add a Side Window (Notable PRs : [PR #165](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/165)).
    * What it does: Side window displays the User Profile, and also indicator of which models that you are looking now (for e.g. if you are looking at Patient, the patient logo would be highlighted to bold blue text and icon).
    * Credits: User Profile features are done by my teammates, mostly by Yuhang.
    
* **Ui Enhancement**: Change the font and the main display to use responsive Table View (Notable PRs : [PR #151](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/151/files), [PR #295](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/295)).
    * What it does: Based on the Figma design which we use very closely as reference, I change the font to `Lato`. Also, by using table view instead of cards, users can see more patients / activities / or colleagues at once. 
    * Highlights: The table view is made to be responsive, meaning the texts inside it will wrap if the window is resized. This is done by implementing custom `cellFactory` by JavaFX. Not only that, the columns of the table can be reordered. I also used some generic commands to make the code less redundant.
    * Credits: This command heavily uses [**JavaFX**](https://docs.oracle.com/javafx/2/) and I mostly learn about `TableView` from this [blog](http://tutorials.jenkov.com/javafx/tableview.html).
    
* **Overall Code Contribution**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=AY2122S1-CS2103-F09-1%2Ftp%5Bmaster%5D&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=simonjulianl&tabRepo=AY2122S1-CS2103T-T15-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)
* **Project management**:
  * Setting up the GitHub team org/repo, and manage `Gradle`
  * Lead the team in weekly sprint meeting, where each sprint records can be found [here](#https://github.com/AY2122S1-CS2103T-T15-1/tp/projects).
  * Triage most of the [issues](https://github.com/AY2122S1-CS2103T-T15-1/tp/issues) to ensure their consistency.
  * Introduce [PR Templates](https://github.com/AY2122S1-CS2103T-T15-1/tp/blob/master/.github/pull_request_template.md) to ensure proper context is given and the proper workflow such as assigning reviewer, linking PRs and passing CIs is being done for every PR.

* **Notable Documentations**:
    * User Guide:
        * Wrote the [activities related features](https://ay2122s1-cs2103t-t15-1.github.io/tp/UserGuide.html#33-activities-related-features)
        * Wrote the [introduction](https://ay2122s1-cs2103t-t15-1.github.io/tp/UserGuide.html#introduction), [how to use the guide](https://ay2122s1-cs2103t-t15-1.github.io/tp/UserGuide.html#1-how-to-use-this-guide), [Faq](https://ay2122s1-cs2103t-t15-1.github.io/tp/UserGuide.html#5-faq)
        * Wrote the [Tips and Tricks](https://ay2122s1-cs2103t-t15-1.github.io/tp/UserGuide.html#4-tips-and-tricks) except for Suggestion which is written by Seng Leng.
        * Tweak the [Quick Start](https://ay2122s1-cs2103t-t15-1.github.io/tp/UserGuide.html#table-of-contents) to ensure it is consistent with the latest version.
    * Developer Guide:
      * Tweak the manual testing for deleting an [activity](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#deleting-an-activity)
      * Clean up and move the [Product Scope](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#product-scope), [User Stories](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#user-stories) from the internal notes.
      * Wrote [Use Case 1 and Use Case 2](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#use-cases), [Non-functional requirements](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#non-functional-requirements), and [Glossary](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#glossary).
      * Rewrote the entire [Ui Component](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#ui-component).
      * Wrote about [referral feature](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#generating-medical-referral-feature).

* **Review/Mentoring contributions**:
  * Review ~82 out of ~90 PRs created (excluding mine already). Mostly because I frequent GitHub most often and just ensure code consistency. The List of PRs reviewed and their comments can be found [here](https://github.com/AY2122S1-CS2103T-T15-1/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3A%40me).

* **Community**:
    * Contributed to [forum discussions](https://nus-cs2103-ay2122s1.github.io/dashboards/contents/forum-activities.html), especially during iP days (examples: [1](https://github.com/nus-cs2103-AY2122S1/forum/issues/20#issuecomment-899572782) and [2](https://github.com/nus-cs2103-AY2122S1/forum/issues/179#issuecomment-912944865)). 
    * Reported bugs and suggestions for other teams during [PE-D](https://github.com/simonjulianl/ped). 
