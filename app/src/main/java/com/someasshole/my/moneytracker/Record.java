package com.someasshole.my.moneytracker;

import android.os.Parcel;
import android.os.Parcelable;

public class Record implements Parcelable{

    protected static final String TYPE_INCOMES = "incomes";
    protected static final String TYPE_EXPENSES = "expenses";
    protected static final String TYPE_UNKNOWN = "unknown";

    protected static final String RUB = "₽";

    public int id;
    public String name;
    public String price;
    public String type;

    protected Record(String name, String price, String type){
        this.name = name;
        this.price=price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected String getPrice() {
        return price;
    }
    protected int getPriceInt() {
        String tmp = this.price;
        tmp=tmp.replace(RUB,"");
        return (int) Integer.valueOf(tmp);
    }

    protected String getPriceBeautify(){
        return String.valueOf(this.price)+RUB;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected Record(Parcel in){
        id = in.readInt();
        name = in.readString();
        price = in.readString();
        type = in.readString();
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(type);
    }
    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel parcel) {
            return new Record(parcel);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };
}