package com.android.benben.mynetworkstateview;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.benben.mynetworkstateview.base.BaseActivity;
import com.android.benben.mynetworkstateview.event.MessageEvent;
import com.android.benben.mynetworkstateview.view.NetworkStateView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.nsv_state_view)
    NetworkStateView mStateView;

    @BindView(R.id.main_tv)
    TextView mTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    private void initView() {
        mStateView.setOnRefreshListener(this);
        mStateView.showLoading();
        showSuccess();

    }

    @OnClick({R.id.main_button_loading, R.id.main_button_error, R.id.main_button_no, R.id.main_button_empty, R.id.main_button_open, R.id.main_button_delivery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_button_loading:
                mStateView.showLoading();
                break;
            case R.id.main_button_error:
                mStateView.showError();
                break;
            case R.id.main_button_no:
                mStateView.showNoNetwork();
                break;
            case R.id.main_button_empty:
                mStateView.showEmpty();
                break;
            case R.id.main_button_open:
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "权限已经允许了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(List<String> deniedPermissions) {

                    }
                });
                break;
            case R.id.main_button_delivery:
                startActivity(new Intent(this, HomeActivity.class));
                break;
        }

        showSuccess();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        mTV.setText(messageEvent.getMessage());
    }

    @Override
    public void onRefresh() {
        mStateView.showLoading();
        showSuccess();
    }


    private void showSuccess() {
        App.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mStateView.showSuccess();
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
