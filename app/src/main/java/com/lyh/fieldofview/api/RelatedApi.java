package com.lyh.fieldofview.api;

import com.lyh.fieldofview.model.Related;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lyh on 2017/3/20.
 */

public interface RelatedApi {

    @GET("v3/video/{id}/detail/related")
    Observable<Related> related(@Path("id") int id);

}
