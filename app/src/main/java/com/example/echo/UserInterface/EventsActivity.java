package com.example.echo.UserInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

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
    ArrayList<Event> eventArrayList = new ArrayList<>();

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


        //eventArrayList.addAll(threadedDataList);
        eventsRV = findViewById(R.id.eventsRV);
        eventsRV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        eventsRV.setLayoutManager(layoutManager);
        eventsAdapter = new EventsAdapter(this, eventArrayList, eventsRV);
        eventsRV.setAdapter(eventsAdapter);


    }
}