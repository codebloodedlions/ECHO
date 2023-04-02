package com.example.echo.UserInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.echo.R;
import com.example.echo.api.EventGrabber;
import com.example.echo.db.model.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EventsActivity extends AppCompatActivity {

    RecyclerView eventsRV;
    EventsAdapter eventsAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Object> eventArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        ImageView event = findViewById(R.id.image4);

        event.setOnClickListener(view -> {
            Intent intent = new Intent(this, EventDetail.class);
            startActivity(intent);
        });

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