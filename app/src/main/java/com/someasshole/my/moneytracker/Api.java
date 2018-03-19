package com.someasshole.my.moneytracker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("/items")

    Call<RecordList> getItems(@Query("type") String type);
}
