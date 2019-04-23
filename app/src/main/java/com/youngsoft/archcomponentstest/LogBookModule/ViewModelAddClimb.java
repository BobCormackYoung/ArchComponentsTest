package com.youngsoft.archcomponentstest.LogBookModule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class ViewModelAddClimb extends AndroidViewModel {

    private MutableLiveData<String> routeName = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFirstAscent = new MutableLiveData<>();

    public ViewModelAddClimb(@NonNull Application application) {
        super(application);
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
}
