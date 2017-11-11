package com.hackday.play.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

@Entity(tableName = "user")
public class UserInfo {
    /**
     * _id : No002
     * nickname : 第一个
     * phone : 15824578908
     * qq : 643708967
     * age : 18
     * loveLevel : 0
     * needs : ["59d9f08fffc15019a717e10c","59d9f098ffc15019a717e10d"]
     */

    private String nickname;
    @PrimaryKey
    private String phone;

    private String qq;
    private int loveLevel;
    private List<String> needs;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }



    public int getLoveLevel() {
        return loveLevel;
    }

    public void setLoveLevel(int loveLevel) {
        this.loveLevel = loveLevel;
    }

    public List<String> getNeeds() {
        return needs;
    }

    public void setNeeds(List<String> needs) {
        this.needs = needs;
    }
}
