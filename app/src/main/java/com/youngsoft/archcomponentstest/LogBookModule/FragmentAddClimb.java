package com.youngsoft.archcomponentstest.LogBookModule;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.wang.avi.AVLoadingIndicatorView;
import com.youngsoft.archcomponentstest.R;
import com.youngsoft.archcomponentstest.UtilModule.TimeUtils;
import com.youngsoft.archcomponentstest.data.AscentType;
import com.youngsoft.archcomponentstest.data.CombinedGradeData;
import com.youngsoft.archcomponentstest.data.LocationList;

import java.util.Calendar;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

//TODO: Remove the

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
    Context mContext;
    Boolean gpsAccessPermission = false;
    Boolean isNewLocation = false;

    FusedLocationProviderClient fusedLocationClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    public FragmentAddClimb() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_climb, container, false);
        mContext = this.getParentFragment().getActivity();
        mapViews();

        ivGreenTick.setVisibility(View.GONE);
        ivGreyCross.setVisibility(View.VISIBLE);
        ivAvi.hide();

        ascentTypeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickAscentType();
            }
        });

        locationNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickLocation();
            }
        });

        firstAscentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModelAddClimb.setIsFirstAscent(isChecked);
            }
        });

        routeNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModelAddClimb.setRouteName(routeNameView.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        locationNewNameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    viewModelAddClimb.setOutputLocationNameMutable(locationNewNameView.getText().toString());
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModelAddClimb.saveClimbData();
                exitFragment();
            }
        });

        gpsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (viewModelAddClimb.getGpsAccessPermission()) {
                    //gpsGetLastLocation();
                    createLocationRequest();
                    startLocationUpdates();
                } else {
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
        Create viewmodel for AddClimb fragment - scope to Fragment Lifecycle
         */
        viewModelAddClimb = ViewModelProviders.of(getParentFragment()).get(ViewModelAddClimb.class);
        viewModelLogBook = ViewModelProviders.of(getActivity()).get(ViewModelLogBook.class);

        checkGpsAccessPermission();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    ivGreenTick.setVisibility(View.GONE);
                    ivGreyCross.setVisibility(View.VISIBLE);
                    //ivAvi.setVisibility(View.GONE);
                    ivAvi.hide();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update viewModel with location data
                    viewModelAddClimb.setOutputLatitudeMutable(location.getLatitude());
                    viewModelAddClimb.setOutputLongitudeMutable(location.getLongitude());
                    viewModelAddClimb.setOutputHasGpsMutable(true);

                    // new GPS value... turn off GPS updates
                    stopLocationUpdates();
                    viewModelAddClimb.setRequestingLocationUpdates(false);
                }
            }
        };

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
        Observe the combined picked grade live data
        Update editText if data changes
         */
        viewModelAddClimb.getPickedCombinedGradeLiveData().observe(getViewLifecycleOwner(), new Observer<CombinedGradeData>() {
            @Override
            public void onChanged(@Nullable CombinedGradeData combinedGradeData) {
                if (!combinedGradeData.getNull()) {
                    gradeView.setText(combinedGradeData.getGradeType().getGradeTypeName() + " | " + combinedGradeData.getGradeList().getGradeName());
                } else {
                    gradeView.setText("");
                }
            }
        });

        /*
        Observe longitude & update views
         */
        viewModelAddClimb.getOutputLongitudeMutable().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double aDouble) {
                if (aDouble != null) {
                    textViewLongitude.setText(Double.toString(aDouble));
                } else {
                    textViewLongitude.setText("");
                }
            }
        });

        /*
        Observe latitude & update views
         */
        viewModelAddClimb.getOutputLatitudeMutable().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double aDouble) {
                if (aDouble != null) {
                    textViewLatitude.setText(Double.toString(aDouble));
                } else {
                    textViewLatitude.setText("");
                }
            }
        });

        /*
        Observe gps status and update views
         */
        viewModelAddClimb.getOutputHasGpsMutable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null) {
                    if (aBoolean) {
                        ivGreenTick.setVisibility(View.VISIBLE);
                        ivGreyCross.setVisibility(View.GONE);
                        ivAvi.hide();
                    } else {
                        ivGreenTick.setVisibility(View.GONE);
                        ivGreyCross.setVisibility(View.VISIBLE);
                        ivAvi.hide();
                    }
                } else {
                    ivGreenTick.setVisibility(View.GONE);
                    ivGreyCross.setVisibility(View.VISIBLE);
                    ivAvi.hide();
                }
            }
        });

        /*
        Observe location name
         */
        viewModelAddClimb.getOutputLocationNameMutable().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {
                    if (!s.isEmpty()) {
                        if (isNewLocation) {
                            locationNewNameView.setText(s);
                        } else {
                            locationNameView.setText(s);
                        }
                    }
                } else {
                    locationNewNameView.setText(s);
                    locationNameView.setText(s);
                }
            }
        });

        /*
        Observe the date for adding the climb
         */
        viewModelLogBook.getCurrentDate().observe(getViewLifecycleOwner(), new Observer<Calendar>() {
            @Override
            public void onChanged(@Nullable Calendar calendar) {
                dateView.setText(TimeUtils.convertDate(calendar.getTimeInMillis(), "yyyy-MM-dd"));
                viewModelAddClimb.setDateEntry(calendar.getTimeInMillis());
            }
        });

        /*
        Observe whether we're creating a new location or not
         */
        viewModelAddClimb.getIsNewLocationMutable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    Log.i(this.toString(), "New Location = true");
                    locationNameView.setText("New Location...");
                    viewModelAddClimb.setOutputHasGpsMutable(false);
                    viewModelAddClimb.setOutputLatitudeMutable(null);
                    viewModelAddClimb.setOutputLongitudeMutable(null);
                    viewModelAddClimb.setOutputLocationNameMutable(null);
                    locationNewNameView.setVisibility(View.VISIBLE);
                    isNewLocation = true;
                } else {
                    viewModelAddClimb.setOutputLocationNameMutable("");
                    locationNewNameView.setVisibility(View.GONE);
                    isNewLocation = false;
                }
            }
        });

        /*
        Observe the picked location type
         */
        viewModelAddClimb.getPickedLocationList().observe(getViewLifecycleOwner(), new Observer<LocationList>() {
            @Override
            public void onChanged(@Nullable LocationList locationList) {
                if (locationList != null) {
                    viewModelAddClimb.setOutputLocationNameMutable(locationList.getLocationName());
                    viewModelAddClimb.setOutputHasGpsMutable(locationList.isGps());
                    viewModelAddClimb.setOutputLatitudeMutable(locationList.getGpsLatitude());
                    viewModelAddClimb.setOutputLongitudeMutable(locationList.getGpsLongitude());
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
        FragmentAddClimbContainer fragmentAddClimbContainer = (FragmentAddClimbContainer) this.getParentFragment();
        fragmentAddClimbContainer.startPickGradeTypeFragment();
    }

    private void pickLocation() {
        FragmentAddClimbContainer fragmentAddClimbContainer = (FragmentAddClimbContainer) this.getParentFragment();
        fragmentAddClimbContainer.startPickLocationFragment();
    }

    private void pickAscentType() {
        FragmentAddClimbContainer fragmentAddClimbContainer = (FragmentAddClimbContainer) this.getParentFragment();
        fragmentAddClimbContainer.startPickAscentFragment();
    }

    private void exitFragment() {
        this.getParentFragment().getActivity().onBackPressed();
    }

    // check permission for accessing location
    private void checkGpsAccessPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.i("AddClimb GPS", "checkGpsAccessPermission = No access, ask get permission");
                requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
            }
        } else {
            viewModelAddClimb.setGpsAccessPermission(true);
        }
    }

    // handle gps permission request result
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModelAddClimb.setGpsAccessPermission(true);
            Log.i("AddClimb GPS", "onRequestPermissionsResult = Access granted");
        }

    }

    // get the last GPS location
    @SuppressLint("MissingPermission")
    private void gpsGetLastLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) mContext, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    viewModelAddClimb.setOutputLatitudeMutable(location.getLatitude());
                    viewModelAddClimb.setOutputLongitudeMutable(location.getLongitude());
                    viewModelAddClimb.setOutputHasGpsMutable(true);
                } else {
                }
            }
        });
    }

    // create the location request
    protected void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000); // 10 seonds
        locationRequest.setFastestInterval(5000); // 5 seconds
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(mContext);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener((Activity) mContext, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                viewModelAddClimb.setRequestingLocationUpdates(true);
            }
        });

        task.addOnFailureListener((Activity) mContext, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    Log.i("AddClimb GPS", "createLocationRequest > task.addOnFailureListener = No Success!");
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult((Activity) mContext,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        Log.i("AddClimb GPS", "startLocationUpdates = starting updates");

        if (viewModelAddClimb.getGpsAccessPermission()) {
            ivGreenTick.setVisibility(View.GONE);
            ivGreyCross.setVisibility(View.GONE);
            //ivAvi.setVisibility(View.VISIBLE);
            ivAvi.show();
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */);
        }
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
        ivAvi.hide();
        Log.i("AddClimb GPS", "stopLocationUpdates = stopping location updates");
    }

}
