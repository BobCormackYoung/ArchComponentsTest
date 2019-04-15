package com.youngsoft.archcomponentstest;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youngsoft.archcomponentstest.data.LocationList;
import com.youngsoft.archcomponentstest.LocationManagerModule.LocationManagerAdapter;

import java.util.List;

public class FragmentLocationManager extends Fragment {

    private ViewModelDatabase viewModelDatabase;

    public FragmentLocationManager() {
        // Required empty public constructor
    }

    public static FragmentLocationManager newInstance() {
        FragmentLocationManager fragment = new FragmentLocationManager();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_manager, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_locationmanager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final LocationManagerAdapter locationManagerAdapter = new LocationManagerAdapter();
        recyclerView.setAdapter(locationManagerAdapter);

        viewModelDatabase = ViewModelProviders.of(getActivity()).get(ViewModelDatabase.class);
        viewModelDatabase.getAllLocationList().observe(getActivity(), new Observer<List<LocationList>>() {
            @Override
            public void onChanged(@Nullable List<LocationList> locationLists) {
                locationManagerAdapter.submitList(locationLists);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
