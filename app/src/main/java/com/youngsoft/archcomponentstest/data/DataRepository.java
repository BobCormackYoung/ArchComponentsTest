package com.youngsoft.archcomponentstest.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class DataRepository {

    private DataDao dataDao;
    private LiveData<List<AscentType>> allAscentTypes;
    private LiveData<List<GradeType>> allGradeTypes;
    private LiveData<List<GradeList>> allGradeLists;
    private LiveData<List<GradeList>> subsetGradeLists;
    private LiveData<List<LocationList>> allLocationLists;
    //private LiveData<List<CalendarTracker>> calendarTrackerBetweenDates;
    private LiveData<List<CalendarTracker>> allCalendarTracker;

    public DataRepository(Application application) {
        Log.i("DataRepository", "in the constructor");
        DataDatabase database = DataDatabase.getInstance(application);
        dataDao = database.dataDao();
        allAscentTypes = dataDao.getAllAscents();
        allGradeTypes = dataDao.getAllGradeTypes();
        allGradeLists = dataDao.getAllGradeLists();
        allLocationLists = dataDao.getAllLocationLists();
        //calendarTrackerBetweenDates = dataDao.getCalendarTrackerBetweenDates(Double firstDate, Double lastDate);
    }


    //Get all table data
    public LiveData<List<LocationList>> getAllLocationLists() {
        return allLocationLists;
    }

    public LiveData<List<AscentType>> getAllAscentTypes() {
        return allAscentTypes;
    }

    public LiveData<List<GradeType>> getAllGradeTypes() {
        return allGradeTypes;
    }

    public LiveData<List<GradeList>> getAllGrades() {
        return allGradeLists;
    }

    //Get calendar tracker between dates
    public LiveData<List<CalendarTracker>> getCalendarTrackerBetweenDates(long firstDate, long lastDate) {
        Log.i("DataRepository", "in getCalendarTrackerBetweenDates");
        LiveData<List<CalendarTracker>> calendarTrackerBetweenDates = dataDao.getCalendarTrackerBetweenDates(firstDate, lastDate);
        return calendarTrackerBetweenDates;
    }

    //Get subset grade list
    public LiveData<List<GradeList>> getSubsetGradeLists(int index) {
        subsetGradeLists = dataDao.getSubsetGradeLists(index);
        return subsetGradeLists;
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

    public String getAscentTypeClimb(int ascentTypeCode) {
        return dataDao.getAscentType(ascentTypeCode);
    }

    public LiveData<LocationList> getLocation(int locationId) {
        return dataDao.getLocation(locationId);
    }

    public void updateClimbLogName(String newName, int rowId, int caseId) {
        ClimbLogData climbLogData = new ClimbLogData(newName, rowId, caseId);
        new UpdateNoteAsyncTask(dataDao).execute(climbLogData);
    }

    public void updateClimbLogGrade(int gradeTypeCode, int gradeCode, int rowId, int caseId) {
        ClimbLogData climbLogData = new ClimbLogData(gradeTypeCode, gradeCode, rowId, caseId);
        new UpdateNoteAsyncTask(dataDao).execute(climbLogData);
    }

    public void updateClimbLogAscentType(int ascentType, int rowId, int caseId) {
        ClimbLogData climbLogData = new ClimbLogData(ascentType, rowId, caseId);
        new UpdateNoteAsyncTask(dataDao).execute(climbLogData);
    }

    public void updateClimbLogFirstAscent(boolean firstAscent, int rowId, int caseId) {
        ClimbLogData climbLogData = new ClimbLogData(firstAscent, rowId, caseId);
        new UpdateNoteAsyncTask(dataDao).execute(climbLogData);
    }

    public void updateClimbLogLocation(int location, int rowId, int caseId) {

    }


    private static class UpdateNoteAsyncTask extends AsyncTask<ClimbLogData, Void, Void> {
        private DataDao dataDao;

        private UpdateNoteAsyncTask(DataDao dataDao) {
            this.dataDao = dataDao;
        }

        @Override
        protected Void doInBackground(ClimbLogData... climbLogData) {
            switch (climbLogData[0].caseId) {
                case 1:
                    dataDao.update(climbLogData[0].name, climbLogData[0].id);
                    break;
                case 2:
                    dataDao.update(climbLogData[0].gradeTypeCode, climbLogData[0].gradeCode, climbLogData[0].id);
                    break;
                case 3:
                    dataDao.update(climbLogData[0].ascentTypeCode, climbLogData[0].id);
                    break;
                case 4:
                    dataDao.update(climbLogData[0].firstAscentCode, climbLogData[0].id);
                    break;
                case 5:
                    break;
            }

            return null;
        }
    }

    private class ClimbLogData {
        int id;
        String name;
        int gradeTypeCode;
        int gradeCode;
        int ascentTypeCode;
        boolean firstAscentCode;
        int caseId;

        ClimbLogData(String name, int id, int caseId) {
            this.name = name;
            this.id = id;
            this.caseId = caseId;
        }

        ClimbLogData(int gradeTypeCode, int gradeCode, int id, int caseId) {
            this.gradeTypeCode = gradeTypeCode;
            this.gradeCode = gradeCode;
            this.id = id;
            this.caseId = caseId;
        }

        ClimbLogData(int ascentTypeCode, int id, int caseId) {
            this.ascentTypeCode = ascentTypeCode;
            this.id = id;
            this.caseId = caseId;
        }

        ClimbLogData(boolean firstAscentCode, int id, int caseId) {
            this.firstAscentCode = firstAscentCode;
            this.id = id;
            this.caseId = caseId;
        }
    }
}
