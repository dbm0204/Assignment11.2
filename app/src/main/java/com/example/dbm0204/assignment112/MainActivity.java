package com.example.dbm0204.assignment112;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    /**
     * Change to type CustomAutoCompleteView instead of AutoCompleteTextView
     * since we are extending to customize the view and disable filter
     * The same with the XML view, type will be CustomAutoCompleteView
     *
     */
    CustomAutoCompleteView myAutoComplete;
    ArrayAdapter<String> myAdapter;
    DatabaseHandler databaseHandler;
    String[] item = new String[]{"Search..."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try
        {
            // instantiate database handler
            databaseHandler = new DatabaseHandler(MainActivity.this);

            // put sample data to database
            insertSampleData();

            // autocompletetextview is in activity_main.xml
            myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);

            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));

            // set our adapter
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            myAutoComplete.setAdapter(myAdapter);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void insertSampleData(){

        // CREATE
        databaseHandler.create( new MyObject("Papua New Guinea") );
        databaseHandler.create( new MyObject("COFFEE-1K") );
        databaseHandler.create( new MyObject("coffee raw") );
        databaseHandler.create( new MyObject("authentic COFFEE") );
        databaseHandler.create( new MyObject("k12-coffee") );
        databaseHandler.create( new MyObject("view coffee") );
        databaseHandler.create( new MyObject("Indian-coffee-two") );

    }
    // this function is used in CustomAutoCompleteTextChangedListener.java
    public String[] getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        List<MyObject> products = databaseHandler.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (MyObject record : products) {

            item[x] = record.objectName;
            x++;
        }

        return item;
    }
}
