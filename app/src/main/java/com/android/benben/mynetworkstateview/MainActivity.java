package com.android.benben.mynetworkstateview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.benben.mynetworkstateview.view.NetworkStateView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NetworkStateView.OnRefreshListener {

    @InjectView(R.id.nsv_state_view)
    NetworkStateView mStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mStateView.setOnRefreshListener(this);
        mStateView.showLoading();
        showSuccess();

    }

    @OnClick({R.id.main_button_loading, R.id.main_button_error, R.id.main_button_no, R.id.main_button_empty})
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
        }

        showSuccess();
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
}
