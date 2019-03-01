package com.lifeassistant.presenter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifeassistant.R;
import com.lifeassistant.adapter.AirAdapter;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.dialog.AirDialog;
import com.lifeassistant.model.LifeSharePreference;
import com.lifeassistant.retrofit.AQIBean;
import com.lifeassistant.view.AirView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AirPresenter extends BasePresenter<AirView> {
    private final static int COLOR_LEVEL_GOOD = 1;
    private final static int COLOR_LEVEL_NORMAL = 2;
    private final static int COLOR_LEVEL_MAYBE_BAD = 3;
    private final static int COLOR_LEVEL_BAD = 4;
    private final static int COLOR_LEVEL_VERY_BAD = 5;
    private final static int COLOR_LEVEL_DANGER = 6;

    private ArrayList<AQIBean> list;

    private Context context;

    public void getAirData(Context context) {
        this.context = context;
        LifeSharePreference preference = new LifeSharePreference(context);
        if (!preference.readAQIData().equals("")) {
            // 使用 Gson 解析 AQI API的回傳資料
            Gson gson = new Gson();
            Type typeToken = new TypeToken<ArrayList<AQIBean>>() {
            }.getType();
            list = gson.fromJson(preference.readAQIData(), typeToken);

            getView().setRecyclerView(new AirAdapter(list, clickEvent));
        }
    }

    private AirAdapter.ClickEvent clickEvent = new AirAdapter.ClickEvent() {
        @Override
        public void clickItem(int position, int level, int color) {
            // 取得 Dialog 內容
            String status = list.get(position).getStatus();
            String location = list.get(position).getCounty() + " " + list.get(position).getSiteName();
            String pollutant = list.get(position).getPollutant();
            String aqi = list.get(position).getAQI();
            String influence = "";
            String suggestion = "";

            switch (level) {
                case COLOR_LEVEL_GOOD:
                    influence = context.getString(R.string.aqi_influence_good);
                    suggestion = context.getString(R.string.aqi_sensitive_suggestion_good);
                    break;
                case COLOR_LEVEL_NORMAL:
                    influence = context.getString(R.string.aqi_influence_normal);
                    suggestion = context.getString(R.string.aqi_sensitive_suggestion_normal);
                    break;
                case COLOR_LEVEL_MAYBE_BAD:
                    influence = context.getString(R.string.aqi_influence_maybe_bad);
                    suggestion = context.getString(R.string.aqi_sensitive_suggestion_maybe_bad);
                    break;
                case COLOR_LEVEL_BAD:
                    influence = context.getString(R.string.aqi_influence_bad);
                    suggestion = context.getString(R.string.aqi_sensitive_suggestion_bad);
                    break;
                case COLOR_LEVEL_VERY_BAD:
                    influence = context.getString(R.string.aqi_influence_very_bad);
                    suggestion = context.getString(R.string.aqi_sensitive_suggestion_very_bad);
                    break;
                case COLOR_LEVEL_DANGER:
                    influence = context.getString(R.string.aqi_influence_danger);
                    suggestion = context.getString(R.string.aqi_sensitive_suggestion_danger);
                    break;
                default:
                    break;
            }

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
