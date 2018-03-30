package com.someasshole.my.moneytracker;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemHolder>{

    private static final String TAG = ListItemAdapter.class.getSimpleName();
    private ListItemAdapterListener mListener=null;

    private List<Record> mRecordList;

    public void setListener(ListItemAdapterListener listener){
        mListener = listener;
    }
    public ListItemAdapter(List<Record> records){
        mRecordList = records;
    }
    protected void setData(List<Record> serverResponse){
        mRecordList = serverResponse;
        notifyDataSetChanged();
    }
    protected void addData(Record record){
        mRecordList.add(record);
        notifyItemInserted(mRecordList.size());
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
        holder.applyData(record,position,mListener,selections.get(position));
    }
    @Override
    public int getItemCount(){
        if (mRecordList==null){return 0;}
        return mRecordList.size();
    }

    private SparseBooleanArray selections = new SparseBooleanArray();

    public void toggleSelection(int position){
        if(selections.get(position,false)){
            selections.delete(position);
        }else{
            selections.put(position,true);
        }
        notifyItemChanged(position);
    }

    protected void clearSelections(){
        selections.clear();
        notifyDataSetChanged();
    }

    protected int getSelectedItemCount(){
        return selections.size();
    }

    protected List<Integer> getSelectedItems(){
        List<Integer> items = new ArrayList<>(selections.size());
        for (int i=0;i<selections.size();i++){
            items.add(selections.keyAt(i));
        }
        return items;
    }

    protected Record remove(int position){
        final Record mRecord = mRecordList.remove(position);
        notifyItemRemoved(position);
        return mRecord;
    }

    static class ListItemHolder extends RecyclerView.ViewHolder{

        private final TextView nameTextView;
        private final TextView priceTextView;

        public ListItemHolder(View itemView){
            super(itemView);
            nameTextView = itemView.findViewById(R.id.list_item_name);
            priceTextView = itemView.findViewById(R.id.list_item_price);
        }

        public void applyData(final Record record, final int position, final ListItemAdapterListener listener,boolean selected){
            nameTextView.setText(record.getName());
            priceTextView.setText(record.getPriceBeautify());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.onItemClick(record,position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {
                    if(listener!=null){
                        listener.onItemLongClick(record,position);
                    }
                    return true;
                }
            });

            itemView.setActivated(selected);
        }
    }
}