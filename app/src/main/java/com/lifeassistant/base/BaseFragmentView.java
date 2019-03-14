package com.lifeassistant.base;

import android.view.View;

public interface BaseFragmentView extends BaseView {
    // 顯示 Snackbar
    void showSnackbar(String msg, String action, View.OnClickListener listener);

    // 顯示 Snackbar
    void showSnackbar(String msg);

    // 顯示進度條
    void showProgress();

    // 關閉進度條
    void closeProgress();
}
