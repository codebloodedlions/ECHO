package com.example.echo.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.echo.encyption.MD5;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

@SuppressLint("Range")
public class UserDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "user.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "user";

    // column names
    private static final String ID_COL = "id";
    private static final String NAME_COL = "username";
    private static final String PASS_COL = "password";

    public UserDB(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    // DB init
    @Override
    public void onCreate(SQLiteDatabase db) {
        // query for user table
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + PASS_COL + " TEXT)";

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

    // call this to add new user, must populate all args
    public void addNewUser(String username, String password) throws NoSuchAlgorithmException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String encryptedPass = MD5.generateMD5(password);

        values.put(NAME_COL, username);
        values.put(PASS_COL, encryptedPass);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    // retrieves every row from DB and stores it in a array
    // [0] = name, [1] = password
    public String[] getUserInfo(){
        String[] userData = new String[2];
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor csr = db.query(TABLE_NAME, null, null, null,null,null,null);

        while (csr.moveToNext()){
            userData[0] = csr.getString(csr.getColumnIndex(NAME_COL));
            userData[1] = csr.getString(csr.getColumnIndex(PASS_COL));
        }
        csr.close();

        return userData;
    }

    // populate db
    public void populateDB() throws NoSuchAlgorithmException {
        Log.i("USER_DB", " >> Populating DB");

        addNewUser("deonna.rocks",
                MD5.generateMD5("langston24"));

        Log.i("USER_DB", " >> DB Populated");
        printUserDB();
    }

    public void printUserDB(){
        Log.i("[USER-DB-INF]", Arrays.toString(getUserInfo()));
    }

    // call this to check password
    public boolean checkPassword(String password) throws NoSuchAlgorithmException {
        boolean match = false;

        if (MD5.generateMD5(password).equals(getUserInfo()[1])) match = true;

        return match;
    }
}
