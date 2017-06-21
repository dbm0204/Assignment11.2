package com.example.dbm0204.assignment112;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by dbm0204 on 6/21/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String TAG ="DatabaseHandler.java";
    public static final int DATABASE_VERSION=4;
    protected static final String DATABASE_NAME="productDb";

    //table details
    public String tableName="locations";
    public String fieldObjectId="id";
    public String fieldObjectName="name";

    //constructor
    public DatabaseHandler(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql ="";
        sql += "CREATE TABLE " + tableName;
        sql += " ( ";
        sql += fieldObjectId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += fieldObjectName + " TEXT ";
        sql += " ) ";
        db.execSQL(sql);
    }

    public boolean create(MyObject myObj) {
        boolean createSuccessful = false;
        if (!checkIfExists(myObj.objectName)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(fieldObjectName, myObj.objectName);
            createSuccessful = db.insert(tableName, null, values) > 0;
            db.close();

            if (createSuccessful) {
                Log.e(TAG, myObj.objectName + "created");
            }
        }
        return createSuccessful;
    }

    public boolean checkIfExists(String objectName) {
        boolean recordExists = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + fieldObjectId + " FROM " + tableName + " WHERE " + fieldObjectName + " = '" + objectName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                recordExists = true;

            }
        }
        cursor.close();
        db.close();
        return recordExists;
    }
    // Read records related to the search term
    public List<MyObject> read(String searchTerm) {

        List<MyObject> recordsList = new ArrayList<MyObject>();

        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldObjectName + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + fieldObjectId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
                String objectName = cursor.getString(cursor.getColumnIndex(fieldObjectName));
                MyObject myObject = new MyObject(objectName);

                // add to list
                recordsList.add(myObject);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return the list of records
        return recordsList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql ="DROP TABLE IF EXISTS"+tableName;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
