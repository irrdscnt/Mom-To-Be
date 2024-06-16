package com.example.momtobe.ui.calendar;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.momtobe.R;
import com.example.momtobe.remote_data.Api;
import com.example.momtobe.remote_data.RetrofitClient;
import com.google.gson.Gson;

import java.time.LocalTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time)); // Pass the LocalTime object
        api = RetrofitClient.getInstance().getApi();

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        String eventDate = CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        String eventTime = CalendarUtils.formattedTime(time);

        Event newEvent = new Event(eventName, eventDate, eventTime);
        Log.d("EventEditActivity", "Sending event: " + new Gson().toJson(newEvent));

        Call<Void> call = api.saveEvent(newEvent);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EventEditActivity.this, "Event saved", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EventEditActivity.this, "Failed to save event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EventEditActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
