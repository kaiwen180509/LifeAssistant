package com.lifeassistant.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.lifeassistant.adapter.WeatherAdapter;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.model.LifeSharePreference;
import com.lifeassistant.model.WeatherDataParser;
import com.lifeassistant.retrofit.WeatherBean;
import com.lifeassistant.view.WeatherView;

public class WeatherPresenter extends BasePresenter<WeatherView> {
    private Context context;

    // 取得 RecyclerView 的 資料
    public void setRecyclerViewData(Context context) {
        // 檢查 View 是否連接
        checkView();

        this.context = context;

        // 取出 API 回傳的資料
        LifeSharePreference preference = new LifeSharePreference(context);

        // 使用 Gson 解析 API 的資料
        if (!preference.readWeatherData().equals("")) {
            // 使用 Gson 解析 API 的資料
            Gson gson = new Gson();
            WeatherBean weather = gson.fromJson(preference.readWeatherData(), WeatherBean.class);
            // 建立解析器解析資料
            WeatherDataParser weatherDataParser = new WeatherDataParser(context, weather);
            // 設定 RecyclerView 畫面
            getView().setRecyclerView(new WeatherAdapter(weatherDataParser));
        }
    }
}
