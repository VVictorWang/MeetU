package com.hackday.play.data;

import org.litepal.crud.DataSupport;

/**
 * Created by victor on 17-6-3.
 */

public class LocationInfor extends DataSupport {
    public static int NEED_ED = 3;
    public static int IS_SHARING = 2;
    public static int COMPLETED = 1;
    private String time;
    private float acuurancy;
    private String name;
    private int sex;
    private String building;
    private String addr;
    private double longtitude;
    private double latitude;

    private String detail;
    private int id;
    private String specific_infor;
    private String qq;
    private String phone;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }


    public void setAcuurancy(float acuurancy) {
        this.acuurancy = acuurancy;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSex() {

        return sex;
    }

    public String getDetail() {
        return detail;
    }

    public int getId() {
        return id;
    }

    public float getAcuurancy() {

        return acuurancy;
    }



    public void setSpecific_infor(String specific_infor) {
        this.specific_infor = specific_infor;
    }

    public String getSpecific_infor() {
        return specific_infor;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public void setBuilding(String building) {
        this.building = building;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTime() {
        return time;
    }


    public String getBuilding() {
        return building;
    }

    public String getAddr() {
        return addr;
    }
}
