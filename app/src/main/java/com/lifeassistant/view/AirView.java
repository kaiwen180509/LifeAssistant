package com.lifeassistant.view;

import com.lifeassistant.adapter.AirAdapter;
import com.lifeassistant.base.BaseView;

public interface AirView extends BaseView {
    // 設定 RecyclerView
    void setRecyclerView(AirAdapter adapter);
}
