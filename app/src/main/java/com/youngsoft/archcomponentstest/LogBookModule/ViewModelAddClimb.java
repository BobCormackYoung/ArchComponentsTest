package com.youngsoft.archcomponentstest.LogBookModule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.youngsoft.archcomponentstest.data.AscentType;
import com.youngsoft.archcomponentstest.data.DataRepository;

import java.util.List;

public class ViewModelAddClimb extends AndroidViewModel {

    private DataRepository dataRepository;
    private MutableLiveData<String> routeName = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFirstAscent = new MutableLiveData<>();
    private LiveData<List<AscentType>> ascentTypeLiveData;
    private MutableLiveData<AscentType> pickedAscentType = new MutableLiveData<>();

    public ViewModelAddClimb(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        setAscentTypeLiveData();
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

    public LiveData<List<AscentType>> getAscentTypeLiveData() {
        return ascentTypeLiveData;
    }

    public void setAscentTypeLiveData() {
        ascentTypeLiveData = dataRepository.getAllAscentTypes();
    }

    public MutableLiveData<AscentType> getPickedAscentType() {
        return pickedAscentType;
    }

    public void setPickedAscentType(AscentType pickedAscentType) {
        this.pickedAscentType.setValue(pickedAscentType);
    }

    public void resetData() {
        //routeName.setValue("");
        //isFirstAscent.setValue(null);
        //pickedAscentType.setValue(null);
        this.onCleared();
    }

    public DataRepository getDataRepository() {
        return dataRepository;
    }
}
