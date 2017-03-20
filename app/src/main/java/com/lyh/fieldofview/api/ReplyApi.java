package com.lyh.fieldofview.api;

import com.lyh.fieldofview.model.Replies;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lyh on 2017/3/20.
 */

public interface ReplyApi {

    @GET("v1/replies/video")
    Observable<Replies> fetchReplies(@Query("id") int id);

    @GET("v1/replies/video?num=10")
    Observable<Replies> fetchReplies(@Query("id") int id, @Query("lastId") int lastId);
}
