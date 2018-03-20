package com.someasshole.my.moneytracker;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponse {

    protected static final String STATE_SUCCSESS = "success";
    protected static final String STATE_DELETED = "deleted";

    @SerializedName("status")
    protected String status;
    @SerializedName("data")
    protected List<Record> mRecords;
}
