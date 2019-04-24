package com.youngsoft.archcomponentstest.LogBookModule;

import android.Manifest;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.youngsoft.archcomponentstest.MainActivity;
import com.youngsoft.archcomponentstest.R;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class FragmentAddClimb extends Fragment {

    private static final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int LOCATION_REQUEST = 3;
    private static final int REQUEST_CHECK_SETTINGS = 4;
    View view;
    AVLoadingIndicatorView ivAvi;
    ImageView ivGreenTick;
    ImageView ivGreyCross;
    TextView textViewLatitude;
    TextView textViewLongitude;
    EditText dateView;
    EditText routeNameView;
    EditText locationNewNameView;
    CheckBox firstAscentCheckBox;
    EditText locationNameView;
    EditText ascentTypeView;
    EditText gradeView;
    Button gpsButton;
    Button cancelButton;
    Button saveButton;
    SpinnerDialog spinnerDialog;
    private ViewModelAddClimb viewModelAddClimb;
    private ViewModelLogBook viewModelLogBook;

    //FusedLocationProviderClient fusedLocationClient;
    //LocationRequest locationRequest;
    //LocationCallback locationCallback;


    public FragmentAddClimb() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_climb, container, false);
        mapViews();

        ascentTypeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch new fragment
            }
        });

        firstAscentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModelAddClimb.setIsFirstAscent(isChecked);
            }
        });

        routeNameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    viewModelAddClimb.setRouteName(routeNameView.getText().toString());
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelAddClimb = ViewModelProviders.of(getActivity()).get(ViewModelAddClimb.class);
        viewModelLogBook = ViewModelProviders.of(getActivity()).get(ViewModelLogBook.class);

        /*
        Observe the checked data holder
        If does not agree with the view, then update. If does agree, do nothing
        */
        viewModelAddClimb.getIsFirstAscent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.i("FragAddClimb", "FA CheckBox Observer, aBoolean: " + aBoolean + ", CheckBox: " + firstAscentCheckBox.isChecked());
                if (aBoolean != firstAscentCheckBox.isChecked()) {
                    firstAscentCheckBox.setChecked(aBoolean);
                }
            }
        });

        /*
        Observe the routeName data holder
        Update if does not match the value in the EditText box
         */
        viewModelAddClimb.getRouteName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.i("FragAddClimb", "RouteName Observer, s: " + s + ", EditText: " + routeNameView.getText().toString());
                if (!s.equals(routeNameView.getText().toString())) {
                    routeNameView.setText(s);
                }
            }
        });

    }

    private void mapViews() {
        ivGreenTick = view.findViewById(R.id.iv_green_tick);
        ivGreyCross = view.findViewById(R.id.iv_grey_cross);
        ivAvi = view.findViewById(R.id.iv_av_loading_indicator);
        textViewLatitude = view.findViewById(R.id.tv_latitude);
        textViewLongitude = view.findViewById(R.id.tv_longitude);
        routeNameView = view.findViewById(R.id.et_add_climb_1);
        locationNewNameView = view.findViewById(R.id.et_add_climb_2);
        locationNameView = view.findViewById(R.id.et_add_climb_2a);
        ascentTypeView = view.findViewById(R.id.et_add_climb_3);
        gradeView = view.findViewById(R.id.et_add_climb_4);
        dateView = view.findViewById(R.id.et_add_climb_5);
        firstAscentCheckBox = view.findViewById(R.id.cb_add_climb_firstascent);
        gpsButton = view.findViewById(R.id.bt_getGps);
        cancelButton = view.findViewById(R.id.bt_add_climb_cancel);
        saveButton = view.findViewById(R.id.bt_add_climb_save);
    }

    private void pickGrade() {
        //FragmentParentGradeHolder fragmentParentGradeHolder = new FragmentParentGradeHolder();

        /*getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, MainActivity.fragmentParentGradeHolder, MainActivity.fragmentNameParentGradeHolder)
                .addToBackStack(null)
                .commit();*/
    }

    private void pickAscentType() {
        /*FragmentAscentHolder fragmentAscentHolder = new FragmentAscentHolder();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragmentAscentHolder, MainActivity.fragmentNameAscentHolder)
                .addToBackStack(null)
                .commit();*/
    }
}
