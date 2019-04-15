package com.youngsoft.archcomponentstest.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "WorkoutType_Table")
public class WorkoutType {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;

    public WorkoutType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static WorkoutType[] populateWorkoutTypeData() {
        return new WorkoutType[]{
                new WorkoutType("Strength", "Gym-based exercises performed to increase your strength, i.e. pull-ups, tricep-dips, squats."),
                new WorkoutType("Gym Climb", "Exercises done at the climbing gym, i.e. traverses, lead-climbs, boulders, series."),
                new WorkoutType("Core", "Gym-based exercises performed to increase core-body strength, i.e. planks, leg-raises, sit-ups."),
                new WorkoutType("Hangboard", "Hangboard/Fingerboard exercises for increase finger/crimp strength, i.e. frenchies, 45deg finger-tip hangs, 120deg sloper-hangs."),
                new WorkoutType("Campus", "Campus exercises for increase finger/crimp and upper-body strength, i.e. 1-4-3-4 finger-tips, 1-5-9 slopers."),
                new WorkoutType("Custom", "Any user-created custom exercises."),
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
