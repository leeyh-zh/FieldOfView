package com.lyh.fieldofview;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lyh on 2017/3/15.
 */

public class InteressantRetrofit {

    private static final String BASE_URL = "http://baobab.kaiyanapp.com/api/";

    private Map<Class, Object> apis = new HashMap<>();

    private Retrofit retrofit;

    public InteressantRetrofit() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public <T> T createApi(Class<T> serice) {
        if (!apis.containsKey(serice)) {
            T instance = retrofit.create(serice);
            apis.put(serice, instance);
        }
        return (T) apis.get(serice);
    }
}
