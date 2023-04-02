package com.example.echo.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.echo.R;
import com.example.echo.db.model.Event;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    //final View.OnClickListener onClickListener = new MyOnClickListener();
    private Context context;
    private ArrayList<Event> eventArrayList;

    private RecyclerView eventsRV;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventNameTV);
            eventDescription = itemView.findViewById(R.id.eventDescriptionTV);
        }
    }

    public EventsAdapter(Context context, ArrayList<Event> eventArrayList, RecyclerView eventsRV) {
        this.context = context;
        this.eventArrayList = eventArrayList;
        this.eventsRV = eventsRV;
    }

    @NonNull
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_item, parent, false);
        //view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventArrayList.get(position);
        holder.eventName.setText(event.event_name);
        holder.eventDescription.setText(event.event_description);
    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }
}
