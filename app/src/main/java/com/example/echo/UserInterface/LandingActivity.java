package com.example.echo.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.echo.R;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Button eventsBtn = findViewById(R.id.eventsBtn);
        Button chatbotBtn = findViewById(R.id.chatbotBtn);
        Button candidatesBtn = findViewById(R.id.candidatesBtn);

        eventsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, EventsActivity.class);
            startActivity(intent);
        });

        chatbotBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Chat.class);
            startActivity(intent);
        });

        candidatesBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, JobsActivity.class);
            startActivity(intent);
        });

    }
}