package com.youngsoft.archcomponentstest.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "ClimbLog_Table")
public class ClimbLog {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long date;
    private String name;
    private int gradeTypeCode;
    private int gradeCode;
    private int ascentTypeCode;
    private int location;
    private boolean firstAscentCode;
    private boolean isClimbCode;

    public ClimbLog(long date, String name, int gradeTypeCode, int gradeCode, int ascentTypeCode, int location, boolean firstAscentCode, boolean isClimbCode) {
        this.date = date;
        this.name = name;
        this.gradeTypeCode = gradeTypeCode;
        this.gradeCode = gradeCode;
        this.ascentTypeCode = ascentTypeCode;
        this.location = location;
        this.firstAscentCode = firstAscentCode;
        this.isClimbCode = isClimbCode;
    }

    public static ClimbLog[] populateClimbLogData() {
        long tempDate = Calendar.getInstance().getTimeInMillis();
        return new ClimbLog[]{
                new ClimbLog(tempDate - 2 * (24 * 60 * 60000), "Action Directe", 1, 1, 1, 1, true, true),
                new ClimbLog(tempDate - 2 * (24 * 60 * 60000), "Silence", 1, 1, 1, 2, true, true),
                new ClimbLog(tempDate - 1 * (24 * 60 * 60000), "Marina Superstar", 1, 1, 1, 4, true, true),
                new ClimbLog(tempDate - 1 * (24 * 60 * 60000), "La Planta de Shiva", 1, 1, 1, 5, true, true),
                new ClimbLog(tempDate - 0 * (24 * 60 * 60000), "La Dura Dura", 1, 1, 1, 6, true, true),
                new ClimbLog(tempDate - 0 * (24 * 60 * 60000), "Brad Pitt", 1, 1, 1, 7, true, true),
                new ClimbLog(tempDate + 1 * (24 * 60 * 60000), "Golpe de Estado", 1, 1, 1, 8, true, true),
                new ClimbLog(tempDate + 1 * (24 * 60 * 60000), "El Bon Combat", 1, 1, 1, 9, true, true),
                new ClimbLog(tempDate + 2 * (24 * 60 * 60000), "First Round First Minute", 1, 1, 1, 10, true, true)
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getGradeTypeCode() {
        return gradeTypeCode;
    }

    public int getGradeCode() {
        return gradeCode;
    }

    public int getAscentTypeCode() {
        return ascentTypeCode;
    }

    public int getLocation() {
        return location;
    }

    public boolean getFirstAscentCode() {
        return firstAscentCode;
    }

    public boolean getIsClimbCode() {
        return isClimbCode;
    }
}
