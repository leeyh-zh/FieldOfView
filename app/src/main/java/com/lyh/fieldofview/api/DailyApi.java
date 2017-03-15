package com.lyh.fieldofview.api;

import com.lyh.fieldofview.model.Daily;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lyh on 2017/3/15.
 */

public interface DailyApi {

    @GET("v2/feed?num=2")
    Observable<Daily> getDaily(@Query("date") long date);

    @GET("v2/feed?num=2")
    Observable<Daily> getDaily();
}
