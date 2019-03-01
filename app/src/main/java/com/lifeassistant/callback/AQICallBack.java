package com.lifeassistant.callback;

import com.lifeassistant.base.BaseCallBack;

public interface AQICallBack extends BaseCallBack {
    // AQI 資料取得完成
    void onAQIDataPrepared(String response);

    // AQI 資料取得失敗
    void onFailure(String msg);
}
