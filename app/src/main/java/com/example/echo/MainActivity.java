package com.example.echo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;

import com.example.echo.db.CandidateDB;
import com.example.echo.db.EventDB;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    // set to true to create DB
    boolean POPULATE_DB = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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