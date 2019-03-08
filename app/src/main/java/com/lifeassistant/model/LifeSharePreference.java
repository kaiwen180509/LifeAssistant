package com.lifeassistant.model;

import android.content.Context;
import android.content.SharedPreferences;

public class LifeSharePreference {
    // Data KEY
    private static final String LIFE_DATA = "LIFE_DATA";
    // API KEY
    private static final String AQI_KEY = "AQI_KEY";
    private static final String WEATHER_KEY = "WEATHER_KEY";
    // Default Data KEY
    private static final String AQI_DEFAULT_SITE = "AQI_DEFAULT_SITE";
    private static final String WEATHER_DEFAULT_LOCATION = "WEATHER_DEFAULT_LOCATION";

    private SharedPreferences settings;

    public LifeSharePreference(Context context) {
        settings = context.getSharedPreferences(LIFE_DATA, Context.MODE_PRIVATE);
    }

    // 讀取 AQI 的預設觀測站資料
    public String readDefaultAQISite() {
        return settings.getString(AQI_DEFAULT_SITE, "臺北市 陽明");
    }

    // 儲存 AQI 的預設觀測站資料
    public void saveDefaultAQISite(String site) {
        settings.edit().putString(AQI_DEFAULT_SITE, site).commit();
    }

    // 讀取 Weather 的預設地點資料
    public String readDefaultWeatherLocation() {
        return settings.getString(WEATHER_DEFAULT_LOCATION, "臺北市");
    }

    // 儲存 Weather 的預設地點資料
    public void saveDefaultWeatherLocation(String location) {
        settings.edit().putString(WEATHER_DEFAULT_LOCATION, location).commit();
    }

    // 儲存 AQI 的 API 資料
    public String readAQIData() {
        return settings.getString(AQI_KEY, "");
    }

    // 讀取 AQI 的 API 資料
    public void saveAQIData(String data) {
        settings.edit().putString(AQI_KEY, data).commit();
    }

    // 儲存 Weather 的 API 資料
    public String readWeatherData() {
        return settings.getString(WEATHER_KEY, "");
    }

    // 讀取 Weather 的 API 資料
    public void saveWeatherData(String data) {
        settings.edit().putString(WEATHER_KEY, data).commit();
    }

}
