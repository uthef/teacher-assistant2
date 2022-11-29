package com.nightcrawler.teacher_assistant.activities;

import android.app.AlertDialog;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.database.Group;
import com.nightcrawler.teacher_assistant.viewmodels.GroupViewModel;

import java.util.Objects;

public class GroupActivity extends AppCompatActivity {
    private GroupViewModel viewModel;

    private RecyclerView groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        viewModel.groupEditListener = this::showEditDialog;
        viewModel.groupSelectionListener = this::goToStudentActivity;

        groupList = findViewById(R.id.group_list);
        FloatingActionButton addGroupButton = findViewById(R.id.add_group_button);
        addGroupButton.setOnClickListener((v) -> showInsertionDialog());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        groupList.setLayoutManager(layoutManager);
        groupList.setAdapter(viewModel.getAdapter());

        viewModel.emptyLabelState.observe(this,
                (value) -> findViewById(R.id.empty_group_list_info).setVisibility(value));
    }

    private void showEditDialog(Group group) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(GroupActivity.this);
        View view = getLayoutInflater().inflate(R.layout.edit_group_layout, null);

        TextInputLayout groupInputLayout = view.findViewById(R.id.group_input_layout);
        EditText editText = Objects.requireNonNull(groupInputLayout.getEditText());
        editText.setText(group.name);

        dialogBuilder.setTitle(getString(R.string.edit_title));
        dialogBuilder.setView(view);

        dialogBuilder.setNegativeButton(
                getString(R.string.dialog_cancel), (dialogInterface, i) -> {});

        dialogBuilder.setPositiveButton(
                getString(R.string.dialog_ok), ((dialogInterface, i) -> {}));

        AlertDialog dialog = dialogBuilder.create();

        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((v) -> {
            String previousName = group.name;
            group.name = editText.getText().toString();
            if (viewModel.updateGroup(group, editText, previousName, groupList.getLayoutManager()))
                dialog.dismiss();
        });
    }

    private void showInsertionDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(GroupActivity.this);
        View view = getLayoutInflater().inflate(R.layout.edit_group_layout, null);

        TextInputLayout groupInputLayout = view.findViewById(R.id.group_input_layout);
        EditText editText = Objects.requireNonNull(groupInputLayout.getEditText());

        dialogBuilder.setTitle(getString(R.string.add_group_title));
        dialogBuilder.setView(view);

        dialogBuilder.setNegativeButton(
                getString(R.string.dialog_cancel), (dialogInterface, i) -> {});

        dialogBuilder.setPositiveButton(
                getString(R.string.dialog_ok), ((dialogInterface, i) -> {}));

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((v) -> {
            Group group = new Group(editText.getText().toString());
            if (viewModel.addGroup(group, editText, groupList.getLayoutManager()))
                dialog.dismiss();
        });
    }

    private void goToStudentActivity(Group group) {
        Intent intent = new Intent(this, StudentActivity.class);
        intent.putExtra("group", group);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}