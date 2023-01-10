package com.example.repeatingtasks.utils;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskRepeatOnHelperTest {


    @Test
    public void returnStringDate_When_GivenCalendarDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        Calendar date = Calendar.getInstance();
        date.set(2022, 0, 0);
        assertThat(TaskRepeatOnHelper.getOnceStringFromCalendar(date)).isEqualTo(sdf.format(date.getTime()));
    }

    @Test
    public void returnCalendar_When_GivenStringDate() throws ParseException {
        String date = "15.05.2022";
        Calendar calendar = TaskRepeatOnHelper.getOnceCalendarFromString(date);
        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2022);
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(4);
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(15);
    }

    @Test(expected = ParseException.class)
    public void parseException_When_GivenWrongStringDateFormat() throws ParseException {
        String date = "aaa";
        TaskRepeatOnHelper.getOnceCalendarFromString(date);
    }

    @Test
    public void returnWeekDaysString_When_GivenListOfWeekDays(){
        List<Integer> days = new ArrayList<>();
        days.add(0);
        days.add(2);
        days.add(3);
        days.add(5);
        String daysString = "0 - 2 - 3 - 5";
        assertThat(TaskRepeatOnHelper.getWeeklyCodeString(days)).isEqualTo(daysString);
    }

    @Test
    public void returnWeekDaysList_When_GivenStringOfWeekDays(){
        String daysString = "0 - 2 - 3 - 5";
        List<Integer> days = TaskRepeatOnHelper.getListFromCodeString(daysString);
        assertThat(days.size()).isEqualTo(4);
        assertThat(days.get(0)).isEqualTo(0);
        assertThat(days.get(1)).isEqualTo(2);
        assertThat(days.get(2)).isEqualTo(3);
        assertThat(days.get(3)).isEqualTo(5);
    }

    @Test
    public void returnDayOfWeekCode_When_GivenDate(){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        assertThat(TaskRepeatOnHelper.getWeeklyCodeForDay(date)).isEqualTo("0");
        date.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        assertThat(TaskRepeatOnHelper.getWeeklyCodeForDay(date)).isEqualTo("2");
        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        assertThat(TaskRepeatOnHelper.getWeeklyCodeForDay(date)).isEqualTo("6");
    }

}