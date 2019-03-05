package com.lifeassistant.view;

import android.support.v7.widget.RecyclerView;

import com.lifeassistant.adapter.AirAdapter;
import com.lifeassistant.base.BaseView;

public interface AirView extends BaseView {
    // 設定 RecyclerView
    void setRecyclerView(RecyclerView.Adapter adapter);
}
