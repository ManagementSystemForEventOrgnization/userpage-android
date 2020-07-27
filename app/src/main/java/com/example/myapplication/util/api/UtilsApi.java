package com.example.myapplication.util.api;

import android.content.Context;
//10.253.141.242
// "https://event-orgnization.herokuapp.com/api/"

public class UtilsApi {
    public static String BASE_URL_API =
            "https://event-orgnization.herokuapp.com/api/";
    //  Interface BaseApiService
    public static BaseApiService getAPIService(Context context){
        return UsageSample.getClient(BASE_URL_API, context).create(BaseApiService.class);
//        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);

    }
}
