package com.lifeassistant.fragment;

import android.os.Bundle;

import com.lifeassistant.R;
import com.lifeassistant.base.BaseFragment;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.presenter.SettingPresenter;
import com.lifeassistant.view.SettingView;

public class SettingFragment extends BaseFragment implements SettingView {
    private SettingPresenter presenter;

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void initPresenter() {
        presenter = new SettingPresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.setting_fragment;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }
}
