package com.lifeassistant.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lifeassistant.adapter.AirAdapter;
import com.lifeassistant.base.BaseFragmentView;
import com.lifeassistant.base.BaseView;

public interface AirView extends BaseFragmentView {
    // 設定 RecyclerView
    void setRecyclerView(RecyclerView.Adapter adapter);
}
