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

import com.youngsoft.archcomponentstest.R;
import com.youngsoft.archcomponentstest.data.AscentType;

import java.util.List;

public class FragmentPickAscent extends Fragment {

    ViewModelAddClimb viewModelAddClimb;
    View view;
    RecyclerView recyclerView;
    CustomOnKeyListener customOnKeyListener;

    public FragmentPickAscent() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview_parent_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //viewModelAddClimb = ViewModelProviders.of(getActivity()).get(ViewModelAddClimb.class);

        //FragmentManager fragmentManager = getFragmentManager();
        //FragmentAddClimb fragmentAddClimb = fragmentManager.getFragment();
        viewModelAddClimb = ViewModelProviders.of(getParentFragment()).get(ViewModelAddClimb.class);

        final AdapterPickAscent adapter = new AdapterPickAscent(viewModelAddClimb.getDataRepository(), this, viewModelAddClimb);
        recyclerView = view.findViewById(R.id.rv_parent);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);
        viewModelAddClimb.getAscentTypeLiveData().observe(getViewLifecycleOwner(), new Observer<List<AscentType>>() {
            @Override
            public void onChanged(@Nullable List<AscentType> ascentTypes) {
                adapter.submitList(ascentTypes);
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

    public void exitFragment() {
        FragmentAddClimbContainer fragmentAddClimbContainer = (FragmentAddClimbContainer) this.getParentFragment();
        fragmentAddClimbContainer.startAddClimbFragment();
    }

}
