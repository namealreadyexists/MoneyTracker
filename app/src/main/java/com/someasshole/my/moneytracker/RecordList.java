package com.someasshole.my.moneytracker;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecordList {
    @SerializedName("data")
    protected List<Record> mRecords;
}
