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
import com.youngsoft.archcomponentstest.data.GradeList;

public class AdapterPickGrade extends ListAdapter<GradeList, AdapterPickGrade.GradeListHolder> {

    private static final DiffUtil.ItemCallback<GradeList> DIFF_CALLBACK = new DiffUtil.ItemCallback<GradeList>() {
        @Override
        public boolean areItemsTheSame(GradeList oldItem, GradeList newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(GradeList oldItem, GradeList newItem) {
            if (oldItem.getGradeName().equals(newItem.getGradeName()) &&
                    oldItem.getRelativeDifficulty() == newItem.getRelativeDifficulty()) {
                return false;
            } else {
                return false;
            }
        }
    };
    private FragmentPickGrade parentFragment;
    private DataRepository dataRepository;
    private ViewModelAddClimb viewModelAddClimb;

    public AdapterPickGrade(DataRepository dataRepository, FragmentPickGrade parentFragment, ViewModelAddClimb viewModelAddClimb) {
        super(DIFF_CALLBACK);
        this.dataRepository = dataRepository;
        this.parentFragment = parentFragment;
        this.viewModelAddClimb = viewModelAddClimb;
    }

    @NonNull
    @Override
    public AdapterPickGrade.GradeListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item_nodescription, parent, false);
        return new AdapterPickGrade.GradeListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPickGrade.GradeListHolder holder, int position) {
        GradeList currentGradeList = getItem(position);
        holder.nameTextView.setText(currentGradeList.getGradeName());
        holder.nameTextView.setOnClickListener(new AdapterPickGrade.PickGradeOnClickListener(currentGradeList, viewModelAddClimb));
    }

    public class PickGradeOnClickListener implements View.OnClickListener {

        ViewModelAddClimb viewModel;
        GradeList gradeList;

        public PickGradeOnClickListener(GradeList gradeList, ViewModelAddClimb viewModel) {
            this.viewModel = viewModel;
            this.gradeList = gradeList;
        }

        @Override
        public void onClick(View v) {
            viewModel.setPickedGradeListMutableLiveData(gradeList);
            parentFragment.exitFragment();
        }
    }

    class GradeListHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public GradeListHolder(View itemView) {
            super(itemView);
            //map all views
            nameTextView = itemView.findViewById(R.id.list_item_text);

        }
    }
}

