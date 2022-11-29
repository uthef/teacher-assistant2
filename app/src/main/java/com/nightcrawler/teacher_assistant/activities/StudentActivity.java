package com.nightcrawler.teacher_assistant.activities;

import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        findViewById(R.id.add_student_button).setOnClickListener((v) -> launchCreationActivity());

        RecyclerView studentList = findViewById(R.id.student_list);
        studentList.setLayoutManager(new LinearLayoutManager(this));
        studentList.setAdapter(viewModel.getAdapter());
        viewModel.getAdapter().editListener = this::launchEditActivity;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void launchCreationActivity() {
        Intent intent = new Intent(this, StudentSubmissionActivity.class);
        intent.putExtra("group", group);
        editorLauncher.launch(intent);
    }

    private void launchEditActivity(Student student, int position) {
        Intent intent = new Intent(this, StudentSubmissionActivity.class);

        Bundle bundle = new Bundle();
        intent.putExtra("student", student);
        intent.putExtra("position", position);
        intent.putExtra("group", group);

        editorLauncher.launch(intent);
    }

    private void onEditorResult(ActivityResult result) {
        viewModel.processEditorResult(result);
    }
}