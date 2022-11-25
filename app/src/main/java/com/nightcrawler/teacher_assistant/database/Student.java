package com.nightcrawler.teacher_assistant.database;

import java.io.Serializable;

public class Student implements Serializable {
    public String firstName, middleName, lastName;
    public Subgroup subgroup;
    public int variant, kp;

    private Student() {}

    public Student(String firstName, String middleName, String lastName, Subgroup subgroup,
                   int variant, int kp) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.subgroup = subgroup;
        this.variant = variant;
        this.kp = kp;
    }
}
