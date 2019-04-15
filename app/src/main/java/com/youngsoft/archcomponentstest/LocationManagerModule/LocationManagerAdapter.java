package com.youngsoft.archcomponentstest.LocationManagerModule;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youngsoft.archcomponentstest.R;
import com.youngsoft.archcomponentstest.data.LocationList;

public class LocationManagerAdapter extends ListAdapter<LocationList, LocationManagerAdapter.LocationHolder> {

    private static final DiffUtil.ItemCallback<LocationList> DIFF_CALLBACK = new DiffUtil.ItemCallback<LocationList>() {
        @Override
        public boolean areItemsTheSame(LocationList oldItem, LocationList newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(LocationList oldItem, LocationList newItem) {
            return oldItem.getLocationName().equals(newItem.getLocationName()) &&
                    oldItem.getClimbCount() == (newItem.getClimbCount()) &&
                    oldItem.getGpsLatitude() == newItem.getGpsLatitude() &&
                    oldItem.getGpsLongitude() == newItem.getGpsLongitude();
        }
    };

    public LocationManagerAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new LocationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHolder holder, int position) {
        LocationList currentLocation = getItem(position);
        holder.textViewLocationName.setText(currentLocation.getLocationName());
        holder.textViewClimbCount.setText(Integer.toString(currentLocation.getClimbCount()));
        holder.textViewLatitude.setText(Double.toString(currentLocation.getGpsLatitude()));
        holder.textViewLongitude.setText(Double.toString(currentLocation.getGpsLongitude()));
    }

    public LocationList getLocationAt(int position) {
        return getItem(position);
    }

    class LocationHolder extends RecyclerView.ViewHolder {
        private TextView textViewLocationName;
        private TextView textViewClimbCount;
        private TextView textViewLatitude;
        private TextView textViewLongitude;

        public LocationHolder(View itemView) {
            super(itemView);
            textViewLocationName = itemView.findViewById(R.id.tv_locationname);
            textViewClimbCount = itemView.findViewById(R.id.tv_climbcount);
            textViewLatitude = itemView.findViewById(R.id.tv_latitude);
            textViewLongitude = itemView.findViewById(R.id.tv_longitude);
        }
    }

}
