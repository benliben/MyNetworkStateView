package com.android.benben.mynetworkstateview;

import android.app.Application;
import android.os.Handler;

/**
 * Time      2017/6/14 11:15 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class App extends Application {

    private static App mContext;

    private static Handler mMainThreadHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        mMainThreadHandler = new Handler();
    }

    public static App getApplication() {
        return mContext;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }
}