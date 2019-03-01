package com.lifeassistant.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.base.DataModel;
import com.lifeassistant.callback.AQICallBack;
import com.lifeassistant.callback.NavigationCallBack;
import com.lifeassistant.callback.PM25CallBack;
import com.lifeassistant.callback.WeatherCallBack;
import com.lifeassistant.fragment.AirFragment;
import com.lifeassistant.fragment.HomeFragment;
import com.lifeassistant.fragment.SettingFragment;
import com.lifeassistant.fragment.WeatherFragment;
import com.lifeassistant.model.AQIModel;
import com.lifeassistant.model.LifeSharePreference;
import com.lifeassistant.model.MainNavigationListener;
import com.lifeassistant.model.NavigationModel;
import com.lifeassistant.model.PM25Model;
import com.lifeassistant.model.WeatherModel;
import com.lifeassistant.retrofit.AQIBean;
import com.lifeassistant.retrofit.PM25APIBean;
import com.lifeassistant.retrofit.WeatherBean;
import com.lifeassistant.view.MainView;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
                getView().replaceFragmentContainer(new HomeFragment()).commit();
            }

            @Override
            public void onClickWeatherItem() {
                // 關閉 Drawer，並且切換 Fragment
                getView().closeDrawerView();
                getView().replaceFragmentContainer(new WeatherFragment()).commit();
            }

            @Override
            public void onClickAirItem() {
                // 關閉 Drawer，並且切換 Fragment
                getView().closeDrawerView();
                getView().replaceFragmentContainer(new AirFragment()).commit();
            }

            @Override
            public void onClickSettingItem() {
                // 關閉 Drawer，並且切換 Fragment
                getView().closeDrawerView();
                getView().replaceFragmentContainer(new SettingFragment()).commit();
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

        DataModel.request(WeatherModel.class).execute(new WeatherCallBack() {
            @Override
            public void onWeatherDataPrepared(String response) {
                LifeSharePreference preference = new LifeSharePreference(context);
                preference.saveWeatherData(response);
                Gson gson = new Gson();
                WeatherBean weather = gson.fromJson(response, WeatherBean.class);
                Log.e("getWeatherData", "content ->" + weather.getCwbopendata().getDataset().getDatasetInfo().getDatasetDescription());
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

        DataModel.request(AQIModel.class).execute(new AQICallBack() {
            @Override
            public void onAQIDataPrepared(String response) {
                LifeSharePreference preference = new LifeSharePreference(context);
                preference.saveAQIData(response);
                Gson gson = new Gson();
                Type typeToken = new TypeToken<ArrayList<AQIBean>>() {
                }.getType();
                ArrayList<AQIBean> list = gson.fromJson(response, typeToken);
                Log.e("getAQIData", "list.getSiteName->" + list.get(0).getSiteName());
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

    // 取得 PM2.5 資料
    private void getPM25Data() {
        // 檢查 View 是否連接
        checkView();

        DataModel.request(PM25Model.class).execute(new PM25CallBack() {
            @Override
            public void onPM25DataPrepared(String response) {
                Gson gson = new Gson();
                Type typeToken = new TypeToken<ArrayList<PM25APIBean>>() {
                }.getType();
                ArrayList<PM25APIBean> list = gson.fromJson(response, typeToken);
                Log.e("onPM25DataPrepared", "list.getCounty->" + list.get(0).getCounty());
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
