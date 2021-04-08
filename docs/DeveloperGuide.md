**Developer Guide**
===================

## Content Page

1. [Introduction](#1-introduction)
	1. [What is HealthVault?](#11-what-is-healthvault)
	2. [About the Developer Guide](#12-about-the-developer-guide)
2. [How to use this guide](#2-how-to-use-the-guide)
3. [Getting Started](#3-getting-started)
4. [Design](#4-design)
    1. [Architecture](#41-architecture)
    2. [UI component](#42-ui-component)
    3. [Logic component](#43-logic-component)
    4. [Model component](#44-model-component)
    5. [Storage component](#45-storage-component)
    6. [Common classes](#46-common-classes)
5. [Implementation](#5-implementation)
    1. [Staff](#51-staff) 
    	1. [Staff Menu](#511-staff-menu)
    	2. [Add](#512-add)
    	3. [Delete](#513-delete)
    	4. [List](#514-list)
    	5. [Find](#515-find)
    2. [Patient](#52-patient)
    	1. [Patient Menu](#521-patient-menu)
    	2. [Add](#522-add)
    	3. [Delete](#523-delete)
    	4. [List](#524-list)
    	5. [Find](#525-find)
    3. [Doctor Appointment](#53-doctor-appointment)
    	1. [Doctor Appointment Menu](#531-doctor-appointment-menu)
    	2. [Add](#532-add)
    	3. [Delete](#533-delete)
    	4. [List](#534-list)
    	5. [Find](#535-find)
    4. [Nurse Schedule](#54-nurse-schedule)
    	1. [Nurse Schedule Menu](#541-nurse-schedule-menu)
    	2. [Add](#542-add)
    	3. [Delete](#543-delete)
    	4. [List](#544-list)
    	5. [Find](#545-find)
    5. [Inventory](#55-inventory)
        1. [Inventory Menu](#551-inventory-menu)
    	2. [Add](#552-add)
    	3. [Delete](#553-delete)
    	4. [List](#554-list)
    	5. [Find](#555-find)

[Appendix A: Product Scope](#a-appendix-a-product-scope)

[Appendix B: User Stories](#b-appendix-b-user-stories)

[Appendix C: Use Cases](#c-appendix-c-use-cases)

[Appendix D: Non Functional Requirements](#d-appendix-d-non-functional-requirements)

[Appendix E: Glossary](#e-appendix-e-glossary)

[Appendix F: Product Survey](#f-appendix-f-product-survey)

[Appendix G: Instructions for Manual Testing](#g-appendix-g-instructions-for-manual-testing)


## 1. Introduction
### 1.1 What is HealthVault?

HealthVault is a desktop app for managing doctor, nurse and patient information, optimised for use through the command line interface. This app is for the head nurse of a hospital, if the user can type fast, it is better than a traditional GUI app.


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **Purpose and scope**

The purpose of this developer guide is to describe the architecture and software design decisions for our application. This guide will cover our program architecture, the logical view of major components and how our functions work.

### 1.2 About the Developer Guide

<br>

## 2. How to use the guide

<br>

## 3. Getting Started

&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; **Setting up**

There are 2 prerequisites for this project

1.  Java 11

-   Launch your terminal and type "java -version" to ensure the correct version

2.  Intellij IDEA

&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; **Getting Started**

1.  Fork this repo, and clone the fork into your computer.

2.  Import the project as a gradle project

3.  Run Duke.main and try a few commands

4.  Run the tests to ensure they all pass.


<br>


## 4. Design

###  4.1 Architecture

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="images/ArchitectureDiagram.png">

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Our application utilises many layers of abstraction which allows each individual component to be self contained yet able to work with other components. The Main Menu component allows direct access to other components as shown in the diagram.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Shared functionalities are placed within the Common Classes component which will be elaborated on in the section [Common Classes](#common-classes)

### 4.2 UI component
### 4.3 Logic component
### 4.4 Model component
### 4.5 Storage component
### 4.6 Common classes

<br>

## 5. Implementation


###  5.1 Staff

### 5.1.1 Staff Menu

Similar to the Start Menu, the Staff Menu will repeatedly request user input until the `return` command is given.

Whenever a user input is given to the Staff Menu, the following steps will occur.

**Launching Staff Menu**

1. `ToStaffInstance.execute()` will create and call `StaffInstance.run()`
2. `StaffInstance.run()` will start by loading/creating the Staff data .txt file for Staff database records. It will check for any signs of corrupted file when loading. Exception will be thrown if any corruption occurs.
3. `StaffInstance.run()` will then repeatedly call `commandHandler()`.

**Getting User Input**

4. `StaffInstance.run()` will repeatedly request for user input and call `StaffParser.commandHandler()`.
5. `commandHandler()` will call the `smartCommandRecognition()` to assess the given user input and determine which command is most similar to the input
6. Based on the recognised command by the system, the relevant commands will be carried out.

<br>

### 5.1.2 Add

**Implementation:**

The function Add takes in 4 compulsory fields (Staff ID, Name, Age, Specialisation) to create the Staff Object and adds it to an aggregation of Staff Objects. Data input is first checked to ensure validity. Any invalid input detected will result in an Exception thrown and command aborted. A StaffAdd Command object is created. StaffAdd command object will be executed to create the Staff Object which will be added to the aggregation of Staff Objects.

Invalid Input includes:

> 
	- Invalid Staff ID format
	- Duplicated Staff ID
	- Age that < 18 or > 150
	- Blank input (i.e Empty inputs)
	- Illegal Characters

`add/[Staff ID]/[name]/[age]/[specialisation]`

<img src="images/SD Staff Add.png">

**Check validity of the data input**

1. If the command recognised is the add command, `commandHandler()` calls `staffChecker.checkValidDataForAdd()` to ensure data entered is valid
2. `checkValidDataForAdd()` will call the following function in sequence:

	- checkStaffID()	
	- checkDuplicateStaffID()
	- checkStaffAge()
	- checkBlankInput2()
	- invalidCharactersStaffChecker()

**Creating StaffAdd command**

2. If the input data is valid, a StaffAdd Command object is created. Else a relevant error is thrown.
3. The StaffAdd Command object is returned to `StaffInstance.run()`

**Creating Staff Object with User Input**

4. StaffInstance then executes the StaffAdd Command object to begin the process of creating the Staff object

5. `StaffAdd.execute()` will call the function in `staffAggregation.add()`

6. `staffAggregation.add()` will instantiate a new Staff object and add it to the ArrayList<Staff> StaffList. which contains all the Staff Objects. 

**Saving Staff Objects into .txt file**

7. `staffAggregation.add()` then calls `staffStorage.writeToFile()` which starts the process of writing the details of all existing Staff Objects, within the StaffList into a specified .txt file.
8. `staffStorage.writeToFile()` then calls `createFile()` which ensures that the specified .txt file exists.
9. Data is written and saved.
10. Control is then returned to StaffInstance.

<br>

### 5.1.3 Delete

**Implementation:**

The function Delete takes in 1 compulsory field (Staff ID) to identity and delete the Staff Object from the aggregation of Staff Objects. Data input is first checked to ensure validity. Any invalid input detected will result in an Exception thrown and command aborted. After validation, a StaffDelete Command object is created. StaffDelete command object will be executed to iterate through the aggregation of Staff Objects. If Staff Object exists, it will be removed. Else an error message will be displayed.

Invalid Input includes:

> 
	- Invalid Staff ID format
	- Blank input (i.e Empty inputs)
	- Illegal Characters

`delete/Staff ID`


**Check validity of the data input**

1. If the command recognised is the delete command, `commandHandler()` calls `staffChecker.checkDeleteCommand()` to ensure that there are valid and sufficient inputs

**Creating StaffDelete command**

2. If the input data is valid, a StaffDelete Command object is created 
3. The StaffAdd Command object is returned to `StaffInstance.run()`

**Deleting Staff Object using User Input**

4. StaffInstance then executes the StaffDelete Command object to begin the process of deleting the referenced Staff object
5. `StaffDelete.execute()` will call the function `staffAggregation.delete()`
6. `staffAggregation.delete()` will iterate through the objects in ArrayList<Staff> StaffList. The Staff Object referenced by the input given by the user will be deleted.

**Saving changed Staff Objects into .txt file**

7. `staffAggregation.delete()` then calls staffStorage.writeToFile() which starts the process of writing the changed details of Staff Objects, within the StaffList into a specified .txt file.

8. `staffStorage.writeToFile()` then calls `createFile()` which ensures that the specified .txt file exists.

9. Data is written and saved.

10. Control is then returned to StaffInstance.

<br>

### 5.1.4 List

**Implementation:**

The function list takes in 1 option field (nurses/doctors) to identity and list the category of Staff Objects required from the aggregation of Staff Objects. Data input is first checked to ensure validity. Any invalid input detected will result in an Exception thrown and command aborted. After validation, a StaffList Command object is created. StaffList command object will be executed to iterate through the aggregation of Staff Objects. Staff Objects will then be displayed based on the user given input.

Invalid Input includes:

> 
	- Any input apart from Blank Input (i.e Empty input) OR "doctors" OR "nurses"

`list/<doctors/nurses>`

**Check validity of the data input**

1. If the command recognised is the list command, `commandHandler()` calls `staffChecker.checkListCommand()` to check and verify the validity of inputs accompanied by the list command, if any.

**Creating StaffList command**

2. If the input data is valid, a StaffList Command object is created 
3. The StaffList Command object is returned to `StaffInstance.run()` 

**Viewing Staff Objects**

4. StaffInstance then executes the StaffList Command object to begin the process of displaying all Staff objects.
5. `StaffList.execute()` will call the function `staffAggregation.list()`
6. `staffAggregation.list()` will iterate through the objects in ArrayList<Staff> StaffList. 
7. Depending on the input given by the user, the relevant Staff Objects will be displayed.
8. Control is then returned to StaffInstance.

<br>

### 5.1.5 Find

**Implementation:**

The function Add takes in 1 compulsory field (keyword) to find the relvant Staff Objects within the aggregation of Staff Objects. Data input is first checked to ensure validity. Any invalid input detected will result in an Exception thrown and command aborted. The given input is used to match with every single field of the Staff Object. If there is a match, the Staff Object will be displayed. Else, an error message will be displayed.

Invalid Input includes:

> 
	- Blank input (i.e Empty inputs)

`find/[keyword]`

**Check validity of the data input**

1. If the command recognised is the find command, `commandHandler()` calls `MainChecker.checkNumInput()`. `MainChecker.checkNumInput()` does a simple check to ensure there is an accompanying input given by the user together with the find command.

**Creating StaffFind command**

2. If the input data exist, a StaffFind Command object is created 
3. The StaffFind Command object is returned to `StaffInstance.run()` 

**Finding relevant Staff Objects**

4. StaffInstance then executes the StaffList Command object to begin the process of finding and displaying relevant Staff objects.
5. `StaffFind.execute()` will call the function `staffAggregation.find()`
6. `staffAggregation.find()` will iterate through the objects in ArrayList<Staff> StaffList. 
7. `staffAggregation.find()` will utilise a search function in StaffAggregation to find any Staff Objects that matches the given keyword by the user. 
8. The relevant Staff Objects are then displayed.
9. Control is then returned to StaffInstance.

<br>

###  5.2 Patient

### 5.2.1 Staff Menu
### 5.2.2 Add
### 5.2.3 Delete
### 5.2.4 List
### 5.2.5 Find

<br>

###  5.3 Doctor Appointment

### 5.3.1 Doctor Appointment Menu
### 5.3.2 Add
### 5.3.3 Delete
### 5.3.4 List
### 5.3.5 Find

<br>

###  5.4 Nurse Schedule

### 5.4.1 Nurse Schedule Menu

Similar to the start menu, the Nurse Schedule menu will repeatedly request user input until the `return` command is given.

Whenever a user input is given to the Nurse Schedule Menu, the following steps will occur

**Launching Nurse Schedule Menu**

1.`ToNurseScheduleInstance.execute()` will create and call `NurseScheduleInstance.runCommandLoopUntilExit()`.

2.`runCommandLoopUntilExit()` will start by loading/creating the NurseSchedule.txt for database records. It will check for any signs of file corruption when loading. An exception will be thrown if any corruption is present.

3.`runCommandLoopUntilExit()` will then repeatedly call nurseParse().

**Getting User Input**

4.User inputs are repeatedly requested by `runCommandLoopUntilExit`.

5.`nurseParse()` will call `smartCommandRecognition` to assess the given user input and determine which command is the most similar to the input.

6.Based on the recognised command, the relevant execution will be carried out.

### 5.4.2 Add

**Implementation**

When the user attempts to add a new nurse schedule, the
NurseScheduleInstance, NurseScheduleParser, NurseScheduleChecker, NurseScheduleActions and Nurse Schedule Command
classes will be accessed. Data input will be first checked to ensure validty. Any invalid input detected will result in an Exception thrown and command will be aborted. Else, a NurseScheduleAdd Command object is created and executed which will create a NurseSchedule object to be added.

Invalid Inputs include:

>
	- Invalid Nurse ID or Patient ID format
	- Non-existent Nurse ID or Patient ID
	- Blank input
	- Illegal Characters
	- Illegal date format
	- Duplicate schedules (i.e similar Patient ID and date)
	
`add/[Nurse ID]/[Date (DDMMYYYY)]`

**Checking validity of data input**

1.If the command recognised is the add command, the parameters will first be checked for their validity. The following functios will be called in sequence:
	- isValidDate()
	- checkNumInput()
	- illegalCharacterChecker()
	
**Creating NurseScheduleAdd object with User Input**

2.If the parameters are valid, a NurseScheduleAdd Command object is created, which will be passed back to `NurseScheduleInstance.runCommandLoopUntilExit()`.

3.The Command objected is then executed and `NurseScheduleActions.addSchedule()` will be called which creates a NurseSchedule object an adds it into the array list.

**Saving NurseSchedule objects into .txt file**

4.The command loop then calls `NurseScheduleStorage.writeToFile()` which starts the process of writing detials of all existing Nurse Schedule objects within the Arraylist into a specific .txt file.

5.Control is then returned to NurseScheduleInstance.


### 5.4.3 Delete

**Implementation**

When the user wants to delete a specified nurse schedule, the NurseScheduleActions and NurseScheduleStorage classes will be accessed. It takes in Nurse ID and date to identify and delete the Nurse Schedule object. Any invalid input detected will result in an exception thrown and command will be aborted. Else, a NurseScheduleDelete Command object is created and executed.

Invalid Inputs include:

>
	- Invalid Nurse ID format
	- Non-existent Nurse ID
	- Blank input
	- Illegal Characters
	- Illegal date format
	
`delete/[Nurse ID]/[Date (DDMMYYYY)]`

**Checking validity of data input**

1. If the command is recognised as a delete command, the parameters provided will first be checked for its validity.

**Creating NurseScheduleDelete object**

2. If the parameters are valid, a NurseScheduleDelete Command object is created, which will be passed back to `NurseScheduleInstance.runCommandLoopUntilExit()`.

3. `NurseScheduleDelete.execute()` will call the function `NurseScheduleActions.deleteSchedule()`.

4. `deleteSchedule` iterates through the arraylist and removes the first object that matches the user input given.

**Saving updated NurseSchedule objects into .txt file**

5. `runCommandLoopUntilExit()` will then call `NurseScheduleStorage.writeToFile()` which starts the process of writing detials of all existing Nurse Schedule objects within the Arraylist into a specific .txt file.

6. Control is then returned to NurseScheduleInstance.

### 5.4.4 List
### 5.4.5 Find

<br>

###  5.5 Inventory

### 5.5.1 Inventory Menu
### 5.5.2 Add
### 5.5.3 Delete
### 5.5.4 List
### 5.5.5 Find

<br>


## a. Appendix A: Product Scope

<br>

## b. Appendix B: User Stories

<br>

## c. Appendix C: Use Cases

<br>

## d. Appendix D: Non Functional Requirements

<br>

## e. Appendix E: Glossary

<br>

## f. Appendix F: Product Survey

<br>

## g. Appendix G: Instructions for Manual Testing

<br>



### **UI component**

1.  Each class has a specified UI class that serves the specific need
    > for the individual class

2.  There is a Main UI Class that shares all common UI functionalities
    > amongst the classes. This will be elaborated in the Common Classes
    > component

> (Insert UI UML diagrams)

### **Logic component**

1.  This component would be the backbone of our program, where the
    > functions of each individual class are crafted and contribute to
    > the program.

2.  Command is passed through the Parser.java class which determines
    > what actions to perform. The actions to be performed are methods
    > called in the classes stated below.

[Actions:]{.ul}

1.  In the menu of Doctor's Appointment, command of "add D12345 Alex M
    > 21012021".

2.  Input is passed along to the Parser.java class where the keyword
    > "add" is detected.

3.  addAppointment() method in the AppointmentActions.java class is
    > called and adds the details into the list

[Classes:]{.ul}

1.  AppointmentActions

2.  DrugActions

3.  NurseScheduleActions

4.  PatientList

5.  StaffList

### **Object Class component**

This component is to distinguish the separate object that has been
instantiated to hold the details of our target users. An object class
allows for a structure that helps store details from user input in the
format that we want to be saved.

[Objects:]{.ul}

1.  DoctorAppointment.java

2.  Drug.java

3.  Patient.java

4.  NurseSchedule.java

5.  Staff.java

### 

### **Storage component**

The difference classes for storages are separated into their respective
architecture for easier management.

> [Methods:]{.ul}

1.  writeToFile() - Calling upon this method whenever there is change to
    > the data array list.

2.  createFile() - Called during the start of the program when
    > loadFile() is called and returns an error, meaning that there are
    > no text files in storage. Thus, this method is called to create a
    > new text file for data storage.

3.  loadFile() - Called during the start of the program, to initialise
    > the array list and load previously saved data.

[Classes:]{.ul}

1.  DoctorAppointmentStorage

2.  DrugStorage

3.  NurseScheduleStorage

4.  PatientStorage

5.  StaffStorage

### **Common classes**

1.  Main UI Class

2.  Main Storage Class

 

**Implementation**
------------------

This section describes how the features are being implemented. The scope
will include the Doctor Appointments / Drugs / Nurse Schedules /
Patients / Staff related features.

Common features that can be identified across the scope include these
distinct functionalities: Adding, Deleting and Listing. For Nurse
Schedules/ Patients / Staff related features, there is an additional
search functionality.

### **Menu-related Features**

**Main Menu**

This feature allows users to select the different menus for Patient /
Staff / Doctor Appointments / Nurse Schedules and Drugs through
inputting commands within the main menu. Failure to input a correct or
recognized command will then prompt the program to generate an exception
that will alert the user of what they have done wrongly, and also prompt
the user to access the help list to view the correct set of commands and
their proper syntaxes.

If the user enters a correct and recognized command, they will be able
to access the functionalities related to the specific Objects (i.e
Staff, Patient, Doctor Appointments, Nurse Schedules, Drugs).

[Implementation:]{.ul}

1.  A command is requested from the user as the application starts,
    > Duke.run() calls UI.scanInput() to read the user's input from the
    > command line.

2.  The user input determines which type of functionalities will be
    > accessed by the user.

**Staff Menu**

1.  When "Staff" is given as input in the main menu, `duke.run()` will call `StaffParser.run()`

2.  `StaffParser.run()` provides the user a huge range of functionalities to work with the Staff Objects.

3.  `StaffParser.run()` will request input from the user.

4.  User input will determine the type of actions taken on Staff Objects.

5.  The details of the functionalities related to Staff Object is detailed in the section below under [Staff-related-Features](#staff-related-features).


**Doctor Appointment Menu**

This feature of the program can be accessed through the main menu as the
application starts running. Accessing this feature gains access to the
DoctorAppointment package, including the classes
AppointmentActions.java, DoctorAppointment.java,
DoctorAppointmentInstance.java, DoctorAppointmentStorage.java and
Parser.java. The purpose of this feature is to save a database of
patient details when they make an appointment with the doctor.

[Implementation:]{.ul}

6.  When the user types in a command,

    a.  Duke.run() calls UI.scanInput() to read the user's input from
        > the command line.

7.  After scanning the user input, it will be run through case
    > statements in Duke.run() to find the appropriate command that
    > corresponds to the user's input

8.  Depending on the input Duke.run() will then bring the user to the
    > specific instance handler for that feature, which can be
    > Staff.Parser.run() or NurseScheduleInstance.main(). Alternatively,
    > Duke.run() creates either a PatientCommandInstance or
    > DoctorAppointmentInstance or DrugInstance object that may act as
    > an instance handler.

9.  The selected instance handler will then have its own menu that may
    > handle any additional user commands that are inputted to access
    > features within that particular instance.

**Return to main menu**

[Implementation:]{.ul}

User will be returned the Main Menu upon usage of the return command

return

> Saving Staff Objects into .txt file

1.  User executes return command

2.  Parser.run() calls StaffStorage.writeToFile() which starts the
    > process of writing the details of all existing Staff Objects,
    > within the StaffList into a specified .txt file.

3.  StaffStorage.writeToFile() then calls createFile() which ensures
    > that the specified .txt file exists.

4.  Data is written and saved.

5.  StaffList.resetList() will be called to remove all previous entries
    > of Staff Objects within ArrayList\<Staff\> list.

6.  Parser.run() will cease running and control will be returned to
    > duke.run()

**View relevant Staff commands**

[Implementation:]{.ul}

All commands in the Staff Menu will be displayed with detailed
information on its usage

help

1.  StaffUI.printStaffHelpList() is executed and help commands and
    > instructions are displayed;

Failure to input a correct or recognized command will then prompt the
program to generate an exception that will alert the user of what they
have done wrongly, and also prompt the user to access the help list to
view the correct set of commands and their proper syntaxes.

If the user enters a correct and recognized command, they will then be
put into the respective instance of feature that they selected.

###  

**Patient-related Features**

**Adding a Patient**

add \[Patient ID\] \[name\] \[age\] \[gender\] \[illness\] \[medication
needed\]

This feature allows the user to add a patient to the list of current
patients. If the user fails to correctly input the proper parameters.
Exceptions will be triggered to guide the user on what caused the
exception and the action will be aborted. The user will also be directed
to use the help command to refer to the correct syntax.

If the patient is added successfully, a message showing the name of the
patient added will be shown to the user.

[Implementation:]{.ul}

When a user attempts to add a patient the following classes will be
accessed: PatientCommandInstance, UI, PatientUI, Patient, PatientList,
PatientParser, PatientStorage.

1.  User Executes command add P12345 Joe 30 M flu panadol

    a.  PatientCommandInstance calls UI.scanInput() to read the user
        > input

    b.  PatientCommandInstance calls PatientParser.patientParse() to
        > parse the user\'s input into a string array.

2.  Checking user input in parser

    a.  Using the case statements in patientParse, the command add is
        > identified.

    b.  PatientParse then calls lengthCheck() and iDParser() to scan the
        > user input for any errors

    c.  If lengthCheck() and iDParser() throw an exception, the current
        > action will be aborted and the user will be notified with an
        > appropriate error message and once again prompted to enter
        > another command. If successful, the program will move to
        > step 3.

3.  Creating Patient object

    a.  patientParse calls PatientList.addPatient() and creates a
        > Patient object that will be stored in the PatientList Object
        > which contains an ArrayList of Patient objects.

4.  Prompting user that Patient Object has been created

    a.  After successfully creating the Patient Object and adding it to
        > the PatientList object. PatientList.addPatient() calls
        > PatientUI.patientAddedMessage() to print a message notifying
        > the user that a patient has been added.

5.  Saving the current list of patients

    a.  After the new Patient object is created and stored in a
        > PatientList, patientParse returns to the
        > PatientCommandInstance.

    b.  PatientCommandInstance then calls PatientStorage.storePatients()
        > store the updated PatientList object into a text file at a
        > specified directory.

**Listing patients**

list

This feature allows the user to view the list of patients. If the
command is inputted correctly a list showing the current patients in the
order in which they were added to the list will be displayed.

[Implementation:]{.ul}

When a user attempts to add a patient the following classes will be
accessed: PatientCommandInstance, UI, PatientUI, Patient, PatientList,
PatientParser.

1.  User Executes command list

    a.  PatientCommandInstance calls UI.scanInput() to read the user
        > input

    b.  PatientCommandInstance calls PatientParser.patientParse() to
        > parse the user\'s input into a string array.

2.  Checking user input in parser

    a.  Using the case statements in patientParse, the command list is
        > identified.

    b.  patientParse then calls lengthCheck() to scan the user input for
        > any errors

    c.  If lengthCheck() throws an exception, the current action will be
        > aborted and the user will be notified with an appropriate
        > error message and once again prompted to enter another
        > command. If successful, the program will move to step 3.

3.  Listing the Patients in the list

    a.  patientParse calls PatientList.listPatients() to list the
        > patients currently in the PatientList, which is an array of
        > current Patients.

4.  Printing the list of Patients

    a.  If there are no patients currently in the list,
        > PatientList.listPatients() calls
        > PatientUI.emptyPatientListMessage(). And displays to the user
        > that there are no patients in the list.

    b.  If there are patients currently in the list,
        > PatientList.listPatients() calls
        > PatientUI.notEmptyPatientListMessage() before iterating
        > through the list and calling PatientUI.printPatientList() to
        > show each individual patient in the list.

**Deleting a Patient:**

delete \[Patient ID\]

This feature allows the user to delete an existing patient in the list
of patients. If the user fails to correctly input the proper parameters.
Exceptions will be triggered that will notify the user on what caused
the exception and the action will be aborted. The user will also be
directed to use the help command to refer to the correct syntax.

If the patient has been successfully deleted from the list they will be
notified with a message showing the deleted patient's name and that they
have been removed from the list.

[Implementation:]{.ul}

When a user attempts to add a patient the following classes will be
accessed: PatientCommandInstance, UI, PatientUI, Patient, PatientList,
PatientParser, PatientStorage.

1.  User Executes command delete P12345

    a.  PatientCommandInstance calls UI.scanInput() to read the user
        > input

    b.  PatientCommandInstance calls PatientParser.patientParse() to
        > parse the user\'s input into a string array.

2.  Checking user input in parser

    a.  Using the case statements in patientParse, the command delete is
        > identified.

    b.  patientParse then calls lengthCheck() and iDParser() to scan the
        > user input for any errors

    c.  If lengthCheck() and iDParser() throw an exception, the current
        > action will be aborted and the user will be notified with an
        > appropriate error message and once again prompted to enter
        > another command. If successful, the program will move to
        > step 3.

3.  Deleting the Patient from the list

    a.  patientParse calls PatientList.deletePatient() and the patient
        > specified by the user is deleted from the list through
        > iterating through the patientList and discovering the location
        > of Patient specified before deleting the object from the
        > ArrayList

4.  Notifying user that Patient has been deleted

    a.  After successfully deleting the Patient from the PatientList
        > object. PatientList.deletePatient() calls
        > PatientUI.deletePatientMessage() to print a message notifying
        > the user that the Patient has been deleted.

5.  Saving the current list of patients

    a.  After the new Patient object is deleted from the PatientList,
        > patientParse returns to the PatientCommandInstance.

    b.  PatientCommandInstance then calls PatientStorage.storePatients()
        > store the updated PatientList object into a text file at a
        > specified directory.

**Finding a patient:**

find \[Patient ID\]

This feature allows the user to find an existing patient in the list of
patients. If the user fails to correctly input the proper parameters.
Exceptions will be triggered that will notify the user on what caused
the exception and the action will be aborted. The user will also be
directed to use the help command to refer to the correct syntax.

If the find command is successfully executed. The user will be notified
as the patient they were finding will be listed.

[Implementation:]{.ul}

When a user attempts to add a patient the following classes will be
accessed: PatientCommandInstance, UI, PatientUI, Patient, PatientList,
PatientParser, PatientStorage.

1.  User Executes command find P12345

    a.  PatientCommandInstance calls UI.scanInput() to read the user
        > input

    b.  PatientCommandInstance calls PatientParser.patientParse() to
        > parse the user\'s input into a string array.

2.  Checking user input in parser

    a.  Using the case statements in patientParse, the command find is
        > identified.

    b.  patientParse then calls lengthCheck() and iDParser() to scan the
        > user input for any errors.

    c.  If lengthCheck() and iDParser() throw an exception, the current
        > action will be aborted and the user will be notified with an
        > appropriate error message and once again prompted to enter
        > another command. If successful, the program will move to
        > step 3.

3.  Finding the Patient in the list

    a.  patientParse calls PatientList.findPatient() and iterates
        > through the list to find the Patient that matches the input
        > specified by the user.

4.  Notifying user that Patient has been found

    a.  After successfully finding the Patient.
        > PatientList.findPatient() calls PatientUI.printPatientList()
        > to print a message notifying the user that a patient has been
        > found, showing the patients details.

**Help**

help

This feature shows a list of commands usable within the patient-related
functions and their correct syntaxes that must be followed to enable
proper usage.

[Implementation:]{.ul}

When a user attempts to add a patient the following classes will be
accessed: PatientCommandInstance, UI, PatientUI, PatientParser.

1.  User Executes command help

    a.  PatientCommandInstance calls UI.scanInput() to read the user
        > input

    b.  PatientCommandInstance calls PatientParser.patientParse() to
        > parse the user\'s input into a string array.

2.  Checking user input in parser

    a.  Using the case statements in patientParse, the command find is
        > identified.

    b.  PatientParse then calls lengthCheck() to scan the user input for
        > any errors.

    c.  If lengthCheck() throws an exception, the current action will be
        > aborted and the user will be notified with an appropriate
        > error message and once again prompted to enter another
        > command. If successful, the program will move to step 3.

3.  Printing the help list for the user to reference

    a.  patientParse calls PatientUI.printPatientHelpList() to print a
        > list of commands and instructions on how to use them.

**Return to main menu**

return

This feature returns the user to the main menu of the program.

[Implementation:]{.ul}

When a user attempts to add a patient the following classes will be
accessed: PatientCommandInstance, UI, PatientUI, PatientParser.

1.  User Executes command return

    a.  PatientCommandInstance calls UI.scanInput() to read the user
        > input

    b.  PatientCommandInstance calls PatientParser.patientParse() to
        > parse the user\'s input into a string array.

2.  Checking user input in parser

    a.  Using the case statements in patientParse, the command return is
        > identified.

    b.  PatientParse then calls lengthCheck() to scan the user input for
        > any errors.

    c.  If lengthCheck() throws an exception, the current action will be
        > aborted and the user will be notified with an appropriate
        > error message and once again prompted to enter another
        > command. If successful, the program will move to step 3.

3.  Notifying user of returning to main menu

    a.  patientParse calls UI.returningToStartMenuMessage() to show the
        > user that they will now exit from the PatientCommandInstance.

4.  Returning to main menu

```{=html}
<!-- -->
```
a.  patientParse returns true to PatientCommandInstance and this causes
    > the PatientCommandInstance to break out of its input loop. Thereby
    > returning to the main menu from the PatientCommandInstance.

## **Nurse Schedule-related Features**

When the user accesses an instance of Nurse Schedules, the
NurseScheduleParser, NurseScheduleActions and NurseScheduleStorage
classes will be accessed. If data is found on the NurseSchedules text
file, it will be loaded into the arraylist, else a new text file is
created.

### Implementation:

1.  User types in a command

2.  NurseScheduleInstance calls UI.getInput() to receive user input.

3.  NurseScheduleInstance passes the input to nurseParse().

4.  nurseParse() creates a Command object with the relevant parameters and returns it to NurseScheduleInstance.

5.  NurseScheduleInstance executes the object by calling the objects execute method.

### **Adding a new Nurse Schedule**

### Implementation

When the user attempts to add a new nurse schedule, the
NurseScheduleInstance, NurseScheduleParser, NurseScheduleChecker, NurseScheduleActions and Nurse Schedule Command
classes will be accessed, and the following sequence of actions is
called to prompt execution results to user:

`add/[Nurse ID]/[Date (DDMMYYYY)]`:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **Getting User Input**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1. User inputs command which is received by NurseScheduleInstance.runCommandLoopUntilExit().

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2. User input is the passed to NurseScheduleParser.nurseParse() which returns a Command Add object.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **Creating NurseSchedule object with User Input**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3. NurseScheduleInstance then executes the Command Add object.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 4. addSchedule() from NurseScheduleActions will then be called which creates the 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; NurseSchedule object and adds it into the arraylist.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **Saving NurseSchedule objects into .txt file**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 4. The command loop then calls NurseScheduleStorage.writeToFile() which starts the process of 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; writing details of all existing Nurse Schedule objects within the ArrayList to the specified .txt file

### **Listing Nurse Schedules**

### Implementation:

When the user attempts to list nurse schedules, they will have the
choice of listing all schedules or a specified nurse id's schedule. This
is similar to a search function. This will access the
NurseScheduleActions class.

<img src="images/ListNurseIDSequenceDiagram.png">
<p align = "center"> Sequence Diagram when list/N12345 inputted.</p>

`list/[Nurse ID or list/all`:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **Getting User Input**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1. User inputs command which is received by NurseScheduleInstance.runCommandLoopUntilExit().

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2. User input is the passed to NurseScheduleParser.nurseParse() which returns a Command List object.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **Gathering necessary schedules**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3. NurseScheduleInstance then executes Command List object, which calls listSchedules() from NurseScheduleActions.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 4. listSchedules() will call listAllSchedules() if the user inputs all, else it will check if Nurse ID is valid and 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; call getNurseSchedulesById().

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **Printing schedules**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5. printSchedules() is then called to print all schedules.

### **Deleting Nurse Schedules**

### Implementation:

When the user wants to delete a specified nurse schedule, the NurseScheduleActions and NurseScheduleStorage classes will be accessed.

`delete/[Nurse ID]/[Date (DDMMYYYY)]`:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **Getting User Input**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1. User inputs command which is received by NurseScheduleInstance.runCommandLoopUntilExit().

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2. User input is the passed to NurseScheduleParser.nurseParse() which returns a Command Delete object.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **Deleting Schedule**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3. NurseSchedule then executes Command Delete object, which calls deleteSchedule() from NurseScheduleActions.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 4. deleteSchedule() loops through the arraylist of schedules and calls remove() to delete the specified schedule. 

### **Doctor Appointment-related Features**

[Implementation:]{.ul}

1.  User executes command

2.  Depending on the command, the Parser.java Class will determine which
    > method of Appointment Actions to call upon.

3.  Commands includes add, list, delete, help and return.

[Features:]{.ul}

1.  add\[Doctor ID\] \[Patient's Name\] \[Gender\] \[DDMMYYYY\]:

-   When the user inputs the command, the input will be parsed into
    > Parser.java, which will call upon addAppointment() method in the
    > AppointmentActions.java.

-   Within the method, the string input would be split() into an
    > inputArray which will then create a new object of type
    > DoctorAppointment and added into the \<DoctorAppointment\>
    > ArrayList.

2.  list \[Doctor ID\]:

-   When a list command is called, it calls upon the listAppointment()
    > method in the AppointmentActions.java class.

-   The input of the queried doctor ID is passed along as a string into
    > the stated method and it iterates through the ArrayList of saved
    > doctor's appointment with a for loop.

-   Comparing if the Doctor ID while iterating is equals to the input
    > that is passed, the details of the Appointment - ID, Patient's
    > Name, Gender, Date, is printed out for the user.

3.  delete \[Appointment ID\]:

-   When the command delete is called. The Parser.java will recognise
    > the command and call upon deleteAppointment() method in the
    > AppointmentActions.java class.

-   A variable "index" of default value 999 is initialised. A for loop
    > is then called upon to iterate through the current ArrayList of
    > doctor's appointment list to search for the ID that is equal to
    > the input.

-   If there is a match, the variable index would be set to the
    > iteration in which the ID had been matched

-   If the value of index remains at 999, the system recognise that
    > there is no matched ID in the system, else, the system would
    > remove the appointment details from the Doctor Appointment List
    > and call upon writeToFile() method from the storage class to
    > rewrite the file with the most current details.

4.  help:

-   When the command help is called. The Parser.java will recognise the
    > command and call upon helpAppointment() method in the
    > AppointmentActions.java class.

-   The method calls upon doctorAppointmentHelp() method from the UI
    > class which will print the commands that are necessary for the
    > program.

5.  return:

-   When the command return is called, the Parser.java class returns a
    > boolean value of True instead of the default False.

-   This will set the boolean variable, isReturnToStartMenu in the
    > DoctorAppointmentInstance.java class to True.

-   This will prompt the program to return to the staff Menu as if when
    > the program first booted.

 

**Drugs-related features**
--------------------------

This feature can be accessed when the user inputs "5" into the terminal.
The user will gain access to DrugParser, DrugStorage, and DrugActions.
If data is found on the Drugs text file, it will be loaded into the
arraylist, else a new text file is created. The purpose of this feature
is to save a database of all the drugs in the inventory.

[Implementation:]{.ul}

User executes a command. Depending on the command, DrugInstance will
call the relevant methods in DrugActions. DrugActions will either add,
list or delete a Drug object.

add\[Drug name\] \[Price\] \[Quantity\]:

Getting User Input

> User input add command which is processed by DrugInstance.run().

Creating Drug object with User Input

> DrugAction.addDrug() creates a new NurseSchedule object and is stored
> into an existing ArrayList\<Drug\> inventory which contains all the inventory
> objects.

Saving Drug objects into .txt file

> The command loop then calls DrugStorage.writeToFile() which starts the
> process of writing details of all existing Drug objects within the
> ArrayList to the specified .txt file

list \[Drug name\]:

> When the user inputs this command, the input will be parsed into
> Parser.java, which will call the DrugActions.listDrug() method in the
> DrugActions.java.
>
> The most updated database of drugs saved will be listed.

delete \[Drug name\]:

> When the user inputs this command, the input will be parsed into
> Parser.java, which will call the DrugActions.deleteDrug() method in
> the DrugActions.java.
>
> Everything related to that specific inventory will be removed.

help:

> When the user inputs this command, the input will be parsed into
> Parser.java, which will call the DrugUI.printHelpMessage() method in
> the DrugUI.java.
>
> The available features, add, list, delete, help, and return will be
>
> displayed for the user.
>
> The program will wait for the user to input one of the features.

return:

> When the user inputs this command, the input will be parsed into
> Parser.java, it will bring the user back to the main menu of the
> program.

 

**Documentation, logging testing, configuration, dev-ops**
----------------------------------------------------------

### **Product scope**

**Target user profile:**

-   Has a need to manage a huge amount of data like medical records,
    > schedules and doctor info

-   Prefers a no-frills functional app over other types

-   Can type reasonably fast and accurate

-   Does not need a mouse

-   Reasonably comfortable using CLI applications

**Value Proposition:** A hospital management system that is faster and
minimal compared to a typical GUI application

### User stories

  **Priority**   **As a\...**   **I want to\...**                                       **So that I can\...**
  -------------- -------------- ------------------------------------------------------- --------------------------------------------------
  \* \* \*       new user       quickly refer to usage instructions                     quickly get on track with the workflow
  \* \* \*       user           add a new staff/patient                                 
  \* \* \*       user           delete staff/patients                                   remove entries i no longer need
  \* \* \*       user           quickly look up schedules for both nurses and doctors   plan my schedule better
  \* \* \*       user           quickly look up inventory inventories                        plan what and when to restock our inventory supplies
  \*             user           have the program recognize slight errors in typing      have leeway working in a high-stress environment

### 

### Non-Functional Requirements

1.  Should work on any mainstream OS as long as it has Java 11 or above
    > installed as cross-platform testing has been carried out

2.  Should be able to hold all details of hospitals max capacity without
    > any noticeable decrease in performance

3.  User with average typing speed will be able to fulfil tasks faster
    > than regular GUI application with mouse

Changed section: saved here just in case

[Features:]{.ul}

1.  Staff

-   Staff.Parser.run() method is called to instantiate a new instance of
    > a command handler for staff related commands

2.  Patient

-   A new PatientCommandInstance object: (patient) is being instantiated
    > with the variable "PATIENT_FILE_PATH" which is a constant that
    > stores the location of patient save data

-   patient.run() is then called to run the instance of command handler
    > for patient related commands

3.  Appointment

-   A new DoctorAppointmentInstance object: (appointments) is being
    > instantiated with the variable "APPOINTMENT_FILE_PATH" which is a
    > constant that stores the location of appointment save data

-   appointment.run() is then called to run the instance of command
    > handler for appointment related commands

4.  Schedule

-   NurseScheduleInstance.main() is called to instantiate a new instance
    > of a command handler for nurse schedule related commands

5.  Inventory

-   A new DrugInstance object: (addict) is being instantiated with the
    > variable "DRUG_FILE_PATH" which is a constant that stores the
    > location of inventory save data

-   addict.run() is then called to run the instance of command handler
    > for inventory related commands

6.  Bye

-   Updates the value of the boolean variable "isExit" to "true".

7.  Help

-   Calls the method UI.printStartMenu to print a list of helpful
    > commands for the user to view.





<!-- Original Template -->
# Developer Guide

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}


## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
