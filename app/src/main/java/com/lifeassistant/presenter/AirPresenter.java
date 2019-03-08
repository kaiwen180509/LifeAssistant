package com.lifeassistant.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifeassistant.adapter.AirAdapter;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.dialog.AirDialog;
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

    public void setAirData(Context context) {
        // 檢查 View 是否連接
        checkView();

        this.context = context;

        // 取出 AQI API 的回傳資料
        LifeSharePreference preference = new LifeSharePreference(context);
        if (!preference.readAQIData().equals("")) {
            // 使用 Gson 解析資料
            Gson gson = new Gson();
            Type typeToken = new TypeToken<ArrayList<AQIBean>>() {
            }.getType();
            ArrayList<AQIBean> list = gson.fromJson(preference.readAQIData(), typeToken);
            // 使用 AQI 的解析器來取得資料
            aqiDataParser = new AQIDataParser(context, list);

            getView().setRecyclerView(new AirAdapter(aqiDataParser, clickEvent));
        }
    }

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
