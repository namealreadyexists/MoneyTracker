package com.someasshole.my.moneytracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListItemFragment extends Fragment {

    private static final String TAG = ListItemFragment.class.getSimpleName();
    private static final String ARGUMENT_TYPE_KEY = "type";
    public static final String TYPE_INCOMES = "income";
    public static final String TYPE_EXPENSES = "expense";
    private static final String TYPE_UNKNOWN = "unknown";
    private static final int ADD_ITEM_REQUEST_CODE = 0;

    private String type;
    private ListItemAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Record> mRecords;
    private FloatingActionButton fab;
    SwipeRefreshLayout swipeRefreshLayout;
    private Api mApi;

    public static ListItemFragment createItemsFragment(String type){
        ListItemFragment fragment = new ListItemFragment();
        Bundle args = new Bundle();

        if(type.equals(TYPE_INCOMES)) args.putString(ListItemFragment.ARGUMENT_TYPE_KEY, ListItemFragment.TYPE_INCOMES);
        if(type.equals(TYPE_EXPENSES)) args.putString(ListItemFragment.ARGUMENT_TYPE_KEY, ListItemFragment.TYPE_EXPENSES);
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

        mRecords = new ArrayList<>();
        mRecords = Collections.emptyList();
        mAdapter = new ListItemAdapter(mRecords);

        Bundle args = getArguments();
        type = args.getString(ARGUMENT_TYPE_KEY,TYPE_UNKNOWN);
        if(type.equals(TYPE_UNKNOWN)){
            throw new IllegalArgumentException("Unknown argument type");
        }
        Log.e(TAG, "type="+type);
        mApi = ((App) getActivity().getApplication()).getApi();
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
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddItemActivity.class);
                intent.putExtra(AddItemActivity.TYPE_KEY,type);
                startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE,Color.CYAN,Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

        loadItems();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){

        if(requestCode==ADD_ITEM_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            String name = data.getStringExtra(AddItemActivity.ARG_NAME);
            String price = data.getStringExtra(AddItemActivity.ARG_PRICE);

            Record record = new Record(name,price,type);
            mAdapter.addData(record);

            Log.e(TAG, "onActivityResult: name="+name + " | price="+price);
        }

        super.onActivityResult(requestCode,resultCode,data);
    }

    private void loadItems(){
        Log.e(TAG,"Loading items");
        Call<ServerResponse> call = mApi.getItems(type);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Log.e(TAG, "onResponse: ");
                mAdapter.setData(response.body());
                swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "onFailure: " +t.toString());
            }
        });
    }
}
