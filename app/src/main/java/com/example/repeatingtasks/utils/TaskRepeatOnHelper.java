package com.example.repeatingtasks.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskRepeatOnHelper {

    /**
     * Formats a Date into a string. Used for the task that with the repeat type 'once'
     */
    public static String getOnceStringFromCalendar(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        return sdf.format(date.getTime());
    }

    /**
     * @return a Calendar object representing the date in the given string. Used for the task that with the repeat type 'once'
     */
    public static Calendar getOnceCalendarFromString(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        cal.setTime(sdf.parse(date));
        return cal;
    }

    /**
     * @return repeat on string for the task with the repeat type 'daily'
     */
    public static String getDailyString() {
        return "daily";
    }

    /**
     * Used for the task that with the repeat type 'weekly'
     *
     * @param daysSelected list of integers representing the selected days in a week with 0 being monday, 1 tuesday and so on
     * @return string containing all the given days of the week
     */
    public static String getWeeklyCodeString(List<Integer> daysSelected) {
        StringBuilder repeatOnText = new StringBuilder();
        if (daysSelected.contains(0)) {
            repeatOnText.append("0 - ");
        }
        if (daysSelected.contains(1)) {
            repeatOnText.append("1 - ");
        }
        if (daysSelected.contains(2)) {
            repeatOnText.append("2 - ");
        }
        if (daysSelected.contains(3)) {
            repeatOnText.append("3 - ");
        }
        if (daysSelected.contains(4)) {
            repeatOnText.append("4 - ");
        }
        if (daysSelected.contains(5)) {
            repeatOnText.append("5 - ");
        }
        if (daysSelected.contains(6)) {
            repeatOnText.append("6 - ");
        }
        if (StringUtils.isEmpty(repeatOnText)) {
            return null;
        }
        return repeatOnText.substring(0, repeatOnText.length() - 3);
    }

    /**
     * Used for the task that with the repeat type 'weekly'
     *
     * @param codeString string containing days of the week
     * @return list of integers representing the days in a week with 0 being monday, 1 tuesday and so on
     */
    public static List<Integer> getListFromCodeString(String codeString) {
        List<Integer> daysSelected = new ArrayList<>();
        for (String code : StringUtils.split(codeString, " - ")) {
            daysSelected.add(Integer.parseInt(code));
        }
        return daysSelected;
    }

    /**
     * @return numeric value for the day of the week for the given day with 0 being monday, 1 tuesday and so on
     */
    public static String getWeeklyCodeForDay(Calendar day) {
        //Day off week starts from sunday with 1, this app uses codes that start from monday with 0
        int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek + 5) % 7 + "";
    }

    /**
     * @param weeklyCodes numeric value for the day of the week for the given day with 0 being monday, 1 tuesday and so on
     * @return name of the given day of the week
     */
    public static String getWeeklyStringFromCodes(String weeklyCodes) {
        StringBuilder repeatOnText = new StringBuilder();
        //todo: strings should be in xml, add that at some point
        if (StringUtils.contains(weeklyCodes, "0")) {
            repeatOnText.append("Monday - ");
        }
        if (StringUtils.contains(weeklyCodes, "1")) {
            repeatOnText.append("Tuesday - ");
        }
        if (StringUtils.contains(weeklyCodes, "2")) {
            repeatOnText.append("Wednesday - ");
        }
        if (StringUtils.contains(weeklyCodes, "3")) {
            repeatOnText.append("Thursday - ");
        }
        if (StringUtils.contains(weeklyCodes, "4")) {
            repeatOnText.append("Friday - ");
        }
        if (StringUtils.contains(weeklyCodes, "5")) {
            repeatOnText.append("Saturday - ");
        }
        if (StringUtils.contains(weeklyCodes, "6")) {
            repeatOnText.append("Sunday - ");
        }
        if (StringUtils.isEmpty(repeatOnText)) {
            return null;
        }
        return repeatOnText.substring(0, repeatOnText.length() - 3);
    }

}
