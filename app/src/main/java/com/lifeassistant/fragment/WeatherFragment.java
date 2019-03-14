package com.lifeassistant.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lifeassistant.R;
import com.lifeassistant.base.BaseFragment;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.presenter.WeatherPresenter;
import com.lifeassistant.view.WeatherView;

import butterknife.BindView;

public class WeatherFragment extends BaseFragment implements WeatherView {
    @BindView(R.id.weather_recycler)
    RecyclerView recyclerView;

    private WeatherPresenter presenter;

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void initPresenter() {
        presenter = new WeatherPresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.weather_fragment;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        // 取得 RecyclerView 的資料
        presenter.setRecyclerViewData(context);
    }

    @Override
    public void setRecyclerView(RecyclerView.Adapter adapter) {
        // 設定 RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showSnackbar(String msg, String action, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(action, listener);
        snackbar.show();
    }

    @Override
    public void showSnackbar(String msg) {
        Snackbar.make(rootView, msg, Snackbar.LENGTH_INDEFINITE).show();
    }
}
