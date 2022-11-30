package com.nightcrawler.teacher_assistant.listeners;

import com.nightcrawler.teacher_assistant.database.Lesson;

import java.util.Date;

public interface DatePickListener {
    void onDatePicked(int position, Lesson lesson, boolean isStartTime);
}
