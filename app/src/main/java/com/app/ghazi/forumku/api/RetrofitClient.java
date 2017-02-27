package com.app.ghazi.forumku.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Riesto on 27/02/2017.
 */

public class RetrofitClient {

    public static Retrofit retrofit = null;
    private static final String BASE_URL = "http://192.168.1.3:8081/";

    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
