package com.hackday.play.api;

import android.util.Log;

import com.hackday.play.data.GlobaData;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

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

    public JsonRequestBody setCreatorPhone(String creatorPhone) {
        try {
            mJSONObject.put("creator_phone", creatorPhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setDesc(String desc) {
        try {
            mJSONObject.put("desc", desc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setContinue(int continue_time) {
        try {
            mJSONObject.put("continue_time", continue_time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setSex(String sex) {
        try {
            mJSONObject.put("sex", sex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setLongitude(float longitude) {
        try {
            mJSONObject.put("longitude", longitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setLatitude(float latitude) {
        try {
            mJSONObject.put("latitude", latitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
    public JsonRequestBody setLocation(String location) {
        try {
            mJSONObject.put("location", location);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setHeplerPhone(String heplerPhone) {
        try {
            mJSONObject.put("helper_phone", heplerPhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setStatus(int status) {
        try {
            mJSONObject.put("status", status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonRequestBody setDestination(String destination) {
        try {
            mJSONObject.put("destination", destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    public JsonRequestBody addClient() {
        try {
            mJSONObject.put("client_id", GlobaData.CLIENT_ID);
            mJSONObject.put("client_secret", GlobaData.CLIENT_SECRET);
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
