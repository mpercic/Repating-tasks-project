package com.example.repeatingtasks.model.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String text;

    private int repeatType;

    private String repeatOn;

    private boolean checked;

    public Task(String text, int repeatType, String repeatOn, boolean checked) {
        this.text = text;
        this.repeatType = repeatType;
        this.repeatOn = repeatOn;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(int repeatType) {
        this.repeatType = repeatType;
    }

    public String getRepeatOn() {
        return repeatOn;
    }

    public void setRepeatOn(String repeatOn) {
        this.repeatOn = repeatOn;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
