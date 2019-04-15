package com.youngsoft.archcomponentstest.LogBookModule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.youngsoft.archcomponentstest.data.AscentType;
import com.youngsoft.archcomponentstest.data.CalendarTracker;
import com.youngsoft.archcomponentstest.data.DataRepository;

import java.util.List;

import static com.youngsoft.archcomponentstest.UtilModule.TimeUtils.convertDate;

public class ViewModelLogBookDay extends AndroidViewModel {

    private DataRepository dataRepository;
    private long startDate;
    private long endDate;
    private LiveData<List<CalendarTracker>> calendarTrackerBetweenDates;

    public ViewModelLogBookDay(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public LiveData<List<CalendarTracker>> getCalendarTrackerBetweenDates() {
        return calendarTrackerBetweenDates;
    }

    public void init() {
        Log.i("ViewModelLogBook", "Initialising Log Book View Model");
        Log.i("ViewModelLogBook", "Start Date: " + convertDate(startDate, "yyyy-MM-dd  HH:mm:ss") + ", End Date: " +
                convertDate(endDate, "yyyy-MM-dd  HH:mm:ss"));
        calendarTrackerBetweenDates = dataRepository.getCalendarTrackerBetweenDates(startDate, endDate);
    }

    public DataRepository getDataRepository() {
        return dataRepository;
    }
}
