package com.example.echo.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.echo.R;

import java.util.ArrayList;
import com.example.echo.UserInterface.Message;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Message> messageList;

    public MessageAdapter(Context context, ArrayList<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        //inflate layout based on receive or sent
        switch (viewType) {
            case 0:
                //inflate sent layout
                view = LayoutInflater.from(context).inflate(R.layout.send, parent, false);
                return new SentViewHolder(view);

            case 1:
                //inflate receive layout
                view = LayoutInflater.from(context).inflate(R.layout.receive, parent, false);
                return new ReceiveViewHolder(view);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //the current view holder is sent view holder
        Message currentMessage = messageList.get(position);

        switch (currentMessage.getSenderId()) {
            case "user":
                //for sent view holder
                ((SentViewHolder)holder).sentMessage.setText(currentMessage.getMessage());
                break;

            case "bot":
                //for the receive view holder
                ((ReceiveViewHolder)holder).receiveMessage.setText(currentMessage.getMessage());
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {

        switch(messageList.get(position).getSenderId()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class SentViewHolder extends RecyclerView.ViewHolder {
        private TextView sentMessage;

        public SentViewHolder(View itemView) {
            super(itemView);
            sentMessage = itemView.findViewById(R.id.sentTxt);
        }
    }

    public static class ReceiveViewHolder extends RecyclerView.ViewHolder {
        private TextView receiveMessage;

        public ReceiveViewHolder(View itemView) {
            super(itemView);
            receiveMessage = itemView.findViewById(R.id.receiveTxt);
        }
    }
}
