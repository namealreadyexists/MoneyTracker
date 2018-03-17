package com.someasshole.my.moneytracker;

public class Record {

    protected static final String TYPE_INCOMES = "incomes";
    protected static final String TYPE_EXPENSES = "expenses";
    protected static final String TYPE_UNKNOWN = "unknown";

    protected static final String RUB = "₽";

    private String name;
    private int price;
    private String type;

    protected Record(String name, int price, String type){
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

    protected int getPrice() {
        return price;
    }

    protected String getPriceBeautify(){
        return String.valueOf(this.price)+RUB;
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