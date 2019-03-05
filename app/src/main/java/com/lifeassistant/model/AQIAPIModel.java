package com.lifeassistant.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifeassistant.base.BaseCallBack;
import com.lifeassistant.base.BaseModel;
import com.lifeassistant.callback.AQICallBack;
import com.lifeassistant.retrofit.APIService;
import com.lifeassistant.retrofit.AQIBean;
import com.lifeassistant.retrofit.RetrofitFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

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
                    // 已經有舊的資料了
                    if (mParams[0] != null && !mParams[0].equals("")) {
                        // 宣告 Gson 用來解析資料
                        Gson gson = new Gson();
                        Type typeToken = new TypeToken<ArrayList<AQIBean>>() {
                        }.getType();
                        // 新的資料
                        ArrayList<AQIBean> list = gson.fromJson(response.body().string(), typeToken);
                        // 舊的資料
                        ArrayList<AQIBean> oldList = gson.fromJson(String.valueOf(mParams[0]), typeToken);

                        // 取得更新時間並比較
                        String time = list.get(0).getPublishTime();
                        String oldTime = oldList.get(0).getPublishTime();
                        int result = time.compareTo(oldTime);

                        // 判斷取得的資料是否有更新
                        if (result <= 0) {
                            // 無更新，不做動作
                        } else {
                            // 有更新的資料，回傳
                            callBack.onAQIDataPrepared(response.body().string());
                        }
                    } else {
                        // 回傳 API 取得的資料
                        callBack.onAQIDataPrepared(response.body().string());
                    }
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
