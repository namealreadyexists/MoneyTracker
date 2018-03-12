package com.someasshole.my.moneytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText priceEditText;
    private Button addButton;

    private boolean isName;
    private boolean isPrice;

    private static final String TAG = "AddItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        setTitle(R.string.add_item_title);

        nameEditText = findViewById(R.id.add_item_name);
        priceEditText = findViewById(R.id.add_item_price);
        addButton = findViewById(R.id.add_item_button);
        addButton.setEnabled(false);

        nameEditText.addTextChangedListener(new LocalTextWatcher(nameEditText));
        priceEditText.addTextChangedListener(new LocalTextWatcher(priceEditText));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String price = priceEditText.getText().toString();

                Log.i(TAG,"Button pressed");
            }
        });
    }

    private boolean checkSetButtonActive(){
        if (isName && isPrice) return true;
        return false;
    }

    private class LocalTextWatcher implements TextWatcher{

        private EditText mView;
        private int mViewId;
        private CharSequence c;

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
            switch (mViewId){
                case R.id.add_item_name: {
                    if(editable.toString().isEmpty()) isName = false;
                    else isName = true;
                    break;}
                case R.id.add_item_price:{
                    if(editable.toString().equals(c)) isPrice = false;
                    else isPrice = true;

                    if (!TextUtils.isEmpty(priceEditText.getText()) && !priceEditText.getText().toString().endsWith(c.toString())) {
                        priceEditText.setText(priceEditText.getText() + c.toString());
                        priceEditText.setSelection(priceEditText.length() - 1); // Спасибо Седых Анне
                    }
                    if (priceEditText.getText().toString().equals(c)) {
                        priceEditText.setText("");
                    }


                    break;}
                default: {// doing nothing
                    break;}
                }
            addButton.setEnabled(checkSetButtonActive());
        }
    }
}
