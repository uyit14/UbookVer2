package com.ubook.ubookapp;

import android.app.Application;

public class BaseApplication extends Application {
    private static BaseApplication mInstance;
    public SharedPreferencesUtils sharedPreferencesUtils;

    public static BaseApplication getInstance() {
        if (mInstance == null) {
            mInstance = new BaseApplication();
        }
        return mInstance;
    }

    public BaseApplication() {
        super();
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
    }
}
