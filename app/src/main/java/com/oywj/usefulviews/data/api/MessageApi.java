package com.oywj.usefulviews.data.api;

import com.oywj.usefulviews.data.bean.GankItemBean;
import com.oywj.usefulviews.data.http.core.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface MessageApi {

    String BASE_URL = "http://gank.io/api/";

    @GET("data/{tech}/{num}/{page}")
    Observable<HttpResult<List<GankItemBean>>> getTechList(@Path("tech") String tech, @Path("num") int num, @Path("page") int page);

//    @GET("data/{tech}/{num}/{page}")
//    Observable<String> getTechList(@Path("tech") String tech, @Path("num") int num, @Path("page") int page);


}
