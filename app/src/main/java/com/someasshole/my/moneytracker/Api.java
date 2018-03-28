package com.someasshole.my.moneytracker;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("auth")
    Call<AuthResult> auth(@Query("social_user_id") String userID);

    @GET("items/add")
    Call<AddItemResult> addItem(@Query("price") String price, @Query("name") String name,@Query("type") String type);

    @GET("/items")
    Call<ServerResponse> getItems(@Query("type") String type);

    @POST("/items/add")
    Call<ServerResponse> registerRecord(@Body RegisterRecordBody registerRecordBody);

    @DELETE("/items")
    Call<ServerResponse> deleteRecord(@Query("id") int id);
}