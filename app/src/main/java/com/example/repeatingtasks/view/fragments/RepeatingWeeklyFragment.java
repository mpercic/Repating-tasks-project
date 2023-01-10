package com.example.repeatingtasks.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repeatingtasks.R;
import com.example.repeatingtasks.databinding.FragmentRepeatingWeeklyBinding;
import com.example.repeatingtasks.utils.TaskRepeatOnHelper;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for selecting a period for a task that is repeated weekly
 */
public class RepeatingWeeklyFragment extends BaseRepeatingTaskFragment {

    private static final String SELECT_PERIOD_KEY = "selected period key";
    private FragmentRepeatingWeeklyBinding binding;
    private List<Integer> daysSelected;

    public static RepeatingWeeklyFragment newInstance(String selectedPeriod) {
        Bundle args = new Bundle();
        if (selectedPeriod != null) {
            args.putString(SELECT_PERIOD_KEY, selectedPeriod);
        }
        RepeatingWeeklyFragment repeatingWeeklyFragment = new RepeatingWeeklyFragment();
        repeatingWeeklyFragment.setArguments(args);
        return repeatingWeeklyFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRepeatingWeeklyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        daysSelected = new ArrayList<>();
        final Bundle args = getArguments();
        if (args != null && args.containsKey(SELECT_PERIOD_KEY)) {
            List<Integer> selectedDaysTemp = TaskRepeatOnHelper.getListFromCodeString(args.getString(SELECT_PERIOD_KEY));
            if (selectedDaysTemp.contains(0)) {
                changeButtonSelection(binding.weeklyButtonMon, 0);
            }
            if (selectedDaysTemp.contains(1)) {
                changeButtonSelection(binding.weeklyButtonTue, 1);
            }
            if (selectedDaysTemp.contains(2)) {
                changeButtonSelection(binding.weeklyButtonWen, 2);
            }
            if (selectedDaysTemp.contains(3)) {
                changeButtonSelection(binding.weeklyButtonThu, 3);
            }
            if (selectedDaysTemp.contains(4)) {
                changeButtonSelection(binding.weeklyButtonFri, 4);
            }
            if (selectedDaysTemp.contains(5)) {
                changeButtonSelection(binding.weeklyButtonSat, 5);
            }
            if (selectedDaysTemp.contains(6)) {
                changeButtonSelection(binding.weeklyButtonSun, 6);
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        binding.weeklyButtonMon.setOnClickListener(v -> changeButtonSelection(binding.weeklyButtonMon, 0));
        binding.weeklyButtonTue.setOnClickListener(v -> changeButtonSelection(binding.weeklyButtonTue, 1));
        binding.weeklyButtonWen.setOnClickListener(v -> changeButtonSelection(binding.weeklyButtonWen, 2));
        binding.weeklyButtonThu.setOnClickListener(v -> changeButtonSelection(binding.weeklyButtonThu, 3));
        binding.weeklyButtonFri.setOnClickListener(v -> changeButtonSelection(binding.weeklyButtonFri, 4));
        binding.weeklyButtonSat.setOnClickListener(v -> changeButtonSelection(binding.weeklyButtonSat, 5));
        binding.weeklyButtonSun.setOnClickListener(v -> changeButtonSelection(binding.weeklyButtonSun, 6));
        super.onResume();
    }

    @Override
    public void onPause() {
        binding.weeklyButtonMon.setOnClickListener(null);
        binding.weeklyButtonTue.setOnClickListener(null);
        binding.weeklyButtonWen.setOnClickListener(null);
        binding.weeklyButtonThu.setOnClickListener(null);
        binding.weeklyButtonFri.setOnClickListener(null);
        binding.weeklyButtonSat.setOnClickListener(null);
        binding.weeklyButtonSun.setOnClickListener(null);
        super.onPause();
    }


    @Override
    public String getSelectedPeriod() {
        return TaskRepeatOnHelper.getWeeklyCodeString(daysSelected);
    }

    /**
     * Should be called when a button for the day of the week is clicked. Adds that day to the selected period list
     *
     * @param button view of the button that is clicked
     * @param day    integer representing day of the week starting from monday: 0 - monday, 1 - tuesday ... 6 - sunday
     */
    private void changeButtonSelection(MaterialButton button, int day) {
        button.setSelected(!button.isSelected());
        if (button.isSelected()) {
            button.setBackgroundColor(getResources().getColor(R.color.design_default_color_secondary, null));
            daysSelected.add(day);
        } else {
            button.setBackgroundColor(0);
            daysSelected.remove((Object) day);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}