package com.lifeassistant.model;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.lifeassistant.R;

public class MainNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationItemClick itemClick;

    public MainNavigationListener(NavigationItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // 取得選項id
        int id = menuItem.getItemId();

        // 判斷點了哪個項目並做回應
        if (id == R.id.action_home) {
            // 按下首頁要做的事
            itemClick.clickHomeItem();
            return true;
        } else if (id == R.id.action_weather) {
            // 按下天氣預報要做的事
            itemClick.clickWeatherItem();
            return true;
        } else if (id == R.id.action_air) {
            // 按下空氣品質要做的事
            itemClick.clickAirItem();
            return true;
        } else if (id == R.id.action_setting) {
            // 按下設定要做的事
            itemClick.clickSettingItem();
            return true;
        }

        return false;
    }

    public interface NavigationItemClick {
        // 首頁預報被點擊
        void clickHomeItem();

        // 天氣預報被點擊
        void clickWeatherItem();

        // 空氣品質被點擊
        void clickAirItem();

        // 設定被點擊
        void clickSettingItem();
    }
}
