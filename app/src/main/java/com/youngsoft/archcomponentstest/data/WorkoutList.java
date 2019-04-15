package com.youngsoft.archcomponentstest.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "WorkoutList_Table")
public class WorkoutList {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String workoutName;
    private int workoutTypeCode;
    private String description;
    private boolean isClimbCode;
    private boolean isWeight;
    private boolean isSetCount;
    private boolean isRepCountPerSet;
    private boolean isRepDurationPerSet;
    private boolean isRestDurationPerSet;
    private boolean isGradeCode;
    private boolean isMoveCount;
    private boolean isWallAngle;
    private boolean isHoldType;

    public WorkoutList(String workoutName, int workoutTypeCode, String description, boolean isClimbCode, boolean isWeight, boolean isSetCount, boolean isRepCountPerSet, boolean isRepDurationPerSet, boolean isRestDurationPerSet, boolean isGradeCode, boolean isMoveCount, boolean isWallAngle, boolean isHoldType) {
        this.workoutName = workoutName;
        this.workoutTypeCode = workoutTypeCode;
        this.description = description;
        this.isClimbCode = isClimbCode;
        this.isWeight = isWeight;
        this.isSetCount = isSetCount;
        this.isRepCountPerSet = isRepCountPerSet;
        this.isRepDurationPerSet = isRepDurationPerSet;
        this.isRestDurationPerSet = isRestDurationPerSet;
        this.isGradeCode = isGradeCode;
        this.isMoveCount = isMoveCount;
        this.isWallAngle = isWallAngle;
        this.isHoldType = isHoldType;
    }

    public static WorkoutList[] populateWorkoutListData() {
        return new WorkoutList[]{
                new WorkoutList("Pull-Ups", 1, "Description", false, true, true, true, false, true, false, false, false, false),
                new WorkoutList("Squats", 1, "Description", false, true, true, true, false, true, false, false, false, false),
                new WorkoutList("Push-Ups", 1, "Description", false, true, true, true, false, true, false, false, false, false),
                new WorkoutList("Shoulder Press", 1, "Description", false, true, true, true, false, true, false, false, false, false),
                new WorkoutList("Traverse - indoor", 2, "An single indoor climb with a high number of moves, usually performed without a rope, in bouldering style, with a set number of moves and certain grade.", true, true, false, false, false, false, true, true, true, true),
                new WorkoutList("Bouldering - indoor", 2, "An indoor bouldering route of a certain grade.", true, true, false, false, false, false, true, true, true, true),
                new WorkoutList("Lead Climbing - indoor", 2, "A single indoor lead-climb of a certain grade.", true, true, false, false, false, false, true, true, true, true),
                new WorkoutList("Top-Rope Climbing - indoor", 2, "A single indoor top-roped climb of a certain grade.", true, true, false, false, false, false, true, true, true, true),
                new WorkoutList("Interval Climb - Lead", 2, "A single indoor lead climb, of a certain grade, repeated multiple times with a fixed rest-period.", true, true, true, false, false, true, true, true, true, true),
                new WorkoutList("Interval Climb - Bouldering", 2, "A single indoor bouldering climb, of a certain grade, repeated multiple times with a fixed rest-period.", true, true, true, false, false, true, true, true, true, true),
                new WorkoutList("Plank", 3, "Description", false, false, true, false, true, true, false, false, false, false),
                new WorkoutList("Front Lever", 3, "Description", false, false, true, true, true, true, false, false, false, false),
                new WorkoutList("Sit-Up", 3, "Description", false, false, true, true, false, true, false, false, false, false),
                new WorkoutList("90deg Bent-Arm Hang", 4, "Description", false, true, true, true, true, true, false, false, false, false),
                new WorkoutList("Custom Something", 5, "Description", false, true, true, true, true, true, false, false, false, false),
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public int getWorkoutTypeCode() {
        return workoutTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public boolean isClimbCode() {
        return isClimbCode;
    }

    public boolean isWeight() {
        return isWeight;
    }

    public boolean isSetCount() {
        return isSetCount;
    }

    public boolean isRepCountPerSet() {
        return isRepCountPerSet;
    }

    public boolean isRepDurationPerSet() {
        return isRepDurationPerSet;
    }

    public boolean isRestDurationPerSet() {
        return isRestDurationPerSet;
    }

    public boolean isGradeCode() {
        return isGradeCode;
    }

    public boolean isMoveCount() {
        return isMoveCount;
    }

    public boolean isWallAngle() {
        return isWallAngle;
    }

    public boolean isHoldType() {
        return isHoldType;
    }
}
