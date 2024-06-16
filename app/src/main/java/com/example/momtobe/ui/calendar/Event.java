package com.example.momtobe.ui.calendar;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }


    @SerializedName("name")
    private String name;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    public Event(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Event() {
    }

    public static ArrayList<Event> getEventsList() {
        return eventsList;
    }

    public static void setEventsList(ArrayList<Event> eventsList) {
        Event.eventsList = eventsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
