package com.example.echo.db.model;

import java.util.Date;

public class Event {
    public String event_name;
    public String event_date;
    public String event_location;
    public String event_description;
    public String event_website;
    public EventContact event_contact;

    @Override
    public String toString() {
        return "Event{" +
                "event_name='" + event_name + '\'' +
                ", event_date=" + event_date +
                ", event_location='" + event_location + '\'' +
                ", event_description='" + event_description + '\'' +
                ", event_website='" + event_website + '\'' +
                ", event_contact=" + event_contact +
                '}';
    }
}
