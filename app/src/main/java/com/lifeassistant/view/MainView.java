package com.lifeassistant.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;

import com.lifeassistant.base.BaseView;
import com.lifeassistant.model.MainNavigationListener;

public interface MainView extends BaseView{
    /**
     * 設定 NavigationView 的 Listener
     */
    void setNavigationSelectedListener(MainNavigationListener listener);

    /**
     * 關閉 DrawerLayout
     */
    void closeDrawerView();

    /**
     * 替換 Fragment
     */
    void replaceFragmentContainer(Fragment fragment);

}
