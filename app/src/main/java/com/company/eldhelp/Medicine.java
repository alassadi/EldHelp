package com.company.eldhelp;

/**
 * Created by fatih on 2018-05-02.
 */

public class Medicine {
    private String name;
    private String time;

    public Medicine(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
