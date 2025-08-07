# My Personal Project

## Workout Tracker & Progress Planner

What I’ve decided to do for my term project is a **Workout Tracker and Progress Planner**.

### What will the application do?
- Help users create and manage their workout routines
- Allow users to build workouts by adding their own exercises
- Analyze personal records and track progress over time
- Plan and customize future workouts

Some ***features*** include:
- Tracking dates of workouts
- Recording rep ranges, sets, and weight used
- Saving and loading progress for personal review

### Who is this application for?
This app is designed for everyone, from students and beginners to experienced lifters and fitness influencers who want a simple and easy workout logger.

### Why this project is an interest of mine?
This project interests me because I recently started my own working out journey. Most apps I’ve tried are either filled with unnecessary ads or too complicated. When it came to just using the notes app, it feels tedious and not structured how I'd want it. I want to build something simple and offline to track my training.

## User Stories
- As a user, I want to be able to add an exercise to my workout session, including details such as the date, number of sets, reps done, etc.
- As a user, I want to be able to view a list of all my past workout sessions along with their dates.
- As a user, I want to be able to analyze my progress by viewing my personal records for each exercise.
- As a user, I want to search for an exercise name and see which workout sessions included it.
- As a user, I want to set a target PR for a specific exercise.
- As a user, I want to see the exercise I perform most frequently.
- As a user, I want to be able to edit the details of my past workout sessions (including things like reps, sets, weight used, etc.)
- As a user, I want the option to save my workout log to file so I can keep the history of my workouts.
- As a user, I want the option to load my workout log from file so I can view previous workouts or continue them.\

## Instructions for End User
- You can generate the first required action related to the user story "adding multiple Exercises to a WorkoutSession" by clicking "Add Exercise" in the "Add Workout" tab.
- You can generate the second required action by clicking "Show Exercises" in the "Workout Report" tab to view all exercises per session.
- You can locate my visual component (a dumbbell image) on the "Add Workout" tab below the input fields.
- You can search for all workout dates where a specific exercise was performed using the "Search Dates" button in the "Workout Report" tab.
- You can save the state of my application by clicking the "Save Log" button in the "Load/Save" tab.
- You can reload the state of my application by clicking the "Load Log" button in the "Load/Save" tab.
- You can clear the current workout log by clicking the "Clear Log" button in the "Load/Save" tab.

## Phase 4: Task 2

Event Log:
Wed Aug 06 23:46:08 PDT 2025
Workout session added: 07/12/25
Wed Aug 06 23:46:08 PDT 2025
Exercise added to 07/12/25: Bench Press (3 sets, 10 reps, 135 lbs)
Wed Aug 06 23:46:13 PDT 2025
Searched for sessions with exercise: Bench Press
Wed Aug 06 23:46:15 PDT 2025
Workout log cleared

## Phase 4: Task 3

My UML diagram shows the final design of my workout tracker app. It includes all the main classes I used, like WorkoutLog, WorkoutSession, Exercise, and the GUI. It also shows the Json classes for saving and loading data, and the event logging classes. The relationships are clear and simple. For example, one workout log has a list of 0 to many sessions, and each session has a list of 0 to many exercises, etc.

If I had more time, I would clean up the code by making the GUI class smaller. Right now, the WorkoutTrackerGUI class does too many things all in one place, like setting up the screen, handling user input, showing reports, and saving or loading data. That makes it a bit messy and harder to understand. A better way would be to split it into smaller classes, like one part for handling what the user types in, another part for saving and loading data, and another for showing the workout report. This would make the code easier to read, easier to fix if something breaks, and easier to change in the future.