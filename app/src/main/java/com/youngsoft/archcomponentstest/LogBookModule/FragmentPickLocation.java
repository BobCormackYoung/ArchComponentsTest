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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

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
import com.youngsoft.archcomponentstest.data.LocationList;

import java.util.List;

public class FragmentPickLocation extends Fragment {

    private static final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int LOCATION_REQUEST = 3;
    private static final int REQUEST_CHECK_SETTINGS = 4;

    ViewModelAddClimb viewModelAddClimb;
    ViewModelPickLocation viewModelPickLocation;
    View view;
    RecyclerView recyclerView;
    CustomOnKeyListener customOnKeyListener;
    Context mContext;

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

    FusedLocationProviderClient fusedLocationClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    public FragmentPickLocation() {
        // Required empty public constructor
    }

    private TextWatcher locationNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            viewModelPickLocation.setNewLocationName(newLocationName.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview_pick_location, container, false);
        mContext = this.getParentFragment().getActivity();

        newLocationButtonCreate = view.findViewById(R.id.bt_new_location);
        newLocationButtonCancel = view.findViewById(R.id.bt_new_loc_cancel);
        newLocationButtonSave = view.findViewById(R.id.bt_new_loc_save);
        newLocationDataWrapper = view.findViewById(R.id.ll_new_location_data_wrapper);
        newLocationGetGPS = view.findViewById(R.id.bt_new_loc_getGps);
        newLocationLatitude = view.findViewById(R.id.tv_new_loc_latitude);
        newLocationLongitude = view.findViewById(R.id.tv_new_loc_longitude);
        newLocationName = view.findViewById(R.id.et_new_loc_name);
        newLocationGreenTick = view.findViewById(R.id.iv_new_loc_green_tick);
        newLocationGreenTick.setVisibility(View.GONE);
        newLocationGreyCross = view.findViewById(R.id.iv_new_loc_grey_cross);
        newLocationIvAvi = view.findViewById(R.id.iv_av_new_loc_loading_indicator);
        newLocationIvAvi.setVisibility(View.GONE);
        newLocationDataWrapper.setVisibility(View.GONE);

        newLocationGetGPS.setOnClickListener(new View.OnClickListener() {

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModelAddClimb = ViewModelProviders.of(getParentFragment()).get(ViewModelAddClimb.class);
        viewModelPickLocation = ViewModelProviders.of(getParentFragment()).get(ViewModelPickLocation.class);

        checkGpsAccessPermission();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    newLocationGreenTick.setVisibility(View.GONE);
                    newLocationGreyCross.setVisibility(View.VISIBLE);
                    //ivAvi.setVisibility(View.GONE);
                    newLocationIvAvi.hide();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update viewModel with location data
                    newLocationGreenTick.setVisibility(View.VISIBLE);
                    viewModelPickLocation.setNewLocationLatitude(location.getLatitude());
                    viewModelPickLocation.setNewLocationLongitude(location.getLongitude());
                    viewModelPickLocation.setNewLocationIsGps(true);

                    newLocationLatitude.setText(Double.toString(location.getLatitude()));
                    newLocationLongitude.setText(Double.toString(location.getLongitude()));

                    // new GPS value... turn off GPS updates
                    stopLocationUpdates();
                    //viewModelAddClimb.setRequestingLocationUpdates(false);
                }
            }
        };

        final AdapterPickLocation adapter = new AdapterPickLocation(viewModelPickLocation.getDataRepository(), this, viewModelPickLocation, viewModelAddClimb);
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
                if (viewModelPickLocation.getNewLocationName() == null) {
                    Toast.makeText(mContext, "Need to set a Location Name", Toast.LENGTH_SHORT).show();
                    Log.i("FragPickLocation", "Location Name is Null");
                } else if (viewModelPickLocation.getNewLocationName().trim().length() == 0) {
                    Toast.makeText(mContext, "Need to set a Location Name", Toast.LENGTH_SHORT).show();
                    Log.i("FragPickLocation", "Location Name is Empty");
                } else {
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
            }
        });

        newLocationName.addTextChangedListener(locationNameTextWatcher);

        customOnKeyListener = new CustomOnKeyListener();
    }

    public void exitFragment() {
        stopLocationUpdates();
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
                //viewModelPickLocation.setRequestingLocationUpdates(true);
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
            newLocationGreenTick.setVisibility(View.GONE);
            newLocationGreyCross.setVisibility(View.GONE);
            //ivAvi.setVisibility(View.VISIBLE);
            newLocationIvAvi.show();
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */);
        }
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
        newLocationIvAvi.hide();
        Log.i("AddClimb GPS", "stopLocationUpdates = stopping location updates");
    }

}
