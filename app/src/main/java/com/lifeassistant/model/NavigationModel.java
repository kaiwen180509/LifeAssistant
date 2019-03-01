package com.lifeassistant.model;

import com.lifeassistant.base.BaseCallBack;
import com.lifeassistant.base.BaseModel;
import com.lifeassistant.callback.NavigationCallBack;

public class NavigationModel extends BaseModel {
    private NavigationCallBack callBack;

    @Override
    public void execute(BaseCallBack callback) {
        this.callBack = (NavigationCallBack) callback;

        // 設定 Navigation 的 Listener
        MainNavigationListener listener = new MainNavigationListener(itemClick);
        this.callBack.onNavigationItemSelectedListenerPrepare(listener);
        this.callBack.onComplete();
    }

    private MainNavigationListener.NavigationItemClick itemClick = new MainNavigationListener.NavigationItemClick() {
        @Override
        public void clickHomeItem() {
            callBack.onClickHomeItem();
        }

        @Override
        public void clickWeatherItem() {
            callBack.onClickWeatherItem();
        }

        @Override
        public void clickAirItem() {
            callBack.onClickAirItem();
        }

        @Override
        public void clickSettingItem() {
            callBack.onClickSettingItem();
        }
    };
}
