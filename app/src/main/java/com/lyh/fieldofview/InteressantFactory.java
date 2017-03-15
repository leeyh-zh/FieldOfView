package com.lyh.fieldofview;

/**
 * Created by lyh on 2017/3/15.
 */

public class InteressantFactory {

    private static final Object object = new Object();
    private volatile static InteressantRetrofit retrofit;

    public static InteressantRetrofit getRetrofit() {
        synchronized (object) {
            if (retrofit == null) {
                retrofit = new InteressantRetrofit();
            }
            return retrofit;
        }
    }
}
