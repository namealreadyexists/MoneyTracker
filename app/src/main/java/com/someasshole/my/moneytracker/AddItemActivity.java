package com.someasshole.my.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText priceEditText;
    private Button addButton;
    private Toolbar mToolbar;

    private boolean isName;
    private boolean isPrice;
    private String type;

    private static final String TAG = "AddItemActivity";
    protected static final String TYPE_KEY = "type";
    protected static final String ARG_NAME = "name";
    protected static final String ARG_PRICE = "price";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        setTitle(R.string.add_item_title);
        nameEditText = findViewById(R.id.add_item_name);
        priceEditText = findViewById(R.id.add_item_price);
        addButton = findViewById(R.id.add_item_button);
        mToolbar = findViewById(R.id.toolbar_add_item);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.add_item_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addButton.setEnabled(false);
        priceEditText.setHint(getApplicationContext().getString(R.string.add_item_ruble_placeholder,getResources().getString(R.string.add_item_price_hint)));

        nameEditText.addTextChangedListener(new LocalTextWatcher(nameEditText));
        priceEditText.addTextChangedListener(new LocalTextWatcher(priceEditText));
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String price = priceEditText.getText().toString();

                Intent data = new Intent();
                data.putExtra(ARG_NAME,name);
                data.putExtra(ARG_PRICE,price);

                setResult(RESULT_OK,data);
                Log.i(TAG,"Button pressed");
                finish();
            }
        });

        type = getIntent().getExtras().getString(TYPE_KEY);
    }

    private class LocalTextWatcher implements TextWatcher{

        private EditText mView;

        private int mViewId;
        private String c;

        private LocalTextWatcher(EditText view){
            this.mView = view;
            mViewId= mView.getId();
            c = getResources().getString(R.string.add_item_ruble);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(mViewId==R.id.add_item_name) isName=!(editable.toString().isEmpty());
            if(mViewId==R.id.add_item_price) {
                isPrice=!(editable.toString().isEmpty());
                String tmpStr = priceEditText.getText().toString();
                if (!tmpStr.isEmpty()&&!tmpStr.endsWith(c)) {
                    String tmp = getApplicationContext().getString(R.string.add_item_ruble_placeholder,editable.toString());
                    priceEditText.setText(tmp);
                    priceEditText.setSelection(priceEditText.length() - 1); // Спасибо Седых Анне
                }
                if (tmpStr.equals(c)) priceEditText.setText("");
            }
            addButton.setEnabled(isName && isPrice);
        }
    }
}