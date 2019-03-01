package com.lifeassistant.base;

public abstract class BasePresenter<V extends BaseView> {

    /**
     * MainPresenter 綁定的 View
     */
    private V mView;

    /**
     * 連接 View
     */
    public void attachView(V mView) {
        this.mView = mView;
    }

    /**
     * 在 onDestroy 呼叫，以斷開對 View 的連接
     */
    public void detachView() {
        this.mView = null;
    }

    /**
     * 檢查 View 是否連接，避免 NULL
     */
    public boolean isViewAttached() {
        return mView != null;
    }

    /**
     * View 不是連接時，取消動作
     */
    protected void checkView() {
        if (!isViewAttached()) {
            return;
        }
    }

    /**
     * 獲得連接的 view
     */
    public V getView() {
        return mView;
    }
}