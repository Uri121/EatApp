package com.example.eatapp.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.eatapp.Model.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "shawarma.db";
    private static final int DATABASE_VERSION = 4;

    private SQLiteDatabase db;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creates the sqlite database
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;


        final String Sql_Create_Questions_Table = " CREATE TABLE "+
                ShawarmaDb.ShawarmaTable.TABLE_NAME + " ( " +
                ShawarmaDb.ShawarmaTable._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                ShawarmaDb.ShawarmaTable.BREADTYPE + " TEXT, " +
                ShawarmaDb.ShawarmaTable.NAME + " TEXT, " +
                ShawarmaDb.ShawarmaTable.ADDONES + " TEXT, " +
                ShawarmaDb.ShawarmaTable.PRICE + " INTEGER " +
                ")";

        db.execSQL(Sql_Create_Questions_Table);
    }

    //adding new order to the database
    public boolean AddToCart(String name, int price, ArrayList<String> list, String breadType) throws JSONException {

        StringBuilder builder = new StringBuilder();
        if (list != null)
        {
            for (String details : list) {
                builder.append(details + "\n");
            }
        }
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShawarmaDb.ShawarmaTable.NAME,name);
        contentValues.put(ShawarmaDb.ShawarmaTable.PRICE,price);
        contentValues.put(ShawarmaDb.ShawarmaTable.ADDONES, builder.toString());
        contentValues.put(ShawarmaDb.ShawarmaTable.BREADTYPE, breadType);
        long rs = db.insert(ShawarmaDb.ShawarmaTable.TABLE_NAME,null,contentValues);
        if(rs==-1){
            return false;
        }
        else {
            return true;
        }
    }

    //return the list of orders from sqlite
    public List<Order> GetList() throws JSONException {
        List<Order> OrderList = new ArrayList<>();

        db = getReadableDatabase();

        Cursor cursor= db.rawQuery("SELECT * FROM " + ShawarmaDb.ShawarmaTable.TABLE_NAME,null);

        if (cursor.moveToFirst()){
            do {
                Order order = new Order();
                order.setId(cursor.getString(cursor.getColumnIndex(ShawarmaDb.ShawarmaTable._ID)));
                order.setMeatType(cursor.getString(cursor.getColumnIndex(ShawarmaDb.ShawarmaTable.NAME)));
                order.setPrice(cursor.getInt(cursor.getColumnIndex(ShawarmaDb.ShawarmaTable.PRICE)));
                order.setBreadType(cursor.getString(cursor.getColumnIndex(ShawarmaDb.ShawarmaTable.BREADTYPE)));
                String str = (cursor.getString(cursor.getColumnIndex(ShawarmaDb.ShawarmaTable.ADDONES)));

                List<String> listAddOnes = Arrays.asList(str.split(","));
                order.setAddOnes(listAddOnes);

                OrderList.add(order);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return OrderList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ ShawarmaDb.ShawarmaTable.TABLE_NAME);
        onCreate(db);
    }

    //deletes all the database orders
    public void CleanCart()
    {
        String query = String.format("DELETE FROM "+ ShawarmaDb.ShawarmaTable.TABLE_NAME);
        db.execSQL(query);
    }

    //on swipe deletes the item that was swiped
    public void DeleteItem(String id)
    {
        db.delete(ShawarmaDb.ShawarmaTable.TABLE_NAME, "id="+id,null);
    }



}
