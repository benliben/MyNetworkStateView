package com.android.benben.mynetworkstateview.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Time      2017/6/14 15:36 .
 * Author   : LiYuanXiong.
 * Content  : Activity栈工具类
 */

public class ActivityUtils {

    private static Stack<Activity> mActivityStack;

    /**
     * 添加一个activity到堆栈中
     */
    public static void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 将一个activity从堆栈中移除
     *
     * @param activity 定都的activity
     */
    public static void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 移除所有的activity（退出程序）
     */
    public static void removeAllActivity() {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            for (Activity a : mActivityStack) {
                a.finish();
            }
        }
    }

    /**
     * 获取顶部的Activity
     * @return
     */
    public static Activity getTopActivity() {
        if (mActivityStack.isEmpty()) {
            return null;
        } else {
            return mActivityStack.get(mActivityStack.size() - 1);
        }
    }
}
