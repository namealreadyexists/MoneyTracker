package com.someasshole.my.moneytracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemHolder>{

    private List<Record> mRecordList;
    public ListItemAdapter(List<Record> records){
        mRecordList = records;
    }

    @Override
    public ListItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record,parent,false);
        return new ListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ListItemHolder holder, int position){
        Record record = mRecordList.get(position);
        holder.applyData(record);
    }
    @Override
    public int getItemCount(){
        return mRecordList.size();
    }

    static class ListItemHolder extends RecyclerView.ViewHolder{

        private final TextView nameTextView;
        private final TextView priceTextView;

        public ListItemHolder(View itemView){
            super(itemView);
            nameTextView = itemView.findViewById(R.id.list_item_name);
            priceTextView = itemView.findViewById(R.id.list_item_price);
        }

        public void applyData(Record record){
            nameTextView.setText(record.getName());
            priceTextView.setText(record.getPriceBeautify());
        }
    }
}