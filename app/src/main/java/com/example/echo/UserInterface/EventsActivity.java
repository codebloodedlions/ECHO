package com.example.echo.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.example.echo.R;
import com.example.echo.api.EventGrabber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class EventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        List<Object> threadedDataList = Collections.synchronizedList(new ArrayList<Object>());

        Thread test = new Thread(() -> {
            AssetManager assetManager = getAssets();
            String[] temp = EventGrabber.getEventData(assetManager).get(2);

            threadedDataList.addAll(Arrays.asList(temp));

            Log.i("PARCEL", threadedDataList.toString());
        });
        test.start();
    }
}