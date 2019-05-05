package com.youngsoft.archcomponentstest.LogBookModule;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youngsoft.archcomponentstest.R;
import com.youngsoft.archcomponentstest.data.DataRepository;
import com.youngsoft.archcomponentstest.data.GradeType;

public class AdapterPickGradeType extends ListAdapter<GradeType, AdapterPickGradeType.GradeTypeHolder> {

    private static final DiffUtil.ItemCallback<GradeType> DIFF_CALLBACK = new DiffUtil.ItemCallback<GradeType>() {
        @Override
        public boolean areItemsTheSame(GradeType oldItem, GradeType newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(GradeType oldItem, GradeType newItem) {
            if (oldItem.getGradeTypeName().equals(newItem.getGradeTypeName())) {
                return false;
            } else {
                return false;
            }
        }
    };
    private FragmentPickGradeType parentFragment;
    private DataRepository dataRepository;
    private ViewModelAddClimb viewModelAddClimb;

    public AdapterPickGradeType(DataRepository dataRepository, FragmentPickGradeType parentFragment, ViewModelAddClimb viewModelAddClimb) {
        super(DIFF_CALLBACK);
        this.dataRepository = dataRepository;
        this.parentFragment = parentFragment;
        this.viewModelAddClimb = viewModelAddClimb;
    }

    @NonNull
    @Override
    public AdapterPickGradeType.GradeTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item_nodescription, parent, false);
        return new AdapterPickGradeType.GradeTypeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPickGradeType.GradeTypeHolder holder, int position) {
        GradeType currentGradeType = getItem(position);
        holder.nameTextView.setText(currentGradeType.getGradeTypeName());
        holder.nameTextView.setOnClickListener(new PickGradeTypeOnClickListener(currentGradeType, viewModelAddClimb));
    }

    public class PickGradeTypeOnClickListener implements View.OnClickListener {

        ViewModelAddClimb viewModel;
        GradeType gradeType;

        public PickGradeTypeOnClickListener(GradeType gradeType, ViewModelAddClimb viewModel) {
            this.viewModel = viewModel;
            this.gradeType = gradeType;
        }

        @Override
        public void onClick(View v) {
            viewModel.setPickedGradeTypeMutableLiveData(gradeType);
            viewModelAddClimb.setSubsetGradeLiveData(gradeType.getId());
            Log.i("PickGradeType", "Picked Grade Type = " + gradeType.getGradeTypeName());
            parentFragment.pickGrade();
        }
    }

    class GradeTypeHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public GradeTypeHolder(View itemView) {
            super(itemView);
            //map all views
            nameTextView = itemView.findViewById(R.id.list_item_text);

        }
    }
}
