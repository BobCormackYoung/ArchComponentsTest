package com.youngsoft.archcomponentstest.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "GradeType_Table")
public class GradeType {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String gradeTypeName;

    public GradeType(String gradeTypeName) {
        this.gradeTypeName = gradeTypeName;
    }

    public static GradeType[] populateGradeTypeData() {
        return new GradeType[]{
                new GradeType("French (Boulder)"),
                new GradeType("British (Boulder)"),
                new GradeType("V Grade (Boulder)"),
                new GradeType("French (Lead)"),
                new GradeType("British Trad (Lead)"),
                new GradeType("UIAA (Lead)"),
                new GradeType("Yosemite Decimal (Lead)"),
                new GradeType("Norwegian"),
                new GradeType("Subjective Grade"),
                new GradeType("Polish (Krakowska)"),
                new GradeType("Polish (Tatrzańska)"),
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGradeTypeName() {
        return gradeTypeName;
    }
}
