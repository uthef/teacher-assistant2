package com.nightcrawler.teacher_assistant.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.database.LocalDatabase;

import java.io.File;

public class StartViewModel extends AndroidViewModel {
    public StartViewModel(Application application) {
        super(application);
        LocalDatabase.initialize(new File(application.getFilesDir(), "main.db"));
        LocalDatabase.getInstance().defaultLessonDurations =
                application.getResources().getStringArray(R.array.lesson_durations);
    }
}
