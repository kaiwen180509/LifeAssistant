package com.lifeassistant.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lifeassistant.R;
import com.lifeassistant.adapter.AirAdapter;
import com.lifeassistant.base.BaseFragment;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.presenter.AirPresenter;
import com.lifeassistant.view.AirView;

import butterknife.BindView;

public class AirFragment extends BaseFragment implements AirView {
    private AirPresenter presenter;

    @BindView(R.id.air_recycler)
    RecyclerView recyclerView;

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

    @Override
    public void setRecyclerView(AirAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
}
