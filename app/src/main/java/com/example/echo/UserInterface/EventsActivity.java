package com.example.echo.UserInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.echo.R;
import com.example.echo.db.model.Event;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    RecyclerView eventsRV;
    EventsAdapter eventsAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Event> eventsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        eventsRV = findViewById(R.id.eventsRV);
        eventsRV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        eventsRV.setLayoutManager(layoutManager);
        eventsAdapter = new EventsAdapter(this, eventsList, eventsRV);
        eventsRV.setAdapter(eventsAdapter);
    }
}