package com.youngsoft.archcomponentstest.LogBookModule;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.youngsoft.archcomponentstest.R;
import com.youngsoft.archcomponentstest.data.AscentType;
import com.youngsoft.archcomponentstest.data.GradeList;
import com.youngsoft.archcomponentstest.data.GradeType;

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
                pickAscentType();
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

        gradeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickGrade();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitFragment();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //viewModelAddClimb = ViewModelProviders.of(getActivity()).get(ViewModelAddClimb.class);

        /*
        Create viewmodel for AddClimb fragment - scope to Fragment Lifecycle
         */
        viewModelAddClimb = ViewModelProviders.of(getParentFragment()).get(ViewModelAddClimb.class);
        viewModelLogBook = ViewModelProviders.of(getActivity()).get(ViewModelLogBook.class);

        /*
        Observe the checked data holder
        If does not agree with the view, then update. If does agree, do nothing
        */
        viewModelAddClimb.getIsFirstAscent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                //Log.i("FragAddClimb", "FA CheckBox Observer, aBoolean: " + aBoolean + ", CheckBox: " + firstAscentCheckBox.isChecked());
                if (aBoolean == null) {
                    firstAscentCheckBox.setChecked(false);
                    viewModelAddClimb.setIsFirstAscent(false);
                } else {
                    if (aBoolean != firstAscentCheckBox.isChecked()) {
                        firstAscentCheckBox.setChecked(aBoolean);
                    }
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
                if (s.isEmpty()) {
                    routeNameView.getText().clear();
                } else {
                    if (!s.equals(routeNameView.getText().toString())) {
                        routeNameView.setText(s);
                    }
                }
            }
        });

        /*
        Observe AscentType livedata
        Update editText if so
         */
        viewModelAddClimb.getPickedAscentType().observe(getViewLifecycleOwner(), new Observer<AscentType>() {
            @Override
            public void onChanged(@Nullable AscentType ascentType) {
                if (ascentType != null) {
                    ascentTypeView.setText(ascentType.getAscentName());
                }
            }
        });

        /*
        Observe PickedGradeType Live Data
        Update edit Text if so
         */
        viewModelAddClimb.getPickedGradeTypeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<GradeType>() {
            @Override
            public void onChanged(@Nullable GradeType gradeType) {
                gradeView.setText(gradeType.getGradeTypeName());
            }
        });

        /*
        Observe PickedGradeList Live Data
        Update edit Text if so
         */
        viewModelAddClimb.getPickedGradeListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<GradeList>() {
            @Override
            public void onChanged(@Nullable GradeList gradeList) {
                //do something
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
        FragmentAddClimbContainer fragmentAddClimbContainer = (FragmentAddClimbContainer) this.getParentFragment();
        fragmentAddClimbContainer.startPickGradeTypeFragment();
    }

    private void pickAscentType() {
        FragmentAddClimbContainer fragmentAddClimbContainer = (FragmentAddClimbContainer) this.getParentFragment();
        fragmentAddClimbContainer.startPickAscentFragment();
    }

    private void exitFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            //super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("FragmentAddClimb", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("FragmentAddClimb", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("FragmentAddClimb", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("FragmentAddClimb", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("FragmentAddClimb", "onDetach");
    }
}
