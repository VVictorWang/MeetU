package com.hackday.play.bean;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class LoginResponse {
    /**
     * status : 1
     * token :
     * 3ba6cd4f063ee87045b578fbf854cbe0c121da0a4de60d3ce8b4f5faa622fcddede2fec1ed5c366887a747d0f305e8a7
     */

    private int status;
    private String token;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
