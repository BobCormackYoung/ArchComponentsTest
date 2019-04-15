package com.youngsoft.archcomponentstest.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Calendar;

import static com.youngsoft.archcomponentstest.UtilModule.TimeUtils.convertDate;

@Entity(tableName = "CalendarTracker_Table")
public class CalendarTracker {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long date;

    private boolean isClimbCode;

    private int rowId;

    private int changeTriggerChildData;

    public CalendarTracker(long date, boolean isClimbCode, int rowId, int changeTriggerChildData) {
        this.date = date;
        this.isClimbCode = isClimbCode;
        this.rowId = rowId;
        this.changeTriggerChildData = changeTriggerChildData;
        Log.i("CalendarTracker", "" + convertDate(date, "yyyy-MM-dd  hh:mm:ss") + " " + isClimbCode + " " + rowId);
    }

    public static CalendarTracker[] populateCalendarTracker() {
        long tempDate = Calendar.getInstance().getTimeInMillis();
        return new CalendarTracker[]{
                new CalendarTracker(tempDate - 2 * (24 * 60 * 60000), true, 1, 0),
                new CalendarTracker(tempDate - 2 * (24 * 60 * 60000), true, 2, 0),
                new CalendarTracker(tempDate - 1 * (24 * 60 * 60000), true, 3, 0),
                new CalendarTracker(tempDate - 1 * (24 * 60 * 60000), true, 4, 0),
                new CalendarTracker(tempDate - 0 * (24 * 60 * 60000), true, 5, 0),
                new CalendarTracker(tempDate - 0 * (24 * 60 * 60000), true, 6, 0),
                new CalendarTracker(tempDate + 1 * (24 * 60 * 60000), true, 7, 0),
                new CalendarTracker(tempDate + 1 * (24 * 60 * 60000), true, 8, 0),
                new CalendarTracker(tempDate + 2 * (24 * 60 * 60000), true, 9, 0),
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChangeTriggerChildData() {
        return changeTriggerChildData;
    }

    public long getDate() {
        return date;
    }

    public boolean getIsClimbCode() {
        return isClimbCode;
    }

    public int getRowId() {
        return rowId;
    }
}
