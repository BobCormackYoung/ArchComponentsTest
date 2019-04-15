package com.youngsoft.archcomponentstest.LogBookModule;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youngsoft.archcomponentstest.R;
import com.youngsoft.archcomponentstest.data.CalendarTracker;
import com.youngsoft.archcomponentstest.data.ClimbLog;
import com.youngsoft.archcomponentstest.data.DataRepository;

public class AdapterLogBookList extends ListAdapter<CalendarTracker, AdapterLogBookList.CalendarTrackerHolder> {

    private static final DiffUtil.ItemCallback<CalendarTracker> DIFF_CALLBACK = new DiffUtil.ItemCallback<CalendarTracker>() {
        @Override
        public boolean areItemsTheSame(CalendarTracker oldItem, CalendarTracker newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(CalendarTracker oldItem, CalendarTracker newItem) {
            if (oldItem.getRowId() == newItem.getRowId() &&
                    oldItem.getDate() == newItem.getDate() &&
                    oldItem.getIsClimbCode() == newItem.getIsClimbCode() &&
                    oldItem.getChangeTriggerChildData() == newItem.getChangeTriggerChildData()) {

                return false;
            } else {
                return false;
            }
        }
    };
    private Fragment parentFragment;
    private DataRepository dataRepository;
    private Context mContext;

    public AdapterLogBookList(DataRepository dataRepository, Fragment parentFragment) {
        super(DIFF_CALLBACK);
        this.dataRepository = dataRepository;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public AdapterLogBookList.CalendarTrackerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item_log_book, parent, false);
        return new AdapterLogBookList.CalendarTrackerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLogBookList.CalendarTrackerHolder holder, int position) {
        CalendarTracker currentCalendarTracker = getItem(position);
        Log.i("AdapterLogBook", "id: " + currentCalendarTracker.getId());
        holder.titleTextView.setText(Integer.toString(currentCalendarTracker.getId()));
        //holder.textViewLocationName.setText(currentLocation.getLocationName());
        updateViewData(holder, currentCalendarTracker);

    }

    public CalendarTracker getCalendarTrackerAt(int position) {
        return getItem(position);
    }

    public void updateViewData(final AdapterLogBookList.CalendarTrackerHolder holder, CalendarTracker currentCalendarTracker) {
        int rowId = currentCalendarTracker.getRowId();
        Log.i("AdapterLogBookList", "updateViewData, row: " + rowId);

        if (currentCalendarTracker.getIsClimbCode()) {
            LiveData<ClimbLog> currentClimbLog = dataRepository.getClimbLog(rowId);
            currentClimbLog.observe(parentFragment, new Observer<ClimbLog>() {
                @Override
                public void onChanged(@Nullable final ClimbLog climbLog) {
                    if (climbLog == null) {
                        Log.i("AdapterLogBookList", "updateViewData climbLog is null");
                    } else {
                        Log.i("AdapterLogBookList", "updateViewData climbLog is not null");
                    }

                    holder.titleTextView.setText(climbLog.getName());

                    GradeDataParams gradeDataParams = new GradeDataParams(climbLog.getGradeTypeCode(), climbLog.getGradeCode(), holder);
                    LoadGradeDataAsync loadGradeDataAsync = new LoadGradeDataAsync();
                    loadGradeDataAsync.execute(gradeDataParams);

                    /*Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            String gradeTypeClimb = dataRepository.getGradeTypeClimb(climbLog.getGradeTypeCode());
                            String gradeNameClimb = dataRepository.getGradeTextClimb(climbLog.getGradeCode());
                            holder.subtitleTextView.setText(gradeTypeClimb + " | " + gradeNameClimb);
                            holder.dataClimbingValue1.setText(gradeTypeClimb + " | " + gradeNameClimb);
                        }
                    }); */

                    //holder.subtitleTextView.setText(DatabaseReadWrite.getGradeTypeClimb(climbingDataBundle.getInt("outputGradeName"), mContext) + " | " + DatabaseReadWrite.getGradeTextClimb(climbingDataBundle.getInt("outputGradeNumber"), mContext));
                    //holder.dataClimbingValue1.setText(DatabaseReadWrite.getGradeTypeClimb(climbingDataBundle.getInt("outputGradeName"), mContext) + " | " + DatabaseReadWrite.getGradeTextClimb(climbingDataBundle.getInt("outputGradeNumber"), mContext));
                    //holder.dataClimbingValue2.setText(locationDataBundle.getString("outputLocationName")); // Updated
                    //holder.dataClimbingValue3.setText(DatabaseReadWrite.getAscentNameTextClimb(climbingDataBundle.getInt("outputAscent"), mContext));
                    if (climbLog.getFirstAscentCode()) {
                        holder.trophyIcon.setVisibility(View.VISIBLE);
                        holder.trophyText.setVisibility(View.VISIBLE);
                        holder.dataClimbingValue4.setImageResource(R.drawable.ic_baseline_check_box_checked_24px);
                    } else {
                        holder.trophyIcon.setVisibility(View.GONE);
                        holder.trophyText.setVisibility(View.GONE);
                        holder.dataClimbingValue4.setImageResource(R.drawable.ic_baseline_check_box_unchecked_24px);
                    }

                    //int gpsCode = locationDataBundle.getInt("outputIsGps"); // Updated
                    //if (gpsCode == DatabaseContract.IS_GPS_TRUE) {
                    //    dataClimbingValue5.setImageResource(R.drawable.ic_baseline_check_box_checked_24px);
                    //} else {
                    //    dataClimbingValue5.setImageResource(R.drawable.ic_baseline_check_box_unchecked_24px);
                    //}

                    holder.itemDivider.setBackgroundColor(ContextCompat.getColor(parentFragment.getContext(), R.color.colorClimbingItemsV2));
                    holder.iconView.setImageResource(R.drawable.icons_drawstringbag96);

                }
            });
            if (currentClimbLog == null) {
                Log.i("AdapterLogBookList", "updateViewData currentClimbLog is null");
            } else {
                Log.i("AdapterLogBookList", "updateViewData currentClimbLog is not null");
            }
        } else {
            //WorkoutLog currentWorkoutLog = dataRepository.getWorkoutLog(rowId);
            //Log.i("AdapterLogBookList", "workoutLog " + currentWorkoutLog.getId());
        }

        // load correct item from database
        // fill in data in fields
    }

    private static class GradeDataParams {
        CalendarTrackerHolder holder;
        int gradeTypeCode;
        int gradeNameCode;

        GradeDataParams(int gradeTypeCode, int gradeNameCode, CalendarTrackerHolder holder) {
            this.holder = holder;
            this.gradeTypeCode = gradeTypeCode;
            this.gradeNameCode = gradeNameCode;
        }
    }

    class CalendarTrackerHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView subtitleTextView;
        ImageView titleIcon;
        TextView titleIconText;
        ImageView expandableArrow;
        ImageView trophyIcon;
        TextView trophyText;
        LinearLayout dataDisplayWrapper;

        LinearLayout dataClimbingDisplayWrapper;
        LinearLayout dataClimbingWrapper1;
        LinearLayout dataClimbingWrapper2;
        LinearLayout dataClimbingWrapper3;
        LinearLayout dataClimbingWrapper4;
        LinearLayout dataClimbingWrapper5;
        TextView dataClimbingValue1;
        TextView dataClimbingValue2;
        TextView dataClimbingValue3;
        ImageView dataClimbingValue4;
        ImageView dataClimbingValue5;

        LinearLayout dataWorkoutDisplayWrapper;
        LinearLayout dataWorkoutWrapper1;
        LinearLayout dataWorkoutWrapper2;
        LinearLayout dataWorkoutWrapper3;
        LinearLayout dataWorkoutWrapper4;
        LinearLayout dataWorkoutWrapper5;
        LinearLayout dataWorkoutWrapper6;
        LinearLayout dataWorkoutWrapper7;
        LinearLayout dataWorkoutWrapper8;
        LinearLayout dataWorkoutWrapper9;
        TextView dataWorkoutValue1;
        TextView dataWorkoutValue2;
        TextView dataWorkoutValue3;
        TextView dataWorkoutValue4;
        TextView dataWorkoutValue5;
        TextView dataWorkoutValue6;
        TextView dataWorkoutValue7;
        TextView dataWorkoutValue8;
        ImageView dataWorkoutValue9;

        TextView itemDivider;
        ImageView iconView;

        public CalendarTrackerHolder(View itemView) {
            super(itemView);
            //map all views
            titleTextView = itemView.findViewById(R.id.log_book_list_item_title);
            subtitleTextView = itemView.findViewById(R.id.log_book_list_item_subtitle);
            titleIcon = itemView.findViewById(R.id.trophy_icon);
            titleIconText = itemView.findViewById(R.id.trophy_text);
            expandableArrow = itemView.findViewById(R.id.iv_logbook_expand);
            dataDisplayWrapper = itemView.findViewById(R.id.ll_alldata_wrapper);
            trophyIcon = itemView.findViewById(R.id.trophy_icon);
            trophyText = itemView.findViewById(R.id.trophy_text);

            dataClimbingDisplayWrapper = itemView.findViewById(R.id.ll_logbook_climbing_data_wrapper);
            dataClimbingWrapper1 = itemView.findViewById(R.id.ll_logbook_climbing_data1_wrapper);
            dataClimbingWrapper2 = itemView.findViewById(R.id.ll_logbook_climbing_data2_wrapper);
            dataClimbingWrapper3 = itemView.findViewById(R.id.ll_logbook_climbing_data3_wrapper);
            dataClimbingWrapper4 = itemView.findViewById(R.id.ll_logbook_climbing_data4_wrapper);
            dataClimbingWrapper5 = itemView.findViewById(R.id.ll_logbook_climbing_data5_wrapper);
            dataClimbingValue1 = itemView.findViewById(R.id.tv_logbook_climbing_data1);
            dataClimbingValue2 = itemView.findViewById(R.id.tv_logbook_climbing_data2);
            dataClimbingValue3 = itemView.findViewById(R.id.tv_logbook_climbing_data3);
            dataClimbingValue4 = itemView.findViewById(R.id.iv_logbook_climbing_data4);
            dataClimbingValue5 = itemView.findViewById(R.id.iv_logbook_climbing_data5);

            dataWorkoutDisplayWrapper = itemView.findViewById(R.id.ll_logbook_workout_data_wrapper);
            dataWorkoutWrapper1 = itemView.findViewById(R.id.ll_logbook_workout_data1_wrapper);
            dataWorkoutWrapper2 = itemView.findViewById(R.id.ll_logbook_workout_data2_wrapper);
            dataWorkoutWrapper3 = itemView.findViewById(R.id.ll_logbook_workout_data3_wrapper);
            dataWorkoutWrapper4 = itemView.findViewById(R.id.ll_logbook_workout_data4_wrapper);
            dataWorkoutWrapper5 = itemView.findViewById(R.id.ll_logbook_workout_data5_wrapper);
            dataWorkoutWrapper6 = itemView.findViewById(R.id.ll_logbook_workout_data6_wrapper);
            dataWorkoutWrapper7 = itemView.findViewById(R.id.ll_logbook_workout_data7_wrapper);
            dataWorkoutWrapper8 = itemView.findViewById(R.id.ll_logbook_workout_data8_wrapper);
            dataWorkoutWrapper9 = itemView.findViewById(R.id.ll_logbook_workout_data9_wrapper);
            dataWorkoutValue1 = itemView.findViewById(R.id.tv_logbook_workout_data1);
            dataWorkoutValue2 = itemView.findViewById(R.id.tv_logbook_workout_data2);
            dataWorkoutValue3 = itemView.findViewById(R.id.tv_logbook_workout_data3);
            dataWorkoutValue4 = itemView.findViewById(R.id.tv_logbook_workout_data4);
            dataWorkoutValue5 = itemView.findViewById(R.id.tv_logbook_workout_data5);
            dataWorkoutValue6 = itemView.findViewById(R.id.tv_logbook_workout_data6);
            dataWorkoutValue7 = itemView.findViewById(R.id.tv_logbook_workout_data7);
            dataWorkoutValue8 = itemView.findViewById(R.id.tv_logbook_workout_data8);
            dataWorkoutValue9 = itemView.findViewById(R.id.iv_logbook_workout_data9);

            itemDivider = itemView.findViewById(R.id.list_item_divider);
            iconView = itemView.findViewById(R.id.log_book_list_item_icon_image);
        }
    }

    private class LoadGradeDataAsync extends AsyncTask<GradeDataParams, Void, Void> {

        String gradeTypeClimb;
        String gradeNameClimb;
        CalendarTrackerHolder holder;

        @Override
        protected Void doInBackground(GradeDataParams... inputParams) {
            int gradeTypeCode = inputParams[0].gradeTypeCode;
            int gradeNameCode = inputParams[0].gradeNameCode;
            gradeTypeClimb = dataRepository.getGradeTypeClimb(gradeTypeCode);
            gradeNameClimb = dataRepository.getGradeTextClimb(gradeNameCode);
            holder = inputParams[0].holder;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            holder.subtitleTextView.setText(gradeTypeClimb + " | " + gradeNameClimb);
            holder.dataClimbingValue1.setText(gradeTypeClimb + " | " + gradeNameClimb);
        }

    }
}
