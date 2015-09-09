package com.oneous.currencyconverter.currencyconverter.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by INSTRUCTOR on 9/9/2015.
 */
public class SharedPreferenceUtils {
    private static final String SHARED_PREFERENE_NAME = "com.oneous.currencyconverter";

    public static void saveData(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREFERENE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getData(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREFERENE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, null);
    }
}
