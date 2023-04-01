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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@SuppressLint("Range")
public class CandidateDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "candidate.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "candidate";

    // column names
    private static final String ID_COL = "id";
    private static final String NAME = "name";
    private static final String GENDER = "gender";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String JOB = "job";
    private static final String LOCATION = "location";

    public CandidateDB(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    // DB init
    @Override
    public void onCreate(SQLiteDatabase db) {
        // query for user table
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT,"
                + GENDER + " TEXT,"
                + EMAIL + " TEXT,"
                + PHONE + " TEXT,"
                + JOB + " TEXT,"
                + LOCATION + " TEXT)";

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
        List<Candidate> data = Util.candiateDataToList(assetManager);
        ContentValues values = new ContentValues();

        for (int i=0; i<data.size();i++){
            values.put(NAME, data.get(i).name);
            values.put(GENDER, data.get(i).gender);
            values.put(EMAIL, data.get(i).email);
            values.put(PHONE, data.get(i).phone);
            values.put(JOB, data.get(i).potential_job);
            values.put(LOCATION, data.get(i).location);

            db.insert(TABLE_NAME, null, values);
        }

        db.close();

        printUserDB();
    }

    // retrieves every row from DB and stores it in a hashmap
    // to use: call the method wherever you need and use '.get(ID_YOU_WANT)'
    //         then you can call the get methods from the model
    public HashMap<Integer, Candidate> getUserInfo() {
        HashMap<Integer, Candidate> temp = new HashMap<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor csr = db.query(TABLE_NAME, null, null, null, null, null, null);

        while (csr.moveToNext()) {
            Candidate candidate = new Candidate();

            candidate.name = csr.getString(csr.getColumnIndex(NAME));
            candidate.gender = csr.getString(csr.getColumnIndex(GENDER));
            candidate.email = csr.getString(csr.getColumnIndex(EMAIL));
            candidate.phone = csr.getString(csr.getColumnIndex(PHONE));
            candidate.potential_job = csr.getString(csr.getColumnIndex(JOB));
            candidate.location = csr.getString(csr.getColumnIndex(LOCATION));


            temp.put(csr.getInt(csr.getColumnIndex(ID_COL)), candidate);
        }
        csr.close();

        return temp;
    }
    public void printUserDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor csr = db.query(TABLE_NAME, null, null, null,null,null,null);

        while (csr.moveToNext()) {
            Candidate candidate = new Candidate();

            candidate.name = csr.getString(csr.getColumnIndex(NAME));
            candidate.gender = csr.getString(csr.getColumnIndex(GENDER));
            candidate.email = csr.getString(csr.getColumnIndex(EMAIL));
            candidate.phone = csr.getString(csr.getColumnIndex(PHONE));
            candidate.potential_job = csr.getString(csr.getColumnIndex(JOB));
            candidate.location = csr.getString(csr.getColumnIndex(LOCATION));

            Log.i("CANDIDATE-DB-INFO", candidate.toString());
        }
        csr.close();
    }
}