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

public class TasksViewModel extends BaseAndroidViewModel {

    private final TaskRepo taskRepo;
    private MutableLiveData<List<Task>> allTasks;
    private MutableLiveData<Integer> taskIdToRemove;


    public TasksViewModel(@NonNull Application application) {
        super(application);
        taskRepo = new TaskRepo(application);
    }

    /**
     * loads all the tasks from the database
     */
    public void loadTasks() {
        addDisposable(taskRepo.getAllTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onSuccess
                        tasks -> {
                            allTasks.setValue(tasks);
                        },
                        //onError
                        throwable -> {
                            error.setValue(new CustomError(throwable, R.string.error_loading_tasks));
                        },
                        //onCompleted
                        () -> {
                            allTasks.setValue(null);
                        }
                )
        );
    }

    /**
     * @return MutableLiveData with the list of all tasks
     */
    public MutableLiveData<List<Task>> getAllTasks() {
        if (allTasks == null) {
            allTasks = new MutableLiveData<>();
        }
        return allTasks;
    }

    /**
     * Removes the selected task form the database
     *
     * @param taskId id of the task that will be removed
     */
    public void deleteTask(int taskId) {
        addDisposable(taskRepo.deleteTask(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onCompleted
                        () -> {
                            taskIdToRemove.setValue(taskId);
                        },
                        //onError
                        throwable -> {
                            error.setValue(new CustomError(throwable, R.string.error_deleting_task));
                        }
                )
        );
    }

    /**
     * @return MutableLiveData with the id of the task that has been removed from the database
     */
    public MutableLiveData<Integer> getTaskIdToRemove() {
        if (taskIdToRemove == null) {
            taskIdToRemove = new MutableLiveData<>();
        }
        return taskIdToRemove;
    }

}
