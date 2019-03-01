package com.lifeassistant.fragment;

import android.os.Bundle;

import com.lifeassistant.R;
import com.lifeassistant.base.BaseFragment;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.presenter.AirPresenter;
import com.lifeassistant.view.AirView;

public class AirFragment extends BaseFragment implements AirView {
    private AirPresenter presenter;

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
        presenter.getAirData(context);
    }
}
