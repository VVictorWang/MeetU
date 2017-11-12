package com.hackday.play.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by victor on 11/11/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class TypeConverts {

    @TypeConverter
    public static List<String> stringToList(String needs) {
        String[] result = needs.split(" ");
        return Arrays.asList(result);
    }

    @TypeConverter
    public static String listToString(List<String> needs) {
        StringBuilder result = new StringBuilder();
        for (String item : needs) {
            result.append(item);
            result.append(" ");
        }
        return result.toString();
    }
}
