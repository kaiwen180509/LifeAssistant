package com.lifeassistant.base;

public abstract class BaseModel<T> {
    // 參數
    protected Object[] mParams = null;

    // 設置 Model 的參數
    public BaseModel params(Object... args) {
        mParams = args;
        return this;
    }

    // 將 RecyclerViewCallBack 交給子類別實作
    public abstract void execute(BaseCallBack callback);
}