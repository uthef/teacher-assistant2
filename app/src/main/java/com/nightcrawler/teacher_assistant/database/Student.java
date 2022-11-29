package com.nightcrawler.teacher_assistant.database;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.io.Serializable;

@Indices({
        @Index(value = "groupId", type = IndexType.NonUnique)
})
public class Student implements Serializable {
    @Id
    private NitriteId id;
    public long groupId;
    public String firstName, middleName, lastName;
    public Subgroup subgroup;
    public int variant, kp;

    private Student() {}

    public Student(long groupId, String firstName, String middleName, String lastName,
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
    public String getFullName() {
        return String.format("%s %s %s", lastName, firstName, middleName);
    }
    public String getInfo(String subgroupLabel, String variantLabel, String kpLabel) {
        return String.format("%s %s, %s%s, %s%s",
                subgroupLabel, subgroup.number, variantLabel, variant, kpLabel, kp);
    }
}
