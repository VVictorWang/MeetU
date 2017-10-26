package com.hackday.play.data;

/**
 * Created by victor on 17-6-4.
 */

public class CaseInfor {
    public static int NEED_ED = 0;
    public static int IS_SHARING = 1;
    public static int COMPLETED = 2;
    private int status;
    private String other_name;
    private String time;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
