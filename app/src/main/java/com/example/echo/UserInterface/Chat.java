package com.example.echo.UserInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.echo.R;
import com.example.echo.api.Completions;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {

    private RecyclerView messageRV;
    private EditText chatEV;
    private ImageView sendIV;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private MessageAdapter messageAdapter;
    private ArrayList<Message> messageArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        TextView dummy = findViewById(R.id.dummy);

        messageRV = findViewById(R.id.chatRV);
        chatEV = findViewById(R.id.messageBox);
        sendIV = findViewById(R.id.sendIV);
        messageArrayList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messageArrayList);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        messageRV.setLayoutManager(manager);
        messageRV.setAdapter(messageAdapter);

        sendIV.setOnClickListener(view -> {
            if(chatEV.getText().toString().isEmpty()) {
                Toast.makeText(Chat.this, "Please enter a message", Toast.LENGTH_LONG).show();
                return;
            }
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            Toast.makeText(Chat.this, "Thinking...", Toast.LENGTH_LONG).show();

            messageArrayList.add(new Message(chatEV.getText().toString(),USER_KEY));
            messageAdapter.notifyDataSetChanged();


            getResponse(dummy, chatEV.getText().toString());
            chatEV.setText("");
        });
    }

    private void getResponse(TextView dummy, String message) {
        AssetManager assetManager = getAssets();
        String completionType = "text";

        try {
            Completions.addMsgArrResponse(dummy, messageAdapter, messageArrayList, BOT_KEY, assetManager, message, completionType);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
