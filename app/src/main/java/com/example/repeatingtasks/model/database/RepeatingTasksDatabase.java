package com.example.repeatingtasks.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.repeatingtasks.model.database.daos.TaskDao;
import com.example.repeatingtasks.model.database.entities.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class RepeatingTasksDatabase extends RoomDatabase {

    private static volatile RepeatingTasksDatabase INSTANCE;

    public static RepeatingTasksDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RepeatingTasksDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RepeatingTasksDatabase.class, "repeating_tasks_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TaskDao taskDao();

}
