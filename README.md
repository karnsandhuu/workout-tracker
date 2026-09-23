# Workout Tracker & Progress Planner

A Java desktop application designed to help users log workouts, track exercise history, and monitor their progress over time.

I built this project to create a simple, offline alternative to workout apps that can feel overly complicated or cluttered. The application allows users to organize workout sessions, record exercise details, search previous workouts, and save their workout history for future use.

## Features

- Create and manage workout sessions
- Add multiple exercises to each workout
- Record:
  - Exercise name
  - Sets
  - Reps
  - Weight
  - Workout date
- View previous workout sessions
- Search workout history by exercise name
- View dates when a specific exercise was performed
- Track personal records for exercises
- Set target personal records
- Edit previously recorded workouts
- Save workout history to a file
- Load saved workout history
- Clear the current workout log

## Tech Stack

- **Java**
- **Object-Oriented Programming**
- **Java GUI**
- **JSON**
- **File-based data persistence**
- **Git / GitHub**

## Application Structure

The application follows an object-oriented design built around several core components:

- `WorkoutLog` — stores and manages workout sessions
- `WorkoutSession` — represents an individual workout
- `Exercise` — stores information such as sets, reps, and weight
- GUI — allows users to interact with the workout tracker
- JSON reader/writer classes — handle saving and loading workout data
- Event logging — records important actions performed within the application

A workout log can contain multiple workout sessions, and each workout session can contain multiple exercises.

## Saving and Loading Data

Workout information can be saved locally so users do not lose their training history after closing the application.

Users can:

- Save their current workout log
- Reload previously saved workouts
- Continue adding to their existing workout history
- Clear the current workout log when needed

Workout data is stored using JSON.

## Example Workflow

1. Create a workout session and enter the workout date.
2. Add exercises to the session.
3. Record sets, reps, and weight for each exercise.
4. View previously recorded workouts.
5. Search for an exercise to see when it was performed.
6. Save the workout log.
7. Reload the saved data during a future session.

## Design Improvements

One area I would improve in a future version is the structure of the GUI.

Currently, the main GUI class is responsible for several tasks, including:

- Displaying the interface
- Handling user input
- Generating workout reports
- Saving and loading data

I would refactor these responsibilities into smaller components to improve readability, maintainability, and scalability.

## Future Improvements

Potential features I would like to add include:

- Progress charts for exercises over time
- Workout statistics and analytics
- Improved personal record tracking
- Exercise categories and muscle groups
- User profiles
- Improved interface design
- More advanced workout planning tools

## Project Purpose

This project was created to strengthen my understanding of:

- Object-oriented software design
- Java application development
- GUI development
- Data persistence
- JSON serialization
- Class relationships and application architecture
