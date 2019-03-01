package com.lifeassistant.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements BaseView {
    /**
     * 獲取 Presenter 實例
     */
    protected abstract BasePresenter getPresenter();

    /**
     * 將 Presenter 實例化
     */
    protected abstract void initPresenter();

    // 取得 Fragment 的 View ID
    public abstract int getContentViewId();

    protected Unbinder unbinder;

    // View 元件初始化
    protected abstract void initAllMembersView(Bundle savedInstanceState);

    protected Context context;
    protected View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 實例化 Presenter
        initPresenter();

        // 綁定 Presenter
        if (getPresenter() != null) {
            getPresenter().attachView(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = getActivity();

        // 注入 Fragment 的 View
        rootView = inflater.inflate(getContentViewId(), container, false);

        // 綁定 ButterKnife
        unbinder = ButterKnife.bind(this, rootView);

        // View 元件初始化動作
        initAllMembersView(savedInstanceState);

        return rootView;
    }

    /**
     * 檢查 Activity 連接
     */
    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }

    public static class ActivityNotAttachedException extends RuntimeException {
        public ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity !");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().detachView();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}