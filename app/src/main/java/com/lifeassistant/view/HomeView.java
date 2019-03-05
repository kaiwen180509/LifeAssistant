package com.lifeassistant.view;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.lifeassistant.base.BaseView;

public interface HomeView extends BaseView {
    // 顯示進度條
    void showProgress();

    // 關閉進度條
    void closeProgress();

    // 顯示畫面
    void showView();

    // 隱藏畫面
    void hideView();

    // 顯示 Snackbar
    void showSnackbar(String msg, String action, View.OnClickListener listener);

    // 設定天氣的圖片
    void setWeatherImage(Drawable drawable);

    // 設定 Air 標題
    void setAirTitle(String airTitle);

    // 設定 Air 顏色
    void setAirColor(int color);

    // 設定 Weather 顏色
    void setWeatherColor(int color);

    // 設定 Weather 標題
    void setWeatherTitle(String weatherTitle);

    // 設定溫度
    void setTemperatureText(String temperature);

    // 設定降雨機率
    void setRainText(String rain);

    // 設定天氣描述
    void setDescriptionText(String description);

    // 設定體感描述
    void setFeelText(String feel);

    // 設定預報開始時間
    void setStartTimeText(String startTime);

    // 設定預報結束時間
    void setEndTimeText(String endTime);

    // 設定 AQI 指標
    void setAqiText(String aqi);

    // 設定 AQI 監測站地點
    void setLocationText(String location);

    // 設定汙染指標物
    void setPollutantText(String pollutant);

    // 設定空氣狀態
    void setStatusText(String status);

    // 設定 PM2.5 濃度
    void setPM25Text(String pm25);

    // 設定影響內容
    void setInfluenceText(String influence);

    // 設定建議內容
    void setSuggestionText(String suggestion);

    // 設定 AQI 監測時間
    void setMonitorTimeText(String monitorTime);
}
