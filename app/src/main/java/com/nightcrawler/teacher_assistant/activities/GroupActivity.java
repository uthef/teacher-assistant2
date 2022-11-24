package com.nightcrawler.teacher_assistant.activities;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.viewmodels.GroupActivityViewModel;

import java.util.Objects;

public class GroupActivity extends AppCompatActivity {
    private GroupActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(this).get(GroupActivityViewModel.class);

        RecyclerView groupList = findViewById(R.id.group_list);
        FloatingActionButton addGroupButton = findViewById(R.id.add_group_button);
        addGroupButton.setOnClickListener((v) -> viewModel.addGroup());

        groupList.setLayoutManager(new LinearLayoutManager(this));
        groupList.setAdapter(viewModel.getAdapter());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}