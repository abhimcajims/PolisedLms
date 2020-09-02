package com.project.polishedlms.util;

import android.content.Context;
import android.content.SharedPreferences;


public class MySharedPreference {

    private static MySharedPreference instance;
    private transient SharedPreferences prefs;

    private MySharedPreference(Context context) {
        prefs = context.getSharedPreferences(context.getPackageName(),
                Context.MODE_PRIVATE);
    }

    public static MySharedPreference getInstance(Context context) {
        if (instance == null)
            instance = new MySharedPreference(context);
        return instance;
    }


    public boolean isLogin() {
        return prefs.contains(Constant.LOGIN_DATA);
    }

    public void saveUserData(String login_data) {
        prefs.edit().putString(Constant.LOGIN_DATA, login_data).apply();
    }

    public void saveStudentId(String student_id) {
        prefs.edit().putString(Constant.STUDENT_ID, student_id).apply();
    }

    public void saveStudentName(String student_name) {
        prefs.edit().putString(Constant.STUDENT_NAME, student_name).apply();
    }

    public void saveDeviceId(String uniqueID) {
        prefs.edit().putString(Constant.UNIQUE_ID, uniqueID).apply();
    }

    public void saveFirebaseToken(String token) {
        prefs.edit().putString(Constant.TOKEN, token).apply();
    }

    public String getStudentId() {
        return prefs.getString(Constant.STUDENT_ID, "");
    }

    public String getStudentName() {
        return prefs.getString(Constant.STUDENT_NAME, "");
    }

    public String getDeviceId() {
        return prefs.getString(Constant.UNIQUE_ID, "");
    }

    public String getUserData() {
        return prefs.getString(Constant.LOGIN_DATA, "");
    }

    public String getFirebaseToken() {
        return prefs.getString(Constant.TOKEN, "");
    }


    public void clearUserData() {
        prefs.edit().remove(Constant.LOGIN_DATA).apply();
    }

    public void clearStudentId() {
        prefs.edit().remove(Constant.STUDENT_ID).apply();
    }

    public void clearStudentName() {
        prefs.edit().remove(Constant.STUDENT_NAME).apply();
    }

    public void clearDeviceId() {
        prefs.edit().remove(Constant.UNIQUE_ID).apply();
    }

    public void clearFirebaseToken() {
        prefs.edit().remove(Constant.TOKEN).apply();
    }


}