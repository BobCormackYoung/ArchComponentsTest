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

import java.util.List;

public class FragmentPickGrade extends Fragment {

    ViewModelAddClimb viewModelAddClimb;
    View view;
    RecyclerView recyclerView;

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
                    getFragmentManager().popBackStackImmediate();
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

        /*
        Get the subset grade list for the picked grade type

        viewModelAddClimb.getPickedGradeTypeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<GradeType>() {
            @Override
            public void onChanged(@Nullable GradeType gradeType) {
                Log.i("PickGrade", "gradeType = " + gradeType.getId());
                viewModelAddClimb.setSubsetGradeLiveData(gradeType.getId());
            }
        });*/

        recyclerView.setAdapter(adapter);

        viewModelAddClimb.getSubsetGradeLiveData().observe(getViewLifecycleOwner(), new Observer<List<GradeList>>() {
            @Override
            public void onChanged(@Nullable List<GradeList> gradeLists) {
                adapter.submitList(gradeLists);
            }
        });
    }

    public void exitFragment() {
        FragmentAddClimbContainer fragmentAddClimbContainer = (FragmentAddClimbContainer) this.getParentFragment();
        fragmentAddClimbContainer.startAddClimbFragment();
    }
}
