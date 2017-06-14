package com.android.benben.mynetworkstateview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.benben.mynetworkstateview.base.BaseActivity;
import com.android.benben.mynetworkstateview.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

/**
 * Time      2017/6/14 14:53 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @OnClick({R.id.home_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_button:
                EventBus.getDefault().post(new MessageEvent("HelloWord"));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
