package com.example.repeatingtasks.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String SHARED_PREFERENCES_NAME = "repeatingTaskSharedPreferences";
    private static final String LAST_CHECKED_DATE_KEY = "last checked date";

    private static SharedPreferencesManager instance;
    private String lastCheckedDate;


    public static SharedPreferencesManager getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesManager();
        }
        return instance;
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * loads all data saved in shared preferences
     */
    public void loadData(Context context) {
        lastCheckedDate = getSharedPreferences(context).getString(LAST_CHECKED_DATE_KEY, "");
    }

//todo: maybe think of a better name for this
    /**
     * @return date on which the data was last checked
     */
    public String getLastCheckedDate() {
        return lastCheckedDate;
    }

    /**
     * Set the date that on which the data was checked
     *
     * @param todayDate date of check
     */
    public void setLastCheckedDate(String todayDate, Context context) {
        this.lastCheckedDate = todayDate;
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(LAST_CHECKED_DATE_KEY, todayDate);
        editor.apply();
    }
}
