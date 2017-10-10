package com.hackday.play.data;

import com.hackday.play.utils.AppUtils;

/**
 * Created by victor on 17-6-3.
 */

public interface GlobaData {
    String IS_EDITED = "is_edited";
    String ID = "usr_id";
    String NAME = "user_name";
    String PHONE = "user_phone";
    String QQ = "user_qq";
    String LOVE_LEVEL = "love_level";
    String LONGTITUDE = "longtitude";
    String LATITUDE = "latitude";

    String PASSWORD = "temp";

    public static final String PATH_DATA = AppUtils.getAppContext().getExternalCacheDir().getPath();
}
