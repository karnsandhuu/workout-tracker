package model;

import org.json.JSONObject;

// This represents an exercise done during a workout
// includes information such as exercise name, reps, sets and weight
public class Exercise {

    private String name;
    private int reps;
    private int sets;
    private int weight;

    // REQUIRES: Sets, reps, and weight are >= 0 and name is not a null value
    // EFFECTS: Constructs an exercise with given name, reps, sets and weight
    public Exercise(String name, int reps, int sets, int weight) {
        this.name = name;
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
    }

    // EFFECTS: returns the name value
    public String getName() {
        return name;
    }

    // EFFECTS: returns the amount of reps
    public int getReps() {
        return reps;
    }

    // EFFECTS: returns the amount of sets
    public int getSets() {
        return sets;
    }

    // EFFECTS: returns the weight used
    public int getWeight() {
        return weight;
    }

    // EFFECTS: returns this exercise as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("reps", reps);
        json.put("sets", sets);
        json.put("weight", weight);
        return json;
    }
}
