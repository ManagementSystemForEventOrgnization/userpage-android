package com.example.myapplication.util.api;

import android.content.Context;

public class UtilsApi {
//    172.16.3.198:5000
    public static String BASE_URL_API = "https://event-orgnization.herokuapp.com/api/";
    //  Interface BaseApiService
    public static BaseApiService getAPIService(Context context){
        return UsageSample.getClient(BASE_URL_API, context).create(BaseApiService.class);
//        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);

    }
}
