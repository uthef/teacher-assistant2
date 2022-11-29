package com.nightcrawler.teacher_assistant.database;

import java.util.List;

public interface Database {
    List<Group> listGroups();
    void addGroup(Group group);
    void removeGroup(Group group);
    void updateGroup(Group group);
    boolean hasGroupNamed(String name);
    List<Student> listStudents(Object id);
    List<Student> listAllStudents();
    void addStudent(Student student);
}
