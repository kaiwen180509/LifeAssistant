package com.lifeassistant.base;

public final class DataModel {
    public static BaseModel request(Class clazz) {
        // 藉由反射機制取得 Model 的引用
        BaseModel model = null;

        try {
            model = (BaseModel) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return model;
    }
}