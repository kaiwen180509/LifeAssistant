package com.lifeassistant.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
        presenter.getAirData(context);
    }

    @Override
    public void setRecyclerView(RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // 建立 Menu
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 設定 MenuItem 的動作
        if (item.getItemId() == R.id.action_refresh) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
