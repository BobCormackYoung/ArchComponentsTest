package com.youngsoft.archcomponentstest.LogBookModule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.youngsoft.archcomponentstest.data.AscentType;
import com.youngsoft.archcomponentstest.data.CombinedGradeData;
import com.youngsoft.archcomponentstest.data.DataRepository;
import com.youngsoft.archcomponentstest.data.GradeList;
import com.youngsoft.archcomponentstest.data.GradeType;
import com.youngsoft.archcomponentstest.data.LocationList;

import java.util.List;

public class ViewModelAddClimb extends AndroidViewModel {

    private DataRepository dataRepository;

    private LiveData<List<AscentType>> ascentTypeLiveData;
    private LiveData<List<GradeType>> gradeTypeLiveData;
    private LiveData<List<GradeList>> gradeLiveData;
    private LiveData<List<GradeList>> subsetGradeLiveData;
    private LiveData<List<LocationList>> locationListLiveData;

    private MutableLiveData<String> routeName = new MutableLiveData<>();
    private String outputRouteName;
    private MutableLiveData<Boolean> isFirstAscent = new MutableLiveData<>();
    private Boolean outputIsFirstAscent;
    private MutableLiveData<AscentType> pickedAscentType = new MutableLiveData<>();
    private int outputPickedAscentType;

    private MutableLiveData<GradeType> pickedGradeTypeMutableLiveData = new MutableLiveData<>();
    private int outputGradeType;
    private MutableLiveData<GradeList> pickedGradeListMutableLiveData = new MutableLiveData<>();
    private int outputGrade;
    private MutableLiveData<CombinedGradeData> pickedCombinedGradeLiveData = new MutableLiveData<>();

    private MutableLiveData<LocationList> pickedLocationMutableLiveData = new MutableLiveData<>();
    private int pickedLocation;
    private int pickedLocationClimbCount;
    private MutableLiveData<Boolean> isNewLocationMutable = new MutableLiveData<>();
    private Boolean outputIsNewLocation;
    private MutableLiveData<Double> outputLatitudeMutable = new MutableLiveData<>();
    private Double outputLatitude;
    private MutableLiveData<Double> outputLongitudeMutable = new MutableLiveData<>();
    private Double outputLongitude;
    private MutableLiveData<String> outputLocationNameMutable = new MutableLiveData<>();
    private String outputLocationName;
    private MutableLiveData<Boolean> outputHasGpsMutable = new MutableLiveData<>();
    private Boolean outputHasGps;

    private MutableLiveData<Boolean> requestingLocationUpdates = new MutableLiveData<>();
    private Boolean gpsAccessPermission = false;

    public ViewModelAddClimb(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        setAscentTypeLiveData();
        setGradeTypeLiveData();
        setGradeLiveData();
        setLocationListLiveData();
        setIsNewLocationMutable(false);
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

    public void setAscentTypeLiveData() {
        ascentTypeLiveData = dataRepository.getAllAscentTypes();
    }

    public void setGradeTypeLiveData() {
        gradeTypeLiveData = dataRepository.getAllGradeTypes();
    }

    public void setGradeLiveData() {
        gradeLiveData = dataRepository.getAllGrades();
    }

    public LiveData<List<LocationList>> getLocationListLiveData() {
        return locationListLiveData;
    }

    public LiveData<List<AscentType>> getAscentTypeLiveData() {
        return ascentTypeLiveData;
    }

    public LiveData<List<GradeType>> getGradeTypeLiveData() {
        return gradeTypeLiveData;
    }

    public LiveData<List<GradeList>> getSubsetGradeLiveData() {
        return subsetGradeLiveData;
    }

    public void setSubsetGradeLiveData(int index) {
        subsetGradeLiveData = dataRepository.getSubsetGradeLists(index);
    }

    /*
    Location related data
     */
    public LiveData<Boolean> getIsNewLocationMutable() {
        return isNewLocationMutable;
    }

    public void setIsNewLocationMutable(Boolean input) {
        isNewLocationMutable.setValue(input);
        outputIsNewLocation = input;
    }

    public LiveData<LocationList> getPickedLocationList() {
        return pickedLocationMutableLiveData;
    }

    public void setPickedLocation(LocationList input) {
        pickedLocationMutableLiveData.setValue(input);
        pickedLocation = input.getId();
        pickedLocationClimbCount = input.getClimbCount();
    }

    public LiveData<Double> getOutputLatitudeMutable() {
        return outputLatitudeMutable;
    }

    public void setOutputLatitudeMutable(Double input) {
        outputLatitudeMutable.setValue(input);
        outputLatitude = input;
    }

    public LiveData<Double> getOutputLongitudeMutable() {
        return outputLongitudeMutable;
    }

    public void setOutputLongitudeMutable(Double input) {
        outputLongitudeMutable.setValue(input);
        outputLongitude = input;
    }

    public LiveData<String> getOutputLocationNameMutable() {
        return outputLocationNameMutable;
    }

    public void setOutputLocationNameMutable(String input) {
        outputLocationNameMutable.setValue(input);
        outputLocationName = input;
    }

    public LiveData<Boolean> getOutputHasGpsMutable() {
        return outputHasGpsMutable;
    }

    public void setOutputHasGpsMutable(Boolean input) {
        outputHasGpsMutable.setValue(input);
        outputHasGps = input;
    }

    /*
    Route name data
     */
    public LiveData<String> getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName.setValue(routeName);
        outputRouteName = routeName;
    }

    /*
    First Ascent data
     */
    public LiveData<Boolean> getIsFirstAscent() {
        return isFirstAscent;
    }

    public void setIsFirstAscent(Boolean isFirstAscent) {
        this.isFirstAscent.setValue(isFirstAscent);
        outputIsFirstAscent = isFirstAscent;
    }

    /*
    Ascent Type data
     */
    public LiveData<AscentType> getPickedAscentType() {
        return pickedAscentType;
    }

    public void setPickedAscentType(AscentType pickedAscentType) {
        this.pickedAscentType.setValue(pickedAscentType);
        outputPickedAscentType = pickedAscentType.getId();
    }

    /*
    Grade Data
     */
    public LiveData<GradeType> getPickedGradeTypeMutableLiveData() {
        return pickedGradeTypeMutableLiveData;
    }

    public void setPickedGradeTypeMutableLiveData(GradeType pickedGradeType) {
        pickedGradeTypeMutableLiveData.setValue(pickedGradeType);
    }

    public void setPickedGradeListMutableLiveData(GradeList pickedGradeList) {
        pickedGradeListMutableLiveData.setValue(pickedGradeList);
    }

    public LiveData<CombinedGradeData> getPickedCombinedGradeLiveData() {
        return pickedCombinedGradeLiveData;
    }

    public void setPickedCombinedGradeLiveDataGradeType(GradeType pickedGradeType) {
        CombinedGradeData combinedGradeData = new CombinedGradeData(pickedGradeType);
        pickedCombinedGradeLiveData.setValue(combinedGradeData);
    }

    public void setPickedCombinedGradeLiveData(GradeType pickedGradeType, GradeList pickedGradeList) {
        CombinedGradeData combinedGradeData = new CombinedGradeData(pickedGradeType, pickedGradeList);
        pickedCombinedGradeLiveData.setValue(combinedGradeData);
        outputGradeType = pickedGradeType.getId();
        outputGrade = pickedGradeList.getId();
    }

    /*
    GPS permission related
     */
    public Boolean getGpsAccessPermission() {
        return gpsAccessPermission;
    }

    public void setGpsAccessPermission(Boolean input) {
        this.gpsAccessPermission = input;
    }

    public LiveData<Boolean> getRequestingLocationUpdates() {
        return requestingLocationUpdates;
    }

    public void setRequestingLocationUpdates(Boolean input) {
        requestingLocationUpdates.setValue(input);
    }

    /*
    Saving data
     */
    public void saveClimbData() {
        //If
    }
}
