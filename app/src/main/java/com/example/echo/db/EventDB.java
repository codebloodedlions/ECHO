package com.example.echo.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.echo.Util;
import com.example.echo.db.model.Candidate;
import com.example.echo.db.model.Event;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@SuppressLint("Range")
public class EventDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "event.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "candidate";

    // column names
    private static final String ID_COL = "id";
    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final String LOCATION = "location";
    private static final String DESCRIPTION = "description";
    private static final String URL = "url";

    public EventDB(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    // DB init
    @Override
    public void onCreate(SQLiteDatabase db) {
        // query for user table
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT,"
                + DATE + " TEXT,"
                + LOCATION + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + URL + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //////////////////////////////////////////////////////////////////////
    // CALLABLES
    //////////////////////////////////////////////////////////////////////

    //populate database -- run once then comment out
    public void populateDB(AssetManager assetManager) throws IOException {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Event> data = Util.eventDataToList(assetManager);
        ContentValues values = new ContentValues();

        for (int i=0; i<data.size();i++){
            values.put(NAME, data.get(i).event_name);
            values.put(DATE, data.get(i).event_date);
            values.put(LOCATION, data.get(i).event_location);
            values.put(DESCRIPTION, data.get(i).event_description);
            values.put(URL, data.get(i).event_website);

            db.insert(TABLE_NAME, null, values);
        }

        db.close();

        printUserDB();
    }

    // retrieves every row from DB and stores it in a hashmap
    // to use: call the method wherever you need and use '.get(ID_YOU_WANT)'
    //         then you can call the get methods from the model
    public HashMap<Integer, Event> getUserInfo() {
        HashMap<Integer, Event> temp = new HashMap<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor csr = db.query(TABLE_NAME, null, null, null, null, null, null);

        while (csr.moveToNext()) {
            Event event = new Event();

            event.event_name = csr.getString(csr.getColumnIndex(NAME));
            event.event_date = csr.getString(csr.getColumnIndex(DATE));
            event.event_location = csr.getString(csr.getColumnIndex(LOCATION));
            event.event_description = csr.getString(csr.getColumnIndex(DESCRIPTION));

            temp.put(csr.getInt(csr.getColumnIndex(ID_COL)), event);
        }
        csr.close();

        return temp;
    }
    public void printUserDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor csr = db.query(TABLE_NAME, null, null, null,null,null,null);

        while (csr.moveToNext()) {
            Event event = new Event();

            event.event_name = csr.getString(csr.getColumnIndex(NAME));
            event.event_date = csr.getString(csr.getColumnIndex(DATE));
            event.event_location = csr.getString(csr.getColumnIndex(LOCATION));
            event.event_description = csr.getString(csr.getColumnIndex(DESCRIPTION));

            Log.i("EVENT-DB-INFO", event.toString());
        }
        csr.close();
    }
}
