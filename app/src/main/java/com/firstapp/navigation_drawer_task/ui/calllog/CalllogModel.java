package com.firstapp.navigation_drawer_task.ui.calllog;

import android.net.Uri;

public class CalllogModel {
    String name,number,time,duration,type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CalllogModel(String name, String number, String time, String duration, String type) {
        this.name = name;
        this.number = number;
        this.time = time;
        this.duration = duration;
        this.type = type;
    }
}
