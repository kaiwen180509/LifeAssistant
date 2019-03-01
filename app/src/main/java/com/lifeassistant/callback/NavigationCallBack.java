package com.lifeassistant.callback;

import com.lifeassistant.base.BaseCallBack;
import com.lifeassistant.model.MainNavigationListener;

public interface NavigationCallBack extends BaseCallBack {
    // 設定 Navigation 用的的 Listener 準備完成
    void onNavigationItemSelectedListenerPrepare(MainNavigationListener listener);

    // 首頁被點擊
    void onClickHomeItem();

    // 天氣預報被點擊
    void onClickWeatherItem();

    // 空氣品質被點擊
    void onClickAirItem();

    // 設定被點擊
    void onClickSettingItem();
}
