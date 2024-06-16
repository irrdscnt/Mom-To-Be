package com.example.momtobe.ui.calendar;

import static android.app.PendingIntent.getActivity;
import static com.example.momtobe.ui.calendar.CalendarUtils.daysInWeekArray;
import static com.example.momtobe.ui.calendar.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.momtobe.R;
import com.example.momtobe.remote_data.Api;
import com.example.momtobe.remote_data.RetrofitClient;
import com.example.momtobe.ui.calendar.CalendarAdapter;
import com.example.momtobe.ui.calendar.CalendarUtils;
import com.example.momtobe.ui.calendar.Event;
import com.example.momtobe.ui.calendar.EventAdapter;
import com.example.momtobe.ui.calendar.EventEditActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();
        Button buttonBack = findViewById(R.id.buttonBack);
        api = RetrofitClient.getInstance().getApi();
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getEventsForSelectedDate();

    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
        getEventsForSelectedDate();


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }
    private void getEventsForSelectedDate() {
        String date = CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        Call<List<Event>> call = api.getEventsByDate(date);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                Log.d("WeekViewActivity", "Response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    List<Event> events = response.body();
                    Log.d("WeekViewActivity", "Events received: " + events.size());
                    EventAdapter adapter = new EventAdapter(WeekViewActivity.this, events);
                    eventListView.setAdapter(adapter);  // Set adapter with updated events
                } else {
                    Log.e("WeekViewActivity", "Failed to get events. Response code: " + response.code());
                    Toast.makeText(WeekViewActivity.this, "No events found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e("WeekViewActivity", "Error fetching events: " + t.getMessage());
                Toast.makeText(WeekViewActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}