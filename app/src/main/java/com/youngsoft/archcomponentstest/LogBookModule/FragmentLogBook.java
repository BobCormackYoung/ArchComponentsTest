package com.youngsoft.archcomponentstest.LogBookModule;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youngsoft.archcomponentstest.MainActivity;
import com.youngsoft.archcomponentstest.R;
import com.youngsoft.archcomponentstest.UtilModule.CachingFragmentStatePagerAdapter;
import com.youngsoft.archcomponentstest.UtilModule.TimeUtils;

import java.util.Calendar;

public class FragmentLogBook extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static Context mContext;
    private static Calendar currentDate;
    final long DAYPERIOD = 86400000;
    View view;
    TextView logBookHeader;
    View button_previous_day;
    View button_next_day;
    ViewPager viewPager;
    View button_add_workout;
    View button_add_climb;
    View button_copy_climb;
    private ViewModelLogBook mViewModelLogBook;
    private CachingFragmentStatePagerAdapter adapterViewPager;

    public FragmentLogBook() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public FragmentLogBook newInstance() {
        FragmentLogBook fragment = new FragmentLogBook();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_log_book, container, false);
        mapViews();
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        // Set PageChangeListener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // When new page is selected, udpate the header to match the new date
            @Override
            public void onPageSelected(int position) {
                logBookHeader.setText(TimeUtils.convertDate(TimeUtils.getDayForPosition(position).getTimeInMillis(), "yyyy-MM-dd"));
                //Calendar newDate = TimeUtils.getDayForPosition(position);
                //mViewModelLogBook.setCurrentDate(newDate);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Previous Day Button
        button_previous_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelLogBook.setCurrentPosition(viewPager.getCurrentItem() - 1);

            }
        });

        // Next Day Button
        button_next_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelLogBook.setCurrentPosition(viewPager.getCurrentItem() + 1);
            }
        });

        // Add Climb Button
        button_add_climb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the currently displayed page position
                // Get it's calendar instance (date)
                // Convert date to milliseconds
                long date = TimeUtils.getDayForPosition(viewPager.getCurrentItem()).getTimeInMillis();

                mViewModelLogBook.setIsNewClimbTrue();
                mViewModelLogBook.setAddClimbDate(date);
                mViewModelLogBook.setAddClimbRowId(-1);

                Fragment fragmentAddClimbContainer = new FragmentAddClimbContainer();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, fragmentAddClimbContainer, MainActivity.fragmentNameAddClimb)
                        .addToBackStack(null)
                        .commit();
            }
        });

/*        button_add_workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the currently displayed page position
                // Get it's calendar instance (date)
                // Convert date to milliseconds
                int position = viewPager.getCurrentItem();
                Calendar cal = TimeUtils.getDayForPosition(position);
                long date = cal.getTimeInMillis();

                mViewModelLogBook.setIsNewWorkoutTrue();
                mViewModelLogBook.setAddClimbDate(date);
                mViewModelLogBook.setAddWorkoutRowId(-1);

                FragmentAddWorkout fragmentAddWorkout = new FragmentAddWorkout();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, fragmentAddWorkout, MainActivity.fragmentNameAddWorkout)
                        .addToBackStack(null)
                        .commit();
            }
        });
*/

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("FragmentLogBook", "onActivityCreated");
        mViewModelLogBook = ViewModelProviders.of(getActivity()).get(ViewModelLogBook.class);
        mViewModelLogBook.getCurrentPosition().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer position) {
                viewPager.setCurrentItem(position, true);
                Calendar date = TimeUtils.getDayForPosition(position);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("FragmentLogBook", "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("FragmentLogBook", "onDetach");
    }

    private void mapViews() {
        logBookHeader = view.findViewById(R.id.textview_date);
        button_previous_day = view.findViewById(R.id.button_previous_day);
        button_next_day = view.findViewById(R.id.button_next_day);
        viewPager = view.findViewById(R.id.log_book_viewpager);
        button_add_workout = view.findViewById(R.id.button_add_workout);
        button_add_climb = view.findViewById(R.id.button_log_climb);
        button_copy_climb = view.findViewById(R.id.button_copy_workout);
    }

    private void refreshViews() {
        //logBookHeader.setText(TimeUtils.convertDate(mViewModelLogBook.getCurrentDate().getTimeInMillis(), "yyyy-MM-dd"));
    }


    // Pager Adapter
    public static class MyPagerAdapter extends CachingFragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return TimeUtils.DAYS_OF_TIME;
        }

        @Override
        public Fragment getItem(int position) {
            long timeForPosition = TimeUtils.getDayForPosition(position).getTimeInMillis();
            return FragmentLogBookViewPager.newInstance(timeForPosition);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Calendar cal = TimeUtils.getDayForPosition(position);
            return TimeUtils.getFormattedDate(mContext, cal.getTimeInMillis());
        }


    }

}
