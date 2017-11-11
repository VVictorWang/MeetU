package com.hackday.play.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hackday.play.db.UserDao;
import com.hackday.play.bean.UserInfo;

/**
 * Created by victor on 11/11/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

@Database(entities = {UserInfo.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {
    public abstract UserDao userDao();
}
