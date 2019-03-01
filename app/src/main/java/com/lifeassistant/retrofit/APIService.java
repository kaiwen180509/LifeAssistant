package com.lifeassistant.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    // 政府開放資料平台 -> PM2.5 資料 API，採用 GET 解析
    @GET("ws/Data/ATM00625/?$format=json")
    Call<ResponseBody> loadPM25Data();

    // 政府開放資料平台 -> 空氣品質指標(AQI) 資料 API，採用 GET 解析
    @GET("ws/Data/AQI/?$format=json")
    Call<ResponseBody> loadAQIData();

    // 政府開放資料平台 -> 一般天氣預報-今明36小時天氣預報 資料 API，採用 GET 解析
    @GET("fileapi/v1/opendataapi/F-C0032-001?Authorization=rdec-key-123-45678-011121314&format=JSON")
    Call<ResponseBody> loadWeatherData();
}

