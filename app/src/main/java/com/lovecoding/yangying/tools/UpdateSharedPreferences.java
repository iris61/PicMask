package com.lovecoding.yangying.tools;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yangying on 18/2/24.
 */

public class UpdateSharedPreferences {
    private static SharedPreferences.Editor editor = null;
    private static SharedPreferences pref = null;

    void UpdateSharedPreferences() {}
    public static void addStringKeyValuePair(String key, String value){
        editor = BaseAcitivity.getContext().getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void addIntKeyValuePair(String key, int value){
        editor = BaseAcitivity.getContext().getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void addBooleanKeyValuePair(String key, boolean value){
        editor = BaseAcitivity.getContext().getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getStringValue(String key) {
        pref = BaseAcitivity.getContext().getSharedPreferences("data",MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public static Boolean getBooleanValue(String key) {
        pref = BaseAcitivity.getContext().getSharedPreferences("data",MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public static int getIntValue(String key) {
        pref = BaseAcitivity.getContext().getSharedPreferences("data",MODE_PRIVATE);
        return pref.getInt(key, 0);
    }
}
