package com.nightcrawler.teacher_assistant.database;

import java.io.Serializable;

public class Student implements Serializable {
    public String firstName, middleName, lastName;
    public Subgroup subgroup;

    private Student() {}

    public Student(String firstName, String middleName, String lastName, Subgroup subgroup) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.subgroup = subgroup;
    }
}
