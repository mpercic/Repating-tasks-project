package com.example.repeatingtasks.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repeatingtasks.R;
import com.example.repeatingtasks.model.database.entities.Task;

import java.util.ArrayList;
import java.util.List;

public class TodayTasksAdapter extends RecyclerView.Adapter<TodayTasksAdapter.ViewHolder> {
    private final List<Task> tasks;
    private CheckTaskListener checkTaskListener;

    public TodayTasksAdapter() {
        this.tasks = new ArrayList<>();
    }

    /**
     * sets the checkTaskListener
     */
    public void setCheckTaskListener(CheckTaskListener checkTaskListener) {
        this.checkTaskListener = checkTaskListener;
    }

    /**
     * clears existing tasks and sets new ones
     *
     * @param tasks list of tasks for today
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.clear();
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodayTasksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.today_task_recycler_view, viewGroup, false);

        return new TodayTasksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayTasksAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.text.setText(task.getText());
        holder.checkbox.setChecked(task.isChecked());
        holder.text.setEnabled(!task.isChecked());
        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkTaskListener.onTaskChecked(task.getId(), isChecked);
            holder.text.setEnabled(!isChecked);
        });

        //show empty view if last object so that the FAB doesn't block the task
        holder.emptyBottomView.setVisibility(position == tasks.size() - 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        CheckBox checkbox;
        View emptyBottomView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            text = view.findViewById(R.id.todayTaskTextView);
            checkbox = view.findViewById(R.id.todayTaskCheckBox);
            emptyBottomView = view.findViewById(R.id.emptyBottomView);
        }

    }

    /**
     * Register a callback to be invoked when the user clicks the task checkbox
     */
    public interface CheckTaskListener {
        void onTaskChecked(int taskId, boolean checked);
    }

}
