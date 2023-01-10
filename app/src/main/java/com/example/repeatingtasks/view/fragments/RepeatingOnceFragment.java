package com.example.repeatingtasks.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.repeatingtasks.databinding.FragmentRepeatingOnceBinding;
import com.example.repeatingtasks.utils.TaskRepeatOnHelper;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Fragment for selecting a period for a task that is displayed only once
 */
public class RepeatingOnceFragment extends BaseRepeatingTaskFragment {

    private static final String SELECT_PERIOD_KEY = "selected period key";
    private FragmentRepeatingOnceBinding binding;
    private Calendar selectedDate;

    public static RepeatingOnceFragment newInstance(String selectedPeriod) {
        Bundle args = new Bundle();
        if (selectedPeriod != null) {
            args.putString(SELECT_PERIOD_KEY, selectedPeriod);
        }
        RepeatingOnceFragment repeatingOnceFragment = new RepeatingOnceFragment();
        repeatingOnceFragment.setArguments(args);
        return repeatingOnceFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRepeatingOnceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        final Bundle args = getArguments();
        if (args != null && args.containsKey(SELECT_PERIOD_KEY)) {
            try {
                selectedDate = TaskRepeatOnHelper.getOnceCalendarFromString(args.getString(SELECT_PERIOD_KEY));
                binding.repeatingOnceCalendarView.setDate(selectedDate.getTimeInMillis());
            } catch (ParseException e) {
                e.printStackTrace();
                selectedDate = Calendar.getInstance();
            }
        } else {
            selectedDate = Calendar.getInstance();
        }

        return view;
    }

    @Override
    public String getSelectedPeriod() {
        return TaskRepeatOnHelper.getOnceStringFromCalendar(selectedDate);
    }

    @Override
    public void onResume() {
        binding.repeatingOnceCalendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> selectedDate.set(year, month, dayOfMonth));
        super.onResume();
    }

    @Override
    public void onPause() {
        binding.repeatingOnceCalendarView.setOnDateChangeListener(null);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}