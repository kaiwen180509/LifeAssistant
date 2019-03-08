package com.lifeassistant.presenter;

import android.content.Context;

import com.lifeassistant.R;
import com.lifeassistant.adapter.SettingAdapter;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.model.LifeSharePreference;
import com.lifeassistant.view.SettingView;

public class SettingPresenter extends BasePresenter<SettingView> {
    private Context context;
    private LifeSharePreference preference;

    // 宣告 Air 與 Weather 的地點資料
    private String[] airLocationArray;
    private String[] weatherLocationArray;

    // 設定設定的畫面
    public void setSettingView(Context context) {
        // 檢查 View
        checkView();

        this.context = context;

        // Air 與 Weather 的地點資料
        airLocationArray = context.getResources().getStringArray(R.array.air_location);
        weatherLocationArray = context.getResources().getStringArray(R.array.weather_location);

        // 取得 Preference
        preference = new LifeSharePreference(context);

        // 設定 Adapter
        getView().setRecyclerView(new SettingAdapter(context, airSpinnerSelected, weatherSpinnerSelected));
    }

    // Air Spinner 的介面
    private SettingAdapter.AirSpinnerSelected airSpinnerSelected = new SettingAdapter.AirSpinnerSelected() {
        @Override
        public void select(int position) {
            // 取得觀測站資料並保存
            preference.saveDefaultAQISite(airLocationArray[position]);
        }
    };

    // Weather Spinner 的介面
    private SettingAdapter.WeatherSpinnerSelected weatherSpinnerSelected = new SettingAdapter.WeatherSpinnerSelected() {
        @Override
        public void select(int position) {
            // 取得地點資料並保存
            preference.saveDefaultWeatherLocation(weatherLocationArray[position]);
        }
    };
}
