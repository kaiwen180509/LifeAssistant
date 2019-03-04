package com.lifeassistant.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.model.AQIDataParser;
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

    // 設定 Home 畫面所需的資料
    public void setHomeViewData(Context context) {
        // 檢查 View
        checkView();

        this.context = context;

        // 取出資料
        LifeSharePreference preference = new LifeSharePreference(context);
        // 天氣資料
        if (!preference.readWeatherData().equals("")) {
            setWeatherData(preference.readWeatherData());
        }
        // 空氣資料
        if (!preference.readAQIData().equals("")) {
            setAirData(preference.readAQIData());
        }
    }

    // 設定天氣資料
    private void setWeatherData(String jsonData) {
        // 使用 Gson 解析
        Gson gson = new Gson();
        WeatherBean weatherBean = gson.fromJson(jsonData, WeatherBean.class);
        // 使用 Weather 的解析器來取得資料
        WeatherDataParser weatherDataParser = new WeatherDataParser(context, weatherBean);

        // 取得對應的資料
        String title = weatherDataParser.parserLocationData(locationValue);
        int[] temp = weatherDataParser.parserTemperatureData(locationValue);
        int rain = weatherDataParser.parserRainData(locationValue);
        String[] description = weatherDataParser.parserDescriptionData(locationValue);
        String feel = weatherDataParser.parserFeelData(locationValue);
        String startTime = weatherDataParser.parserStartTimeData(locationValue);
        String endTime = weatherDataParser.parserEndTimeData(locationValue);
        Drawable image = weatherDataParser.parserWeatherImage(locationValue);
        int color = weatherDataParser.parserColorData(locationValue);

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

        // 取得對應的資料
        String title = aqiDataParser.parserCountryData(locationValue);
        String aqi = aqiDataParser.parserAQIData(locationValue);
        String site = aqiDataParser.parserSiteNameData(locationValue);
        String pollutant = aqiDataParser.parserPollutantData(locationValue);
        String pm25 = aqiDataParser.parserPM25Data(locationValue);
        String status = aqiDataParser.parserStatusData(locationValue);
        String time = aqiDataParser.parserTimeData(locationValue);
        String suggestion = aqiDataParser.parserSuggestionData(locationValue);
        String influence = aqiDataParser.parserInfluenceData(locationValue);
        int color = aqiDataParser.parserColorData(locationValue);

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
