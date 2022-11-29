package com.nightcrawler.teacher_assistant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.adapters.ScheduleAdapter;
import com.nightcrawler.teacher_assistant.database.Database;
import com.nightcrawler.teacher_assistant.database.Lesson;
import com.nightcrawler.teacher_assistant.database.LocalDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.SimpleFormatter;

public class ScheduleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        RecyclerView scheduleView = findViewById(R.id.schedule_view);
        scheduleView.setLayoutManager(new LinearLayoutManager(this));
        ScheduleAdapter adapter = new ScheduleAdapter(LocalDatabase.getInstance().listLessons());
        scheduleView.setAdapter(adapter);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}