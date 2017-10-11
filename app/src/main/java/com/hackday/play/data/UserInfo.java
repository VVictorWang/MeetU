package com.hackday.play.data;

import java.util.List;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class UserInfo {
    /**
     * _id : No002
     * nickname : 第一个
     * phone : 15824578908
     * qq : 643708967
     * age : 18
     * love_level : 0
     * needs : ["59d9f08fffc15019a717e10c","59d9f098ffc15019a717e10d"]
     */

    private String nickname;
    private String phone;
    private String qq;
    private int love_level;
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



    public int getLove_level() {
        return love_level;
    }

    public void setLove_level(int love_level) {
        this.love_level = love_level;
    }

    public List<String> getNeeds() {
        return needs;
    }

    public void setNeeds(List<String> needs) {
        this.needs = needs;
    }
}
