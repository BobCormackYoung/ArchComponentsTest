package com.youngsoft.archcomponentstest.LogBookModule;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.youngsoft.archcomponentstest.R;
import com.youngsoft.archcomponentstest.data.DataRepository;
import com.youngsoft.archcomponentstest.data.LocationList;

public class AdapterPickLocation extends ListAdapter<LocationList, AdapterPickLocation.LocationListHolder> {

    private static final DiffUtil.ItemCallback<LocationList> DIFF_CALLBACK = new DiffUtil.ItemCallback<LocationList>() {
        @Override
        public boolean areItemsTheSame(LocationList oldItem, LocationList newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(LocationList oldItem, LocationList newItem) {
            if (oldItem.getLocationName().equals(newItem.getLocationName()) &&
                    oldItem.getGpsLongitude() == newItem.getGpsLongitude() &&
                    oldItem.getGpsLatitude() == newItem.getGpsLatitude() &&
                    oldItem.getClimbCount() == newItem.getClimbCount()) {
                return false;
            } else {
                return false;
            }
        }
    };
    private FragmentPickLocation parentFragment;
    private DataRepository dataRepository;
    private ViewModelPickLocation viewModelPickLocation;

    public AdapterPickLocation(DataRepository dataRepository, FragmentPickLocation parentFragment, ViewModelPickLocation viewModelPickLocation) {
        super(DIFF_CALLBACK);
        this.dataRepository = dataRepository;
        this.parentFragment = parentFragment;
        this.viewModelPickLocation = viewModelPickLocation;
    }

    @NonNull
    @Override
    public AdapterPickLocation.LocationListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item_location, parent, false);
        return new AdapterPickLocation.LocationListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPickLocation.LocationListHolder holder, int position) {
        LocationList currentLocationList = getItem(position);
        holder.nameTextView.setText(currentLocationList.getLocationName());
        holder.climbCountTextView.setText(Integer.toString(currentLocationList.getClimbCount()));
        if (currentLocationList.isGps()) {
            holder.hasNoGpsImageView.setVisibility(View.GONE);
            holder.hasYesGpsImageView.setVisibility(View.VISIBLE);
        } else {
            holder.hasNoGpsImageView.setVisibility(View.VISIBLE);
            holder.hasYesGpsImageView.setVisibility(View.GONE);
        }
        holder.nameTextView.setOnClickListener(new PickLocationOnClickListener(currentLocationList, viewModelPickLocation));

    }

    public class PickLocationOnClickListener implements View.OnClickListener {

        ViewModelPickLocation viewModel;
        LocationList locationList;

        public PickLocationOnClickListener(LocationList locationList, ViewModelPickLocation viewModel) {
            this.viewModel = viewModel;
            this.locationList = locationList;
        }

        @Override
        public void onClick(View v) {
            viewModel.setPickedLocation(locationList);
            parentFragment.exitFragment();
        }
    }

    class LocationListHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView climbCountTextView;
        ImageView hasNoGpsImageView;
        ImageView hasYesGpsImageView;

        public LocationListHolder(View itemView) {
            super(itemView);
            //map all views
            nameTextView = itemView.findViewById(R.id.list_item_text);
            climbCountTextView = itemView.findViewById(R.id.list_item_climbcount);
            hasNoGpsImageView = itemView.findViewById(R.id.list_item_nogps);
            hasYesGpsImageView = itemView.findViewById(R.id.list_item_yesgps);
        }
    }


}