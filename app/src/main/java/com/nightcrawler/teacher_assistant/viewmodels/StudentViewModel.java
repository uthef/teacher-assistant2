package com.nightcrawler.teacher_assistant.viewmodels;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import com.nightcrawler.teacher_assistant.database.Database;
import com.nightcrawler.teacher_assistant.database.Group;
import com.nightcrawler.teacher_assistant.database.LocalDatabase;
import com.nightcrawler.teacher_assistant.database.Student;

import java.util.List;

public class StudentViewModel extends ViewModel {
    public Group group = null;
    private final Database dbInstance;

    public StudentViewModel() {
        dbInstance = LocalDatabase.getInstance();
    }
    public void initializeGroup(Group group) {
        if (this.group == null) {
            this.group = group;
            List<Student> students = dbInstance.listStudents(group.getId());
            StringBuilder builder = new StringBuilder();
            builder.append(group.name);
            for (Student student : students) {
                builder.append("\n");
                builder.append(student.getFullName());
            }
            if (students.size() > 0)
                Log.d("group_list", builder.toString());
        }
    }

    public void addStudent(Student student) {
        dbInstance.addStudent(student);
    }
}
