package com.youngsoft.archcomponentstest;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.youngsoft.archcomponentstest.data.AscentType;
import com.youngsoft.archcomponentstest.data.CalendarTracker;
import com.youngsoft.archcomponentstest.data.ClimbLog;
import com.youngsoft.archcomponentstest.data.DataRepository;
import com.youngsoft.archcomponentstest.data.GradeList;
import com.youngsoft.archcomponentstest.data.GradeType;
import com.youngsoft.archcomponentstest.data.HoldType;
import com.youngsoft.archcomponentstest.data.LocationList;
import com.youngsoft.archcomponentstest.data.WorkoutList;
import com.youngsoft.archcomponentstest.data.WorkoutLog;
import com.youngsoft.archcomponentstest.data.WorkoutType;

import java.util.List;

public class ViewModelDatabase extends AndroidViewModel {

    private DataRepository dataRepository;
    private LiveData<List<AscentType>> allAscentTypes;
    private LiveData<List<CalendarTracker>> allCalendarTracker;
    private LiveData<List<ClimbLog>> allClimbLog;
    private LiveData<List<GradeList>> allGradeList;
    private LiveData<List<GradeType>> allGradeType;
    private LiveData<List<HoldType>> allHoldType;
    private LiveData<List<LocationList>> allLocationList;
    private LiveData<List<WorkoutList>> allWorkoutList;
    private LiveData<List<WorkoutLog>> allWorkoutLog;
    private LiveData<List<WorkoutType>> allWorkoutType;

    public ViewModelDatabase(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        allAscentTypes = dataRepository.getAllAscentTypes();
        allLocationList = dataRepository.getAllLocationLists();
    }

    public DataRepository getDataRepository() {
        return dataRepository;
    }

    public LiveData<List<AscentType>> getAllAscentTypes() {
        return allAscentTypes;
    }

    public LiveData<List<CalendarTracker>> getAllCalendarTracker() {
        return allCalendarTracker;
    }

    public LiveData<List<ClimbLog>> getAllClimbLog() {
        return allClimbLog;
    }

    public LiveData<List<GradeList>> getAllGradeList() {
        return allGradeList;
    }

    public LiveData<List<GradeType>> getAllGradeType() {
        return allGradeType;
    }

    public LiveData<List<HoldType>> getAllHoldType() {
        return allHoldType;
    }

    public LiveData<List<LocationList>> getAllLocationList() {
        return allLocationList;
    }

    public LiveData<List<WorkoutList>> getAllWorkoutList() {
        return allWorkoutList;
    }

    public LiveData<List<WorkoutLog>> getAllWorkoutLog() {
        return allWorkoutLog;
    }

    public LiveData<List<WorkoutType>> getAllWorkoutType() {
        return allWorkoutType;
    }

}
