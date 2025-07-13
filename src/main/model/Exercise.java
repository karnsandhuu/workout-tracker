package model;

// This represents an exercise done during a workout
// includes information such as exercise name, reps, sets and weight
public class Exercise {

    private String name;
    private int reps;
    private int sets;
    private int weight;

    //REQUIRES: Sets, reps, and weight are >= 0 and name is not a null value
    //EFFECTS: Constructs an exercise with given name, reps, sets and weight
    public Exercise(String name, int reps, int sets, int weight) {
        //
    }

    //EFFECTS: returns the name value
    public String getName() {
        return null;
    }

    //EFFECTS: returns the amount of reps
    public int getReps() {
        return 0;
    }

     //EFFECTS: returns the amount of sets
    public int getSets() {
        return 0;
    }

    //EFFECTS: returns the weight used
    public int getWeight() {
        return 0;
    }
}
