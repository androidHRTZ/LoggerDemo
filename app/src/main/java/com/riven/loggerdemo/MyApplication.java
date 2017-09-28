package com.riven.loggerdemo;

import android.app.Application;

/**
 * Created by Riven on 2017/9/26.
 * Email : 1819485687@qq.com
 */

public class MyApplication extends Application {

    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }
}
