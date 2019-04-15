package com.youngsoft.archcomponentstest.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "WorkoutLog_Table")
public class WorkoutLog {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int date;
    private int workoutTypeCode;
    private int workoutCode;
    private boolean isClimbCode;
    private double weight;
    private int setCount;
    private int repCountPerSet;
    private int repDurationPerSet;
    private int restDurationPerSet;
    private int gradeTypeCode;
    private int gradeCode;
    private int moveCount;
    private int wallAngle;
    private int holdType;
    private boolean isComplete;

    public WorkoutLog(int date, int workoutTypeCode, int workoutCode, boolean isClimbCode, double weight, int setCount, int repCountPerSet, int repDurationPerSet, int restDurationPerSet, int gradeTypeCode, int gradeCode, int moveCount, int wallAngle, int holdType, boolean isComplete) {
        this.date = date;
        this.workoutTypeCode = workoutTypeCode;
        this.workoutCode = workoutCode;
        this.isClimbCode = isClimbCode;
        this.weight = weight;
        this.setCount = setCount;
        this.repCountPerSet = repCountPerSet;
        this.repDurationPerSet = repDurationPerSet;
        this.restDurationPerSet = restDurationPerSet;
        this.gradeTypeCode = gradeTypeCode;
        this.gradeCode = gradeCode;
        this.moveCount = moveCount;
        this.wallAngle = wallAngle;
        this.holdType = holdType;
        this.isComplete = isComplete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public int getWorkoutTypeCode() {
        return workoutTypeCode;
    }

    public int getWorkoutCode() {
        return workoutCode;
    }

    public boolean isClimbCode() {
        return isClimbCode;
    }

    public double getWeight() {
        return weight;
    }

    public int getSetCount() {
        return setCount;
    }

    public int getRepCountPerSet() {
        return repCountPerSet;
    }

    public int getRepDurationPerSet() {
        return repDurationPerSet;
    }

    public int getRestDurationPerSet() {
        return restDurationPerSet;
    }

    public int getGradeTypeCode() {
        return gradeTypeCode;
    }

    public int getGradeCode() {
        return gradeCode;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getWallAngle() {
        return wallAngle;
    }

    public int getHoldType() {
        return holdType;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
