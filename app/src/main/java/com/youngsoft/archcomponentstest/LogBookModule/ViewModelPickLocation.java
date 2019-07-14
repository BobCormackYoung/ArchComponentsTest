package com.youngsoft.archcomponentstest.LogBookModule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.youngsoft.archcomponentstest.data.DataRepository;
import com.youngsoft.archcomponentstest.data.LocationList;

import java.util.List;

public class ViewModelPickLocation extends AndroidViewModel {

    private DataRepository dataRepository;
    private LiveData<List<LocationList>> locationListLiveData;
    private MutableLiveData<LocationList> pickedLocationMutableLiveData = new MutableLiveData<>();

    private String newLocationName;
    private double newLocationLatitude = 0;
    private double newLocationLongitude = 0;
    private boolean newlocationisgps = false;

    private MutableLiveData<Boolean> requestingLocationUpdates = new MutableLiveData<>();
    private Boolean gpsAccessPermission = false;

    public ViewModelPickLocation(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        setLocationListLiveData();
    }

    /*
    Data Repository
     */
    public DataRepository getDataRepository() {
        return dataRepository;
    }

    /*
    Input LiveData lists
     */
    public void setLocationListLiveData() {
        locationListLiveData = dataRepository.getAllLocationLists();
    }

    public LiveData<List<LocationList>> getLocationListLiveData() {
        return locationListLiveData;
    }

    public void setPickedLocation(LocationList input) {
        pickedLocationMutableLiveData.setValue(input);
    }

    public String getNewLocationName() {
        return newLocationName;
    }

    public void setNewLocationName(String newLocationName) {
        this.newLocationName = newLocationName;
    }

    public double getNewLocationLatitude() {
        return newLocationLatitude;
    }

    public void setNewLocationLatitude(double newLocationLatitude) {
        this.newLocationLatitude = newLocationLatitude;
    }

    public double getNewLocationLongitude() {
        return newLocationLongitude;
    }

    public void setNewLocationLongitude(double newLocationLongitude) {
        this.newLocationLongitude = newLocationLongitude;
    }

    public boolean isNewLocationIsGps() {
        return newlocationisgps;
    }

    public void setNewLocationIsGps(boolean newlocationisgps) {
        this.newlocationisgps = newlocationisgps;
    }

    public boolean saveNewLocation() {
        if (newLocationName.isEmpty()) {
            return false;
        } else {
            dataRepository.addNewLocation(newLocationName, newLocationLatitude, newLocationLongitude, newlocationisgps);
            return true;
        }
    }

    public void resetNewLocation() {
        setNewLocationName(null);
        setNewLocationLatitude(0);
        setNewLocationLongitude(0);
        setNewLocationIsGps(false);
    }

}
