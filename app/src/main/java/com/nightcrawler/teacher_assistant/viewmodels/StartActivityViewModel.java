package com.nightcrawler.teacher_assistant.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import com.nightcrawler.teacher_assistant.database.Database;

import java.io.File;

public class StartActivityViewModel extends AndroidViewModel {
    public StartActivityViewModel(Application application) {
        super(application);
        Database.initialize(new File(application.getFilesDir(), "main.db"));
    }
}
