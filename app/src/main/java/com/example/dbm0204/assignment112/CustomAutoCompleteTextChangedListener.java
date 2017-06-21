package com.example.dbm0204.assignment112;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

/**
 * Created by dbm0204 on 6/21/17.
 */

public class CustomAutoCompleteTextChangedListener implements TextWatcher {
    public static final String TAG ="CustomAutoCompleteTextChanged";
    Context context;

    public CustomAutoCompleteTextChangedListener(Context context) {
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //TODO Auto-generated method
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        MainActivity mainActivity = ((MainActivity) context);
        // query the database based on user input
        mainActivity.item = mainActivity.getItemsFromDb(charSequence.toString());
        //update the adapter
        mainActivity.myAdapter.notifyDataSetChanged();
        mainActivity.myAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_dropdown_item_1line, mainActivity.item);
        mainActivity.myAutoComplete.setAdapter(mainActivity.myAdapter);

    }

    @Override
    public void afterTextChanged(Editable editable) {
        //TODO Auto-generated method
    }
}
