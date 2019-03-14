package com.lifeassistant.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lifeassistant.R;
import com.lifeassistant.base.BaseFragment;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.presenter.AirPresenter;
import com.lifeassistant.view.AirView;

import butterknife.BindString;
import butterknife.BindView;

public class AirFragment extends BaseFragment implements AirView {
    private AirPresenter presenter;

    @BindView(R.id.air_recycler)
    RecyclerView recyclerView;
    @BindString(R.string.air_dialog_confirm)
    String confirm;

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void initPresenter() {
        presenter = new AirPresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.air_fragment;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        presenter.setAirData(context);
    }

    @Override
    public void setRecyclerView(RecyclerView.Adapter adapter) {
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
