package com.android.benben.mynetworkstateview;

import java.util.List;

/**
 * Time      2017/6/14 16:31 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public interface PermissionListener {
    void onGranted();

    void onDenied(List<String> deniedPermissions);
}
