package com.lifeassistant.callback;

import android.support.v7.widget.RecyclerView;

import com.lifeassistant.base.BaseCallBack;

public interface RecyclerViewCallBack<T> extends BaseCallBack {
    // 設定 RecyclerView 用的 Adapter 準備完成
    void onAdapterPrepare(RecyclerView.Adapter adapter);
}
