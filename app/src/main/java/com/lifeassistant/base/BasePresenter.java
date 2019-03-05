package com.lifeassistant.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public abstract class BasePresenter<V extends BaseView> {

    /**
     * MainPresenter 綁定的 View
     */
    private V mView;

    /**
     * 連接 View
     */
    public void attachView(V mView) {
        this.mView = mView;
    }

    /**
     * 在 onDestroy 呼叫，以斷開對 View 的連接
     */
    public void detachView() {
        this.mView = null;
    }

    /**
     * 檢查 View 是否連接，避免 NULL
     */
    public boolean isViewAttached() {
        return mView != null;
    }

    /**
     * View 不是連接時，取消動作
     */
    protected void checkView() {
        if (!isViewAttached()) {
            return;
        }
    }

    /**
     * 檢查網路是否有連線
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = manager.getActiveNetworkInfo();

            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 獲得連接的 view
     */
    public V getView() {
        return mView;
    }
}