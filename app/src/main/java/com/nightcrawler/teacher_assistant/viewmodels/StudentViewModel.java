package com.nightcrawler.teacher_assistant.viewmodels;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.lifecycle.ViewModel;

import com.nightcrawler.teacher_assistant.adapters.StudentAdapter;
import com.nightcrawler.teacher_assistant.database.Database;
import com.nightcrawler.teacher_assistant.database.Group;
import com.nightcrawler.teacher_assistant.database.LocalDatabase;
import com.nightcrawler.teacher_assistant.database.Student;

import java.util.Collections;
import java.util.List;

public class StudentViewModel extends ViewModel {
    public Group group = null;
    private final Database dbInstance;
    private  StudentAdapter adapter;
    private List<Student> students;

    public StudentViewModel() {
        dbInstance = LocalDatabase.getInstance();
    }
    public void initializeGroup(Group group) {
        if (this.group == null) {
            this.group = group;
            students = dbInstance.listStudents(group.getId());
            adapter = new StudentAdapter(students);
            adapter.itemRemovalListener = this::removeStudent;
        }
    }

    public void addStudent(Student student) {
        students.add(student);
        dbInstance.addStudent(student);
        Collections.sort(students, (a, b) -> a.lastName.compareTo(b.lastName));
        adapter.notifyItemInserted(students.indexOf(student));

    }
    public void updateStudent(Student student, int position) {
        dbInstance.updateStudent(student);
        students.set(position, student);
        Collections.sort(students, (a, b) -> a.lastName.compareTo(b.lastName));
        int newIndex = students.indexOf(student);

        adapter.notifyItemChanged(position);
        adapter.notifyItemMoved(position, newIndex);
    }

    public void processEditorResult(ActivityResult result) {
        Intent intent = result.getData();
        if (intent != null && result.getResultCode() == RESULT_OK && intent.hasExtra("student")) {
            Student student = (Student) intent.getSerializableExtra("student");
            int position = intent.getIntExtra("position", -1);
            if (intent.getBooleanExtra("editMode", false)) {
                updateStudent(student, position);
            }
            else addStudent(student);
        }
    }

    private boolean removeStudent(MenuItem item) {
        int id = item.getItemId();
        dbInstance.removeStudent(students.get(id));
        students.remove(id);

        adapter.notifyItemRemoved(id);

        return true;
    }

    public StudentAdapter getAdapter() {
        return adapter;
    }
}
