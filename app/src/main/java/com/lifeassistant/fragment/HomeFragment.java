package com.lifeassistant.fragment;

import android.os.Bundle;

import com.lifeassistant.R;
import com.lifeassistant.base.BaseFragment;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.presenter.HomePresenter;
import com.lifeassistant.view.HomeView;

public class HomeFragment extends BaseFragment implements HomeView {
    private HomePresenter presenter;

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void initPresenter() {
        presenter = new HomePresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }
}