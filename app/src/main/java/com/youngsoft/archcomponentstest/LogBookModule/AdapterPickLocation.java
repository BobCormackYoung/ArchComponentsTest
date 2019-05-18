package com.youngsoft.archcomponentstest.LogBookModule;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ViewModelAddClimb viewModelAddClimb;

    public AdapterPickLocation(DataRepository dataRepository, FragmentPickLocation parentFragment, ViewModelAddClimb viewModelAddClimb) {
        super(DIFF_CALLBACK);
        this.dataRepository = dataRepository;
        this.parentFragment = parentFragment;
        this.viewModelAddClimb = viewModelAddClimb;
    }

    @NonNull
    @Override
    public AdapterPickLocation.LocationListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item_nodescription, parent, false);
        return new AdapterPickLocation.LocationListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPickLocation.LocationListHolder holder, int position) {
        LocationList currentLocationList = getItem(position);
        holder.nameTextView.setText(currentLocationList.getLocationName());
        //holder.descriptionIcon.setOnClickListener(new AdapterPickAscent.AscentDescriptionOnClickLister(currentAscentType, holder));
        holder.nameTextView.setOnClickListener(new PickLocationOnClickListener(currentLocationList, viewModelAddClimb));
    }

    public class PickLocationOnClickListener implements View.OnClickListener {

        ViewModelAddClimb viewModel;
        LocationList locationList;

        public PickLocationOnClickListener(LocationList locationList, ViewModelAddClimb viewModel) {
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
        //ImageView descriptionIcon;

        public LocationListHolder(View itemView) {
            super(itemView);
            //map all views
            nameTextView = itemView.findViewById(R.id.list_item_text);
            //descriptionIcon = itemView.findViewById(R.id.list_item_desc_info_button);

        }
    }


}