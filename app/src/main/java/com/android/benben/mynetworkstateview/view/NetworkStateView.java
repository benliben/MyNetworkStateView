package com.android.benben.mynetworkstateview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.benben.mynetworkstateview.R;
import com.android.benben.mynetworkstateview.utils.UIUtils;

/**
 * Time      2017/6/14 10:59 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class NetworkStateView extends LinearLayout {
    /*当前的加载状态*/
    private int mCurrentState;
    private static final int STATE_SUCCESS = 0;//加载成功
    private static final int STATE_LOADING = 1;//加载中
    private static final int STATE_NETWORK_ERROR = 2;//加载失败
    private static final int STATE_NO_NETWORK = 3;//没有网络
    private static final int STATE_EMPTY = 4;//没有数据


    /*布局添加位置*/
    private static final int VIEW_POSITION = 0;
    private int mLoadingViewId;//加载中的布局id

    private int mErrorViewId;//加载失败的布局id
    private int mErrorImageId;//加载错误的布局图片
    private String mErrorText;//加载中的布局文字

    private int mNoNetworkViewId;//没有数据的布局id
    private int mNoNetworkImageId;//没有数据的布局图片
    private String mNoNetworkText;//没有数据的布局文字


    private int mEmptyViewId;//没有网络的布局id
    private int mEmptyImageId;//没有数据的布局图片
    private String mEmptyText;//没有数据的布局文字

    private int mRefreshViewId;//刷新的ImageView图片id

    private int mTextColor;//文字大小
    private int mTextSize;//文字颜色

    private View mLoadingView;//加载中的布局
    private View mErrorView;//加载失败的布局
    private View mNoNetworkView;//没有网络的布局
    private View mEmptyView;//没有数据的布局

    private LayoutInflater mInflater;
    private LayoutParams params;

    private OnRefreshListener mRefreshListener;



    public NetworkStateView(Context context) {
        this(context,null);
    }

    public NetworkStateView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NetworkStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NetworkStateView, defStyleAttr, R.style.NetworkStateView_Style);

        mLoadingViewId = typedArray.getResourceId(R.styleable.NetworkStateView_loadingView, R.layout.view_loading);

        mErrorViewId = typedArray.getResourceId(R.styleable.NetworkStateView_errorView, R.layout.view_network_error);
        mErrorImageId = typedArray.getResourceId(R.styleable.NetworkStateView_nsvErrorImage, NO_ID);
        mErrorText = typedArray.getString(R.styleable.NetworkStateView_nsvErrorText);

        mNoNetworkViewId = typedArray.getResourceId(R.styleable.NetworkStateView_noNetworkView, R.layout.view_no_network);
        mNoNetworkImageId = typedArray.getResourceId(R.styleable.NetworkStateView_nsvNoNetworkImage, NO_ID);
        mNoNetworkText = typedArray.getString(R.styleable.NetworkStateView_nsvNoNetworkText);

        mEmptyViewId = typedArray.getResourceId(R.styleable.NetworkStateView_emptyView, R.layout.view_empty);
        mEmptyImageId = typedArray.getResourceId(R.styleable.NetworkStateView_nsvEmptyImage, NO_ID);
        mEmptyText = typedArray.getString(R.styleable.NetworkStateView_nsvEmptyText);

        mRefreshViewId = typedArray.getResourceId(R.styleable.NetworkStateView_nsvRefreshImage, NO_ID);

        mTextColor = typedArray.getColor(R.styleable.NetworkStateView_nsvTextColor, 0x8a000000);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.NetworkStateView_nsvTextSize, UIUtils.dp2px(14));

        typedArray.recycle();

        mInflater = LayoutInflater.from(context);
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundColor(UIUtils.getColor(R.color.white));
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showSuccess();
    }

    /**
     * 加载成功，隐藏加载状态的View
     */
    public void showSuccess() {
        mCurrentState = STATE_SUCCESS;
        showViewByState(mCurrentState);
    }

    /**
     * 显示加载中的状态
     */
    public void showLoading() {
        mCurrentState = STATE_LOADING;
        if (null == mLoadingView) {
            mLoadingView = mInflater.inflate(mLoadingViewId, null);
            addView(mLoadingView, VIEW_POSITION, params);
        }
        showViewByState(mCurrentState);
    }

    /**
     * 显示加载失败(网络错误)状态
     */
    public void showError() {
        mCurrentState = STATE_NETWORK_ERROR;
        if (null == mErrorView) {
            mErrorView = mInflater.inflate(mErrorViewId, null);
            ImageView errorImage = (ImageView) mErrorView.findViewById(R.id.error_image);
            TextView errorText = (TextView) mErrorView.findViewById(R.id.error_text);
            ImageView errorRefreshView = (ImageView) mErrorView.findViewById(R.id.refresh_view);

            image(errorImage, mErrorImageId);

            text(errorText, mErrorText);

            image(errorRefreshView, mRefreshViewId);

            if (null != errorRefreshView) {
                errorRefreshView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mRefreshListener) {
                            mRefreshListener.onRefresh();
                        }
                    }
                });
            }
            addView(mErrorView, VIEW_POSITION, params);
        }
        showViewByState(mCurrentState);
    }

    /**
     * 显示没有网络状态
     */
    public void showNoNetwork() {
        mCurrentState = STATE_NO_NETWORK;
        if (null == mNoNetworkView) {
            mNoNetworkView = mInflater.inflate(mNoNetworkViewId, null);
            ImageView noNetworkImage = (ImageView) mNoNetworkView.findViewById(R.id.no_network_image);
            TextView noNetworkText = (TextView) mNoNetworkView.findViewById(R.id.no_network_text);
            ImageView networkRefreshView = (ImageView) mNoNetworkView.findViewById(R.id.refresh_view);

            image(noNetworkImage, mNoNetworkImageId);

            text(noNetworkText, mNoNetworkText);

            image(networkRefreshView, mRefreshViewId);

            if (null != networkRefreshView) {
                networkRefreshView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mRefreshListener) {
                            mRefreshListener.onRefresh();
                        }
                    }
                });
            }
            addView(mNoNetworkView, VIEW_POSITION, params);
        }
        showViewByState(mCurrentState);
    }

    /**
     * 显示无数据状态
     */
    public void showEmpty() {
        mCurrentState = STATE_EMPTY;
        if (null == mEmptyView) {
            mEmptyView = mInflater.inflate(mEmptyViewId, null);
            ImageView emptyImage = (ImageView) mEmptyView.findViewById(R.id.empty_image);
            TextView emptyText = (TextView) mEmptyView.findViewById(R.id.empty_text);
            ImageView emptyRefreshView = (ImageView) mEmptyView.findViewById(R.id.refresh_view);

            image(emptyImage, mEmptyImageId);

            text(emptyText, mEmptyText);

            image(emptyRefreshView, mRefreshViewId);

            if (null != emptyRefreshView) {
                emptyRefreshView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mRefreshListener) {
                            mRefreshListener.onRefresh();
                        }
                    }
                });
            }
            addView(mEmptyView, VIEW_POSITION, params);
        }
        showViewByState(mCurrentState);
    }

    private void showViewByState(int state) {

        //如果当前状态为加载成功，隐藏此View，反之显示
        this.setVisibility(state == STATE_SUCCESS ? View.GONE : View.VISIBLE);

        if (null != mLoadingView) {
            mLoadingView.setVisibility(state == STATE_LOADING ? View.VISIBLE : View.GONE);
        }

        if (null != mErrorView) {
            mErrorView.setVisibility(state == STATE_NETWORK_ERROR ? View.VISIBLE : View.GONE);
        }

        if (null != mNoNetworkView) {
            mNoNetworkView.setVisibility(state == STATE_NO_NETWORK ? View.VISIBLE : View.GONE);
        }

        if (null != mEmptyView) {
            mEmptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.GONE);
        }
    }

    private void image(ImageView view, int imageId) {
        if (null != view && imageId != NO_ID) {
            view.setImageResource(imageId);
        }
    }

    private void text(TextView view, String str) {
        if (null != view && !TextUtils.isEmpty(str)) {
            view.setText(str);
            view.setTextColor(mTextColor);
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        }
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }


}
