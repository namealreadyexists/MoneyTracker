package com.someasshole.my.moneytracker;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by transcend on 19.03.2018.
 */

public class RecordList {
    @SerializedName("data")
    protected List<Record> mRecords;
}
