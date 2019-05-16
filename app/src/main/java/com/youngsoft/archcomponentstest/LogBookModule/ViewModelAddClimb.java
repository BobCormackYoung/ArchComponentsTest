package com.youngsoft.archcomponentstest.LogBookModule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

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
    private MutableLiveData<Boolean> isFirstAscent = new MutableLiveData<>();
    private MutableLiveData<AscentType> pickedAscentType = new MutableLiveData<>();
    private MutableLiveData<GradeType> pickedGradeTypeMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<GradeList> pickedGradeListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<CombinedGradeData> pickedCombinedGradeLiveData = new MutableLiveData<>();
    private Boolean gpsAccessPermission = false;

    private MutableLiveData<Double> outputLatitude = new MutableLiveData<>();
    private MutableLiveData<Double> outputLongitude = new MutableLiveData<>();
    private MutableLiveData<String> outputLocationName = new MutableLiveData<>();
    private MutableLiveData<Boolean> outputHasGps = new MutableLiveData<>();
    private MutableLiveData<Boolean> requestingLocationUpdates = new MutableLiveData<>();

    public ViewModelAddClimb(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        setAscentTypeLiveData();
        setGradeTypeLiveData();
        setGradeLiveData();
        setLocationListLiveData();
    }

    public LiveData<String> getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName.setValue(routeName);
    }

    public LiveData<Boolean> getIsFirstAscent() {
        return isFirstAscent;
    }

    public void setIsFirstAscent(Boolean isFirstAscent) {
        this.isFirstAscent.setValue(isFirstAscent);
    }

    public void setLocationListLiveData() {
        locationListLiveData = dataRepository.getAllLocationLists();
    }

    public LiveData<List<LocationList>> getLocationListLiveData() {
        return locationListLiveData;
    }

    public LiveData<List<AscentType>> getAscentTypeLiveData() {
        return ascentTypeLiveData;
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

    public LiveData<List<GradeType>> getGradeTypeLiveData() {
        return gradeTypeLiveData;
    }

    public LiveData<List<GradeList>> getGradeLiveData() {
        return gradeLiveData;
    }

    public LiveData<List<GradeList>> getSubsetGradeLiveData() {
        return subsetGradeLiveData;
    }

    public void setSubsetGradeLiveData(int index) {
        subsetGradeLiveData = dataRepository.getSubsetGradeLists(index);
    }

    public MutableLiveData<AscentType> getPickedAscentType() {
        return pickedAscentType;
    }

    public void setPickedAscentType(AscentType pickedAscentType) {
        this.pickedAscentType.setValue(pickedAscentType);
    }

    public DataRepository getDataRepository() {
        return dataRepository;
    }

    public MutableLiveData<GradeType> getPickedGradeTypeMutableLiveData() {
        return pickedGradeTypeMutableLiveData;
    }

    public void setPickedGradeTypeMutableLiveData(GradeType pickedGradeType) {
        Log.i("ViewModelAddClimb", "Picked Grade Type = " + pickedGradeType.getGradeTypeName());
        pickedGradeTypeMutableLiveData.setValue(pickedGradeType);
    }

    public MutableLiveData<GradeList> getPickedGradeListMutableLiveData() {
        return pickedGradeListMutableLiveData;
    }

    public void setPickedGradeListMutableLiveData(GradeList pickedGradeList) {
        pickedGradeListMutableLiveData.setValue(pickedGradeList);
    }

    public MutableLiveData<CombinedGradeData> getPickedCombinedGradeLiveData() {
        return pickedCombinedGradeLiveData;
    }

    public void setPickedCombinedGradeLiveDataGradeType(GradeType pickedGradeType) {
        CombinedGradeData combinedGradeData = new CombinedGradeData(pickedGradeType);
        pickedCombinedGradeLiveData.setValue(combinedGradeData);
    }

    public void setPickedCombinedGradeLiveData(GradeType pickedGradeType, GradeList pickedGradeList) {
        CombinedGradeData combinedGradeData = new CombinedGradeData(pickedGradeType, pickedGradeList);
        pickedCombinedGradeLiveData.setValue(combinedGradeData);
    }

    public Boolean getGpsAccessPermission() {
        return gpsAccessPermission;
    }

    public void setGpsAccessPermission(Boolean input) {
        this.gpsAccessPermission = input;
    }

    public MutableLiveData<Double> getOutputLatitude() {
        return outputLatitude;
    }

    public void setOutputLatitude(Double input) {
        outputLatitude.setValue(input);
    }

    public MutableLiveData<Double> getOutputLongitude() {
        return outputLongitude;
    }

    public void setOutputLongitude(Double input) {
        outputLongitude.setValue(input);
    }

    public MutableLiveData<String> getOutputLocationName() {
        return outputLocationName;
    }

    public void setOutputLocationName(String input) {
        outputLocationName.setValue(input);
    }

    public MutableLiveData<Boolean> getOutputHasGps() {
        return outputHasGps;
    }

    public void setOutputHasGps(Boolean input) {
        outputHasGps.setValue(input);
    }

    public MutableLiveData<Boolean> getRequestingLocationUpdates() {
        return requestingLocationUpdates;
    }

    public void setRequestingLocationUpdates(Boolean input) {
        requestingLocationUpdates.setValue(input);
    }
}
