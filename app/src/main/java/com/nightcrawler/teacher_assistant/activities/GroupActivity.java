package com.nightcrawler.teacher_assistant.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.nightcrawler.teacher_assistant.R;
import java.util.Objects;

public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}