package com.example.repeatingtasks.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repeatingtasks.R;
import com.example.repeatingtasks.utils.TaskRepeatOnHelper;

/**
 * Fragment for selecting a period for a task that is repeated daily
 */
public class RepeatingDailyFragment extends BaseRepeatingTaskFragment {

    public static BaseRepeatingTaskFragment newInstance() {
        return new RepeatingDailyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repeating_daily, container, false);
    }

    @Override
    public String getSelectedPeriod() {
        return TaskRepeatOnHelper.getDailyString();
    }
}