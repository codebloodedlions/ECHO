package com.example.echo.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.echo.R;
import com.example.echo.Util;
import com.example.echo.db.model.Candidate;
import com.example.echo.db.model.Jobs;

import java.io.IOException;
import java.util.List;

public class JobsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        TextView body = findViewById(R.id.articleBodyNull);

        body.setOnClickListener(view -> {
            Intent intent = new Intent(this, JobDetail.class);
            startActivity(intent);
        });

        AssetManager assetManager = getAssets();

        List<Candidate> candidateData;
        List<Jobs> jobsData;

        try {
            candidateData = Util.candiateDataToList(assetManager);
            jobsData = Util.jobDatatoList(assetManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Log.i("job-info", jobsData.toString());
        Log.i("job-info", candidateData.toString());

    }
}