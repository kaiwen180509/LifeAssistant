package com.lifeassistant.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 綁定 View
        ButterKnife.bind(this);

        // 設定 ActionBar
        setToolBar();

        // 呼叫 Presenter 設置 NavigationView
        presenter.getNavigationViewListener();

        // 呼叫 Presenter 準備資料
        presenter.getAQIData(this);
        presenter.getWeatherData(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // 建立 Menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 設定 MenuItem 的動作
        if (item.getItemId() == R.id.action_refresh) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 設定 Navigation
    @Override
    public void setNavigationSelectedListener(MainNavigationListener listener) {

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

}
