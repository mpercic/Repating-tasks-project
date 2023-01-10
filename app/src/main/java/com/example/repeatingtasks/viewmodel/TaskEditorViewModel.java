package com.example.repeatingtasks.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.repeatingtasks.R;
import com.example.repeatingtasks.model.database.entities.Task;
import com.example.repeatingtasks.model.repositories.TaskRepo;
import com.example.repeatingtasks.utils.CustomError;
import com.example.repeatingtasks.utils.TaskRepeatType;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TaskEditorViewModel extends BaseAndroidViewModel {

    private final TaskRepo taskRepo;
    private MutableLiveData<Boolean> closeActivity;
    private MutableLiveData<Task> savedTask;

    public TaskEditorViewModel(@NonNull Application application) {
        super(application);
        taskRepo = new TaskRepo(application);
    }

    /**
     * @return MutableLiveData with a boolean that is true when the activity should be closed
     */
    public MutableLiveData<Boolean> getCloseActivity() {
        if (closeActivity == null) {
            closeActivity = new MutableLiveData<>();
        }
        return closeActivity;
    }

    /**
     * @return MutableLiveData with the saved Task loaded form the database
     */
    public MutableLiveData<Task> getSavedTask() {
        if (savedTask == null) {
            savedTask = new MutableLiveData<>();
        }
        return savedTask;
    }

    /**
     * Loads the task from the database
     *
     * @param taskId task id
     */
    public void loadSavedTask(int taskId) {
        addDisposable(taskRepo.getTaskById(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onSuccess
                        task -> {
                            savedTask.setValue(task);
                        },
                        //onError
                        throwable -> {
                            error.setValue(new CustomError(throwable, R.string.error_getting_task));
                        }
                )
        );
    }

    /**
     * Inserts the given task into the database
     *
     * @param text           task text
     * @param taskRepeatType task repeat type
     * @param repeatOn       when the task should be repeated on
     */
    public void insertNewTask(String text, TaskRepeatType taskRepeatType, String repeatOn) {
        addDisposable(taskRepo.insertNewTask(text, taskRepeatType, repeatOn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onComplete
                        () -> {
                            closeActivity.setValue(true);
                        },
                        //onError
                        throwable -> {
                            error.setValue(new CustomError(throwable, R.string.error_inserting_task));
                        }
                )
        );
    }

    /**
     * Edits the given task in the database
     *
     * @param taskId         id of the task that will be edited
     * @param text           task text
     * @param taskRepeatType task repeat type
     * @param repeatOn       when the task should be repeated on
     */
    public void editTask(int taskId, String text, TaskRepeatType taskRepeatType, String repeatOn) {
        addDisposable(taskRepo.editTask(taskId, text, taskRepeatType, repeatOn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onComplete
                        () -> {
                            closeActivity.setValue(true);
                        },
                        //onError
                        throwable -> {
                            error.setValue(new CustomError(throwable, R.string.error_updating_task));
                        }
                )
        );
    }


}
