package com.example.momtobe.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.momtobe.R;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    private LayoutInflater inflater;

    public EventAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0, events);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.event_cell, parent, false);
        }

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        if (event != null) {
            String eventTitle = event.getName() + " " + event.getTime();
            eventCellTV.setText(eventTitle);
        }

        return convertView;
    }

    // Method to format time from string to desired format
    private String formatTime(String timeString) {
        // Assuming timeString format is "HH:mm:ss" based on previous discussion
        LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
        return time.format(DateTimeFormatter.ofPattern("hh:mm a")); // Example format: 12-hour with AM/PM
    }
}