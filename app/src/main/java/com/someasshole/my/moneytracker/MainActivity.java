package com.someasshole.my.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private MainPagesAdapter mainPagesAdapter;
    private ActionMode mActionMode = null;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_account);

        mainPagesAdapter = new MainPagesAdapter(this,getSupportFragmentManager());
        mViewPager.setAdapter(mainPagesAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        floatingActionButton = findViewById(R.id.fab1);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddItemActivity.class);
                int currentItem = mViewPager.getCurrentItem();
                String type = null;
                switch (currentItem){
                    case 0: type = ListItemFragment.TYPE_INCOMES;break;
                    case 1: type = ListItemFragment.TYPE_EXPENSES;break;
                    default: type = ListItemFragment.TYPE_UNKNOWN; break;
                }
                intent.putExtra(AddItemActivity.TYPE_KEY,type);
                startActivityForResult(intent, ListItemFragment.ADD_ITEM_REQUEST_CODE);
                Log.e(TAG,"Type: "+ type);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case MainPagesAdapter.PAGE_INCOMES: {
                        floatingActionButton.show();
                        break;
                    }
                    case MainPagesAdapter.PAGE_EXPENSES: {
                        floatingActionButton.show();
                        break;
                    }
                    case MainPagesAdapter.PAGE_BALANCE: {
                        floatingActionButton.hide();
                        break;
                    }
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    case ViewPager.SCROLL_STATE_IDLE:
                        floatingActionButton.setEnabled(true);
                        break;
                        case ViewPager.SCROLL_STATE_DRAGGING:
                        case ViewPager.SCROLL_STATE_SETTLING:{
                            if (mActionMode!=null){
                                mActionMode.finish();
                            }
                            floatingActionButton.setEnabled(false);
                            break;
                        }
                }
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG,"onStart");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG,"onResume");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG,"onPause");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG,"onStrop");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        for (Fragment fragment: getSupportFragmentManager().getFragments()){
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }
    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode){
        super.onSupportActionModeStarted(mode);
        Log.i(TAG, "onSupportActionModeFinish: ");
        mActionMode=mode;
        floatingActionButton.hide();

    }
    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode){
        super.onSupportActionModeFinished(mode);
        Log.i(TAG, "onSupportActionModeFinished: ");
        mActionMode = null;
        floatingActionButton.show();
    }
}
