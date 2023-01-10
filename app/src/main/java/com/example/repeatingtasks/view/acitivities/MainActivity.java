package com.example.repeatingtasks.view.acitivities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.repeatingtasks.databinding.ActivityMainBinding;
import com.example.repeatingtasks.model.SharedPreferencesManager;
import com.example.repeatingtasks.utils.ViewUtils;
import com.example.repeatingtasks.view.adapters.TodayTasksAdapter;
import com.example.repeatingtasks.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TodayTasksAdapter todayTasksAdapter;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferencesManager.getInstance().loadData(this);
        mainViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(MainViewModel.class);

        todayTasksAdapter = new TodayTasksAdapter();
        binding.todayTasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.todayTasksRecyclerView.setAdapter(todayTasksAdapter);
        binding.todayTasksRecyclerView.setHasFixedSize(true);

        mainViewModel.getTodaysTasks().observe(this, tasks -> {
            todayTasksAdapter.setTasks(tasks);
        });
        mainViewModel.getError().observe(this, customError -> ViewUtils.showErrorDialog(customError, this));
    }

    @Override
    protected void onResume() {
        //todo: could add a check here to see if anything has changed so that it loads tasks only if something is different
        mainViewModel.loadTasks();
        binding.editTasksFab.setOnClickListener(view -> {
            Intent intent = new Intent(this, TasksActivity.class);
            startActivity(intent);
        });
        todayTasksAdapter.setCheckTaskListener((taskId, checked) -> mainViewModel.setTaskChecked(taskId, checked));
        super.onResume();
    }

    @Override
    protected void onPause() {
        binding.editTasksFab.setOnClickListener(null);
        todayTasksAdapter.setCheckTaskListener(null);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mainViewModel.dispose();
        super.onDestroy();
    }
}