package com.someasshole.my.moneytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by transcend on 13.03.2018.
 */

public class ListItemActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ItemListAdapter mAdapter;

    private List<Record> mRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        mRecords = new ArrayList<>();
        mRecords.add(new Record("Milk",90));
        mRecords.add(new Record("Milk",80));
        mRecords.add(new Record("Milk",70));
        mRecords.add(new Record("Milk",60));
        mRecords.add(new Record("Milk",50));
        mRecords.add(new Record("Milk",40));
        mRecords.add(new Record("Milk",30));
        mRecords.add(new Record("Milk",20));
        mRecords.add(new Record("Milk",10));
        mRecords.add(new Record("Milk",9));
        mRecords.add(new Record("Milk",8));
        mRecords.add(new Record("Milk",7));
        mRecords.add(new Record("Milk",6));
        mRecords.add(new Record("Milk",5));

        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemListAdapter(mRecords);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class ItemListHolder extends RecyclerView.ViewHolder{

        private final TextView nameTextView;
        private final TextView priceTextView;

        public ItemListHolder(View itemView){
            super(itemView);
            nameTextView = itemView.findViewById(R.id.list_item_name);
            priceTextView = itemView.findViewById(R.id.list_item_price);
        }

        public void applyData(Record record){
            nameTextView.setText(record.getName());
            priceTextView.setText(getApplicationContext().getString(R.string.add_item_ruble_placeholder,String.valueOf(record.getPrice())));
        }
    }

    private class ItemListAdapter extends RecyclerView.Adapter<ItemListHolder>{

        private List<Record> mRecordList;
        public ItemListAdapter(List<Record> records){
            mRecordList = records;
        }

        @Override
        public ItemListHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_record,parent,false);
            return new ItemListHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemListHolder holder, int position){
            Record record = mRecordList.get(position);
            holder.applyData(record);
        }
        @Override
        public int getItemCount(){
            return mRecordList.size();
        }
    }
}
