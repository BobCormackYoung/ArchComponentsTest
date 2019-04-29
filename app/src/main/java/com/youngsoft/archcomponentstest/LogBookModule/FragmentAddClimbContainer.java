package com.youngsoft.archcomponentstest.LogBookModule;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youngsoft.archcomponentstest.R;
import com.youngsoft.archcomponentstest.ViewModelMainActivity;

public class FragmentAddClimbContainer extends Fragment {

    View view;
    ViewModelAddClimb viewModelAddClimb;
    ViewModelLogBook viewModelLogBook;
    ViewModelMainActivity viewModelMainActivity;
    Fragment fragmentAddClimb;// = new FragmentAddClimb();
    Fragment fragmentPickAscent;// = new FragmentPickAscent();

    public void FragmentAddClimbContainer() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentAddClimb = new FragmentAddClimb();
        fragmentPickAscent = new FragmentPickAscent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_climb_container, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
        Create viewmodel for AddClimb fragment - scope to Fragment Lifecycle
         */
        viewModelAddClimb = ViewModelProviders.of(this).get(ViewModelAddClimb.class);
        viewModelLogBook = ViewModelProviders.of(getActivity()).get(ViewModelLogBook.class);
        viewModelMainActivity = ViewModelProviders.of(getActivity()).get(ViewModelMainActivity.class);
        viewModelMainActivity.setFragmentClass(FragmentAddClimbContainer.class);

        startAddClimbFragment();


    }

    public void startPickAscentFragment() {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.flAddClimbContainer, fragmentPickAscent, "fragmentPickAscent")
                .addToBackStack(null).commit();
    }

    public void startAddClimbFragment() {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.flAddClimbContainer, fragmentAddClimb, "fragmentAddClimb")
                .addToBackStack(null).commit();
    }

}
