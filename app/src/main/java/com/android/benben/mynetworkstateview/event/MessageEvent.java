package com.android.benben.mynetworkstateview.event;

/**
 * Time      2017/6/14 15:58 .
 * Author   : LiYuanXiong.
 * Content  : 定义消息事件
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
