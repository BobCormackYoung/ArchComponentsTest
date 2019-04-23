package com.youngsoft.archcomponentstest.LogBookModule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.youngsoft.archcomponentstest.UtilModule.TimeUtils;

import java.util.Calendar;

public class ViewModelLogBook extends AndroidViewModel {

    private MutableLiveData<Calendar> currentDate = new MutableLiveData<>();
    private MutableLiveData<Integer> currentPosition = new MutableLiveData<>();

    boolean isDate = false;
    boolean isNewClimb = true;
    boolean isNewWorkout = true;
    int addClimbRowId;
    int addWorkoutRowId;
    long addClimbDate;

    public ViewModelLogBook(@NonNull Application application) {
        super(application);
        currentDate.setValue(Calendar.getInstance());
        currentPosition.setValue(TimeUtils.getPositionForDay(Calendar.getInstance()));
    }

    public LiveData<Calendar> getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Calendar date) {
        Log.i("ViewModelLogBook", "setCurrentDate: " + TimeUtils.convertDate(date.getTimeInMillis(), "yyyy-MM-dd"));
        currentDate.setValue(date);
        currentPosition.setValue(TimeUtils.getPositionForDay(date));
        setIsDateTrue();
    }

    public LiveData<Integer> getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int position) {
        currentPosition.setValue(position);
    }

    public void setIsDateFalse() {
        isDate = false;
    }

    public void setIsDateTrue() {
        isDate = true;
    }

    public void setIsNewClimbTrue() {
        isNewClimb = true;
    }

    public void setAddClimbRowId(int input) {
        addClimbRowId = input;
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
