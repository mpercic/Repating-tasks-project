package com.example.repeatingtasks.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repeatingtasks.R;
import com.example.repeatingtasks.model.database.entities.Task;
import com.example.repeatingtasks.utils.TaskRepeatOnHelper;
import com.example.repeatingtasks.utils.TaskRepeatType;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private final List<Task> tasks;
    private DeleteTaskListener deleteTaskListener;
    private EditTaskListener editTaskListener;

    public TasksAdapter() {
        this.tasks = new ArrayList<>();
    }

    /**
     * clears existing tasks and sets new ones
     *
     * @param tasks list of tasks
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.clear();
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
        notifyDataSetChanged();
    }

    /**
     * Sets the delete task listener
     */
    public void setDeleteTaskListener(DeleteTaskListener deleteTaskListener) {
        this.deleteTaskListener = deleteTaskListener;
    }

    /**
     * Sets the edit task listener
     */
    public void setEditTaskListener(EditTaskListener editTaskListener) {
        this.editTaskListener = editTaskListener;
    }

    @NonNull
    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_recycler_view, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.text.setText(task.getText());
        holder.type.setText(TaskRepeatType.getNameByCode(task.getRepeatType()) + ":");
        if (task.getRepeatType() == TaskRepeatType.WEEKLY.getCode()) {
            holder.repeatOn.setText(TaskRepeatOnHelper.getWeeklyStringFromCodes(task.getRepeatOn()));
        } else {
            holder.repeatOn.setText(task.getRepeatOn());
        }
        if (deleteTaskListener != null) {
            holder.deleteButton.setOnClickListener(v -> deleteTaskListener.onDeleteButtonClicked(task.getId()));
        }
        if (editTaskListener != null) {
            holder.editButton.setOnClickListener(v -> editTaskListener.onEditButtonClicked(task.getId()));
        }

        //show empty view if last object so that the fab doesn't block the task
        holder.emptyBottomView.setVisibility(position == tasks.size() - 1 ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /**
     * Removes task by id
     *
     * @param taskId task id
     */
    public void removeTask(Integer taskId) {
        int position = -1;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == taskId) {
                tasks.remove(i);
                position = i;
                break;
            }
        }
        notifyItemRemoved(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text, type, repeatOn;
        ImageView editButton, deleteButton;
        View emptyBottomView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            text = view.findViewById(R.id.editTaskTextView);
            type = view.findViewById(R.id.editTaskTypeText);
            repeatOn = view.findViewById(R.id.editTaskRepeatOnText);
            editButton = view.findViewById(R.id.editTaskEditButton);
            deleteButton = view.findViewById(R.id.editTaskDeleteButton);
            emptyBottomView = view.findViewById(R.id.emptyBottomView);
        }

    }

    /**
     * Register a callback to be invoked when the user clicks the delete task button
     */
    public interface DeleteTaskListener {
        void onDeleteButtonClicked(int taskId);
    }

    /**
     * Register a callback to be invoked when the user clicks the edit task button
     */
    public interface EditTaskListener {
        void onEditButtonClicked(int taskId);
    }
}
