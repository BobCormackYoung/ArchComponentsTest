package com.youngsoft.archcomponentstest.LogBookModule;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.youngsoft.archcomponentstest.data.AscentType;
import com.youngsoft.archcomponentstest.data.DataRepository;

public class AdapterPickAscent extends ListAdapter<AscentType, AdapterPickAscent.AscentTypeHolder> {

    private static final DiffUtil.ItemCallback<AscentType> DIFF_CALLBACK = new DiffUtil.ItemCallback<AscentType>() {
        @Override
        public boolean areItemsTheSame(AscentType oldItem, AscentType newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(AscentType oldItem, AscentType newItem) {
            if (oldItem.getAscentName().equals(newItem.getAscentName()) &&
                    oldItem.getDescription().equals(newItem.getDescription())) {

                return false;
            } else {
                return false;
            }
        }
    };
    private FragmentPickAscent parentFragment;
    private DataRepository dataRepository;
    private ViewModelAddClimb viewModelAddClimb;

    public AdapterPickAscent(DataRepository dataRepository, FragmentPickAscent parentFragment, ViewModelAddClimb viewModelAddClimb) {
        super(DIFF_CALLBACK);
        this.dataRepository = dataRepository;
        this.parentFragment = parentFragment;
        this.viewModelAddClimb = viewModelAddClimb;
    }

    @NonNull
    @Override
    public AdapterPickAscent.AscentTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item_description, parent, false);
        return new AdapterPickAscent.AscentTypeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPickAscent.AscentTypeHolder holder, int position) {
        AscentType currentAscentType = getItem(position);
        holder.nameTextView.setText(currentAscentType.getAscentName());
        holder.descriptionIcon.setOnClickListener(new AscentDescriptionOnClickLister(currentAscentType, holder));
        holder.nameTextView.setOnClickListener(new PickAscentOnClickListener(currentAscentType, viewModelAddClimb));
    }

    public AlertDialog.Builder descriptionAlert(final AscentType ascentType, final Context context) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(ascentType.getDescription());
        alert.setPositiveButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return (alert);

    }

    public class AscentDescriptionOnClickLister implements View.OnClickListener {

        AscentTypeHolder holder;
        AscentType ascentType;

        public AscentDescriptionOnClickLister(AscentType ascentType, AscentTypeHolder holder) {
            this.ascentType = ascentType;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder alert = descriptionAlert(ascentType, holder.itemView.getContext());
            alert.show();
        }

    }

    public class PickAscentOnClickListener implements View.OnClickListener {

        ViewModelAddClimb viewModel;
        AscentType ascentType;

        public PickAscentOnClickListener(AscentType ascentType, ViewModelAddClimb viewModel) {
            this.viewModel = viewModel;
            this.ascentType = ascentType;
        }

        @Override
        public void onClick(View v) {
            viewModel.setPickedAscentType(ascentType);
            parentFragment.exitFragment();
        }
    }

    class AscentTypeHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView descriptionIcon;

        public AscentTypeHolder(View itemView) {
            super(itemView);
            //map all views
            nameTextView = itemView.findViewById(R.id.list_item_desc_text);
            descriptionIcon = itemView.findViewById(R.id.list_item_desc_info_button);

        }
    }



}
