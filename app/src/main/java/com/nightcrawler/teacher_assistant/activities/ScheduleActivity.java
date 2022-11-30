package com.nightcrawler.teacher_assistant.activities;

import android.app.TimePickerDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.viewmodels.ScheduleViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ScheduleActivity extends AppCompatActivity {
    private ScheduleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        RecyclerView scheduleView = findViewById(R.id.schedule_view);
        scheduleView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        viewModel.getAdapter().datePickListener =
            (position, lesson, isStartTime) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(isStartTime ? lesson.startTime : lesson.endTime);

                String title = String.format("%s %s",
                        isStartTime ? getString(R.string.lesson_start_time) : getString(R.string.lesson_end_time),
                        position + 1);

                showTimePickerDialog(viewModel.getTimeSetListener(position, lesson, isStartTime),
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), title);
            };

        scheduleView.setAdapter(viewModel.getAdapter());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showTimePickerDialog(TimePickerDialog.OnTimeSetListener listener,
                                      int hourOfDay, int minute, String title) {
        TimePickerDialog datePickerDialog =
                new TimePickerDialog(this, listener, hourOfDay, minute, true);
        datePickerDialog.setTitle(title);
        datePickerDialog.show();
    }
}