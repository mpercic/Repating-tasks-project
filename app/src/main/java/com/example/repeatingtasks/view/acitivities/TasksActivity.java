package com.example.repeatingtasks.view.acitivities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.repeatingtasks.R;
import com.example.repeatingtasks.databinding.ActivityTasksBinding;
import com.example.repeatingtasks.utils.ViewUtils;
import com.example.repeatingtasks.view.adapters.TasksAdapter;
import com.example.repeatingtasks.viewmodel.TasksViewModel;

public class TasksActivity extends AppCompatActivity {

    private ActivityTasksBinding binding;
    private TasksAdapter tasksAdapter;
    private TasksViewModel tasksViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTasksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tasksViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(TasksViewModel.class);

        tasksAdapter = new TasksAdapter();
        binding.tasksRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.tasksRecyclerview.setAdapter(tasksAdapter);
        binding.tasksRecyclerview.setHasFixedSize(true);

        tasksViewModel.getAllTasks().observe(this, tasks -> {
            tasksAdapter.setTasks(tasks);
        });
        tasksViewModel.getTaskIdToRemove().observe(this, taskId -> {
            if (taskId != null) {
                tasksAdapter.removeTask(taskId);
            }

        });
        tasksViewModel.getError().observe(this, customError -> ViewUtils.showErrorDialog(customError, this));
    }

    @Override
    protected void onResume() {
        //todo: could add a check here to see if anything has changed so that it loads tasks only if something is different
        tasksViewModel.loadTasks();
        tasksAdapter.setDeleteTaskListener(this::showDeleteTaskDialog);
        tasksAdapter.setEditTaskListener(taskId -> {
            Intent intent = new Intent(this, TaskEditorActivity.class);
            intent.putExtra(TaskEditorActivity.TASK_ID, taskId);
            startActivity(intent);
        });
        binding.newTaskFab.setOnClickListener(view -> {
            Intent intent = new Intent(this, TaskEditorActivity.class);
            startActivity(intent);
        });
        super.onResume();
    }

    @Override
    protected void onPause() {
        binding.newTaskFab.setOnClickListener(null);
        tasksAdapter.setDeleteTaskListener(null);
        tasksAdapter.setEditTaskListener(null);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        tasksViewModel.dispose();
        super.onDestroy();
    }

    /**
     * Open an alert dialog for the delting a tast
     *
     * @param taskId id of the task that is to be deleted
     */
    private void showDeleteTaskDialog(int taskId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.delete_task_question))
                .setPositiveButton(getResources().getString(R.string.yes_text), (dialog, which) -> tasksViewModel.deleteTask(taskId))
                .setNegativeButton(getResources().getString(R.string.no_text), null).show();
    }

}