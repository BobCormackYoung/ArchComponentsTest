package com.youngsoft.archcomponentstest.LogBookModule;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.youngsoft.archcomponentstest.MainActivity;
import com.youngsoft.archcomponentstest.R;
import com.youngsoft.archcomponentstest.UtilModule.TimeUtils;
import com.youngsoft.archcomponentstest.data.CalendarTracker;

import java.util.ArrayList;
import java.util.List;

import static com.youngsoft.archcomponentstest.UtilModule.TimeUtils.convertDate;
import static com.youngsoft.archcomponentstest.UtilModule.TimeUtils.millisToStartOfDay;

/**
 * Created by Bobek on 26/02/2018.
 */

public class FragmentLogBookViewPager extends Fragment {

    private static final String KEY_DATE = "date";
    final long DAYPERIOD = 86400000;
    View view;
    long fragmentDate;
    RecyclerView listView;
    Context mContext;
    //ArrayList<LogBookArrayListItem> logBookArrayList;
    private TextView tvContent;
    private ViewModelLogBookDay mViewModelLogBookDay;

    public static FragmentLogBookViewPager newInstance(long date) {
        FragmentLogBookViewPager fragmentFirst = new FragmentLogBookViewPager();
        Bundle args = new Bundle();
        args.putLong(KEY_DATE, date);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final long millis = getArguments().getLong(KEY_DATE);
        if (millis > 0) {
            final Context context = getActivity();
            if (context != null) {
                fragmentDate = millis;
                return;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_log_book_view_pager, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        long dayStart = fragmentDate - millisToStartOfDay();
        long dayEnd = dayStart + DAYPERIOD;

        Log.i("FragLogBookViewPager", "onActivityCreated, Fragment Date: " + fragmentDate);
        Log.i("FragLogBookViewPager", "onActivityCreated, Start Date: " + dayStart);
        Log.i("FragLogBookViewPager", "onActivityCreated, End Date: " + dayEnd);
        Log.i("FragLogBookViewPager", "onActivityCreated, MillisToStartOfDay: " + millisToStartOfDay());

        mViewModelLogBookDay = ViewModelProviders.of(this).get(ViewModelLogBookDay.class);
        mViewModelLogBookDay.setStartDate(dayStart);
        mViewModelLogBookDay.setEndDate(dayEnd);

        Log.i("LogBookFragmentContent", "onActivityCreated " + TimeUtils.convertDate(fragmentDate, "yyyy-MM-dd"));
        listView = view.findViewById(R.id.log_book_list);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setHasFixedSize(true);

        final AdapterLogBookList adapter = new AdapterLogBookList(mViewModelLogBookDay.getDataRepository(), this);
        listView.setAdapter(adapter);
        mViewModelLogBookDay.init();
        if (mViewModelLogBookDay.getCalendarTrackerBetweenDates() != null) {
            //Log.i("LogBookFragmentContent","is not null");
        } else {
            //Log.i("LogBookFragmentContent","is null");
        }
        mViewModelLogBookDay.getCalendarTrackerBetweenDates().observe(this, new Observer<List<CalendarTracker>>() {
            @Override
            public void onChanged(@Nullable List<CalendarTracker> calendarTrackers) {
                Log.i("onActCrtd in Observer", "date: " + convertDate(fragmentDate, "yyyy-MM-dd  HHs:mm:ss") +
                        ", count: " + calendarTrackers.size());
                adapter.submitList(calendarTrackers);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*public void refreshData() {
        long dayStart = fragmentDate - millisToStartOfDay();
        long dayEnd = dayStart + DAYPERIOD;
        logBookArrayList = DatabaseReadWrite.getCalendarEntriesBetweenDates(dayStart, dayEnd, (Context) getActivity());
        adapter = new LogBookListArrayAdapter(mContext, logBookArrayList);
        listView.setAdapter(adapter);
    }*/

    /*public void updateExpandedItemsList() {
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int rowID = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CalendarTrackerEntry.COLUMN_ROWID));
            LogBookListAdapter.itemExpanded.add(i, new ExpandedArrayItem(rowID, false)); // initializes all items value with false
        }
    }*/

    /*private class LoadCursorTask extends AsyncTask<LoadCursorDataInput, Integer, Cursor> {
        protected Cursor doInBackground(LoadCursorDataInput... loadCursorDataInputs) {
            Log.i("AsyncTask","doInBackground "+ TimeUtils.convertDate(fragmentDate, "yyyy-MM-dd"));
            long startDate = loadCursorDataInputs[0].getDayStart();
            long endDate = loadCursorDataInputs[0].getDayEnd();
            return getCursorBetweenDates(startDate, endDate, database);
        }

        protected void onPreExecute() {
            Log.i("AsyncTask","onPreExecute "+ TimeUtils.convertDate(fragmentDate, "yyyy-MM-dd"));
            Log.i("AsyncTask","onPreExecute "+ database.isOpen() + " " + TimeUtils.convertDate(fragmentDate, "yyyy-MM-dd"));
            final Context context = getActivity();
            handler = new DatabaseHelper(context);
            database = handler.getWritableDatabase();
            //ivAvi.show();
        }

        protected void onPostExecute(Cursor result) {
            Log.i("AsyncTask","onPostExecute "+ TimeUtils.convertDate(fragmentDate, "yyyy-MM-dd"));
            if (cursor!=null) {
                cursor.close();
            }
            cursor=result;
            if (adapter!=null) {
                adapter.changeCursor(cursor);
            }
            updateExpandedItemsList();
            //ivAvi.hide();
        }
    }*/

    /*private class LoadCursorDataInput {

        long dayStart;
        long dayEnd;
        SQLiteDatabase cursorDatabase;

        public void setCursorDatabase(SQLiteDatabase cursorDatabase) {
            this.cursorDatabase = cursorDatabase;
        }

        public void setDayEnd(long dayEnd) {
            this.dayEnd = dayEnd;
        }

        public void setDayStart(long dayStart) {
            this.dayStart = dayStart;
        }

        public long getDayEnd() {
            return dayEnd;
        }

        public long getDayStart() {
            return dayStart;
        }

        public SQLiteDatabase getCursorDatabase() {
            return cursorDatabase;
        }

    }*/

}