package com.lifeassistant.model;

import com.lifeassistant.base.BaseCallBack;
import com.lifeassistant.base.BaseModel;
import com.lifeassistant.callback.AQICallBack;
import com.lifeassistant.retrofit.APIService;
import com.lifeassistant.retrofit.RetrofitFactory;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AQIAPIModel extends BaseModel<AQICallBack> {
    private AQICallBack callBack;

    @Override
    public void execute(BaseCallBack callback) {
        this.callBack = (AQICallBack) callback;

        // 用 Retrofit 創建 APIService 介面並呼叫
        APIService apiService = RetrofitFactory.getEPAInstance().create(APIService.class);
        Call<ResponseBody> call = apiService.loadAQIData();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callBack.onAQIDataPrepared(response.body().string());
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
