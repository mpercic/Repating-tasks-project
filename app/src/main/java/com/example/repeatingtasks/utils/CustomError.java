package com.example.repeatingtasks.utils;

/**
 * Class for errors that has the error and a string resource id for the name/title of the error
 */
public class CustomError {

    private Throwable throwable;
    private int stringResourceId;


    public CustomError(Throwable throwable, int stringResourceId) {
        this.throwable = throwable;
        this.stringResourceId = stringResourceId;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }


    public int getStringResourceId() {
        return stringResourceId;
    }

    public void setStringResourceId(int stringResourceId) {
        this.stringResourceId = stringResourceId;
    }
}
