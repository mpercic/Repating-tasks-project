package com.example.repeatingtasks.model.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.repeatingtasks.model.database.entities.Task;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    /**
     * Updates the given task
     *
     * @param taskId     id of the task that will be updated
     * @param text       task text
     * @param repeatType task repeat on type, use values form {@link com.example.repeatingtasks.utils.TaskRepeatType}
     * @param repeatOn   when the task will be repeated, use values form {@link com.example.repeatingtasks.utils.TaskRepeatOnHelper}
     */
    @Query("UPDATE task SET text = :text, repeatType = :repeatType, repeatOn = :repeatOn WHERE id = :taskId")
    void update(int taskId, String text, int repeatType, String repeatOn);

    /**
     * @return All tasks ordered by latest added
     */
    @Query("SELECT * FROM task ORDER BY id DESC")
    Maybe<List<Task>> getAllTasksOrderByLatestMaybe();

    /**
     * @param date     of the task
     * @param onceCode code for the repeat type 'once', use values form {@link com.example.repeatingtasks.utils.TaskRepeatType}
     * @return list of tasks that are repeated once for the given date
     */
    @Query("SELECT * FROM task WHERE repeatType = :onceCode AND repeatOn = :date")
    List<Task> getOnceTasksForDate(String date, int onceCode);

    /**
     * @param dayOfWeekCode day of week code, use values form {@link com.example.repeatingtasks.utils.TaskRepeatOnHelper}
     * @param weeklyCode    code for the repeat type 'weekly', use values form {@link com.example.repeatingtasks.utils.TaskRepeatType}
     * @return List of tasks that are repeated weekly on the given day of the week
     */
    @Query("SELECT * FROM task WHERE repeatType = :weeklyCode AND repeatOn LIKE '%'||:dayOfWeekCode||'%'")
    List<Task> getWeeklyTasksForDayOfWeek(String dayOfWeekCode, int weeklyCode);

    /**
     * @param dailyString use values form {@link com.example.repeatingtasks.utils.TaskRepeatOnHelper}
     * @param dailyCode   code for the repeat type 'daily', use values form {@link com.example.repeatingtasks.utils.TaskRepeatType}
     * @return List of tasks that are repeated daily
     */
    @Query("SELECT * FROM task WHERE repeatType = :dailyCode AND repeatOn = :dailyString")
    List<Task> getDailyTasks(String dailyString, int dailyCode);

    /**
     * Deletes task with the given id
     *
     * @param taskId task id
     */
    @Query("DELETE FROM task WHERE id = :taskId")
    void deleteTaskWithId(int taskId);

    /**
     * @param taskId task id
     * @return task with the given id
     */
    @Query("SELECT * FROM task WHERE id =:taskId")
    Single<Task> getTaskByIdSingle(int taskId);

    /**
     * Sets the given task 'checked' status
     *
     * @param taskId  task id
     * @param checked true if checked, false if not
     */
    @Query("UPDATE task SET checked = :checked WHERE id = :taskId")
    void setTaskChecked(int taskId, boolean checked);

    /**
     * Sets all tasks 'checked' status to false
     */
    @Query("UPDATE task SET checked = 0")
    void resetAllTaskChecked();
}
