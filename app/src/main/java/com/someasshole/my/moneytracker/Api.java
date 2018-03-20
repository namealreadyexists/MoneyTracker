package com.someasshole.my.moneytracker;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("/items")
    Call<ServerResponse> getItems(@Query("type") String type);

    @POST("/items/add")
    Call<ServerResponse> registerRecord(@Body RegisterRecordBody registerRecordBody);

    @DELETE("/items")
    Call<ServerResponse> deleteRecord(@Query("id") int id);
}