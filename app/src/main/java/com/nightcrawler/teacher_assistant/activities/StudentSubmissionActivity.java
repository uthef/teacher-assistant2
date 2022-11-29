package com.nightcrawler.teacher_assistant.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputLayout;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.database.Group;
import com.nightcrawler.teacher_assistant.database.Student;
import com.nightcrawler.teacher_assistant.viewmodels.StudentSubmissionViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class StudentSubmissionActivity extends AppCompatActivity {
    private StudentSubmissionViewModel viewModel;
    private Group group;
    private TextInputLayout firstNameInput, lastNameInput, middleNameInput, variantInput, kpInput;
    private RadioGroup subgroupRadio;
    private boolean editMode = false;
    private Student student;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_submission);

        viewModel = new ViewModelProvider(this).get(StudentSubmissionViewModel.class);

        Intent intent = getIntent();
        TextView textView = findViewById(R.id.group_label);

        firstNameInput = findViewById(R.id.name_input);
        middleNameInput = findViewById(R.id.middle_name_input);
        lastNameInput = findViewById(R.id.last_name_input);
        variantInput = findViewById(R.id.variant_input);
        kpInput = findViewById(R.id.kp_input);
        subgroupRadio = findViewById(R.id.subgroup_radio);

        if (intent.hasExtra("student")) {
            student = (Student) intent.getSerializableExtra("student");
            position = intent.getIntExtra("position", -1);

            setTitle(getString(R.string.edit_student_title));
            editMode = true;

            Objects.requireNonNull(firstNameInput.getEditText()).setText(student.firstName);
            Objects.requireNonNull(middleNameInput.getEditText()).setText(student.middleName);
            Objects.requireNonNull(lastNameInput.getEditText()).setText(student.lastName);
            Objects.requireNonNull(kpInput.getEditText()).setText(String.valueOf(student.kp));
            Objects.requireNonNull(variantInput.getEditText()).setText(String.valueOf(student.variant));
            RadioButton rb = (RadioButton) subgroupRadio.getChildAt(student.subgroup.number - 1);
            rb.setChecked(true);
        }
        else {
            setTitle(getString(R.string.add_student_title));
        }

        group = (Group) intent.getSerializableExtra("group");
        textView.setText(String.format("%s %s", getString(R.string.group), group.name));

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.student_action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if (item.getItemId() == R.id.save_student_item) {
            Student student = null;
            Intent intent = new Intent();

            if (editMode) {
                intent.putExtra("editMode", true);
                student = viewModel.validateFields(group.getId().getIdValue(),
                        firstNameInput,
                        middleNameInput,
                        lastNameInput,
                        subgroupRadio,
                        variantInput,
                        kpInput, this.student);
            }
            else {
                student = viewModel.validateFields(group.getId().getIdValue(),
                        firstNameInput,
                        middleNameInput,
                        lastNameInput,
                        subgroupRadio,
                        variantInput,
                        kpInput, null);
            }

            if (student != null) {
                intent.putExtra("student", student);
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                onBackPressed();
            }
        }

        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        Objects.requireNonNull(lastNameInput.getEditText()).setText(state.getString("lastName"));
        Objects.requireNonNull(firstNameInput.getEditText()).setText(state.getString("firstName"));
        Objects.requireNonNull(middleNameInput.getEditText()).setText(state.getString("middleName"));
        Objects.requireNonNull(variantInput.getEditText()).setText(state.getString("variant"));
        Objects.requireNonNull(kpInput.getEditText()).setText(state.getString("kp"));
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putString("lastName", Objects.requireNonNull(lastNameInput.getEditText()).getText().toString());
        outState.putString("firstName", Objects.requireNonNull(firstNameInput.getEditText()).getText().toString());
        outState.putString("middleName", Objects.requireNonNull(middleNameInput.getEditText()).getText().toString());
        outState.putString("variant", Objects.requireNonNull(variantInput.getEditText()).getText().toString());
        outState.putString("kp", Objects.requireNonNull(kpInput.getEditText()).getText().toString());
        super.onSaveInstanceState(outState);
    }
}