package com.hackday.play.bean;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NeedInfo {

    /**
     * _id : 59dcea28ffc15062181599c8
     * creator_phone : 15824578911
     * desc : 带带我把
     * continue_time : 10
     * sex : 男
     * longitude : 121.2
     * latitude : 134.3
     * location : 华中科技大学
     * destination : 东一食堂
     * create_time : 1507650088
     * status : 0
     * helper_phone :
     */

    private String _id;
    private String creator_phone;
    private String desc;
    private String continue_time;
    private String sex;
    private double longitude;
    private double latitude;
    private String location;
    private String destination;
    private long created_time;
    private int status;
    private String helper_phone;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreator_phone() {
        return creator_phone;
    }

    public void setCreator_phone(String creator_phone) {
        this.creator_phone = creator_phone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContinue_time() {
        return continue_time;
    }

    public void setContinue_time(String continue_time) {
        this.continue_time = continue_time;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getCreate_time() {
        return created_time;
    }

    public void setCreate_time(long create_time) {
        this.created_time = create_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHelper_phone() {
        return helper_phone;
    }

    public void setHelper_phone(String helper_phone) {
        this.helper_phone = helper_phone;
    }
}
