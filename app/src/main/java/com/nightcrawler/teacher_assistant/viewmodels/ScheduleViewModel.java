package com.nightcrawler.teacher_assistant.viewmodels;

import android.app.Application;
import android.app.TimePickerDialog;
import androidx.lifecycle.AndroidViewModel;
import com.nightcrawler.teacher_assistant.adapters.ScheduleAdapter;
import com.nightcrawler.teacher_assistant.database.Lesson;
import com.nightcrawler.teacher_assistant.database.LocalDatabase;

import java.util.*;
import java.time.Instant;

public class ScheduleViewModel extends AndroidViewModel {
    private final ScheduleAdapter adapter;
    private final List<Lesson> lessons;
    public ScheduleViewModel(Application application) {
        super(application);

        lessons = LocalDatabase.getInstance().listLessons();
        adapter = new ScheduleAdapter(lessons);
    }
    public TimePickerDialog.OnTimeSetListener getTimeSetListener(int position, Lesson lesson, boolean isStartTime) {
        return (timePicker, i, i1) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.HOUR_OF_DAY, i);
            calendar.set(Calendar.MINUTE, i1);

            if (isStartTime) lesson.startTime = calendar.getTime();
            else lesson.endTime = calendar.getTime();

            LocalDatabase.getInstance().updateLesson(lesson);
            adapter.notifyItemChanged(position);
        };
    }

    public ScheduleAdapter getAdapter() {
        return adapter;
    }
}
