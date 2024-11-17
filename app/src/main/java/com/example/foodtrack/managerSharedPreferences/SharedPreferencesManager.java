package com.example.foodtrack.managerSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private Context context;

    public SharedPreferencesManager(Context context) {
        this.context = context;
    }

    // Lấy SharedPreferences dựa trên tên file
    private SharedPreferences getSharedPreferences(String prefName) {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    // Lưu String
    public void saveString(String prefName, String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(prefName);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // Lấy String
    public String getString(String prefName, String key, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(prefName);
        return sharedPreferences.getString(key, defaultValue);
    }

    // Lưu Boolean
    public void saveBoolean(String prefName, String key, boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences(prefName);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // Lấy Boolean
    public boolean getBoolean(String prefName, String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(prefName);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Lưu Int
    public void saveInt(String prefName, String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences(prefName);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    // Lấy Int
    public int getInt(String prefName, String key, int defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(prefName);
        return sharedPreferences.getInt(key, defaultValue);
    }

    // Xóa dữ liệu theo key
    public void remove(String prefName, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(prefName);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    // Xóa toàn bộ dữ liệu trong một SharedPreferences
    public void clear(String prefName) {
        SharedPreferences sharedPreferences = getSharedPreferences(prefName);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
