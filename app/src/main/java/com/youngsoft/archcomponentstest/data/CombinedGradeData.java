package com.youngsoft.archcomponentstest.data;

public class CombinedGradeData {

    GradeList gradeList;
    GradeType gradeType;

    public CombinedGradeData(GradeType gradeType) {
        this.gradeType = gradeType;
    }

    public CombinedGradeData(GradeType gradeType, GradeList gradeList) {
        this.gradeType = gradeType;
        this.gradeList = gradeList;
    }

    public GradeList getGradeList() {
        return gradeList;
    }

    public void setGradeList(GradeList gradeList) {
        this.gradeList = gradeList;
    }

    public GradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }
}
