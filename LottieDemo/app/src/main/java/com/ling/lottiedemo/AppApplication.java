package com.ling.lottiedemo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by acer on 2016/11/6.
 */

public class AppApplication extends Application {


    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化context
        context = getApplicationContext();
        //初始化handler
        handler = new Handler();
        //获取当前线程ID(主线程ID)
        mainThreadId = android.os.Process.myTid();
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}
