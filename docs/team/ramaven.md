---
layout: page
title: Rama Venkatesh's Project Portfolio Page
---
### Project: GoMedic

GoMedic is a cross-platform desktop application written in Java and designed for doctors and medical residents to manage contacts and patient details.
We aim GoMedic to be used by someone who can type fast and take advantage of the optimized features for
Command Line Interface.

Given below are my notable contributions to the project.
* **Documentation**:
    * UI:
        * What it is: Created the UI mockup for GoMedic using Figma
        * Justification: This mockup is an initial and important part of our software development
          process as it decides the UI and frontend development that is to be done. Our team then 
          referred to this mockup when doing the UI enhancements and creating models. 
        * Highlights: The mockup shows the doctor's
          page, patient's page, activity page, the result of the ```view``` command for
          patients, doctors and activities, as well as the side bar.
        * Notable PRs: [PR #72](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/72),
          [PR #71](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/71),
          [PR #70](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/70)
      
    * User guide:
      * Added initial information about viewing patient and doctor command, displaying
      full details of a patient, displaying full details of a doctor 
        [Issue #19](https://github.com/AY2122S1-CS2103T-T15-1/tp/issues/19),
        [Issue #24](https://github.com/AY2122S1-CS2103T-T15-1/tp/issues/24),
        [Issue #25](https://github.com/AY2122S1-CS2103T-T15-1/tp/issues/25)
      * Updated full details of find command 
        [PR #286](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/286)
        
    * Developer guide
      * Wrote the [find command implementation](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/286)


* **UI Enhancement**: Change the dark theme of GoMedic to a light theme
    * What it does: Enables the app to follow the mockup more closely by changing from the 
      original dark grey theme of AB3 to a light theme with hues of blue and turquoise
    * Notable PRs: [PR #120](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/120)
    * Credits: This heavily uses JavaFx's CSS feature. 
    
* **Enhancement to existing feature**: Develop more robust ```find``` command
  * What it does: Allows user to search for entries based on their category (Doctor, Patient, or Activity),
    and specify a field to which the user input will be matched to. 
  * Justification: Allows for more targeted search so users can get the specific entries
    that they are looking for
  * Highlights: For example, within the patient category, users can search specifically
    within the blood type category. 
  * Notable PRs: [PR #160](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/160)
  * Credits: SE-EDU AB3 for creating the project template
    
* **New feature**: Develop initial skeleton of datetime parser
    * What it does: Takes the input time string from the user and parses it
      to create a Time object
    * Justification: This is needed as we our ```Activity``` class has start times and end times
      which are of the type Time.  
    * Notable PRs: [PR #110](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/110)

* **Project Management**: 
  * Opened issues that sought to fix bugs
    * Examples: [Issue #184](https://github.com/AY2122S1-CS2103T-T15-1/tp/issues/184)
  * Reviewed and approved PR of team member for merging. 
    * Example: [PR #112](https://github.com/AY2122S1-CS2103T-T15-1/tp/pull/112)
    
* **Community**: 
   * Reported bugs and suggestions for other teams during [PE-D](https://github.com/ramaven/ped). 

