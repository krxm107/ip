# BroBot User Guide

Hello, welcome to the BroBot user guide, where BroBot (pictured below) will tell you all about them!

![](../src/main/resources/images/Mascot.png)

BroBot is a task manager app for those who prefer typing and do not like digging through deep menus.

They can be your Bro BroBot if you let them!

Below is an example of how the BroBot UI looks like:

![](Ui.png)


## Adding Tasks

### Adding ToDo Tasks
Adds a simple named task to the tasklist.

Format: todo TASK_NAME

Example: `todo CS1234 Lab 1`

```
Got it. I've added this task:
    1. [T][ ] CS1234 Lab 1
Your tasks have successfully been saved to the hard drive.
Now you have 1 tasks in the list.
```

### Adding Deadline Tasks
Adds a task that must be done by a certain date.

Format:
deadline TASK_NAME by DEADLINE_DATE

Example (3-letter month): `deadline CS Project by 18 Oct 2025`
```
Got it. I've added this task:
    2. [D][ ] CS Project (by: 18 Oct 2025)
Your tasks have successfully been saved to the hard drive.
Now you have 2 tasks in the list.
```
Example (Full-name month): `deadline CS Project by 18 October 2025`
```
Got it. I've added this task:
    3. [D][] CS Project (by: 18 Oct 2025)
Your tasks have successfully been saved to the hard drive.
Now you have 3 tasks in the list. 
```

### Adding Event Tasks
Adds a task that starts at a certain date and ends at a certain date.

Format: event EVENT_NAME from START_DATE to END_DATE

Note that for dates in BroBot, users may input 3-letter months or full-name months, just like for Deadline Tasks. 

However, all dates will be displayed to the user by BroBot in 3-letter month format, just like for Deadline Tasks.

See the example below for clarification.

Example: `event project meeting from 17 November 2025 to 24 Nov 2025`

```
Got it. I've added this task:
    4. [E][ ] project meeting (from: 17 Nov 2025 to: 24 Nov 2025)
Your tasks have successfully been saved to the hard drive.
Now you have 4 tasks in the list.
```

## Deleting tasks
Deletes selected tasks by position. Note that this works like a command line form of the "select items to delete" menu in GUI apps.

Note that all indices and positions in BroBot are 1-indexed for ease of use.

Example command formats:

1. delete

    No tasks are deleted.
    ```
    Noted. I've removed these tasks:
    Your tasks have successfully been saved to the hard drive.
    Now you have 4 tasks in the list.
    ```
   
2. delete TASK_INDEX_1 TASK_INDEX_2 ... TASK_INDEX_N
    
    Deletes the tasks with the selected indices in the input (1-indexed).

    For example, "delete 1 2 4" deletes the 1st, 2nd and 4th tasks
    ```
    Noted. I've removed these tasks:
    4. [E][ ] Project Meeting (from: 17 Nov 2025 to: 24 Nov 2025)
    2. [D][ ] CS Project (by: 18 Oct 2025)
    1. [T][ ] CS1234 Lab 1
    Your tasks have successfully been saved to the hard drive.
    Now you have 1 tasks in the list.
    ```

3. delete all

    Deletes ALL the tasks in the list.
    ```
    Noted. I've removed these tasks:
    1. [D][ ] CS Project (by: 18 Oct 2025)
    Your tasks have successfully been saved to the hard drive.
    Now you have 0 tasks in the list.
    ```

## Find tasks by keyword
Find tasks whose task description contains the given keyword. The keyword can be in uppercase or lowercase.

Command Format: find KEYWORD

As a demonstration, populate the tasklist with tasks by entering the following commands:
```
todo biology MEETING at 3pm
deadline chemistry meEting by 15 Jun 2026
event physics meeting from 17 Jul 2026 to 19 Jul 2026
todo biology homework at 9pm
```

Then enter the following command
```
find meeting
```

The output will be as follows
```
1. [T][ ] biology MEETING at 3pm
2. [D][ ] chemistry meEting (by: 15 Jun 2026)
3. [E][ ] physics meeting (from: 17 Jul 2026 to: 19 Jul 2026)
```

## Inspect the task list

To inspect the task list at a current point in time, enter the following command:
```
list
```

An example output is
```
1. [T][ ] biology MEETING at 3pm
2. [D][ ] chemistry meEting (by: 15 Jun 2026)
3. [E][ ] physics meeting (from: 17 Jul 2026 to: 19 Jul 2026)
4. [T][ ] biology homework at 9pm
```

## Mark tasks as done

Marks selected tasks as done by position. Note that this works like a command line form of the "select items to mark" menu in GUI apps.

Note that all indices and positions in BroBot are 1-indexed for ease of use.

Example command formats:

1. mark

   No tasks are marked as done.
    ```
    Nice! I've marked these tasks as done:
    Your tasks have successfully been saved to the hard drive.
    Now you have 4 tasks in the list.
    ```

2. mark TASK_INDEX_1 TASK_INDEX_2 ... TASK_INDEX_N

   Marks the tasks with the selected indices in the input (1-indexed).

   For example, "mark 1 2 4" marks the 1st, 2nd and 4th tasks
    ```
    Nice! I've marked these tasks as done:
        4. [T][X] biology homework at 9pm
        2. [D][X] chemistry meEting (by: 15 Jun 2026)
        1. [T][X] biology MEETING at 3pm
    Your tasks have successfully been saved to the hard drive.
    Now you have 4 tasks in the list.
    ```

3. mark all

   Marks ALL the tasks in the list.
    ```
    Nice! I've marked these tasks as done:
        4. [T][X] biology homework at 9pm
        3. [E][X] physics meeting (from: 17 Jul 2026 to: 19 Jul 2026)
        2. [D][X] chemistry meEting (by: 15 Jun 2026)
        1. [T][X] biology MEETING at 3pm
    Your tasks have successfully been saved to the hard drive.
    Now you have 4 tasks in the list.
    ```

## Unmark tasks as not done yet

Unmarks selected tasks as not done yet by position. Note that this works like a command line form of the "select items" menu in GUI apps.

Note that all indices and positions in BroBot are 1-indexed for ease of use.

Example command formats:

1. unmark

   No tasks are unmarked as not done yet.
    ```
    OK, I've unmarked these tasks as not done yet:
    Your tasks have successfully been saved to the hard drive.
    Now you have 4 tasks in the list.
    ```

2. unmark TASK_INDEX_1 TASK_INDEX_2 ... TASK_INDEX_N

   unmarks the tasks with the selected indices in the input (1-indexed).

   For example, "unmark 1 2 4" unmarks the 1st, 2nd and 4th tasks
    ```
    OK, I've unmarked these tasks as not done yet:
        4. [T][ ] biology homework at 9pm
        2. [D][ ] chemistry meEting (by: 15 Jun 2026)
        1. [T][ ] biology MEETING at 3pm
    Your tasks have successfully been saved to the hard drive.
    Now you have 4 tasks in the list.
    ```

3. unmark all

   unmarks ALL the tasks in the list.
    ```
    OK, I've unmarked these tasks as not done yet:
        4. [T][ ] biology homework at 9pm
        3. [E][ ] physics meeting (from: 17 Jul 2026 to: 19 Jul 2026)
        2. [D][ ] chemistry meEting (by: 15 Jun 2026)
        1. [T][ ] biology MEETING at 3pm
    Your tasks have successfully been saved to the hard drive.
    Now you have 4 tasks in the list.
    ```
   
## Wrapping up

Ok, that's all the commands of BroBot. It's time to close the app.

To do so, either close the app window, or key in the command "bye" into BroBot.

You may see the following message
```
Bye. Hope to see you again soon!
```

If you're planning to come back and see your tasks, don't worry. The tasks are saved to the hard drive every time your task list is modified.

This is BroBot, signing out.

![](../src/main/resources/images/Mascot.png)
