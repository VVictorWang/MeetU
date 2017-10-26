package com.hackday.play.data;

import com.hackday.play.utils.AppUtils;

/**
 * Created by victor on 17-6-3.
 */

public interface GlobaData {
    String IS_EDITED = "is_edited";
    String ID = "usr_id";
    String NICKNAME = "user_nickname";
    String PHONE = "user_phone";
    String QQ = "user_qq";
    String LOVE_LEVEL = "love_level";
    String AGE = "user_age";
    String LONGTITUDE = "user_longtitude";
    String LATITUDE = "user_latitude";

    String CLIENT_ID = "MeetU_client_88";
    String CLIENT_SECRET = "this_is_secret";

    String TOKEN = "user_token";

    String PASSWORD = "temp";

    public static final String PATH_DATA = AppUtils.getAppContext().getExternalCacheDir().getPath();
}
