package com.example.repeatingtasks.view.fragments;

import androidx.fragment.app.Fragment;

public abstract class BaseRepeatingTaskFragment extends Fragment {

    /**
     * @return the selected period for when the task should be displayed
     */
    public abstract String getSelectedPeriod();
}
