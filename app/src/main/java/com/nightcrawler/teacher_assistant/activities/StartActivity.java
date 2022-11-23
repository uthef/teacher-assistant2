package com.nightcrawler.teacher_assistant.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.nightcrawler.teacher_assistant.R;

import java.util.Objects;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Setting action bar icon
        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.drawable.ic_baseline_book_24);

        findViewById(R.id.groups_button).setOnClickListener((v) -> {
            Intent intent = new Intent(StartActivity.this, GroupActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Setting action bar options menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.start_activity_menu, menu);
        return true;
    }
}