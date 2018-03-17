package com.someasshole.my.moneytracker;

import android.content.Context;

public class Record {

    protected static final String TYPE_INCOMES = "incomes";
    protected static final String TYPE_EXPENSES = "expenses";
    protected static final String TYPE_UNKNOWN = "unknown";

    private String name;
    private int price;
    private String type;
    private Context mContext;

    protected Record(Context context, String name, int price, String type){
        this.name = name;
        this.price=price;
        this.mContext = context;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected int getPrice() {
        return price;
    }

    protected String getPriceBeautify(){
        return mContext.getString(R.string.add_item_ruble_placeholder,String.valueOf(this.getPrice()));
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
