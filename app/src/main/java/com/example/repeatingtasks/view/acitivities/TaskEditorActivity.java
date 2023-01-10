package com.example.repeatingtasks.view.acitivities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.repeatingtasks.R;
import com.example.repeatingtasks.databinding.ActivityTaskEditorBinding;
import com.example.repeatingtasks.utils.TaskRepeatType;
import com.example.repeatingtasks.utils.ViewUtils;
import com.example.repeatingtasks.view.fragments.BaseRepeatingTaskFragment;
import com.example.repeatingtasks.view.fragments.RepeatingDailyFragment;
import com.example.repeatingtasks.view.fragments.RepeatingOnceFragment;
import com.example.repeatingtasks.view.fragments.RepeatingWeeklyFragment;
import com.example.repeatingtasks.viewmodel.TaskEditorViewModel;

public class TaskEditorActivity extends AppCompatActivity {

    public static final String TASK_ID = "task id";

    private ActivityTaskEditorBinding binding;
    private TaskRepeatType selectedTaskRepeatType;
    private BaseRepeatingTaskFragment baseRepeatingTaskFragment;
    private TaskEditorViewModel taskEditorViewModel;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //if there is a taskId it means the user is editing an existing task, if there isn't(id = -1) then the user is creating a new task
        taskId = getIntent().getIntExtra(TASK_ID, -1);

        taskEditorViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(TaskEditorViewModel.class);
        taskEditorViewModel.getCloseActivity().observe(this, close -> {
            if (close) {
                Toast.makeText(this, getResources().getString(R.string.task_saved), Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        taskEditorViewModel.getError().observe(this, customError -> ViewUtils.showErrorDialog(customError, this));

        taskEditorViewModel.getSavedTask().observe(this, task -> {
            binding.taskText.setText(task.getText());
            if (task.getRepeatType() == TaskRepeatType.ONCE.getCode()) {
                binding.onceRadioButton.setChecked(true);
                selectedTaskRepeatType = TaskRepeatType.ONCE;
            } else if (task.getRepeatType() == TaskRepeatType.DAILY.getCode()) {
                binding.dailyRadioButton.setChecked(true);
                selectedTaskRepeatType = TaskRepeatType.DAILY;
            } else if (task.getRepeatType() == TaskRepeatType.WEEKLY.getCode()) {
                binding.weeklyRadioButton.setChecked(true);
                selectedTaskRepeatType = TaskRepeatType.WEEKLY;
            } else if (task.getRepeatType() == TaskRepeatType.MONTHLY.getCode()) {
                binding.monthlyRadioButton.setChecked(true);
                selectedTaskRepeatType = TaskRepeatType.MONTHLY;
            }
            changeFragments(selectedTaskRepeatType, task.getRepeatOn());

        });

        if (taskId != -1) {
            binding.newTaskCreateButton.setText(getResources().getString(R.string.save_button));
            taskEditorViewModel.loadSavedTask(taskId);
        }

    }

    @Override
    protected void onResume() {
        binding.onceRadioButton.setOnClickListener(this::onRepeatingTypeRadioButtonClicked);
        binding.dailyRadioButton.setOnClickListener(this::onRepeatingTypeRadioButtonClicked);
        binding.weeklyRadioButton.setOnClickListener(this::onRepeatingTypeRadioButtonClicked);
        binding.monthlyRadioButton.setOnClickListener(this::onRepeatingTypeRadioButtonClicked);
        binding.newTaskBackButton.setOnClickListener(view -> onBackPressed());
        binding.newTaskCreateButton.setOnClickListener(view -> {
            String repeatOn = baseRepeatingTaskFragment.getSelectedPeriod();
            if (taskId == -1) {
                taskEditorViewModel.insertNewTask(binding.taskText.getText().toString(), selectedTaskRepeatType, repeatOn);
            } else {
                taskEditorViewModel.editTask(taskId, binding.taskText.getText().toString(), selectedTaskRepeatType, repeatOn);
            }
        });
        super.onResume();
    }

    @Override
    protected void onPause() {
        binding.onceRadioButton.setOnClickListener(null);
        binding.dailyRadioButton.setOnClickListener(null);
        binding.weeklyRadioButton.setOnClickListener(null);
        binding.monthlyRadioButton.setOnClickListener(null);
        binding.newTaskBackButton.setOnClickListener(null);
        binding.newTaskCreateButton.setOnClickListener(null);
        super.onPause();
    }


    /**
     * Changes fragment to corresponding repeating type based on the radio button that the user clicked
     *
     * @param view Radio button that represents the fragment
     */
    private void onRepeatingTypeRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.onceRadioButton:
                if (checked && selectedTaskRepeatType != TaskRepeatType.ONCE) {
                    selectedTaskRepeatType = TaskRepeatType.ONCE;
                    changeFragments(selectedTaskRepeatType, null);
                }
                break;
            case R.id.dailyRadioButton:
                if (checked && selectedTaskRepeatType != TaskRepeatType.DAILY) {
                    selectedTaskRepeatType = TaskRepeatType.DAILY;
                    changeFragments(selectedTaskRepeatType, null);
                }
                break;
            case R.id.weeklyRadioButton:
                if (checked && selectedTaskRepeatType != TaskRepeatType.WEEKLY) {
                    selectedTaskRepeatType = TaskRepeatType.WEEKLY;
                    changeFragments(selectedTaskRepeatType, null);
                }
                break;
            case R.id.monthlyRadioButton:
                if (checked && selectedTaskRepeatType != TaskRepeatType.MONTHLY) {
                    selectedTaskRepeatType = TaskRepeatType.MONTHLY;
                    changeFragments(selectedTaskRepeatType, null);
                }
                break;
        }
    }

    /**
     * Replaces repeating type fragment and sets the select period if one was selected
     *
     * @param taskRepeatType repeating type
     * @param selectedPeriod selected period for that type or null if none were selected
     */
    private void changeFragments(TaskRepeatType taskRepeatType, String selectedPeriod) {
        switch (taskRepeatType) {
            case ONCE:
                baseRepeatingTaskFragment = RepeatingOnceFragment.newInstance(selectedPeriod);
                break;
            case DAILY:
                baseRepeatingTaskFragment = RepeatingDailyFragment.newInstance();
                break;
            case WEEKLY:
                baseRepeatingTaskFragment = RepeatingWeeklyFragment.newInstance(selectedPeriod);
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.addTaskFragmentView, baseRepeatingTaskFragment, null)
                .commit();
    }


    @Override
    protected void onDestroy() {
        taskEditorViewModel.dispose();
        super.onDestroy();
    }
}