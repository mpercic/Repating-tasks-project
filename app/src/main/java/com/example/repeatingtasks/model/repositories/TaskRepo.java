package com.example.repeatingtasks.model.repositories;

import android.app.Application;

import com.example.repeatingtasks.model.SharedPreferencesManager;
import com.example.repeatingtasks.model.database.RepeatingTasksDatabase;
import com.example.repeatingtasks.model.database.daos.TaskDao;
import com.example.repeatingtasks.model.database.entities.Task;
import com.example.repeatingtasks.utils.TaskRepeatOnHelper;
import com.example.repeatingtasks.utils.TaskRepeatType;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public class TaskRepo {
    TaskDao taskDao;
    Application application;

    public TaskRepo(Application application) {
        RepeatingTasksDatabase db = RepeatingTasksDatabase.getDatabase(application);
        this.application = application;
        taskDao = db.taskDao();
    }

    /**
     * Inserts a new task in the database
     *
     * @param text           task text
     * @param taskRepeatType task repat type, use values form {@link com.example.repeatingtasks.utils.TaskRepeatType}
     * @param repeatOn       when the task will be repeated, use values form {@link com.example.repeatingtasks.utils.TaskRepeatOnHelper}
     */
    public Completable insertNewTask(String text, TaskRepeatType taskRepeatType, String repeatOn) {
        return Completable.fromAction(() -> {
            Task task = new Task(text, taskRepeatType.getCode(), repeatOn, false);
            taskDao.insert(task);
        });
    }

    /**
     * Edits the task with the given id
     *
     * @param taskId         id of the task that will be edited
     * @param text           task text
     * @param taskRepeatType task repat type, use values form {@link com.example.repeatingtasks.utils.TaskRepeatType}
     * @param repeatOn       when the task will be repeated, use values form {@link com.example.repeatingtasks.utils.TaskRepeatOnHelper}
     */
    public Completable editTask(int taskId, String text, TaskRepeatType taskRepeatType, String repeatOn) {
        return Completable.fromAction(() -> {
            taskDao.update(taskId, text, taskRepeatType.getCode(), repeatOn);
        });
    }

    /**
     * @return all tasks ordered by latest added
     */
    public Maybe<List<Task>> getAllTasks() {
        return taskDao.getAllTasksOrderByLatestMaybe();
    }

    /**
     * @return all task for today
     */
    public Maybe<List<Task>> getTasksForToday() {
        return Maybe.create(emitter -> {
            List<Task> tasks = new ArrayList<>();
            //todo: check how this will work for different time zones
            Calendar today = Calendar.getInstance();

            //every day reset the checked tasks
            if (!StringUtils.equals(SharedPreferencesManager.getInstance().getLastCheckedDate(), TaskRepeatOnHelper.getOnceStringFromCalendar(today))) {
                taskDao.resetAllTaskChecked();
                SharedPreferencesManager.getInstance().setLastCheckedDate(TaskRepeatOnHelper.getOnceStringFromCalendar(today), application);
            }

            List<Task> once = taskDao.getOnceTasksForDate(TaskRepeatOnHelper.getOnceStringFromCalendar(today), TaskRepeatType.ONCE.getCode());
            if (once != null && once.size() > 0) {
                tasks.addAll(once);
            }

            List<Task> weekly = taskDao.getWeeklyTasksForDayOfWeek(TaskRepeatOnHelper.getWeeklyCodeForDay(today), TaskRepeatType.WEEKLY.getCode());
            if (weekly != null && weekly.size() > 0) {
                tasks.addAll(weekly);
            }

            List<Task> daily = taskDao.getDailyTasks(TaskRepeatOnHelper.getDailyString(), TaskRepeatType.DAILY.getCode());
            if (daily != null && daily.size() > 0) {
                tasks.addAll(daily);
            }

            if (tasks.isEmpty()) {
                emitter.onComplete();
            } else {
                emitter.onSuccess(tasks);
            }
        });

    }

    /**
     * Removes the task with the given id from the database
     *
     * @param taskId task id
     */
    public Completable deleteTask(int taskId) {
        return Completable.fromAction(() -> {
            taskDao.deleteTaskWithId(taskId);
        });
    }

    /**
     * @param taskId task id
     * @return task with the given id
     */
    public Single<Task> getTaskById(int taskId) {
        return taskDao.getTaskByIdSingle(taskId);
    }

    /**
     * Sets the given task 'checked' status
     *
     * @param taskId  task id
     * @param checked true if checked, false if not
     */
    public Completable setTaskChecked(int taskId, boolean checked) {
        return Completable.fromAction(() -> taskDao.setTaskChecked(taskId, checked));
    }
}
