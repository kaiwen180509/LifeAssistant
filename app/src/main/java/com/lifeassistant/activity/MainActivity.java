package com.lifeassistant.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.lifeassistant.R;
import com.lifeassistant.base.BaseFragmentActivity;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.fragment.HomeFragment;
import com.lifeassistant.model.MainNavigationListener;
import com.lifeassistant.presenter.MainPresenter;
import com.lifeassistant.view.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity implements MainView {
    // 宣告 View 並連結
    @BindView(R.id.main_drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_navigation)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    // 宣告 Presenter
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 綁定 View
        ButterKnife.bind(this);

        // 設定 ActionBar
        setToolBar();

        // 呼叫 Presenter 設置 NavigationView
        presenter.getNavigationViewListener(this);

        // 呼叫 Presenter 準備資料
        presenter.getAQIData(this);
        presenter.getWeatherData(this);
    }


    @Override
    protected BasePresenter getPresenter() {
        // 初始化 MainPresenter
        presenter = new MainPresenter();
        return presenter;
    }

    // 第一個 Fragment
    @Override
    protected Fragment getFirstFragment() {
        return new HomeFragment();
    }

    // Fragment 的容器
    @Override
    protected int getContainerID() {
        return R.id.main_container;
    }

    // 設定上方的 ActionBar
    private void setToolBar() {
        setSupportActionBar(toolbar);

        // 整合 ActionBar 與 DrawerLayout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void setNavigationSelectedListener(MainNavigationListener listener) {
        // 設定 Navigation 的 Listener
        navigationView.setNavigationItemSelectedListener(listener);
    }

    @Override
    public void closeDrawerView() {
        // 收回 DrawerLayout
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    // 切換 Fragment
    @Override
    public void replaceFragmentContainer(Fragment fragment) {
        replaceFragment(fragment);
    }

    @Override
    public void setActionTitle(String title) {
        setTitle(title);
    }

}
