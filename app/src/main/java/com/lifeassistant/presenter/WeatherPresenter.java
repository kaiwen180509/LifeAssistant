package com.lifeassistant.presenter;

import android.support.v7.widget.RecyclerView;

import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.base.DataModel;
import com.lifeassistant.callback.RecyclerViewCallBack;
import com.lifeassistant.model.RecyclerViewModel;
import com.lifeassistant.view.WeatherView;

public class WeatherPresenter extends BasePresenter<WeatherView> {

    // 取得 RecyclerView 的 資料
    public void getRecyclerViewData() {
        // 檢查 View 是否連接
        checkView();

        // 調用 RecyclerViewModel 取得資料
        DataModel.request(RecyclerViewModel.class).execute(new RecyclerViewCallBack<String>() {
            @Override
            public void onAdapterPrepare(RecyclerView.Adapter adapter) {
                getView().setRecyclerView(adapter);
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
