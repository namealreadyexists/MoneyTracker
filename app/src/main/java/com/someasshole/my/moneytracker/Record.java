package com.someasshole.my.moneytracker;

/**
 * Created by transcend on 13.03.2018.
 */

public class Record {

    private String name;
    private int price;

    public Record(String name, int price){
        this.name = name;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
