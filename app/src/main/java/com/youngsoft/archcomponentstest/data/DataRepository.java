package com.youngsoft.archcomponentstest.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

public class DataRepository {

    private DataDao dataDao;
    private LiveData<List<AscentType>> allAscentTypes;
    private LiveData<List<LocationList>> allLocationLists;
    //private LiveData<List<CalendarTracker>> calendarTrackerBetweenDates;
    private LiveData<List<CalendarTracker>> allCalendarTracker;

    public DataRepository(Application application) {
        Log.i("DataRepository", "in the constructor");
        DataDatabase database = DataDatabase.getInstance(application);
        dataDao = database.dataDao();
        allAscentTypes = dataDao.getAllAscents();
        allLocationLists = dataDao.getAllLocationLists();
        //calendarTrackerBetweenDates = dataDao.getCalendarTrackerBetweenDates(Double firstDate, Double lastDate);
    }

    public LiveData<List<LocationList>> getAllLocationLists() {
        return allLocationLists;
    }

    public LiveData<List<AscentType>> getAllAscentTypes() {
        return allAscentTypes;
    }

    public LiveData<List<CalendarTracker>> getCalendarTrackerBetweenDates(long firstDate, long lastDate) {
        Log.i("DataRepository", "in getCalendarTrackerBetweenDates");
        LiveData<List<CalendarTracker>> calendarTrackerBetweenDates = dataDao.getCalendarTrackerBetweenDates(firstDate, lastDate);
        return calendarTrackerBetweenDates;
    }

    public LiveData<List<CalendarTracker>> getAllCalendarTracker() {
        Log.i("DataRepository", "in getAllCalendarTracker");
        allCalendarTracker = dataDao.getAllCalendarTracker();
        return allCalendarTracker;
    }

    public LiveData<ClimbLog> getClimbLog(int rowId) {
        Log.i("DataRepository", "getClimbLog " + rowId);
        LiveData<ClimbLog> climbLogLiveData = dataDao.getClimbLog(rowId);
        return climbLogLiveData;
    }

    public WorkoutLog getWorkoutLog(int rowId) {
        Log.i("DataRepository", "getWorkoutLog " + rowId);
        return dataDao.getWorkoutLog(rowId);
    }

    public String getGradeTypeClimb(int gradeTypeCode) {
        return dataDao.getGradeType(gradeTypeCode);
    }

    public String getGradeTextClimb(int gradeCode) {
        return dataDao.getGradeName(gradeCode);
    }

    public LiveData<LocationList> getLocation(int locationId) {
        return dataDao.getLocation(locationId);
    }
}
