package com.lifeassistant.model;

import android.content.Context;
import android.content.SharedPreferences;

public class LifeSharePreference {
    private static final String LIFE_DATA = "LIFE_DATA";
    private static final String AQI_KEY = "AQI_KEY";
    private static final String WEATHER_KEY = "WEATHER_KEY";

    private SharedPreferences settings;

    public LifeSharePreference(Context context) {
        settings = context.getSharedPreferences(LIFE_DATA, Context.MODE_PRIVATE);
    }


    public String readAQIData() {
        return settings.getString(AQI_KEY, "");
    }

    public void saveAQIData(String data) {
        settings.edit()
                .putString(AQI_KEY, data)
                .commit();
    }

    public String readWeatherData() {
        return settings.getString(WEATHER_KEY, "");
    }

    public void saveWeatherData(String data) {
        settings.edit()
                .putString(WEATHER_KEY, data)
                .commit();
    }

}
