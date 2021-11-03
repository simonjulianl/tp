---
layout: page
title: Yap Yuhang's Project Portfolio Page
---

### Project: GoMedic

GoMedic is a **cross-platform desktop application written in Java and designed for doctors and medical residents to
manage contacts and patient details**. We aim for GoMedic to be used by someone who can type fast and take advantage of the
optimized features for Command Line Interface.

GoMedic is bootstrapped using SE-EDU Address Book 3 and inherits some of its features such as `clear`, parameter
formatting, etc.

Given below are some of my notable contributions to the project.

* **New Feature**: Implement the `Doctor` model
    * What it does: Allows the user to save the details of other doctors (for use cases such as saving the contact details of the user's colleagues)
    * Justification: This feature is one of the core features of GoMedic, allowing the user to store important details of
      doctors within GoMedic. These include the name, contact number, and department of the doctor. With this information,
      the user is also able to make use of the doctor's detail in GoMedic to quickly generate a referral letter to him or her,
      through the `Referral` command (which is done by my teammate, Simon)
    * Highlights: Along with the `Doctor` model, commands that support the usual `CRUD` methods for doctors are created, 
      such as the `add`, `edit`, `delete` and `list` command
    * Notable PRs: [PR #82](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/82), [PR #122](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/122), 
      [PR #127](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/127), [PR #134](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/134)
    * Credits: 
        * SE-EDU AB3 for creating the project template, the architecture used for adding these new commands are very similar to `CRUD` commands for `Person` in SE-EDU AB3.

* **New Feature**: Implement the `UserProfile` model and `profile` command to update `UserProfile`
    * What it does: Allows the user to customize his or her own profile on GoMedic. This includes allowing the user to define
      his or her name, position, department and organization that they work for.
    * Justification: This feature introduces a form of customization in GoMedic, which was previously not available in AB3.
      This can allow the app to feel more personalized for the user.
    * Highlights: This user profile is displayed on the side window in GoMedic. The UI for the side window is implemented by 
      my teammate, Simon.
    * Notable PRs: [PR #167](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/167)

* **Ui Enhancement**: Implement UI component that displays the details of `Doctor`s stored in GoMedic in a tabular form.
    * What it does: Creates a tabular view of the `Doctor`s that users add into GoMedic.
    * Credits: This command heavily uses [**JavaFX**](https://docs.oracle.com/javafx/2/). The format for this
     tabular view of `Doctor` closely follows the format and implementation done by my teammate, Simon, 
     who referenced this [blog](http://tutorials.jenkov.com/javafx/tableview.html) when creating his `TableView` for `Activity` .

* **Enhancements to existing features**:
  * Refactored AB3's `Person` and `UniquePersonList` classes (Notably in [PR #82](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/82), [PR #140](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/140)). 
    Some notable points include:
    * Updating `Person` implementation so that it can be extended by GoMedic's `Doctor` and `Patient` classes.
    * Refactoring `UniquePersonList` to make use of Java generics to increase versatility and reusability of the class.
    * Increasing test coverage for these classes.

* **Overall Code Contribution**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=T15&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=yyhangz&tabRepo=AY2122S1-CS2103T-T15-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&zFR=false)

* **Project management**:
  * Opened issues that sought to improve to code quality (E.g. [Issue #116](https://github.com/AY2122S1-CS2103T-T15-1/tp/issues/116), [Issue #157](https://github.com/AY2122S1-CS2103T-T15-1/tp/issues/157))
  * Reviewed and approved team members' PRs for merging (more details in the **Community section**)
    
* **Documentation**:
    * User Guide:
        * Wrote the [doctor related features](https://ay2122s1-cs2103t-t15-1.github.io/tp/UserGuide.html#32-doctors-related-features) section. 
        * Wrote the [profile command instructions](https://ay2122s1-cs2103t-t15-1.github.io/tp/UserGuide.html#352-customizing-your-own-profile-profile) section. 
          (The summary of the parameter constraints in this section was done by my teammate, Seng Leng)
        * Reviewed and corrected grammar and formatting issues of team members' sections of the UG
          (E.g. Corrected teammate's formatting error while working on my own UG/DG section in [PR #307](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/307),
          Commented and gave suggestion on UG formatting issue in [PR #36](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/36#discussion_r715329502))
    * Developer Guide:
      * Revamped the [Storage Component](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#storage-component) 
        segment of the original AB3 DG. This included updating the Class Diagram, as well as adding more details about the implementation of the `Storage` class in general.
      * Wrote the section about the implementation of the [profile command execution](https://ay2122s1-cs2103t-t15-1.github.io/tp/DeveloperGuide.html#customizing-users-personal-profile-in-gomedic). 

* **Community**:
    * Reviewed PRs of fellow teammates (Some with non-trivial review comments, E.g. [PR #83](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/83), [PR #112](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/112))
    * Improved code quality and reusability of some classes that are shared and jointly used by several team members. 
      (E.g. [PR #140](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/140), [PR #141](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/141))
    * Reported bugs and suggestions for other teams during [PE-D](https://github.com/yyhangz/ped). 

    