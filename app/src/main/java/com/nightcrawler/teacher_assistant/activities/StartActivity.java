package com.nightcrawler.teacher_assistant.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.viewmodels.StartActivityViewModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class StartActivity extends AppCompatActivity {

    StartActivityViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        viewModel = new ViewModelProvider(this).get(StartActivityViewModel.class);

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.export_groups_item) {
            PDDocument document = new PDDocument();
            document.addPage(new PDPage());

            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(dir, "example.txt");

            try {
                document.save(file);
                Toast.makeText(this, "KAVO?", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}