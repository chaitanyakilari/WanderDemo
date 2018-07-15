package com.wanderdemo.model.rest;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wanderdemo.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static Retrofit apiClient = null;

    public static Retrofit getClient(String url) {
        if (apiClient == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(BuildConfig.LOG_DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            // interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
            if (BuildConfig.LOG_DEBUG) {

            }

            apiClient = new Retrofit.Builder()
                    .baseUrl(url)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return apiClient;
    }
}
