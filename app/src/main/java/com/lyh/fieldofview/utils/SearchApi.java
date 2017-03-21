package com.lyh.fieldofview.utils;

import com.lyh.fieldofview.model.SearchResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lyh on 2017/3/21.
 */

public interface SearchApi {
    @GET("v3/queries/hot")
    Observable<List<String>> getTrendingTag();

    @GET("v1/search?num=10")
    Observable<SearchResult> query(@Query("query") String key, @Query("start") int start);

}
