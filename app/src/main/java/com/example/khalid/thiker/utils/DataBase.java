package com.example.khalid.thiker.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.khalid.thiker.model.ThikerModel;
import com.example.khalid.thiker.model.Types;
import com.example.khalid.thiker.model.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khalid on 11/12/2017.
 */

public class DataBase extends SQLiteOpenHelper {
    String Athkar = "athkar";
    String saved_Athkar = "saved_athkar";
    String messages = "messages";
    String home_athkar = "home_athkar";
    String wall = "wall";
    String types = "types";

    public DataBase(Context context) {
        super(context, "thiker", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE " + Athkar + " (id TEXT PRIMARY KEY,text TEXT,count TEXT,type TEXT,fadel TEXT)";
        String sql3 = "CREATE TABLE " + saved_Athkar + " (id TEXT PRIMARY KEY,text TEXT,token TEXT)";
        String sql2 = "CREATE TABLE " + messages + " (id TEXT PRIMARY KEY,text TEXT)";
        String sql4 = "CREATE TABLE " + home_athkar + " (id TEXT PRIMARY KEY,text TEXT,token TEXT)";
        String sql5 = "CREATE TABLE " + wall + " (id TEXT PRIMARY KEY,text TEXT,token TEXT)";
        String sql6 = "CREATE TABLE " + types + " (id TEXT PRIMARY KEY,name TEXT,image TEXT)";

        db.execSQL(sql2);
        db.execSQL(sql1);
        db.execSQL(sql3);
        db.execSQL(sql5);
        db.execSQL(sql6);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addThikerPage(String id, String text, String count, String type, String fadel) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("text", text);
        values.put("count", count);
        values.put("type", type);
        values.put("fadel", fadel);


        database.insert(Athkar, null, values);

    }

    public void addThiker(String id, String text, String token) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("text", text);
        values.put("token", token);


        database.insert(saved_Athkar, null, values);

    }

    public void deleteThiker(String id) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete("saved_Athkar", "id=?", new String[]{id});

    }

    public boolean checkThiker(String id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor mCursor = database.rawQuery("SELECT * FROM " + saved_Athkar
                + " WHERE id=?", new String[]{id});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTypes() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor mCursor = database.rawQuery("SELECT * FROM " + types
                , null);
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }

    public void addType(String id, String name, String image) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("image", image);

        database.insert(types, null, values);

    }

    public void addWall(String id, String text, String token) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("text", text);
        values.put("token", token);


        database.insert(wall, null, values);

    }

    public List<Types> getTypes() {
        List<Types> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + types;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to selectedMenuItems
        if (cursor.moveToFirst()) {
            do {
                Types thiker = new Types();
                thiker.setId(cursor.getString(cursor.getColumnIndex("id")));
                thiker.setName(cursor.getString(cursor.getColumnIndex("name")));
                thiker.setImage(cursor.getString(cursor.getColumnIndex("image")));

                contactList.add(thiker);
            } while (cursor.moveToNext());
        }

        // return contact selectedMenuItems
        return contactList;
    }

    public List<Wall> getWall() {
        List<Wall> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + wall;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to selectedMenuItems
        if (cursor.moveToFirst()) {
            do {
                Wall thiker = new Wall();
                thiker.setId(cursor.getString(cursor.getColumnIndex("id")));
                thiker.setText(cursor.getString(cursor.getColumnIndex("text")));
                thiker.setToken(cursor.getString(cursor.getColumnIndex("token")));

                contactList.add(thiker);
            } while (cursor.moveToNext());
        }

        // return contact selectedMenuItems
        return contactList;
    }

    public List<Wall> getSavedAthkar() {
        List<Wall> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + saved_Athkar;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to selectedMenuItems
        if (cursor.moveToFirst()) {
            do {
                Wall thiker = new Wall();
                thiker.setId(cursor.getString(cursor.getColumnIndex("id")));
                thiker.setText(cursor.getString(cursor.getColumnIndex("text")));
                thiker.setToken(cursor.getString(cursor.getColumnIndex("token")));

                contactList.add(thiker);
            } while (cursor.moveToNext());
        }

        // return contact selectedMenuItems
        return contactList;
    }

    public List<ThikerModel> getMessage() {
        List<ThikerModel> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + messages;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to selectedMenuItems
        if (cursor.moveToFirst()) {
            do {
                ThikerModel thiker = new ThikerModel();
                thiker.setId(cursor.getString(cursor.getColumnIndex("id")));
                thiker.setText(cursor.getString(cursor.getColumnIndex("text")));


                contactList.add(thiker);
            } while (cursor.moveToNext());
        }

        // return contact selectedMenuItems
        return contactList;
    }

    public List<ThikerModel> getThiker(String type) {
        List<ThikerModel> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + Athkar + " WHERE type=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{type});

        // looping through all rows and adding to selectedMenuItems
        if (cursor.moveToFirst()) {
            do {
                ThikerModel thiker = new ThikerModel();
                thiker.setId(cursor.getString(cursor.getColumnIndex("id")));
                thiker.setText(cursor.getString(cursor.getColumnIndex("text")));
                thiker.setCount(cursor.getString(cursor.getColumnIndex("count")));
                thiker.setType(cursor.getString(cursor.getColumnIndex("type")));
                thiker.setFadel(cursor.getString(cursor.getColumnIndex("fadel")));

                contactList.add(thiker);
            } while (cursor.moveToNext());
        }

        // return contact selectedMenuItems
        return contactList;
    }
}
