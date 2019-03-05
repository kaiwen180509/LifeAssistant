package com.lifeassistant.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.base.DataModel;
import com.lifeassistant.callback.AQICallBack;
import com.lifeassistant.callback.NavigationCallBack;
import com.lifeassistant.callback.WeatherCallBack;
import com.lifeassistant.fragment.AirFragment;
import com.lifeassistant.fragment.HomeFragment;
import com.lifeassistant.fragment.SettingFragment;
import com.lifeassistant.fragment.WeatherFragment;
import com.lifeassistant.model.AQIAPIModel;
import com.lifeassistant.model.LifeSharePreference;
import com.lifeassistant.model.MainNavigationListener;
import com.lifeassistant.model.NavigationModel;
import com.lifeassistant.model.WeatherAPIModel;
import com.lifeassistant.retrofit.WeatherBean;
import com.lifeassistant.view.MainView;

public class MainPresenter extends BasePresenter<MainView> {

    // 取得 Navigation 的 Listener
    public void getNavigationViewListener() {
        // 檢查 View 是否連接
        checkView();

        // 調用 NavigationModel 取得資料
        DataModel.request(NavigationModel.class).execute(new NavigationCallBack() {
            @Override
            public void onNavigationItemSelectedListenerPrepare(MainNavigationListener listener) {
                getView().setNavigationSelectedListener(listener);
            }

            @Override
            public void onClickHomeItem() {
                // 關閉 Drawer，並且切換 Fragment
                getView().closeDrawerView();
                getView().replaceFragmentContainer(new HomeFragment());
            }

            @Override
            public void onClickWeatherItem() {
                // 關閉 Drawer，並且切換 Fragment
                getView().closeDrawerView();
                getView().replaceFragmentContainer(new WeatherFragment());
            }

            @Override
            public void onClickAirItem() {
                // 關閉 Drawer，並且切換 Fragment
                getView().closeDrawerView();
                getView().replaceFragmentContainer(new AirFragment());
            }

            @Override
            public void onClickSettingItem() {
                // 關閉 Drawer，並且切換 Fragment
                getView().closeDrawerView();
                getView().replaceFragmentContainer(new SettingFragment());
            }

            @Override
            public void onComplete() {
            }
        });
    }

    // 取得 Weather 資料
    public void getWeatherData(Context context) {
        // 檢查 View 是否連接
        checkView();

        DataModel.request(WeatherAPIModel.class).execute(new WeatherCallBack() {
            @Override
            public void onWeatherDataPrepared(String response) {
                LifeSharePreference preference = new LifeSharePreference(context);
                preference.saveWeatherData(response);
            }

            @Override
            public void onFailure(String msg) {
                Log.e("onFailure", msg);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    // 取得 AQI 資料
    public void getAQIData(Context context) {
        // 檢查 View 是否連接
        checkView();

        // 取得 SharePreference
        LifeSharePreference preference = new LifeSharePreference(context);

        // 呼叫 AQI API 模組取得 AQI 內容，傳遞舊的內容以便判斷是否有更新
        DataModel.request(AQIAPIModel.class).params(preference.readAQIData()).execute(new AQICallBack() {
            @Override
            public void onAQIDataPrepared(String response) {
                preference.saveAQIData(response);
            }

            @Override
            public void onFailure(String msg) {
                Log.e("onFailure", msg);
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
