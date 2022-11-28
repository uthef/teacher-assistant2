package com.nightcrawler.teacher_assistant.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.database.Group;

import java.util.Objects;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Group group = (Group) getIntent().getSerializableExtra("group");
        setTitle(String.format("%s %s", getString(R.string.group), group.name));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}