package com.lifeassistant.presenter;

import android.content.Context;
import android.view.View;

import com.google.gson.Gson;
import com.lifeassistant.R;
import com.lifeassistant.adapter.WeatherAdapter;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.base.DataModel;
import com.lifeassistant.callback.WeatherCallBack;
import com.lifeassistant.model.LifeSharePreference;
import com.lifeassistant.model.WeatherAPIModel;
import com.lifeassistant.model.WeatherDataParser;
import com.lifeassistant.retrofit.WeatherBean;
import com.lifeassistant.view.WeatherView;

public class WeatherPresenter extends BasePresenter<WeatherView> {
    private Context context;
    private LifeSharePreference preference;

    // 取得 RecyclerView 的 資料
    public void setRecyclerViewData(Context context) {
        // 檢查 View 是否連接
        checkView();

        this.context = context;

        // 取出預設的資料
        preference = new LifeSharePreference(context);
        if (!preference.readWeatherData().equals("")) {
            setViewData(preference.readWeatherData());
        } else {
            // 沒有預設的資料
            getAPIData();
        }
    }

    // 呼叫 API 取得資料
    private void getAPIData() {
        // 檢查 View 是否連接
        checkView();

        // 檢查網路連線
        if (!isNetworkConnected(context)) {
            // 沒有網路連線
            String msg = context.getString(R.string.snack_no_network);
            getView().showSnackbar(msg);
            return;
        }

        // 顯示進度條
        getView().showProgress();

        // 取得資料
        DataModel.request(WeatherAPIModel.class).params("").execute(new WeatherCallBack() {
            @Override
            public void onWeatherDataPrepared(String response) {
                // 保存取得的資料
                preference.saveWeatherData(response);
                // 設定畫面
                setViewData(response);
            }

            @Override
            public void onFailure(String msg) {
                // 關閉進度條
                getView().closeProgress();

                // 顯示 Snackbar
                String content = context.getString(R.string.snack_failure);
                String action = context.getString(R.string.snack_action);
                getView().showSnackbar(content, action, listener);
            }

            @Override
            public void onComplete() {
                // 關閉進度條
                getView().closeProgress();
            }
        });
    }

    // Snackbar 的 Click 動作
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 重新取得資料
            getAPIData();
        }
    };

    // 設定 RecyclerView 畫面資料
    private void setViewData(String data) {
        // 使用 Gson 解析 API 的資料
        Gson gson = new Gson();
        WeatherBean weather = gson.fromJson(data, WeatherBean.class);
        // 建立解析器解析資料
        WeatherDataParser weatherDataParser = new WeatherDataParser(context, weather);
        // 設定 RecyclerView 畫面
        getView().setRecyclerView(new WeatherAdapter(weatherDataParser));
    }
}
