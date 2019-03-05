package com.lifeassistant.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public abstract class BaseFragmentActivity extends BaseActivity {
    protected FragmentManager fragmentManager;

    // 第一個 Fragment
    protected abstract Fragment getFirstFragment();

    protected abstract int getContainerID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();

        // 建立第一個 Fragment
        addFragment(getFirstFragment());
    }

    // 增加 Fragment
    protected void addFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(getContainerID(), fragment).commit();
    }

    // 替換 Fragment
    protected void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(getContainerID(), fragment).commit();
    }
}
