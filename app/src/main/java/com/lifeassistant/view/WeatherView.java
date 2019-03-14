package com.lifeassistant.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lifeassistant.base.BaseFragmentView;
import com.lifeassistant.base.BaseView;

public interface WeatherView extends BaseFragmentView {
    /**
     * 設定 RecyclerView 的參數
     *
     * @param adapter
     */
    void setRecyclerView(RecyclerView.Adapter adapter);
}
