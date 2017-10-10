package com.hackday.play.api;

import android.util.Log;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.R.attr.password;

/**
 * Created by victor on 10/10/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class JsonRequestBody {
    private static MediaType sMediaType = MediaType.parse("application/json");
    private JSONObject mJSONObject;

    public JsonRequestBody() {
        mJSONObject = new JSONObject();
    }

    public JsonRequestBody setUsername(String username) {
        try {
            mJSONObject.put("nickname", username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setPassword(String password) {
        try {
            mJSONObject.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setMyId(String myId) {
        try {
            mJSONObject.put("id", myId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setPhone(String phone) {
        try {
            mJSONObject.put("phone", phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setQq(String qq) {
        try {
            mJSONObject.put("qq", qq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setAge(int age) {
        try {
            mJSONObject.put("age", age);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public RequestBody create() {
        Log.d("@victor", mJSONObject.toString());
        return RequestBody.create(sMediaType, mJSONObject.toString());
    }
}
