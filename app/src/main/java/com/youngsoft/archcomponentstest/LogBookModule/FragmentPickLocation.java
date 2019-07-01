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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.youngsoft.archcomponentstest.R;
import com.youngsoft.archcomponentstest.data.LocationList;

import java.util.List;

public class FragmentPickLocation extends Fragment {

    ViewModelAddClimb viewModelAddClimb;
    ViewModelPickLocation viewModelPickLocation;
    View view;
    RecyclerView recyclerView;
    CustomOnKeyListener customOnKeyListener;

    Button newLocationButtonCreate;
    Button newLocationButtonCancel;
    Button newLocationButtonSave;
    LinearLayout newLocationDataWrapper;
    Button newLocationGetGPS;
    TextView newLocationLatitude;
    TextView newLocationLongitude;
    EditText newLocationName;
    ImageView newLocationGreenTick;
    ImageView newLocationGreyCross;
    AVLoadingIndicatorView newLocationIvAvi;

    public FragmentPickLocation() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview_pick_location, container, false);
        newLocationButtonCreate = view.findViewById(R.id.bt_new_location);
        newLocationButtonCancel = view.findViewById(R.id.bt_new_loc_cancel);
        newLocationButtonSave = view.findViewById(R.id.bt_new_loc_save);
        newLocationDataWrapper = view.findViewById(R.id.ll_new_location_data_wrapper);
        newLocationGetGPS = view.findViewById(R.id.bt_new_loc_getGps);
        newLocationLatitude = view.findViewById(R.id.tv_new_loc_latitude);
        newLocationLongitude = view.findViewById(R.id.tv_new_loc_longitude);
        newLocationName = view.findViewById(R.id.et_new_loc_name);
        newLocationGreenTick = view.findViewById(R.id.iv_new_loc_green_tick);
        newLocationGreyCross = view.findViewById(R.id.iv_new_loc_grey_cross);
        newLocationIvAvi = view.findViewById(R.id.iv_av_new_loc_loading_indicator);
        newLocationDataWrapper.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModelAddClimb = ViewModelProviders.of(getParentFragment()).get(ViewModelAddClimb.class);
        viewModelPickLocation = ViewModelProviders.of(getParentFragment()).get(ViewModelPickLocation.class);

        final AdapterPickLocation adapter = new AdapterPickLocation(viewModelPickLocation.getDataRepository(), this, viewModelPickLocation);
        recyclerView = view.findViewById(R.id.rv_parent_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);
        viewModelPickLocation.getLocationListLiveData().observe(getViewLifecycleOwner(), new Observer<List<LocationList>>() {
            @Override
            public void onChanged(@Nullable List<LocationList> locationLists) {
                adapter.submitList(locationLists);
            }
        });

        newLocationButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newLocationDataWrapper.setVisibility(View.VISIBLE);
            }
        });

        newLocationButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newLocationDataWrapper.setVisibility(View.GONE);
                viewModelPickLocation.resetNewLocation();
                newLocationLatitude.setText(Double.toString(viewModelPickLocation.getNewLocationLatitude()));
                newLocationLongitude.setText(Double.toString(viewModelPickLocation.getNewLocationLongitude()));
                newLocationName.getText().clear();
                newLocationGreenTick.setVisibility(View.GONE);
                newLocationGreyCross.setVisibility(View.VISIBLE);
                newLocationIvAvi.setVisibility(View.GONE);
            }
        });

        newLocationButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newLocationDataWrapper.setVisibility(View.GONE);
                viewModelPickLocation.saveNewLocation();
                viewModelPickLocation.resetNewLocation();
                newLocationLatitude.setText(Double.toString(viewModelPickLocation.getNewLocationLatitude()));
                newLocationLongitude.setText(Double.toString(viewModelPickLocation.getNewLocationLongitude()));
                newLocationName.getText().clear();
                newLocationGreenTick.setVisibility(View.GONE);
                newLocationGreyCross.setVisibility(View.VISIBLE);
                newLocationIvAvi.setVisibility(View.GONE);
            }
        });

        newLocationName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    viewModelPickLocation.setNewLocationName(newLocationName.getText().toString());
                }
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

    public void exitFragment() {
        FragmentAddClimbContainer fragmentAddClimbContainer = (FragmentAddClimbContainer) this.getParentFragment();
        fragmentAddClimbContainer.startAddClimbFragment();
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
                    exitFragment();
                    return true;
                }
            }
            return false;
        }
    }
}
