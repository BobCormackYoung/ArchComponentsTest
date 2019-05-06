package com.youngsoft.archcomponentstest.LogBookModule;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youngsoft.archcomponentstest.R;
import com.youngsoft.archcomponentstest.data.GradeList;
import com.youngsoft.archcomponentstest.data.GradeType;

import java.util.List;

public class FragmentPickGrade extends Fragment {

    ViewModelAddClimb viewModelAddClimb;
    View view;
    RecyclerView recyclerView;
    GradeType pickedGradeType;
    CustomOnKeyListener customOnKeyListener;

    public FragmentPickGrade() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview_parent_list, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("Popping", "Popping backstacks baby");
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //getFragmentManager().popBackStackImmediate();
                    goBackFragment();
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelAddClimb = ViewModelProviders.of(getParentFragment()).get(ViewModelAddClimb.class);
        final AdapterPickGrade adapter = new AdapterPickGrade(viewModelAddClimb.getDataRepository(), this, viewModelAddClimb);
        recyclerView = view.findViewById(R.id.rv_parent);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        viewModelAddClimb.getPickedGradeTypeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<GradeType>() {
            @Override
            public void onChanged(@Nullable GradeType gradeType) {
                pickedGradeType = gradeType;
            }
        });

        recyclerView.setAdapter(adapter);

        viewModelAddClimb.getSubsetGradeLiveData().observe(getViewLifecycleOwner(), new Observer<List<GradeList>>() {
            @Override
            public void onChanged(@Nullable List<GradeList> gradeLists) {
                adapter.submitList(gradeLists);
            }
        });
        customOnKeyListener = new CustomOnKeyListener();
    }

    @Override
    public void onResume() {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(customOnKeyListener);
        super.onResume();
    }

    @Override
    public void onPause() {
        view.setOnKeyListener(null);
        super.onPause();
    }

    public void goBackFragment() {
        FragmentAddClimbContainer fragmentAddClimbContainer = (FragmentAddClimbContainer) this.getParentFragment();
        fragmentAddClimbContainer.startPickGradeTypeFragment();
    }

    public class CustomOnKeyListener implements View.OnKeyListener {

        public CustomOnKeyListener() {
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            Log.i("Popping", "Popping backstacks baby");
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //getFragmentManager().popBackStackImmediate();
                    goBackFragment();
                    return true;
                }
            }
            return false;
        }
    }

    public void exitFragment() {
        FragmentAddClimbContainer fragmentAddClimbContainer = (FragmentAddClimbContainer) this.getParentFragment();
        fragmentAddClimbContainer.startAddClimbFragment();
    }

    public GradeType getPickedGradeType() {
        return pickedGradeType;
    }
}
