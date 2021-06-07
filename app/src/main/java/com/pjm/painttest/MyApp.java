package com.pjm.painttest;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class MyApp extends Application {

    private Context mContext;
    private static MyApp instance;
    public static Handler handler;
    public static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        handler = new Handler(Looper.getMainLooper());
        mainThreadId = android.os.Process.myTid();//获取当前线程的id
        instance = this;
    }

    public static MyApp getInstance() {
        if(instance == null){
            instance = new MyApp();
        }
        return instance;
    }

    public void post(Runnable runnable){
        if(runnable != null){
            handler.post(runnable);
        }
    }


}
