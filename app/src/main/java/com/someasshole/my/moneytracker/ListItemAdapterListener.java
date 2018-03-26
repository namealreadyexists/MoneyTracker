package com.someasshole.my.moneytracker;

public interface ListItemAdapterListener {
    void onItemClick(Record record, int position);
    void onItemLongClick(Record record,int position);
}
