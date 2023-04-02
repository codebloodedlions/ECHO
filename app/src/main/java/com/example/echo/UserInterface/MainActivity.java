package com.example.echo.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.echo.R;
import com.example.echo.api.EventApiConn;
import com.example.echo.api.EventGrabber;
import com.example.echo.db.CandidateDB;
import com.example.echo.db.EventDB;
import com.example.echo.db.UserDB;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();

        // init DB if true
        boolean POPULATE_DB = true;
        if (POPULATE_DB){
            //AssetManager assetManager = getAssets();
            UserDB userDB = new UserDB(this);
            EventDB eventDB = new EventDB(this);
            CandidateDB candidateDB = new CandidateDB(this);
            try {
                userDB.populateDB();
                eventDB.populateDB(assetManager);
                candidateDB.populateDB(assetManager);
            } catch (NoSuchAlgorithmException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        Button loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, LandingActivity.class);
            startActivity(intent);
        });
    }
}