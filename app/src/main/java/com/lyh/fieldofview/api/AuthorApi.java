package com.lyh.fieldofview.api;

import com.lyh.fieldofview.model.VideoAuthor;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lyh on 2017/3/20.
 */

public interface AuthorApi {

    @GET("v4/pgcs/all?num=10")
    Observable<VideoAuthor> authors(@Query("start") int start);

}
