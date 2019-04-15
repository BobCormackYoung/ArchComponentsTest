package com.youngsoft.archcomponentstest.LogBookModule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.youngsoft.archcomponentstest.UtilModule.TimeUtils;

import java.util.Calendar;

public class ViewModelLogBook extends AndroidViewModel {

    Calendar currentDate = null;
    boolean isDate = false;
    boolean isNewClimb = true;
    boolean isNewWorkout = true;
    int addClimbRowId;
    int addWorkoutRowId;
    long addClimbDate;

    public ViewModelLogBook(@NonNull Application application) {
        super(application);
    }

    public Calendar getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Calendar date) {
        Log.i("ViewModelLogBook", "setCurrentDate: " + TimeUtils.convertDate(date.getTimeInMillis(), "yyyy-MM-dd"));
        currentDate = date;
        setIsDateTrue();
    }

    public void setIsDateFalse() {
        isDate = false;
    }

    public void setIsDateTrue() {
        isDate = true;
    }

    public boolean getIsDate() {
        return isDate;
    }

    public void setIsNewClimbTrue() {
        isNewClimb = true;
    }

    public void setIsNewClimbFalse() {
        isNewClimb = false;
    }

    public boolean getIsNewClimb() {
        return isNewClimb;
    }

    public void setIsNewWorkoutTrue() {
        isNewWorkout = true;
    }

    public void setIsNewWorkoutFalse() {
        isNewWorkout = false;
    }

    public boolean getIsNewWorkout() {
        return isNewClimb;
    }

    public int getAddClimbRowId() {
        return addClimbRowId;
    }

    public void setAddClimbRowId(int input) {
        addClimbRowId = input;
    }

    public int getAddWorkoutRowId() {
        return addClimbRowId;
    }

    public void setAddWorkoutRowId(int input) {
        addClimbRowId = input;
    }

    public long getAddClimbDate() {
        return addClimbDate;
    }

    public void setAddClimbDate(long input) {
        addClimbDate = input;
    }

    public void resetData() {
        currentDate = null;
        setIsDateFalse();
        setIsNewClimbTrue();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("ViewModelLogBook", "onCleared");
    }
}
