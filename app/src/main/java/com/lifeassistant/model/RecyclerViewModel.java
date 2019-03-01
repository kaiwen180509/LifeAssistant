package com.lifeassistant.model;


import com.lifeassistant.adapter.MainAdapter;
import com.lifeassistant.base.BaseCallBack;
import com.lifeassistant.base.BaseModel;
import com.lifeassistant.callback.RecyclerViewCallBack;

import java.util.ArrayList;

public class RecyclerViewModel extends BaseModel<String> {

    @Override
    public void execute(BaseCallBack callback) {
        RecyclerViewCallBack recyclerViewCallBack = (RecyclerViewCallBack) callback;
        // 產生一筆20個的資料給 Adapter
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(i, "TEST : " + i);
        }

        // 設定 RecyclerView Adapter
        recyclerViewCallBack.onAdapterPrepare(new MainAdapter(arrayList));
        recyclerViewCallBack.onComplete();
    }
}