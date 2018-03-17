package com.someasshole.my.moneytracker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by transcend on 17.03.2018.
 */

public class ListItemFragment extends Fragment {

    private static final String TAG = ListItemFragment.class.getSimpleName();
    private static final String ARGUMENT_TYPE_KEY = "type";
    public static final String TYPE_INCOMES = "incomes";
    public static final String TYPE_EXPENSES = "expenses";
    private static final String TYPE_UNKNOWN = "unknown";

    private String type;
    private ListItemAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Record> mRecords;

    public static ListItemFragment createItemsFragment(String type){
        ListItemFragment fragment = new ListItemFragment();
        Bundle args = new Bundle();
        args.putString(ListItemFragment.ARGUMENT_TYPE_KEY, ListItemFragment.TYPE_INCOMES);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Log.i(TAG, "onAttach: ");
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Context mContext = getActivity().getApplicationContext();
        mRecords = new ArrayList<>();
        mRecords.add(new Record("Milk",90,Record.TYPE_EXPENSES));
        mRecords.add(new Record("Milk",80,Record.TYPE_EXPENSES));
        mRecords.add(new Record("Milk",70,Record.TYPE_EXPENSES));
        mRecords.add(new Record("Milk",60,Record.TYPE_EXPENSES));
        mRecords.add(new Record("Milk",50,Record.TYPE_EXPENSES));
        mRecords.add(new Record("Milk",40,Record.TYPE_EXPENSES));
        mRecords.add(new Record("Milk",30,Record.TYPE_EXPENSES));
        mRecords.add(new Record("Milk",20,Record.TYPE_EXPENSES));
        mRecords.add(new Record("Milk",10,Record.TYPE_INCOMES));
        mRecords.add(new Record("Milk",9,Record.TYPE_INCOMES));
        mRecords.add(new Record("Milk",8,Record.TYPE_INCOMES));
        mRecords.add(new Record("Milk",7,Record.TYPE_INCOMES));
        mRecords.add(new Record("Milk",6,Record.TYPE_INCOMES));
        mRecords.add(new Record("Milk",5,Record.TYPE_INCOMES));
        mAdapter = new ListItemAdapter(mRecords);

        Bundle args = getArguments();
        type = args.getString(ARGUMENT_TYPE_KEY,TYPE_UNKNOWN);
        if(type.equals(TYPE_UNKNOWN)){
            throw new IllegalArgumentException("Unknown argument type");
        }
        Log.i(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_itemlist, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        Log.i(TAG, "onViewCreated: ");
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.i(TAG, "onStart: ");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG, "onResume: ");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "onPause: ");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.i(TAG, "onStop: ");
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
    @Override
    public void onDetach(){
        super.onDetach();
        Log.i(TAG, "onDetach: ");
    }
}
