package com.lifeassistant.presenter;

import android.content.Context;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifeassistant.R;
import com.lifeassistant.adapter.AirAdapter;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.base.DataModel;
import com.lifeassistant.callback.AQICallBack;
import com.lifeassistant.dialog.AirDialog;
import com.lifeassistant.model.AQIAPIModel;
import com.lifeassistant.model.AQIDataParser;
import com.lifeassistant.model.LifeSharePreference;
import com.lifeassistant.retrofit.AQIBean;
import com.lifeassistant.view.AirView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AirPresenter extends BasePresenter<AirView> {
    // 宣告 AQI 解析器
    private AQIDataParser aqiDataParser;

    private Context context;
    private LifeSharePreference preference;

    public void setAirData(Context context) {
        // 檢查 View 是否連接
        checkView();

        this.context = context;

        // 取出預設的資料
        preference = new LifeSharePreference(context);
        if (!preference.readAQIData().equals("")) {
            setViewData(preference.readAQIData());
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
        DataModel.request(AQIAPIModel.class).params("").execute(new AQICallBack() {
            @Override
            public void onAQIDataPrepared(String response) {
                // 保存取得的資料
                preference.saveAQIData(response);
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
        // 使用 Gson 解析資料
        Gson gson = new Gson();
        Type typeToken = new TypeToken<ArrayList<AQIBean>>() {
        }.getType();
        ArrayList<AQIBean> list = gson.fromJson(data, typeToken);
        // 使用 AQI 的解析器來取得資料
        aqiDataParser = new AQIDataParser(context, list);

        getView().setRecyclerView(new AirAdapter(aqiDataParser, clickEvent));
    }

    // AirAdapter 的 Item Click 事件
    private AirAdapter.ClickEvent clickEvent = new AirAdapter.ClickEvent() {
        @Override
        public void clickItem(int position) {
            // 取得 Dialog 內容
            String status = aqiDataParser.parserStatusData(position);
            String location = aqiDataParser.parserCountryData(position) + " " + aqiDataParser.parserSiteNameData(position);
            String pollutant = aqiDataParser.parserPollutantData(position);
            String aqi = aqiDataParser.parserAQIData(position);
            int color = aqiDataParser.parserColorData(position);
            String influence = aqiDataParser.parserInfluenceData(position);
            String suggestion = aqiDataParser.parserSuggestionData(position);

            // 顯示 Dialog
            AirDialog dialog = new AirDialog(context);
            dialog.setColor(color);
            dialog.setDialogTitle(status);
            dialog.setDialogLocation(location);
            dialog.setDialogPollutant(pollutant);
            dialog.setDialogAQI(aqi);
            dialog.setDialogInfluence(influence);
            dialog.setDialogSuggestion(suggestion);
            dialog.show();
        }
    };
}
