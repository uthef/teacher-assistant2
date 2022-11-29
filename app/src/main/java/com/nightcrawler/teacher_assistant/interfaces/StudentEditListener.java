package com.nightcrawler.teacher_assistant.interfaces;

import com.nightcrawler.teacher_assistant.database.Student;

public interface StudentEditListener {
    void onEdit(Student student, int position);
}
