package com.example.repeatingtasks.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.repeatingtasks.R;
import com.example.repeatingtasks.model.database.entities.Task;
import com.example.repeatingtasks.model.repositories.TaskRepo;
import com.example.repeatingtasks.utils.CustomError;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends BaseAndroidViewModel {

    private final TaskRepo taskRepo;
    private MutableLiveData<List<Task>> todaysTasks;

    public MainViewModel(@NonNull Application application) {
        super(application);
        taskRepo = new TaskRepo(application);
    }

    /**
     * Loads tasks for today
     */
    public void loadTasks() {
        addDisposable(taskRepo.getTasksForToday()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onSuccess
                        tasks -> {
                            todaysTasks.setValue(tasks);
                        },
                        //onError
                        throwable -> {
                            error.setValue(new CustomError(throwable, R.string.error_loading_tasks));
                        },
                        //onCompleted
                        () -> {
                            todaysTasks.setValue(null);
                        }
                )
        );
    }

    /**
     * Changes the checked status of the selected task
     *
     * @param taskId  task id
     * @param checked true if the task checked, false if it isn't
     */
    public void setTaskChecked(int taskId, boolean checked) {
        addDisposable(taskRepo.setTaskChecked(taskId, checked)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onSuccess
                        () -> {

                        },
                        //onError
                        throwable -> {
                            error.setValue(new CustomError(throwable, R.string.error_changing_task_checked));
                        }
                )
        );
    }

    /**
     * @return MutableLiveData with the list of tasks for today
     */
    public MutableLiveData<List<Task>> getTodaysTasks() {
        if (todaysTasks == null) {
            todaysTasks = new MutableLiveData<>();
        }
        return todaysTasks;
    }



}
