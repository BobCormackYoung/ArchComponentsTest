package com.youngsoft.archcomponentstest.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Executors;


@Database(entities = {AscentType.class, CalendarTracker.class, ClimbLog.class, GradeList.class, GradeType.class, HoldType.class, LocationList.class, WorkoutList.class, WorkoutLog.class, WorkoutType.class}, version = 1, exportSchema = false)
public abstract class DataDatabase extends RoomDatabase {

    private static DataDatabase instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //Log.i("Callback","running async task");
            //new PopulateDbAsyncTask(instance).execute();

            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    instance.dataDao().insertMultipleAscentType(AscentType.populateAscentTypeData());
                    instance.dataDao().insertMultipleGradeType(GradeType.populateGradeTypeData());
                    instance.dataDao().insertMultipleGradeList(GradeList.populateGradeListData());
                    instance.dataDao().insertMultipleHoldType(HoldType.populateHoldTypeData());
                    instance.dataDao().insertMultipleLocationList(LocationList.populateLocationListData());
                    instance.dataDao().insertMultipleWorkoutList(WorkoutList.populateWorkoutListData());
                    instance.dataDao().insertMultipleWorkoutType(WorkoutType.populateWorkoutTypeData());
                    instance.dataDao().insertMultipleClimbLog(ClimbLog.populateClimbLogData());
                    instance.dataDao().insertMultipleCalendarTracker(CalendarTracker.populateCalendarTracker());
                }
            });
        }
    };

    public static synchronized DataDatabase getInstance(Context context) {
        if (instance == null) {
            Log.i("DataDatabase", "In constructor, instance = null");
            //Populate database on first creation
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DataDatabase.class, "dataDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public abstract DataDao dataDao();
}
