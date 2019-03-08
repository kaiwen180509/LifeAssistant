package com.lifeassistant.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifeassistant.R;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.base.DataModel;
import com.lifeassistant.callback.AllAPICallBack;
import com.lifeassistant.dialog.ExplanationDialog;
import com.lifeassistant.dialog.RefreshDialog;
import com.lifeassistant.model.AQIDataParser;
import com.lifeassistant.model.AllAPIModel;
import com.lifeassistant.model.LifeSharePreference;
import com.lifeassistant.model.WeatherDataParser;
import com.lifeassistant.retrofit.AQIBean;
import com.lifeassistant.retrofit.WeatherBean;
import com.lifeassistant.view.HomeView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomePresenter extends BasePresenter<HomeView> {
    private Context context;
    private int locationValue = 0;
    private LifeSharePreference preference;
    private RefreshDialog refreshDialog;

    // 設定 Home 畫面所需的資料
    public void setHomeViewData(Context context) {
        // 檢查 View
        checkView();

        this.context = context;

        // 是否無資料，預設有資料
        boolean noData = false;

        // 資料載入完成前先隱藏畫面
        getView().hideView();

        // 取出資料
        preference = new LifeSharePreference(context);
        // 天氣資料
        if (!preference.readWeatherData().equals("")) {
            setWeatherData(preference.readWeatherData());
        } else {
            // 無資料
            noData = true;
        }
        // 空氣資料
        if (!preference.readAQIData().equals("")) {
            setAirData(preference.readAQIData());
        } else {
            // 無資料
            noData = true;
        }

        // 沒有資料
        if (noData) {
            // 沒有網路連線
            if (!isNetworkConnected(context)) {
                String msg = context.getString(R.string.home_snack_no_network);
                getView().showSnackbar(msg);
                return;
            }

            // Snackbar 要顯示的資料
            String msg = context.getString(R.string.home_snack_msg);
            String action = context.getString(R.string.home_snack_action);
            getView().showSnackbar(msg, action, refreshListener);
        } else {
            // 資料載入完成，顯示畫面
            getView().showView();
        }
    }

    // 顯示 RefreshDialog
    public void showRefreshDialog(Context context) {
        // 檢查 View
        checkView();

        this.context = context;

        // 建立 RefreshDialog 並顯示
        refreshDialog = new RefreshDialog(context);
        refreshDialog.setConfirmClick(confirmClickListener);
        refreshDialog.show();
    }

    // RefreshDialog 的 Click 事件
    private View.OnClickListener confirmClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 先關閉 Dialog 並隱藏畫面
            refreshDialog.close();
            getView().hideView();
            // 更新資料
            refreshData();
        }
    };

    // 顯示 ExplanationDialog
    public void showExplanationDialog(Context context) {
        // 檢查 View
        checkView();

        // 建立 ExplanationDialog 並顯示
        ExplanationDialog explanationDialog = new ExplanationDialog(context);
        explanationDialog.show();
    }

    // 取得資料的 Click 事件
    private View.OnClickListener refreshListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 更新資料
            refreshData();
        }
    };

    // 更新 API 資料
    private void refreshData() {
        // 顯示進度條
        getView().showProgress();

        // 呼叫 API 取得資料
        DataModel.request(AllAPIModel.class).execute(new AllAPICallBack() {
            @Override
            public void onWeatherDataPrepared(String response) {
                // 保存資料並且設定畫面
                preference.saveWeatherData(response);
                setWeatherData(response);
            }

            @Override
            public void onAqiDataPrepared(String response) {
                // 保存資料並且設定畫面
                preference.saveAQIData(response);
                setAirData(response);
            }

            @Override
            public void onFailure() {
                // 關閉進度條並且顯示 Snackbar
                getView().closeProgress();
                String msg = context.getString(R.string.home_snack_failure);
                String action = context.getString(R.string.home_snack_action);
                getView().showSnackbar(msg, action, refreshListener);
            }

            @Override
            public void onComplete() {
                // 關閉進度條並且顯示畫面
                getView().closeProgress();
                getView().showView();
            }
        });
    }

    // 設定天氣資料
    private void setWeatherData(String jsonData) {
        // 使用 Gson 解析
        Gson gson = new Gson();
        WeatherBean weatherBean = gson.fromJson(jsonData, WeatherBean.class);
        // 使用 Weather 的解析器來取得資料
        WeatherDataParser weatherDataParser = new WeatherDataParser(context, weatherBean);

        // 取得設定內的地點的資料
        String data = preference.readDefaultWeatherLocation();
        // 取得要顯示的資料所在位置
        int index = weatherDataParser.parserPositionData(data);

        // 取得位置對應的資料
        String title = weatherDataParser.parserLocationData(index);
        int[] temp = weatherDataParser.parserTemperatureData(index);
        int rain = weatherDataParser.parserRainData(index);
        String[] description = weatherDataParser.parserDescriptionData(index);
        String feel = weatherDataParser.parserFeelData(index);
        String startTime = weatherDataParser.parserStartTimeData(index);
        String endTime = weatherDataParser.parserEndTimeData(index);
        Drawable image = weatherDataParser.parserWeatherImage(index);
        int color = weatherDataParser.parserColorData(index);

        // 設定畫面
        getView().setWeatherColor(color);
        getView().setWeatherTitle(title);
        getView().setTemperatureText(temp[0] + " ~ " + temp[1] + " °C");
        getView().setRainText(rain + " %");
        getView().setDescriptionText(description[0]);
        getView().setFeelText(feel);
        getView().setStartTimeText(startTime);
        getView().setEndTimeText(endTime);
        getView().setWeatherImage(image);
    }

    // 設定空氣資料
    private void setAirData(String jsonData) {
        // 使用 Gson 解析
        Gson gson = new Gson();
        Type typeToken = new TypeToken<ArrayList<AQIBean>>() {
        }.getType();
        ArrayList<AQIBean> list = gson.fromJson(jsonData, typeToken);
        // 使用 AQI 的解析器來取得資料
        AQIDataParser aqiDataParser = new AQIDataParser(context, list);

        // 取得設定內的地點的資料
        String data = preference.readDefaultAQISite();
        // 取得要顯示的資料所在位置
        int index = aqiDataParser.parserPositionData(data);

        // 取得對應的資料
        String title = aqiDataParser.parserCountryData(index);
        String aqi = aqiDataParser.parserAQIData(index);
        String site = aqiDataParser.parserSiteNameData(index);
        String pollutant = aqiDataParser.parserPollutantData(index);
        String pm25 = aqiDataParser.parserPM25Data(index);
        String status = aqiDataParser.parserStatusData(index);
        String time = aqiDataParser.parserTimeData(index);
        String suggestion = aqiDataParser.parserSuggestionData(index);
        String influence = aqiDataParser.parserInfluenceData(index);
        int color = aqiDataParser.parserColorData(index);

        // 設定畫面
        getView().setAirTitle(title);
        getView().setAirColor(color);
        getView().setAqiText(aqi);
        getView().setLocationText(site);
        getView().setPollutantText(pollutant);
        getView().setPM25Text(pm25);
        getView().setStatusText(status);
        getView().setMonitorTimeText(time);
        getView().setSuggestionText(suggestion);
        getView().setInfluenceText(influence);
    }
}
