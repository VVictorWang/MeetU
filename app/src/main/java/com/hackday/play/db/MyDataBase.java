package com.hackday.play.db;

import android.arch.persistence.room.Database;
<<<<<<< HEAD
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

=======
import android.arch.persistence.room.RoomDatabase;

import com.hackday.play.db.UserDao;
>>>>>>> refs/remotes/origin/master
import com.hackday.play.bean.UserInfo;

/**
 * Created by victor on 11/11/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

@Database(entities = {UserInfo.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {
<<<<<<< HEAD
    abstract public UserDao userDao();

    private static MyDataBase instance;

    public static MyDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class,
                    "meetu.db").build();
        }
        return instance;

    }
=======
    public abstract UserDao userDao();
>>>>>>> refs/remotes/origin/master
}
