package com.lifeassistant.view;

import android.support.v7.widget.RecyclerView;

import com.lifeassistant.base.BaseView;

public interface WeatherView extends BaseView {
    /**
     * 設定 RecyclerView 的參數
     *
     * @param adapter
     */
    void setRecyclerView(RecyclerView.Adapter adapter);
}
