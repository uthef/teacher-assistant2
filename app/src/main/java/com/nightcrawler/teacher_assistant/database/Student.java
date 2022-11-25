package com.nightcrawler.teacher_assistant.database;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

import java.io.Serializable;

public class Student implements Serializable {
    @Id
    private NitriteId id;
    public NitriteId groupId;

    public String firstName, middleName, lastName;
    public Subgroup subgroup;
    public int variant, kp;

    private Student() {}

    public Student(NitriteId groupId, String firstName, String middleName, String lastName,
                   Subgroup subgroup, int variant, int kp) {
        this.groupId = groupId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.subgroup = subgroup;
        this.variant = variant;
        this.kp = kp;
    }

    public NitriteId getId() {
        return id;
    }
}
