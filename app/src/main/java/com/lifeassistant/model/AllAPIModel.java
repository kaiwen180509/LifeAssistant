package com.lifeassistant.model;

import com.lifeassistant.base.BaseCallBack;
import com.lifeassistant.base.BaseModel;
import com.lifeassistant.base.DataModel;
import com.lifeassistant.callback.AQICallBack;
import com.lifeassistant.callback.AllAPICallBack;
import com.lifeassistant.callback.WeatherCallBack;

public class AllAPIModel extends BaseModel<AllAPICallBack> {
    private AllAPICallBack callBack;

    // API 執行次數
    private volatile int callTime = 0;
    // API 的數量
    private int apiSum = 2;

    @Override
    public void execute(BaseCallBack callback) {
        this.callBack = (AllAPICallBack) callback;

        // 開始取得資料
        getWeatherData();
        getAqiData();
    }

    // 取得 Weather 資料
    private void getWeatherData() {
        DataModel.request(WeatherAPIModel.class).params("").execute(new WeatherCallBack() {
            @Override
            public void onWeatherDataPrepared(String response) {
                callBack.onWeatherDataPrepared(response);
            }

            @Override
            public void onFailure(String msg) {
                callBack.onFailure();
            }

            @Override
            public void onComplete() {
                callTime++;
                if (callTime == apiSum) {
                    callBack.onComplete();
                }
            }
        });
    }

    // 取得 AQI 資料
    private void getAqiData() {
        DataModel.request(AQIAPIModel.class).params("").execute(new AQICallBack() {
            @Override
            public void onAQIDataPrepared(String response) {
                callBack.onAqiDataPrepared(response);
            }

            @Override
            public void onFailure(String msg) {
                callBack.onFailure();
            }

            @Override
            public void onComplete() {
                callTime++;
                if (callTime == apiSum) {
                    callBack.onComplete();
                }
            }
        });
    }

}
