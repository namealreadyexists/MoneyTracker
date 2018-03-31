package com.someasshole.my.moneytracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListItemFragment extends Fragment{

    private static final String TAG = ListItemFragment.class.getSimpleName();
    private static final String ARGUMENT_TYPE_KEY = "type";
    protected static final String TYPE_INCOMES = "income";
    protected static final String TYPE_EXPENSES = "expense";
    protected static final String TYPE_UNKNOWN = "unknown";
    protected static final int ADD_ITEM_REQUEST_CODE = 0;

    private String type;
    public ListItemAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Record> mRecords;
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
        mAdapter.setListener(new AdapterListener());

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

        swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
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
            Log.e(TAG, "onActivityResult: returning result to parent fragment");

            Record record = data.getParcelableExtra(AddItemActivity.ARG_RECORD);
            if (record.getType().equals(type)){
                addItem(record);
                Log.e(TAG, "fragment onActivityResult: name="+record.getName()+" | price="+record.getPrice()+" | type="+record.getType());
            }

        }
        super.onActivityResult(requestCode,resultCode,data);
    }


    private void loadItems(){
        Log.e(TAG,"Loading items");
        Call<List<Record>> call = mApi.getItems(type);
        call.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
                Log.e(TAG, "onResponse: ");
                mAdapter.setData(response.body());
                swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<List<Record>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "onFailure: " +t.toString());
            }
        });
    }

    private void addItem(final Record record){

        Log.e(TAG, "addItem: price="+record.getPriceInt()+" name="+record.name+" type="+record.type);

        Call<ItemResult> call = mApi.addItem(record.getPriceInt(),record.name,record.type);

        call.enqueue(new Callback<ItemResult>() {
            @Override
            public void onResponse(Call<ItemResult> call, Response<ItemResult> response) {
                ItemResult result = response.body();
                if(result.status.equals("success")){
                    mAdapter.addData(record);
                }else{
                    Log.e(TAG, "try adding element on backend, status: " + result.status + "record: " + record.price+" "+record.name+ " "+record.type);
                }
            }

            @Override
            public void onFailure(Call<ItemResult> call, Throwable t) {

            }
        });
    }
    /* ACTION MODE */

    protected ActionMode mActionMode = null;

    private void removeSelectedItems(){
        for (int i = mAdapter.getSelectedItems().size()-1;i>=0;i--){
            Record record = mAdapter.remove(mAdapter.getSelectedItems().get(i));
            Call<ItemResult> call = mApi.removeItem(record.id);
                call.enqueue(new Callback<ItemResult>() {
                    @Override
                    public void onResponse(Call<ItemResult> call, Response<ItemResult> response) {

                    }

                    @Override
                    public void onFailure(Call<ItemResult> call, Throwable t) {

                    }
                });
        }
        mActionMode.finish();
    }



    private class AdapterListener implements ListItemAdapterListener{
        @Override
        public void onItemClick(Record record, int position) {
            if (isInActionMode()){
                toggleSelection(position);
            }
            Log.e(TAG, "onItemClick: "+record.name + " position: " +position);
        }

        @Override
        public void onItemLongClick(Record record, int position) {
            if (isInActionMode()){
                return;
            }
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
            toggleSelection(position);
            Log.e(TAG, "onItemLongClick: "+record.name + " position: " +position);
        }

        private boolean isInActionMode(){
            return mActionMode!=null;
        }

        private void toggleSelection(int position){
            mAdapter.toggleSelection(position);
        }
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            mActionMode = actionMode;
            MenuInflater inflater = new MenuInflater(getContext());
            inflater.inflate(R.menu.items_menu,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.remove: showDialog();break;
                default: break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mAdapter.clearSelections();
            mActionMode = null;
        }
    };

    private void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(getContext(),R.style.DialogTheme)
                .setMessage(R.string.sure)
                .setTitle(R.string.delete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeSelectedItems();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create();
        dialog.show();
    }
}
