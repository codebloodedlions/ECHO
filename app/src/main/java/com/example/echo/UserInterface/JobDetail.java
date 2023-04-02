package com.example.echo.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.example.echo.R;
import com.example.echo.Util;
import com.example.echo.db.model.Jobs;

import java.io.IOException;
import java.util.List;

public class JobDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
    }
}