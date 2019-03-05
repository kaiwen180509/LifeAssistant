package com.lifeassistant.callback;

import com.lifeassistant.base.BaseCallBack;

public interface AllAPICallBack extends BaseCallBack {
    void onWeatherDataPrepared(String response);

    void onAqiDataPrepared(String response);

    void onFailure();
}
