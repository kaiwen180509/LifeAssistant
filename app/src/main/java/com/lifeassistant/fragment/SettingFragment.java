package com.lifeassistant.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lifeassistant.R;
import com.lifeassistant.base.BaseFragment;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.presenter.SettingPresenter;
import com.lifeassistant.view.SettingView;

import butterknife.BindView;

public class SettingFragment extends BaseFragment implements SettingView {
    private SettingPresenter presenter;

    @BindView(R.id.setting_recycler)
    RecyclerView recyclerView;

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
        presenter.setSettingView(context);
    }

    @Override
    public void setRecyclerView(RecyclerView.Adapter adapter) {
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
}
