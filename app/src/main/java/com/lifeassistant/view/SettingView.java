package com.lifeassistant.view;

import android.support.v7.widget.RecyclerView;

import com.lifeassistant.base.BaseView;

public interface SettingView extends BaseView {
    // 設定 RecyclerView
    void setRecyclerView(RecyclerView.Adapter adapter);
}
