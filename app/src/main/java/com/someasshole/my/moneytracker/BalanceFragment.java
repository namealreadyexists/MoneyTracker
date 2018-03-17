package com.someasshole.my.moneytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BalanceFragment extends Fragment {

    private static final String TAG = BalanceFragment.class.getSimpleName();

    public static BalanceFragment createBalanceFragment(){
        return new BalanceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_balance,container,false);
        return view;
    }
}
