package com.example.repeatingtasks.utils;

public enum TaskRepeatType {
    ONCE, DAILY, WEEKLY, MONTHLY;

    private int code;

    static {
        //Codes saved in database. Do not change this codes without changing the saved values in the migration
        ONCE.code = 0;
        DAILY.code = 1;
        WEEKLY.code = 2;
        MONTHLY.code = 3;
    }

    public int getCode() {
        return code;
    }


    public static String getNameByCode(int code) {
        String name = "";
        //todo: strings should be in xml, add that at some point
        switch (code) {
            case 0:
                name = "Once";
                break;
            case 1:
                name = "Daily";
                break;
            case 2:
                name = "Weekly";
                break;
            case 3:
                name = "Monthly";
                break;
        }
        return name;
    }
}
