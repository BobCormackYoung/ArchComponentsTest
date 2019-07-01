package com.youngsoft.archcomponentstest.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DataDao {

    // Inserting multiple items for each type
    @Insert
    void insertMultipleGradeList(GradeList... gradeLists);

    @Insert
    void insertMultipleAscentType(AscentType... ascentTypes);

    @Insert
    void insertMultipleGradeType(GradeType... gradeTypes);

    @Insert
    void insertMultipleHoldType(HoldType... holdTypes);

    @Insert
    void insertMultipleLocationList(LocationList... locationLists);

    @Insert
    void insertMultipleWorkoutList(WorkoutList... workoutLists);

    @Insert
    void insertMultipleWorkoutType(WorkoutType... workoutTypes);

    @Insert
    void insertMultipleClimbLog(ClimbLog... climbLogs);

    @Insert
    void insertNewLocation(LocationList locationList);

    @Insert
    void insertMultipleCalendarTracker(CalendarTracker... calendarTrackers);


    //Select all items from a specific type
    @Query("SELECT * FROM AscentType_Table ORDER BY id DESC")
    LiveData<List<AscentType>> getAllAscents();

    @Query("SELECT * FROM LocationList_Table ORDER BY id DESC")
    LiveData<List<LocationList>> getAllLocationLists();

    @Query("SELECT * FROM GradeType_Table ORDER BY id DESC")
    LiveData<List<GradeType>> getAllGradeTypes();

    @Query("SELECT * FROM GradeList_Table ORDER BY id DESC")
    LiveData<List<GradeList>> getAllGradeLists();

    @Query("SELECT * FROM CalendarTracker_Table ORDER BY date DESC")
    LiveData<List<CalendarTracker>> getAllCalendarTracker();

    //Select calendar tracker info between dates
    @Query("SELECT * FROM CalendarTracker_Table WHERE date BETWEEN :firstDate AND :lastDate")
    LiveData<List<CalendarTracker>> getCalendarTrackerBetweenDates(long firstDate, long lastDate);

    //Select specific gradeList data
    @Query("SELECT * FROM GradeList_Table WHERE gradeTypeCode=:index ORDER BY id DESC")
    LiveData<List<GradeList>> getSubsetGradeLists(int index);

    //Select specific climbLog entry
    @Query("SELECT * FROM ClimbLog_Table WHERE id = :rowId")
    LiveData<ClimbLog> getClimbLog(int rowId);

    //Select specific workoutLog entry
    @Query("SELECT * FROM WorkoutLog_Table WHERE id = :rowId")
    WorkoutLog getWorkoutLog(int rowId);

    @Query("SELECT gradeTypeName FROM GradeType_Table WHERE id = :rowId")
    String getGradeType(int rowId);

    @Query("SELECT gradeName FROM GradeList_Table WHERE id = :rowId")
    String getGradeName(int rowId);

    @Query("SELECT ascentName FROM AscentType_Table WHERE id = :rowId")
    String getAscentType(int rowId);

    @Query("SELECT * FROM LocationList_Table WHERE id = :rowId")
    LiveData<LocationList> getLocation(int rowId);

    @Update
    void updateClimbLog(ClimbLog climbLog);

    @Query("UPDATE climblog_table SET name=:newName WHERE id = :rowID")
    void update(String newName, int rowID);

    @Query("UPDATE climblog_table SET gradeTypeCode=:newGradeTypeCode, gradeCode=:newGradeCode WHERE id = :rowID")
    void update(int newGradeTypeCode, int newGradeCode, int rowID);

    @Query("UPDATE climblog_table SET ascentTypeCode=:newAscentCode WHERE id = :rowID")
    void update(int newAscentCode, int rowID);

    @Query("UPDATE climblog_table SET firstAscentCode=:newFirstAscentCode WHERE id = :rowID")
    void update(boolean newFirstAscentCode, int rowID);

    @Query("UPDATE climblog_table SET location=:newLocation WHERE id = :rowID")
    void updateLoc(int newLocation, int rowID);




    @Insert
    void insertAscentType(AscentType ascentType);

    @Update
    void updateAscentType(AscentType ascentType);

    @Delete
    void deleteAscentType(AscentType ascentType);


    @Insert
    void insertCalendarTracker(CalendarTracker calendarTracker);

    @Update
    void updateCalendarTracker(CalendarTracker calendarTracker);

    @Delete
    void deleteCalendarTracker(CalendarTracker calendarTracker);

    @Insert
    void insertClimbLog(ClimbLog climbLog);


    @Delete
    void deleteClimbLog(ClimbLog climbLog);

    @Insert
    void insertGradeList(GradeList gradeList);

    @Update
    void updateGradeList(GradeList gradeList);

    @Delete
    void deleteGradeList(GradeList gradeList);

    @Insert
    void insertGradeType(GradeType gradeType);

    @Update
    void updateGradeType(GradeType gradeType);

    @Delete
    void deleteGradeType(GradeType gradeType);

    @Insert
    void insertHoldType(HoldType holdType);

    @Update
    void updateHoldType(HoldType holdType);

    @Delete
    void deleteHoldType(HoldType holdType);

    @Insert
    void insertLocationList(LocationList locationList);

    @Update
    void updateLocationList(LocationList locationList);

    @Delete
    void deleteLocationList(LocationList locationList);

    @Insert
    void insertWorkoutList(WorkoutList workoutList);

    @Update
    void updateWorkoutList(WorkoutList workoutList);

    @Delete
    void deleteWorkoutList(WorkoutList workoutList);

    @Insert
    void insertWorkoutLog(WorkoutLog workoutLog);

    @Update
    void updateWorkoutLog(WorkoutLog workoutLog);

    @Delete
    void deleteWorkoutLog(WorkoutLog workoutLog);

    @Insert
    void insertWorkoutType(WorkoutType workoutType);

    @Update
    void updateWorkoutType(WorkoutType workoutType);

    @Delete
    void deleteWorkoutType(WorkoutType workoutType);


}
