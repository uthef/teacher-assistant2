package com.nightcrawler.teacher_assistant.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.database.Lesson;
import com.nightcrawler.teacher_assistant.listeners.DatePickListener;

import java.text.SimpleDateFormat;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private final List<Lesson> lessons;
    public DatePickListener datePickListener;

    public ScheduleAdapter(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_view_layout, parent, false);

        return new ScheduleAdapter.ViewHolder(view, this);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        holder.lessonNumber.setText(String.format("%s", position + 1));
        holder.lessonStartTime.setText(simpleDateFormat.format(lesson.startTime));
        holder.lessonEndTime.setText(simpleDateFormat.format(lesson.endTime));
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView lessonNumber, lessonStartTime, lessonEndTime;
        private ScheduleAdapter adapter;
        public ViewHolder(@NonNull View itemView, ScheduleAdapter adapter) {
            super(itemView);

            this.adapter = adapter;

            lessonNumber = itemView.findViewById(R.id.schedule_lesson_number);
            lessonStartTime = itemView.findViewById(R.id.schedule_start_time);
            lessonEndTime = itemView.findViewById(R.id.schedule_end_time);

            lessonStartTime.setOnClickListener((v) -> {
                Lesson lesson = adapter.lessons.get(getAdapterPosition());
                adapter.datePickListener.onDatePicked(getAdapterPosition(), lesson, true);
            });

            lessonEndTime.setOnClickListener((v) -> {
                Lesson lesson = adapter.lessons.get(getAdapterPosition());
                adapter.datePickListener.onDatePicked(getAdapterPosition(), lesson, false);
            });
        }


    }
}
