package com.nightcrawler.teacher_assistant.database;

import java.util.List;

public interface Database {
    List<Group> listGroups();
    void addGroup(Group group);
    void removeGroup(Group group);
    void updateGroup(Group group);
    boolean hasGroupNamed(String name);
    List<Student> listStudents(Object id);
    void updateStudent(Student student);
    void addStudent(Student student);
    void removeStudent(Student student);
}
