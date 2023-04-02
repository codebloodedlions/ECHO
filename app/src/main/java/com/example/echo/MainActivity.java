package com.example.echo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.example.echo.db.CandidateDB;
import com.example.echo.db.EventDB;
import com.example.echo.userInterface.Chat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    // set to true to create DB
    boolean POPULATE_DB = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);

        try {
            AssetManager assetManager = getAssets();
            CandidateDB candidateDB = new CandidateDB(this);
            EventDB eventDB = new EventDB(this);
            if (POPULATE_DB) {
                candidateDB.populateDB(assetManager);
                eventDB.populateDB(assetManager);

            }
        }catch (IOException e){}
    }
}