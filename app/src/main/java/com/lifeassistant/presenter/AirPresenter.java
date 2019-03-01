package com.lifeassistant.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifeassistant.adapter.AirAdapter;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.model.LifeSharePreference;
import com.lifeassistant.retrofit.AQIBean;
import com.lifeassistant.view.AirView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AirPresenter extends BasePresenter<AirView> {
    private ArrayList<AQIBean> list;

    public void getAirData(Context context) {
        LifeSharePreference preference = new LifeSharePreference(context);
        if (!preference.readAQIData().equals("")) {
            Gson gson = new Gson();
            Type typeToken = new TypeToken<ArrayList<AQIBean>>() {
            }.getType();
            list = gson.fromJson(preference.readAQIData(), typeToken);

            getView().setRecyclerView(new AirAdapter(list, clickEvent));
        }
    }

    private AirAdapter.ClickEvent clickEvent = new AirAdapter.ClickEvent() {
        @Override
        public void clickItem(int position) {
            Log.e("ClickEvent", list.get(position).getSiteName());
        }
    };
}
