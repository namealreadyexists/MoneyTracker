package com.someasshole.my.moneytracker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("auth")
    Call<AuthResult> auth(@Query("social_user_id") String userID);

    @POST("items/add")
    Call<AddItemResult> addItem(@Query("price") int price, @Query("name") String name,@Query("type") String type);

    @GET("items")
    Call<List<Record>> getItems(@Query("type") String type);

    @GET("balance")
    Call<BalanceResult> balance();
}