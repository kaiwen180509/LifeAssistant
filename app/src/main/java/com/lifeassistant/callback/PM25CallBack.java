package com.lifeassistant.callback;

import com.lifeassistant.base.BaseCallBack;
import com.lifeassistant.retrofit.PM25APIBean;

import java.util.ArrayList;

public interface PM25CallBack extends BaseCallBack {
    // PM2.5 資料取得完成
    void onPM25DataPrepared(String response);

    // PM2.5 資料取得失敗
    void onFailure(String msg);
}
