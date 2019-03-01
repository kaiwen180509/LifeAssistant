package com.lifeassistant.model;

import com.lifeassistant.base.BaseCallBack;
import com.lifeassistant.base.BaseModel;
import com.lifeassistant.callback.PM25CallBack;
import com.lifeassistant.retrofit.APIService;
import com.lifeassistant.retrofit.RetrofitFactory;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PM25Model extends BaseModel {
    private PM25CallBack callBack;

    @Override
    public void execute(BaseCallBack callback) {
        this.callBack = (PM25CallBack) callback;

        getPM25Data();
    }

    // 取得 PM25 的資料
    private void getPM25Data() {
        // 用 Retrofit 創建 APIService 介面並呼叫
        APIService apiService = RetrofitFactory.getEPAInstance().create(APIService.class);
        Call<ResponseBody> call = apiService.loadPM25Data();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callBack.onPM25DataPrepared(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callBack.onComplete();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onFailure(t.toString());
            }
        });
    }
}
