package com.someasshole.my.moneytracker;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagesAdapter extends FragmentPagerAdapter {

    private static final String TAG = MainPagesAdapter.class.getSimpleName();
    protected static final int PAGE_EXPENSES = 0;
    protected static final int PAGE_INCOMES = 1;
    protected static final int PAGE_BALANCE = 2;

    private String[] titles;

    public MainPagesAdapter(Context context, FragmentManager fragmentManager){
        super(fragmentManager);
        titles = context.getResources().getStringArray(R.array.tab_title);
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case PAGE_INCOMES:return ListItemFragment.createItemsFragment(ListItemFragment.TYPE_INCOMES);
            case PAGE_EXPENSES: return ListItemFragment.createItemsFragment(ListItemFragment.TYPE_EXPENSES);
            case PAGE_BALANCE: return BalanceFragment.createBalanceFragment();
            default: return null;
        }
    }
    @Override
    public int getCount(){
        return titles.length;
    }
    @Override
    public CharSequence getPageTitle(int position){
        return titles[position];
    }

}
