package com.lifeassistant.presenter;

import android.content.Context;
import android.util.Log;

import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.model.LifeSharePreference;
import com.lifeassistant.view.AirView;

public class AirPresenter extends BasePresenter<AirView> {

    public void getAirData(Context context) {
        LifeSharePreference preference = new LifeSharePreference(context);
        Log.e("AirPresenter", preference.readAQIData());
    }
}
