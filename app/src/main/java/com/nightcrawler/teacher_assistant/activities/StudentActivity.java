package com.nightcrawler.teacher_assistant.activities;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.database.Group;
import com.nightcrawler.teacher_assistant.database.Student;
import com.nightcrawler.teacher_assistant.viewmodels.StudentViewModel;

import java.util.Objects;

public class StudentActivity extends AppCompatActivity {
    private StudentViewModel viewModel;
    private ActivityResultLauncher<Intent> editorLauncher;
    private Group group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        editorLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::onEditorResult
        );

        viewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        group = (Group) getIntent().getSerializableExtra("group");
        viewModel.initializeGroup(group);
        setTitle(String.format("%s %s", getString(R.string.group), group.name));
        findViewById(R.id.add_student_button).setOnClickListener(this::onFloatingButtonClick);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void onFloatingButtonClick(View view) {
        Intent intent = new Intent(this, StudentSubmissionActivity.class);
        intent.putExtra("group", group);
        editorLauncher.launch(intent);
    }

    private void onEditorResult(ActivityResult result) {
        Intent intent = result.getData();

        if (intent != null && result.getResultCode() == RESULT_OK && intent.hasExtra("student")) {
            viewModel.addStudent((Student) intent.getSerializableExtra("student"));
        }
    }
}